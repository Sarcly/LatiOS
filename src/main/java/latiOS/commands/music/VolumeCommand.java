package latiOS.commands.music;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;

import latiOS.music.AudioUtil;

public class VolumeCommand extends Command{

	public VolumeCommand() {
		this.name = "volume";
		this.guildOnly = true;
		this.aliases = new String[]{"v"};
		this.category = new Category("Music Control");
		this.help = "Sets the volume";
		this.arguments = "[new volume]";
	}
	
	@Override
	protected void execute(CommandEvent event) {
		AudioUtil au = new AudioUtil();
		au.volume(event);
	}
	
}
