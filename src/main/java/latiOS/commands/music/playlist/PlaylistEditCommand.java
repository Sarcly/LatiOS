package latiOS.commands.music.playlist;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;

public class PlaylistEditCommand extends Command {
	
	public PlaylistEditCommand() {
		this.name = "edit";
		this.help = "Edits a playlist";
		this.arguments = "[name|add|remove]";
		this.children = new Command[] {new PlaylistEditAddSongCommand(), new PlaylistEditRemoveSongCommand(), new PlaylistRenameCommand()};
		this.guildOnly = true;
	}
	
	@Override
	protected void execute(CommandEvent event) {
	}

}
