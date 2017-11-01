package latiOS.commands.admin;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;

public class PingCommand extends Command {

	public PingCommand()
    {
        this.name = "ping";
        this.help = "Pong!";
        this.guildOnly = false;
        this.aliases = new String[]{"pong","pongo","poing"};
    }
	
	@Override
	protected void execute(CommandEvent event) {
		event.reply("Pong! ("+event.getJDA().getPing()+"ms)");

	}

}
