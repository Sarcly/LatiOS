package latiOS.commands.music;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;

import latiOS.music.AudioUtil;
import net.dv8tion.jda.core.Permission;

public class JoinCommand extends Command{

	public JoinCommand() {
		this.name = "join";
		this.guildOnly = true;
		this.category = new Category("Music Control");
		this.help = "Joins the channel you are currently in";
		this.botPermissions = new Permission[] {Permission.VOICE_CONNECT,Permission.VOICE_SPEAK,Permission.VOICE_USE_VAD};
	}
	
	@Override
	protected void execute(CommandEvent event) {
		if (event.getMember().getVoiceState().inVoiceChannel()==false) {
			event.replyError("Your not in a voice channel for me to join!");
			return;
		}
		AudioUtil au = new AudioUtil();
		au.joinChannel(event);
	}
	
}
