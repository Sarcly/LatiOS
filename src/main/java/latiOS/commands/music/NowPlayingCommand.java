package latiOS.commands.music;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;

import latiOS.music.AudioUtil;

public class NowPlayingCommand extends Command {

	public NowPlayingCommand() {
		this.name = "nowplaying";
		this.help = "Tell what is currently playing";
		this.aliases = new String[] {"np"};
		this.category = new Category("Music Control");
		this.guildOnly = true;
	}
	
	@Override
	protected void execute(CommandEvent event) {
		AudioUtil au = new AudioUtil();
		au.np(event);
	}

}
