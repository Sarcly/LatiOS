package latiOS.commands.user;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;

public class PingCommand extends Command {

	public PingCommand()
    {
        this.name = "ping";
        this.help = "Pong!";
        this.guildOnly = false;
        this.aliases = new String[]{"pong"};
        this.category = new Category("User Commands");
    }
	
	@Override
	protected void execute(CommandEvent event) {
		event.reply("Pong! ("+event.getJDA().getPing()+"ms)");
	}

}
