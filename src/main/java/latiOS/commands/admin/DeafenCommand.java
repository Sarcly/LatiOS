package latiOS.commands.admin;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;

import net.dv8tion.jda.core.Permission;

public class DeafenCommand extends Command {

	public DeafenCommand() {
		this.name =  "deafen";
		this.help = "Deafen the specified user in all TextChannels";
		this.arguments = "<@User> [@User...]";
		this.category = new Category("Admin Commands");
		this.botPermissions = new Permission[] {Permission.VOICE_DEAF_OTHERS};
		this.userPermissions = new Permission[] {Permission.VOICE_DEAF_OTHERS};
	}
	
	@Override
	protected void execute(CommandEvent event) {
		if (event.getArgs().isEmpty()||!event.getArgs().matches("^<{1}@{1}!?\\d{17,}>{1}$")) {
			event.replyError("You need to mention a user to deafen!");
			return;
		}
		if (!event.getMessage().getMentionedUsers().stream().anyMatch(k->event.getGuild().getSelfMember().canInteract(event.getGuild().getMember(k)))) {
			event.replyError("I dont have permission to deafen that user!");
			return;
		}
		if (!event.getMessage().getMentionedUsers().stream().anyMatch(k->event.getMember().canInteract(event.getGuild().getMember(k)))) {
			event.replyError("You dont have permission to deafen that user!");
			return;
		}
		event.getMessage().getMentionedUsers().forEach(k->{
			event.getGuild().getController().setDeafen(event.getGuild().getMember(k), true).complete();
			event.reply("Deafened user "+k.getName()+"#"+k.getDiscriminator());
		});
	}
}
