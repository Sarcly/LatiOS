package latiOS.commands.music;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;

import latiOS.music.AudioUtil;

public class SkipCommand extends Command {

	public SkipCommand() {
		this.name = "skip";
		this.help = "Skips the current song";
		this.guildOnly = true;
		this.category = new Category("Music Control");
	}
	
	@Override
	protected void execute(CommandEvent event) {
		AudioUtil au = new AudioUtil();
		au.skip(event);
	}

}
