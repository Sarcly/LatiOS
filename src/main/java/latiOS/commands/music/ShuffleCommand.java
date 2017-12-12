package latiOS.commands.music;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;

import latiOS.music.AudioUtil;

public class ShuffleCommand extends Command {

	public ShuffleCommand() {
		this.name = "shuffle";
		this.help = "Shuffles the current queue";
		this.category = new Category("Music Control");
		this.guildOnly = true;
	}
	
	@Override
	protected void execute(CommandEvent event) {
		AudioUtil au = new AudioUtil();
		au.shuffle(event);
	}

}
