package latiOS.commands.music.playlist;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;

public class PlaylistEditAddSongCommand extends Command {

	public PlaylistEditAddSongCommand() {
		this.name = "add";
		this.help = "Adds a song to the playlist";
		this.arguments = "<URL>";
		this.guildOnly = true;
	}
	
	@Override
	protected void execute(CommandEvent event) {
	}

}
