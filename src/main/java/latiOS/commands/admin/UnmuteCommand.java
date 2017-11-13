package latiOS.commands.admin;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;

import net.dv8tion.jda.core.Permission;

public class UnmuteCommand extends Command {

	public UnmuteCommand() {
		this.name =  "unmute";
		this.help = "Unmutes the specified user in all VoiceChannels";
		this.arguments = "<@User> [@User...]";
		this.category = new Category("Admin Commands");
		this.botPermissions = new Permission[] {Permission.VOICE_MUTE_OTHERS};
		this.userPermissions = new Permission[] {Permission.VOICE_MUTE_OTHERS};
	}
	
	@Override
	protected void execute(CommandEvent event) {
		if (event.getArgs().isEmpty()||!event.getArgs().matches("^<{1}@{1}!?\\d{17,}>{1}$")) {
			event.reply("You need to mention a user to unmute!");
			return;
		}
		if (!event.getMessage().getMentionedUsers().stream().anyMatch(k->event.getGuild().getSelfMember().canInteract(event.getGuild().getMember(k)))) {
			event.reply("I dont have permission to unmute that user!");
			return;
		}
		if (!event.getMessage().getMentionedUsers().stream().anyMatch(k->event.getMember().canInteract(event.getGuild().getMember(k)))) {
			event.reply("You dont have permission to unmute that user!");
			return;
		}
		event.getMessage().getMentionedUsers().forEach(k->{
			event.getGuild().getController().setMute(event.getGuild().getMember(k), false).complete();
			event.reply("Unmuted user "+k.getName()+"#"+k.getDiscriminator());
		});
	}
}
