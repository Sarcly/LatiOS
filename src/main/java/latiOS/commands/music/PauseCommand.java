package latiOS.commands.music;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;

import latiOS.music.AudioUtil;

public class PauseCommand extends Command {

	public PauseCommand() {
		this.name = "pause";
		this.help = "Pauses the current the music";
		this.category = new Category("Music Control");
		this.guildOnly = true;
	}
	
	@Override
	protected void execute(CommandEvent event) {
		AudioUtil au = new AudioUtil();
		au.pause(event);
	}

}
