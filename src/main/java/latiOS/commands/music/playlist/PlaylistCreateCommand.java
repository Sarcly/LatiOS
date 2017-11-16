package latiOS.commands.music.playlist;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;

public class PlaylistCreateCommand extends Command {

	public PlaylistCreateCommand() {
		this.name="create";
		this.help = "Makes a new playlist";
		this.arguments = "<name>";
		this.guildOnly=true;
		this.category = new Category("Music Contol");
	}
	
	@Override
	protected void execute(CommandEvent event) {
	}

}
