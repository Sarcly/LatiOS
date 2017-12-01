package latiOS.music;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.jagrosh.jdautilities.commandclient.CommandEvent;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.bandcamp.BandcampAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.http.HttpAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.soundcloud.SoundCloudAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.twitch.TwitchStreamAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.vimeo.VimeoAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.exceptions.PermissionException;

public class AudioUtil {
	public static final int DEFAULT_VOLUME = 35; // (0 - 150, where 100 is default max volume)

	private final AudioPlayerManager playerManager;
	private final Map<String, GuildMusicManager> musicManagers;
	private GuildMusicManager mng;
	private AudioPlayer player;
	private TrackScheduler scheduler;

	public AudioUtil(Guild guild) {
		this.playerManager = new DefaultAudioPlayerManager();
		playerManager.registerSourceManager(new YoutubeAudioSourceManager());
		playerManager.registerSourceManager(new SoundCloudAudioSourceManager());
		playerManager.registerSourceManager(new BandcampAudioSourceManager());
		playerManager.registerSourceManager(new VimeoAudioSourceManager());
		playerManager.registerSourceManager(new TwitchStreamAudioSourceManager());
		playerManager.registerSourceManager(new HttpAudioSourceManager());

		musicManagers = new HashMap<String, GuildMusicManager>();
		mng = getMusicManager(guild);
		player = mng.player;
		scheduler = mng.scheduler;
	}

	public void join(CommandEvent event, boolean defaultChan) {
		if (defaultChan) {
			event.getGuild().getAudioManager().openAudioConnection(event.getGuild().getVoiceChannels().get(0));
			return;
		}
		if (event.getArgs().isEmpty()) {
			if (event.getMember().getVoiceState().getChannel() != null) {
				event.getGuild().getAudioManager().openAudioConnection(event.getMember().getVoiceState().getChannel());
			} else {
				event.replyError("You're not in a voice channel to join!");
			}
		} else {
			VoiceChannel chan = null;
			try {
				chan = event.getGuild().getVoiceChannelById(event.getArgs());
			} catch (NumberFormatException ignored) {
			}

			if (chan == null)
				chan = event.getGuild().getVoiceChannelsByName(event.getArgs(), true).stream().findFirst().orElse(null);
			if (chan == null) {
				event.replyError("Could not find VoiceChannel by name: " + event.getArgs());
			} else {
				event.getGuild().getAudioManager().setSendingHandler(mng.sendHandler);
				try {
					event.getGuild().getAudioManager().openAudioConnection(chan);
				} catch (PermissionException e) {
					if (e.getPermission() == Permission.VOICE_CONNECT) {
						event.replyError("I don't have permission to connect to: " + chan.getName());
					}
				}
			}
		}
	}

	public void play(CommandEvent event) {
		if (event.getArgs().isEmpty()) // It is only the command to start playback (probably after pause)
		{
			if (player.isPaused()) {
				player.setPaused(false);
				event.reply("Playback as been resumed.");
			} else if (player.getPlayingTrack() != null) {
				event.reply("Player is already playing!");
			} else if (scheduler.queue.isEmpty()) {
				event.reply("The current audio queue is empty! Add something to the queue first!");
			}
		} else // Commands has 2 parts, !play and url.
		{
			loadAndPlay(mng, event, event.getArgs(), false);
		}
	}

	public void queue(CommandEvent event) {
		Queue<AudioTrack> queue = scheduler.queue;
		synchronized (queue) {
			if (queue.isEmpty()) {
				event.reply("The queue is currently empty!");
			} else {
				int trackCount = 0;
				long queueLength = 0;
				StringBuilder sb = new StringBuilder();
				sb.append("Current Queue: Entries: ").append(queue.size()).append("\n");
				for (AudioTrack track : queue) {
					queueLength += track.getDuration();
					if (trackCount < 10) {
						sb.append("`[").append(getTimestamp(track.getDuration())).append("]` ");
						sb.append(track.getInfo().title).append("\n");
						trackCount++;
					}
				}
				sb.append("\n").append("Total Queue Time Length: ").append(getTimestamp(queueLength));

				event.reply(sb.toString());
			}
		}
	}
	
	public void np(CommandEvent event) {
		AudioTrack currentTrack = player.getPlayingTrack();
		if (currentTrack != null) {
			String title = currentTrack.getInfo().title;
			String position = getTimestamp(currentTrack.getPosition());
			String duration = getTimestamp(currentTrack.getDuration());

			String nowplaying = String.format("**Playing:** %s\n**Time:** [%s / %s]", title, position, duration);

			event.reply(nowplaying);
		} else
			event.reply("The player is not currently playing anything!");
	}

