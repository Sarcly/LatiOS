package latiOS.commands.admin;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;

import net.dv8tion.jda.core.Permission;

public class BanCommand extends Command {

	public BanCommand() {
		this.name="ban";
		this.aliases = new String[]{"banhammer"};
		this.arguments = "<@User> [@User...]";
		this.requiredRole="Admin";
		this.category = new Category("Admin Commands");
		this.botPermissions = new Permission[] {Permission.BAN_MEMBERS};
		this.userPermissions = new Permission[] {Permission.BAN_MEMBERS};
	}
	
	@Override
	protected void execute(CommandEvent event) {
		if (event.getArgs().isEmpty()||!event.getArgs().matches("^<{1}@{1}!?\\d{17,}>{1}$")) {
			event.reply("You need to mention a user to kick!");
			return;
		}
		if (!event.getMessage().getMentionedUsers().stream().anyMatch(k->event.getGuild().getSelfMember().canInteract(event.getGuild().getMember(k)))) {
			event.reply("I dont have permission to kick that user!");
			return;
		}
		if (!event.getMessage().getMentionedUsers().stream().anyMatch(k->event.getMember().canInteract(event.getGuild().getMember(k)))) {
			event.reply("You dont have permission to kick that user!");
			return;
		}
		event.getMessage().getMentionedUsers().forEach(k->{
			event.getGuild().getController().ban(k, 0).complete();
			event.reply("Banned user "+k.getName()+"#"+k.getDiscriminator());
		});
	}
}