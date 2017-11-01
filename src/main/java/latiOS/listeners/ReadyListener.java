package latiOS.listeners;

import latiOS.config.Config;
import latiOS.exceptions.ConfigValueNotFoundException;
import latiOS.exceptions.MultiServerException;
import latiOS.util.ChannelUtil;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
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
			try {
				setup(event);
			} catch (ConfigValueNotFoundException e) {
				e.printStackTrace();
			}
			event.getJDA().getPresence().setGame(Game.of("with the server"));
			event.getJDA().getPresence().setStatus(OnlineStatus.ONLINE);
			log.info("LatiOS ready to go!");
			
		}
	}
	
	private void setup(Event event) throws ConfigValueNotFoundException {
		ChannelUtil cb = new ChannelUtil(event.getJDA().getGuilds().get(0));
		cb.formatCatagories();
		cb.formatTextChannel();
		cb.formatVoiceChannel();
	}
}
