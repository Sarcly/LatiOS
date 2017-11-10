package latiOS.commands.music;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;

public class PlaylistCommand extends Command {

	public PlaylistCommand() {
		this.name = "playlist";
		this.help = "changes, creates or deletes a playlist";
		this.category = new Category("Music Commands");
	}
	
	@Override
	protected void execute(CommandEvent event) {

	}

}
