package latiOS.util;

import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.utils.SimpleLog;

public class ChatBox {

	private Event event;

	private static final SimpleLog log = SimpleLog.getLog("LatiOS");
	
	public ChatBox(Event e){
		this.event=e;
	}
	
	public void sendGuildMessagePlain(String msg) {
		if (event instanceof ReadyEvent) {
			log.info("LITTY");
		}
	}
}
