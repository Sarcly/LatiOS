package latiOS.commands.admin;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;

import net.dv8tion.jda.core.Permission;

public class KickCommand extends Command {

	public KickCommand() {
		this.name="kick";
		this.arguments= "<User>";
		this.botPermissions = new Permission[] {Permission.KICK_MEMBERS};
		this.userPermissions = new Permission[] {Permission.KICK_MEMBERS};
		this.help="Kick the specified user";
	}
	
	@Override
	protected void execute(CommandEvent event) {
		if (event.getArgs().isEmpty()||!event.getArgs().matches("^<{1}@{1}!?\\d{17,}>{1}$")) {
			event.reply("ugh");
		}
		
	}

}
