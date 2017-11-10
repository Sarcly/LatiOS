package latiOS.commands.admin;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;

import net.dv8tion.jda.core.Permission;

public class BanCommand extends Command {

	public BanCommand() {
		this.name="ban";
		this.aliases = new String[]{"banhammer"};
		this.arguments = "<User>";
		this.requiredRole="Admin";
		this.category = new Category("Admin Commands");
		this.botPermissions = new Permission[] {Permission.BAN_MEMBERS};
		this.userPermissions = new Permission[] {Permission.BAN_MEMBERS};
	}
	
	@Override
	protected void execute(CommandEvent event) {
		if (event.getArgs().isEmpty()) {
			event.replyError("You need to give me a user!");
		}else {
			if (!event.getGuild().getBans().complete().contains(event.getJDA().getUsersByName(event.getArgs(), true).get(0))) {
				event.getGuild().getController().ban(event.getJDA().getUsersByName(event.getArgs(), true).get(0), 0).complete();
				event.reply("Banned "+event.getArgs());
			}
		}	
	}
}