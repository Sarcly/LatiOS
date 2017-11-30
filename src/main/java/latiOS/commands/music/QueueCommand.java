package latiOS.commands.music;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;

import latiOS.music.AudioUtil;

public class QueueCommand extends Command{

	public QueueCommand() {
		this.name = "queue";
		this.aliases = new String[] {"q"};
		this.help = "displays the queue";
		this.category = new Category("Music Control");
	}
	
	@Override
	protected void execute(CommandEvent event) {
		AudioUtil au = new AudioUtil(event.getGuild());
	}

}
