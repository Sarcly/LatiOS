package latiOS;

import javax.security.auth.login.LoginException;

import latiOS.config.Config;
import latiOS.exceptions.ConfigValueNotFoundException;
import latiOS.listeners.GuildMessageListener;
import latiOS.listeners.MemberJoinListener;
import latiOS.listeners.MemberLeaveListener;
import latiOS.listeners.PrivateMessageListener;
import latiOS.listeners.ReadyListener;
import latiOS.listeners.RoleCreateListener;
import latiOS.listeners.RoleDeleteListener;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

public class Main {

	public static void main(String[] args) {
		try {
			loadConfigs();
		} catch (ConfigValueNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static JDABuilder buildBot() {
		return new JDABuilder(AccountType.BOT)
				.addEventListener(new GuildMessageListener())
				.addEventListener(new PrivateMessageListener())
				.addEventListener(new RoleCreateListener())
				.addEventListener(new RoleDeleteListener())
				.addEventListener(new MemberJoinListener())
				.addEventListener(new MemberLeaveListener())
				.addEventListener(new ReadyListener());
	}
	
	public static JDA startBot(JDABuilder jda) {
		Config cfg = new Config();
		try {
			return jda.setToken(cfg.getValue("botToken")).buildAsync();
		} catch (LoginException | IllegalArgumentException | RateLimitedException | ConfigValueNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void loadConfigs() throws ConfigValueNotFoundException {
		Config cfg = new Config();
		if (!cfg.configExsists()) {
			cfg.openGui();
		}
	}
}
