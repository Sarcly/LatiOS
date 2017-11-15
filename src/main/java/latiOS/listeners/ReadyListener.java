package latiOS.listeners;

import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.EventListener;

public class ReadyListener implements EventListener {
	
	public void onEvent(Event event) {
		if (event instanceof ReadyEvent) {
			if (event.getJDA().getGuilds().size()>1) {
				event.getJDA().shutdown();
			}
			event.getJDA().getPresence().setStatus(OnlineStatus.ONLINE);
		}
	}
}
