package latiOS.commands.music;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;

import latiOS.music.AudioUtil;

public class PPlayCommand extends Command {
	
	public PPlayCommand() {
		this.name = "pplay";
		this.help = "Plays a playlist with the given link";
		this.arguments = "<URL>";
		this.category = new Category("Music Control");
		this.guildOnly = true;
	}
	
	@Override
	protected void execute(CommandEvent event) {
		AudioUtil au = new AudioUtil();
		au.pplay(event);
	}
}
