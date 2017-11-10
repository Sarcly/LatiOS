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
		this.category = new Category("Admin Commands");
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
			event.getGuild().getController().kick(k.getId()).complete();
			event.reply("Kick user "+k.getName()+"#"+k.getDiscriminator());
		});
		
	}

}
