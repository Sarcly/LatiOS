package latiOS.commands.music;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import latiOS.music.GuildMusicManager;
import net.dv8tion.jda.core.entities.Guild;

public class PlayCommand extends Command {

	private static final AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
	
	public PlayCommand() {
		this.name = "play";
		this.help = "Plays a song with the given link";
		this.arguments = "<URL>";
		this.category = new Category("Music Control");
		this.guildOnly = true;
	}
	
	@Override
	protected void execute(CommandEvent event) {

        AudioSourceManagers.registerRemoteSources(playerManager);
        GuildMusicManager manager = new GuildMusicManager(playerManager);
        event.getGuild().getAudioManager().openAudioConnection(event.getGuild().getMember(event.getAuthor()).getVoiceState().getChannel());
        playerManager.loadItem(event.getArgs(), new AudioLoadResultHandler() {
			
			@Override
			public void trackLoaded(AudioTrack track) {
				System.out.println("playing track "+track.getInfo().uri+track.getInfo().identifier);
				manager.scheduler.queue(track);
			}
			
			@Override
			public void playlistLoaded(AudioPlaylist playlist) {
				event.reply("Playlist");
			}
			
			@Override
			public void noMatches() {
				event.replyError("Unable to get Song");
			}
			
			@Override
			public void loadFailed(FriendlyException exception) {
				event.replyError("Load Failed");
			}
		});
	}

}
