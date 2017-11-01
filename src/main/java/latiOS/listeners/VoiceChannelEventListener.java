package latiOS.listeners;

import latiOS.util.ChannelUtil;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.channel.voice.GenericVoiceChannelEvent;
import net.dv8tion.jda.core.hooks.EventListener;

public class VoiceChannelEventListener implements EventListener{
	
	public void onEvent(Event event) {
		if (event instanceof GenericVoiceChannelEvent){
			ChannelUtil cb = new ChannelUtil(((GenericVoiceChannelEvent) event).getGuild());
			cb.formatVoiceChannel();
		}
	}
	
}
