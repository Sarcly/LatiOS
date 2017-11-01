package latiOS.listeners;

import latiOS.util.ChannelUtil;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.channel.text.GenericTextChannelEvent;
import net.dv8tion.jda.core.hooks.EventListener;

public class TextChannelEventListener implements EventListener{

	@Override
	public void onEvent(Event event) {
		if (event instanceof GenericTextChannelEvent){
			ChannelUtil cb = new ChannelUtil(((GenericTextChannelEvent) event).getGuild());
			cb.formatTextChannel();
		}
	}
}
