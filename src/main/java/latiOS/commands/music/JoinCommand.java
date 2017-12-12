package latiOS.commands.music;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;

import latiOS.music.AudioUtil;

public class JoinCommand extends Command{

	public JoinCommand() {
		this.name = "join";
		this.guildOnly = true;
		this.category = new Category("Music Control");
		this.help = "Joins the channel you are currently in";
	}
	
	@Override
	protected void execute(CommandEvent event) {
		AudioUtil au = new AudioUtil();
		au.join(event, false);
	}
	
}
