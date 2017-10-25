package latiOS.listeners;

import latiOS.exceptions.MultiServerException;
import latiOS.util.ChatBox;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.EventListener;
import net.dv8tion.jda.core.utils.SimpleLog;

public class ReadyListener implements EventListener {
	
	private static final SimpleLog log = SimpleLog.getLog("LatiOS");
	
	public void onEvent(Event event) {
		if (event instanceof ReadyEvent) {
			if (event.getJDA().getGuilds().size()>1) {
				log.fatal(new MultiServerException());
				event.getJDA().shutdown();
			}
			ChatBox chat = new ChatBox();
			chat.sendGuildPlain(event.getJDA().getGuilds().get(0).getDefaultChannel(), "Hello World!");
			chat.sendPrivatePlain(event.getJDA().getGuilds().get(0).getMembers().get(1).getUser(), "TEST");
			chat.editLastGuildMessage("My name jeff");
		}
	}
}
