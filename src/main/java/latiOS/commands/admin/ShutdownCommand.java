package latiOS.commands.admin;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;

public class ShutdownCommand extends Command {

	public ShutdownCommand() {
		this.name = "shutdown";
		this.help = "Shuts down the Bot";
	}
	
	@Override
	protected void execute(CommandEvent event) {
		if (event.isOwner()!=true) {
			event.replyError("You dont have permission to use this "+event.getAuthor().getName()+"!");
		}else {
			event.reply("Goodbye!");
			event.getJDA().shutdown();	
		}
	}

}
