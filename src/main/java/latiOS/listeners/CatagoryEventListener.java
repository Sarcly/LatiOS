package latiOS.listeners;

import latiOS.util.ChannelUtil;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.channel.category.GenericCategoryEvent;
import net.dv8tion.jda.core.hooks.EventListener;

public class CatagoryEventListener implements EventListener{
	
	//private static final SimpleLog log = SimpleLog.getLog("LatiOS");
	
	public void onEvent(Event event) {
		if (event instanceof GenericCategoryEvent){
			ChannelUtil cb = new ChannelUtil(((GenericCategoryEvent) event).getGuild());
			cb.formatCatagories();
		}
	}
}
