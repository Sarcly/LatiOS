package latiOS.commands.music;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;

public class PlayCommand extends Command {

	public PlayCommand() {
		this.name = "play";
		this.help = "Plays a song with the given link";
		this.arguments = "<URL>";
		this.category = new Category("Music Control");
		this.guildOnly = true;
	}
	
	@Override
	protected void execute(CommandEvent event) {
		event.reply("Sorry, not implemented");
	}

}
