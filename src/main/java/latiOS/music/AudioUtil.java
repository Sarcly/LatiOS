package latiOS.music;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import com.jagrosh.jdautilities.commandclient.CommandEvent;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import net.dv8tion.jda.core.entities.Guild;

import latiOS.music.GuildMusicManager;

public class AudioUtil {

	private final AudioPlayerManager playerManager;
	private final Map<Long, GuildMusicManager> musicManagers;

	public AudioUtil() {
		this.musicManagers = new HashMap<>();
		this.playerManager = new DefaultAudioPlayerManager();
		AudioSourceManagers.registerRemoteSources(playerManager);
		AudioSourceManagers.registerLocalSource(playerManager);
	}

	public synchronized GuildMusicManager getGuildAudioPlayer(Guild guild) {
		long guildId = Long.parseLong(guild.getId());
		GuildMusicManager musicManager = musicManagers.get(guildId);

		if (musicManager == null) {
			musicManager = new GuildMusicManager(playerManager);
			musicManagers.put(guildId, musicManager);
		}

		guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

		return musicManager;
	}

	public void loadAndPlay(CommandEvent event, final String trackUrl) {
		GuildMusicManager musicManager = getGuildAudioPlayer(event.getGuild());
		playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
			@Override
			public void trackLoaded(AudioTrack track) {
				event.reply("Adding to queue " + track.getInfo().title);
				play(event.getGuild(), musicManager, track, event);
			}

			@Override
			public void playlistLoaded(AudioPlaylist playlist) {
				AudioTrack firstTrack = playlist.getSelectedTrack();

				if (firstTrack == null) {
					firstTrack = playlist.getTracks().get(0);
				}

				event.reply("Adding to queue " + firstTrack.getInfo().title + " (first track of playlist "
						+ playlist.getName() + ")");

				play(event.getGuild(), musicManager, firstTrack, event);
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

	public void play(Guild guild, GuildMusicManager musicManager, AudioTrack track, CommandEvent event) {
		guild.getAudioManager()
				.openAudioConnection(event.getMember().getVoiceState().getChannel());
		musicManager.scheduler.queue(track);
	}

	public void skipTrack(CommandEvent event) {
		GuildMusicManager musicManager = getGuildAudioPlayer(event.getGuild());
		musicManager.scheduler.nextTrack();
	}

	public BlockingQueue<AudioTrack> getQueue(CommandEvent event) {
		GuildMusicManager musicManager = getGuildAudioPlayer(event.getGuild());
		return musicManager.scheduler.getQueue();
	}

	public void joinChannel(CommandEvent event) {
		event.getGuild().getAudioManager().openAudioConnection(event.getMember().getVoiceState().getChannel());
	}
}
