package latiOS.listeners;

import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.hooks.EventListener;

public class MemberLeaveListener implements EventListener {

	public void onEvent(Event event) {
		if (event instanceof GuildMemberLeaveEvent){
			
		}
	}

}