	public void handle(CommandEvent event) {
		String[] command = event.getMessage().getContent().split(" ", 2);
		Guild guild = event.getGuild();
		GuildMusicManager mng = getMusicManager(guild);
		AudioPlayer player = mng.player;
		TrackScheduler scheduler = mng.scheduler;

		if ("join".equals(command[0])) {
			if (command.length == 1) // No channel name was provided to search for.
			{
				event.getChannel().sendMessage("No channel name was provided to search with to join.").queue();
			} else {
				VoiceChannel chan = null;
				try {
					chan = guild.getVoiceChannelById(command[1]);
				} catch (NumberFormatException ignored) {
				}

				if (chan == null)
					chan = guild.getVoiceChannelsByName(command[1], true).stream().findFirst().orElse(null);
				if (chan == null) {
					event.getChannel().sendMessage("Could not find VoiceChannel by name: " + command[1]).queue();
				} else {
					guild.getAudioManager().setSendingHandler(mng.sendHandler);

					try {
						guild.getAudioManager().openAudioConnection(chan);
					} catch (PermissionException e) {
						if (e.getPermission() == Permission.VOICE_CONNECT) {
							event.getChannel()
									.sendMessage("Yui does not have permission to connect to: " + chan.getName())
									.queue();
						}
					}
				}
			}
		} else if (".leave".equals(command[0])) {
			guild.getAudioManager().setSendingHandler(null);
			guild.getAudioManager().closeAudioConnection();
		} else if (".play".equals(command[0])) {
			if (command.length == 1) // It is only the command to start playback (probably after pause)
			{
				if (player.isPaused()) {
					player.setPaused(false);
					event.getChannel().sendMessage("Playback as been resumed.").queue();
				} else if (player.getPlayingTrack() != null) {
					event.getChannel().sendMessage("Player is already playing!").queue();
				} else if (scheduler.queue.isEmpty()) {
					event.getChannel()
							.sendMessage("The current audio queue is empty! Add something to the queue first!").queue();
				}
			} else // Commands has 2 parts, .play and url.
			{
				loadAndPlay(mng, event, command[1], false);
			}
		} else if (".pplay".equals(command[0]) && command.length == 2) {
			loadAndPlay(mng, event, command[1], true);
		} else if (".skip".equals(command[0])) {
			scheduler.nextTrack();
			event.getChannel().sendMessage("The current track was skipped.").queue();
		} else if (".pause".equals(command[0])) {
			if (player.getPlayingTrack() == null) {
				event.getChannel().sendMessage("Cannot pause or resume player because no track is loaded for playing.")
						.queue();
				return;
			}

			player.setPaused(!player.isPaused());
			if (player.isPaused())
				event.getChannel().sendMessage("The player has been paused.").queue();
			else
				event.getChannel().sendMessage("The player has resumed playing.").queue();
		} else if (".stop".equals(command[0])) {
			scheduler.queue.clear();
			player.stopTrack();
			player.setPaused(false);
			event.getChannel().sendMessage("Playback has been completely stopped and the queue has been cleared.")
					.queue();
		} else if (".volume".equals(command[0])) {
			if (command.length == 1) {
				event.getChannel().sendMessage("Current player volume: **" + player.getVolume() + "**").queue();
			} else {
				try {
					int newVolume = Math.max(10, Math.min(100, Integer.parseInt(command[1])));
					int oldVolume = player.getVolume();
					player.setVolume(newVolume);
					event.getChannel()
							.sendMessage("Player volume changed from `" + oldVolume + "` to `" + newVolume + "`")
							.queue();
				} catch (NumberFormatException e) {
					event.getChannel().sendMessage("`" + command[1] + "` is not a valid integer. (10 - 100)").queue();
				}
			}
		} else if (".restart".equals(command[0])) {
			AudioTrack track = player.getPlayingTrack();
			if (track == null)
				track = scheduler.lastTrack;

			if (track != null) {
				event.getChannel().sendMessage("Restarting track: " + track.getInfo().title).queue();
				player.playTrack(track.makeClone());
			} else {
				event.getChannel()
						.sendMessage("No track has been previously started, so the player cannot replay a track!")
						.queue();
			}
		} else if (".repeat".equals(command[0])) {
			scheduler.setRepeating(!scheduler.isRepeating());
			event.getChannel()
					.sendMessage("Player was set to: **" + (scheduler.isRepeating() ? "repeat" : "not repeat") + "**")
					.queue();
		} else if (".reset".equals(command[0])) {
			synchronized (musicManagers) {
				scheduler.queue.clear();
				player.destroy();
				guild.getAudioManager().setSendingHandler(null);
				musicManagers.remove(guild.getId());
			}

			mng = getMusicManager(guild);
			guild.getAudioManager().setSendingHandler(mng.sendHandler);
			event.getChannel().sendMessage("The player has been completely reset!").queue();

		} else if (".nowplaying".equals(command[0]) || ".np".equals(command[0])) {
			AudioTrack currentTrack = player.getPlayingTrack();
			if (currentTrack != null) {
				String title = currentTrack.getInfo().title;
				String position = getTimestamp(currentTrack.getPosition());
				String duration = getTimestamp(currentTrack.getDuration());

				String nowplaying = String.format("**Playing:** %s\n**Time:** [%s / %s]", title, position, duration);

				event.getChannel().sendMessage(nowplaying).queue();
			} else
				event.getChannel().sendMessage("The player is not currently playing anything!").queue();
		} else if (".list".equals(command[0])) {
			Queue<AudioTrack> queue = scheduler.queue;
			synchronized (queue) {
				if (queue.isEmpty()) {
					event.getChannel().sendMessage("The queue is currently empty!").queue();
				} else {
					int trackCount = 0;
					long queueLength = 0;
					StringBuilder sb = new StringBuilder();
					sb.append("Current Queue: Entries: ").append(queue.size()).append("\n");
					for (AudioTrack track : queue) {
						queueLength += track.getDuration();
						if (trackCount < 10) {
							sb.append("`[").append(getTimestamp(track.getDuration())).append("]` ");
							sb.append(track.getInfo().title).append("\n");
							trackCount++;
						}
					}
					sb.append("\n").append("Total Queue Time Length: ").append(getTimestamp(queueLength));

					event.getChannel().sendMessage(sb.toString()).queue();
				}
			}
		} else if (".shuffle".equals(command[0])) {
			if (scheduler.queue.isEmpty()) {
				event.getChannel().sendMessage("The queue is currently empty!").queue();
				return;
			}

			scheduler.shuffle();
			event.getChannel().sendMessage("The queue has been shuffled!").queue();
		}
	}

