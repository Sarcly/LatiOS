package latiOS.commands.music.playlist;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;

public class PlaylistEditRemoveSongCommand extends Command {
	
	public PlaylistEditRemoveSongCommand() {
		this.name = "remove";
		this.help = "Removes a song from the playlist";
		this.arguments = "<playlist name> <song name?|id?>";
		this.guildOnly = true;
		this.category = new Category("Music Control");	
	}
	
	@Override
	protected void execute(CommandEvent event) {
	}

}
