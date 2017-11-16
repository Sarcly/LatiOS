package latiOS.commands.music;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;

import latiOS.commands.music.playlist.PlaylistCreateCommand;
import latiOS.commands.music.playlist.PlaylistEditCommand;
import latiOS.commands.music.playlist.PlaylistRemoveCommand;

public class PlaylistCommand extends Command {

	public PlaylistCommand() {
		this.name = "playlist";
		this.help = "changes, creates or deletes a playlist";
		this.arguments = "<create|edit|remove> <name> [...]";
		this.category = new Category("Music Control");
		this.children = new Command[] {new PlaylistEditCommand(), new PlaylistRemoveCommand(), new PlaylistCreateCommand()};
	}
	
	@Override
	protected void execute(CommandEvent event) {

	}

}
