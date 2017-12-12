package latiOS.commands.music;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;

import latiOS.music.AudioUtil;

public class LeaveCommand extends Command{

	public LeaveCommand() {
		this.name = "leave";
		this.guildOnly = true;
		this.category = new Category("Music Control");
		this.help = "Leaves the voice channel";
	}
	
	@Override
	protected void execute(CommandEvent event) {
		AudioUtil au = new AudioUtil();
		au.leave(event);
	}
	
}
