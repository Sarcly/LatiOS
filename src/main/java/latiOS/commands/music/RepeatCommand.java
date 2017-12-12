package latiOS.commands.music;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;

import latiOS.music.AudioUtil;

public class RepeatCommand extends Command{

	public RepeatCommand() {
		this.name = "repeat";
		this.guildOnly = true;
		this.category = new Category("Music Control");
		this.help = "Repeats the current song";
	}
	
	@Override
	protected void execute(CommandEvent event) {
		AudioUtil au = new AudioUtil();
		au.repeat(event);
	}
	
}
