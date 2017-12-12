package latiOS.commands.music;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;

import latiOS.music.AudioUtil;

public class RestartCommand extends Command{

	public RestartCommand() {
		this.name = "restart";
		this.guildOnly = true;
		this.category = new Category("Music Control");
		this.help = "Restarts the current song";
	}
	
	@Override
	protected void execute(CommandEvent event) {
		AudioUtil au = new AudioUtil();
		au.restart(event);
	}
	
}
