package latiOS.commands.music;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;

public class BackCommand extends Command {

	public BackCommand() {
		this.name = "back";
		this.help = "Goes back one song in the list";
		this.category = new Category("Music Control");
		this.children = new Command[] {new PlayCommand()};
	}
	
	@Override
	protected void execute(CommandEvent event) {

	}

}
