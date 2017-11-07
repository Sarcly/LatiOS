package latiOS.commands.user;

import java.awt.Color;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;

public class ColorCommand extends Command {
	
	public ColorCommand() {
		this.name="color";
		this.help="Changes the color of your name";
		this.arguments="#<Color Hex>";
		this.guildOnly=true;
	}
	
	@Override
	protected void execute(CommandEvent event) {
		if (event.getArgs().isEmpty()) {
			event.replyError("I need you to giv me a color is \"#RRGGBB\" format!");
			return;
		}
		if (!event.getArgs().matches("^#?[0-9A-Fa-f]{6}$")) {
			event.replyError("You need to give me a valid color in \"#RRGGBB\" format!");
			return;
		}
		if (!event.getMember().getRoles().stream().anyMatch(k->k.getName().equals(event.getAuthor().getName()))) {
			event.getGuild().getController().createRole().setName(event.getAuthor().getName()).setPermissions(0L).complete();
		}
		event.getMember().getRoles().stream();
	}
}
