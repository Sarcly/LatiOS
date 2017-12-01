package latiOS.commands.music;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;

import latiOS.music.AudioUtil;

public class PlayCommand extends Command {
	
	public PlayCommand() {
		this.name = "play";
		this.help = "Plays a song with the given link";
		this.arguments = "<URL>";
		this.category = new Category("Music Control");
		this.guildOnly = true;
	}
	
	@Override
	protected void execute(CommandEvent event) {
		AudioUtil au = new AudioUtil(event.getGuild());
		au.play(event);
	}
}
