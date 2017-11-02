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
		this.requiredRole="People";
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
		event.getGuild().getRolesByName(event.getAuthor().getName(), false).get(0).getManager().setColor(Color.decode(event.getArgs())).complete();
		event.reply("Set "+event.getAuthor().getName()+"'s name color to "+event.getArgs());
	}

}
