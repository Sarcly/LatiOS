package latiOS.music;

import java.util.HashMap;
import java.util.List;
import java.util.Queue;

import com.jagrosh.jdautilities.commandclient.CommandEvent;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.exceptions.PermissionException;

public class AudioUtil {
	public static final int DEFAULT_VOLUME = 35; // (0 - 150, where 100 is default max volume)

	private static final AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
	private static HashMap<Guild, GuildMusicManager> guildMusicManagers = new HashMap<>();

	public AudioUtil() {
		AudioSourceManagers.registerRemoteSources(playerManager);
	}

	private static GuildMusicManager getGuildMusicManager(Guild g) {
		if (!guildMusicManagers.containsKey(g)) {
			guildMusicManagers.put(g, new GuildMusicManager(playerManager));
			g.getAudioManager().setSendingHandler(guildMusicManagers.get(g).sendHandler);
		}
		return guildMusicManagers.get(g);
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
				event.getGuild().getAudioManager()
						.setSendingHandler(getGuildMusicManager(event.getGuild()).sendHandler);
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
			if (getGuildMusicManager(event.getGuild()).player.isPaused()) {
				getGuildMusicManager(event.getGuild()).player.setPaused(false);
				event.reply("Playback as been resumed.");
			} else if (getGuildMusicManager(event.getGuild()).player.getPlayingTrack() != null) {
				event.reply("Player is already playing!");
			} else if (getGuildMusicManager(event.getGuild()).scheduler.queue.isEmpty()) {
				event.reply("The current audio queue is empty! Add something to the queue first!");
			}
		} else // Commands has 2 parts, !play and url.
		{
			loadAndPlay(getGuildMusicManager(event.getGuild()), event, event.getArgs(), false);
		}
	}

	public void queue(CommandEvent event) {
		Queue<AudioTrack> queue = getGuildMusicManager(event.getGuild()).scheduler.queue;
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
		AudioTrack currentTrack = getGuildMusicManager(event.getGuild()).player.getPlayingTrack();
		if (currentTrack != null) {
			String title = currentTrack.getInfo().title;
			String position = getTimestamp(currentTrack.getPosition());
			String duration = getTimestamp(currentTrack.getDuration());

			String nowplaying = String.format("**Playing:** %s\n**Time:** [%s / %s]", title, position, duration);

			event.reply(nowplaying);
		} else
			event.reply("The player is not currently playing anything!");
	}

	public void skip(CommandEvent event) {
		getGuildMusicManager(event.getGuild()).scheduler.nextTrack();
		event.reply("The current track was skipped.");
	}

	public void pause(CommandEvent event) {
		if (getGuildMusicManager(event.getGuild()).player.getPlayingTrack() == null) {
			event.reply("Cannot pause or resume player because no track is loaded for playing.");
			return;
		}

		getGuildMusicManager(event.getGuild()).player
				.setPaused(!getGuildMusicManager(event.getGuild()).player.isPaused());
		if (getGuildMusicManager(event.getGuild()).player.isPaused())
			event.reply("The player has been paused.");
		else
			event.reply("The player has resumed playing.");
	}

	public void leave(CommandEvent event) {
		event.getGuild().getAudioManager().setSendingHandler(null);
		event.getGuild().getAudioManager().closeAudioConnection();
	}

	public void stop(CommandEvent event) {
		getGuildMusicManager(event.getGuild()).scheduler.queue.clear();
		getGuildMusicManager(event.getGuild()).player.stopTrack();
		getGuildMusicManager(event.getGuild()).player.setPaused(false);
		event.reply("Playback has been completely stopped and the queue has been cleared.");
	}

	public void volume(CommandEvent event) {
		if (event.getArgs().isEmpty()) {
			event.reply("Current player volume: **" + getGuildMusicManager(event.getGuild()).player.getVolume() + "**");
		} else {
			try {
				int newVolume = Math.max(10, Math.min(100, Integer.parseInt(event.getArgs())));
				int oldVolume = getGuildMusicManager(event.getGuild()).player.getVolume();
				getGuildMusicManager(event.getGuild()).player.setVolume(newVolume);
				event.reply("Player volume changed from `" + oldVolume + "` to `" + newVolume + "`");
			} catch (NumberFormatException e) {
				event.reply("`" + event.getArgs() + "` is not a valid integer. (10 - 100)");
			}
		}
	}

	public void restart(CommandEvent event) {
		AudioTrack track = getGuildMusicManager(event.getGuild()).player.getPlayingTrack();
		if (track == null)
			track = getGuildMusicManager(event.getGuild()).scheduler.lastTrack;

		if (track != null) {
			event.getChannel().sendMessage("Restarting track: " + track.getInfo().title).queue();
			getGuildMusicManager(event.getGuild()).player.playTrack(track.makeClone());
		} else {
			event.getChannel().sendMessage("No track has been previously started, so the player cannot replay a track!")
					.queue();
		}
	}

	public void repeat(CommandEvent event) {
		getGuildMusicManager(event.getGuild()).scheduler.setRepeating(!getGuildMusicManager(event.getGuild()).scheduler.isRepeating());
		event.getChannel()
				.sendMessage("Player was set to: **" + (getGuildMusicManager(event.getGuild()).scheduler.isRepeating() ? "repeat" : "not repeat") + "**")
				.queue();

	}

	@SuppressWarnings("unlikely-arg-type")
	public void reset(CommandEvent event) {
		synchronized (guildMusicManagers) {
			getGuildMusicManager(event.getGuild()).scheduler.queue.clear();
			getGuildMusicManager(event.getGuild()).player.destroy();
			event.getGuild().getAudioManager().setSendingHandler(null);
			guildMusicManagers.remove(event.getGuild().getId());
		}
		event.getGuild().getAudioManager().setSendingHandler(getGuildMusicManager(event.getGuild()).sendHandler);
		event.reply("The player has been completely reset");

	}

	public void shuffle(CommandEvent event) {
		if (getGuildMusicManager(event.getGuild()).scheduler.queue.isEmpty()) {
			event.getChannel().sendMessage("The queue is currently empty!").queue();
			return;
		}

		getGuildMusicManager(event.getGuild()).scheduler.shuffle();
		event.getChannel().sendMessage("The queue has been shuffled!").queue();

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