	private void loadAndPlay(GuildMusicManager mng, CommandEvent event, String url, final boolean addPlaylist) {
		final String trackUrl;
		// Strip <>'s that prevent discord from embedding link resources
		if (url.startsWith("<") && url.endsWith(">"))
			trackUrl = url.substring(1, url.length() - 1);
		else
			trackUrl = url;

		playerManager.loadItemOrdered(mng, trackUrl, new AudioLoadResultHandler() {
			@Override
			public void trackLoaded(AudioTrack track) {
				String msg = "Adding to queue: " + track.getInfo().title;
				mng.scheduler.queue(track);
				event.reply(msg);
			}

			@Override
			public void playlistLoaded(AudioPlaylist playlist) {
				AudioTrack firstTrack = playlist.getSelectedTrack();
				List<AudioTrack> tracks = playlist.getTracks();

				if (firstTrack == null) {
					firstTrack = playlist.getTracks().get(0);
				}

				if (addPlaylist) {
					event.reply("Adding **" + playlist.getTracks().size() + "** tracks to queue from playlist: "
							+ playlist.getName());
					tracks.forEach(mng.scheduler::queue);
				} else {
					event.reply("Adding to queue " + firstTrack.getInfo().title + " (first track of playlist "
							+ playlist.getName() + ")");
					mng.scheduler.queue(firstTrack);
				}
			}

			@Override
			public void noMatches() {
				event.reply("Nothing found by " + trackUrl);
			}

			@Override
			public void loadFailed(FriendlyException exception) {
				event.reply("Could not play: " + exception.getMessage());
			}
		});
	}

	private GuildMusicManager getMusicManager(Guild guild) {
		String guildId = guild.getId();
		GuildMusicManager mng = musicManagers.get(guildId);
		if (mng == null) {
			synchronized (musicManagers) {
				mng = musicManagers.get(guildId);
				if (mng == null) {
					mng = new GuildMusicManager(playerManager);
					mng.player.setVolume(DEFAULT_VOLUME);
					musicManagers.put(guildId, mng);
				}
			}
		}
		return mng;
	}

	private static String getTimestamp(long milliseconds) {
		int seconds = (int) (milliseconds / 1000) % 60;
		int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
		int hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);

		if (hours > 0)
			return String.format("%02d:%02d:%02d", hours, minutes, seconds);
		else
			return String.format("%02d:%02d", minutes, seconds);
	}

}