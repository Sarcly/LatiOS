package latiOS.commands.music;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;

public class PauseCommand extends Command {

	public PauseCommand() {
		this.name = "pause";
		this.help = "Pauses the current the music";
		this.guildOnly = true;
	}
	
	@Override
	protected void execute(CommandEvent event) {
		event.reply("Sorry, not implemented");
	}

}
