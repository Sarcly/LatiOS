package latiOS.listeners;

import latiOS.util.ChannelBuilder;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.channel.category.GenericCategoryEvent;
import net.dv8tion.jda.core.hooks.EventListener;
import net.dv8tion.jda.core.utils.SimpleLog;

public class CatagoryEventListener implements EventListener{
	
	private static final SimpleLog log = SimpleLog.getLog("LatiOS");
	
	public void onEvent(Event event) {
		if (event instanceof GenericCategoryEvent){
			ChannelBuilder cb = new ChannelBuilder(((GenericCategoryEvent) event).getGuild());
			cb.formatCatagories();
			log.info("Fixed catagory");
		}
	}
}
