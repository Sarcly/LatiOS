package latiOS.commands.music.playlist;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;

public class PlaylistRenameCommand extends Command {

	public PlaylistRenameCommand() {
		this.name = "rename";
		this.help = "Renames the playlist";
		this.arguments = "<playlist name> <new name>";
		this.guildOnly = true;
	}
	
	@Override
	protected void execute(CommandEvent event) {
	}

}
