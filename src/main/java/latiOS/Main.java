package latiOS;

import java.io.IOException;

import javax.activation.CommandMap;
import javax.security.auth.login.LoginException;

import org.slf4j.event.Level;

import com.jagrosh.jdautilities.commandclient.CommandClientBuilder;
import com.jagrosh.jdautilities.waiter.EventWaiter;

import latiOS.commands.admin.PingCommand;
import latiOS.config.Config;
import latiOS.exceptions.ConfigValueNotFoundException;
import latiOS.listeners.CatagoryEventListener;
import latiOS.listeners.GuildMessageListener;
import latiOS.listeners.MemberJoinListener;
import latiOS.listeners.MemberLeaveListener;
import latiOS.listeners.PrivateMessageListener;
import latiOS.listeners.ReadyListener;
import latiOS.listeners.RoleCreateListener;
import latiOS.listeners.RoleDeleteListener;
import latiOS.listeners.TextChannelEventListener;
import latiOS.listeners.VoiceChannelEventListener;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDA.Status;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.utils.SimpleLog;

public class Main {

	private static final SimpleLog log = SimpleLog.getLog("LatiOS");
	
	public static void main(String[] args) {
		try {
			loadConfigs();
		} catch (ConfigValueNotFoundException | IOException e) {
			e.printStackTrace();
		}
		try {
			setLogLevel(new Config().getValue("loggingLevel"));
		} catch (ConfigValueNotFoundException e) {
			e.printStackTrace();
		}
		try {
			JDA LatiOS = startBot(buildBot());
		} catch (ConfigValueNotFoundException e) {
			e.printStackTrace();
		}
		log.debug("Bot Started");
	}
	
	public static JDABuilder buildBot() throws ConfigValueNotFoundException {
		EventWaiter commandWaiter = new EventWaiter();
		log.debug("Bot Built");
		return new JDABuilder(AccountType.BOT)
				.addEventListener(new GuildMessageListener())
				.addEventListener(new PrivateMessageListener())
				.addEventListener(new RoleCreateListener())
				.addEventListener(new RoleDeleteListener())
				.addEventListener(new MemberJoinListener())
				.addEventListener(new MemberLeaveListener())
				.addEventListener(new CatagoryEventListener())
				.addEventListener(new TextChannelEventListener())
				.addEventListener(new VoiceChannelEventListener())
				.addEventListener(commandWaiter)
				.addEventListener(addCommands().build())
				.addEventListener(new ReadyListener())
				.setStatus(OnlineStatus.DO_NOT_DISTURB)
				.setGame(Game.of("Loading..."));
	}
	
	public static JDA startBot(JDABuilder jda) {
		Config cfg = new Config();
		try {
			return jda.setToken(cfg.getValue("botToken")).buildAsync();
		} catch (LoginException | IllegalArgumentException | RateLimitedException | ConfigValueNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void setLogLevel(String level) {
		switch (level) {
		case "All":log.setLevel(Level.TRACE);return;
		case "Debug":log.setLevel(Level.DEBUG);return;
		case "Warn":log.setLevel(Level.WARN);return;
		case "Fatal":log.setLevel(Level.ERROR);return;
		case "Info":log.setLevel(Level.INFO);return;
		case "Trace":log.setLevel(Level.TRACE);return;
		default: log.setLevel(Level.INFO);return;
		}
	}
	
	public static void loadConfigs() throws ConfigValueNotFoundException, IOException {
		Config cfg = new Config();
		if (!cfg.configExsists()) {
			cfg.openGui();
		}
		cfg.readConfig();
	}
	
	public static CommandClientBuilder addCommands() throws ConfigValueNotFoundException {
		CommandClientBuilder c = new CommandClientBuilder();
		 c.setEmojis("\u2714", "\u2B55", "\u274C");
		 c.setPrefix(new Config().getValue("commandPrefix"));
		 c.setOwnerId(new Config().getValue("botOwnerID"));
		 c.addCommand(
				 new PingCommand());
		 return c;
	}
}
