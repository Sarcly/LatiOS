package latiOS;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.security.auth.login.LoginException;

import latiOS.config.Config;
import latiOS.config.ConfigDataTypes;
import latiOS.config.ConfigReader;
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
			@SuppressWarnings("unused")
			JDA LatiOS = new JDABuilder(AccountType.BOT)
					.setToken(args[0])
					.addEventListener(new GuildMessageListener())
					.addEventListener(new PrivateMessageListener())
					.addEventListener(new RoleCreateListener())
					.addEventListener(new RoleDeleteListener())
					.addEventListener(new MemberJoinListener())
					.addEventListener(new MemberLeaveListener())
					.addEventListener(new ReadyListener())
					.buildAsync();
		} catch (LoginException e) {
			System.err.println("Login Excpetion");
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			System.err.println("idk somthing happend");
			e.printStackTrace();
		} catch (RateLimitedException e) {
			System.err.println("Rate Limit Exception. Sending messages to fast");
			e.printStackTrace();
		}
		Config cfg = new Config();
		try {
			cfg.addValue("test", ConfigDataTypes.STRING, "jeff", false, "namejeff");
			String[] f = {"11.1","-75757575.3939","9832748923789.3883"};
			cfg.addValue("arraytest", ConfigDataTypes.DOUBLE_ARRAY, "UHHHHHHHHHHHH", true, f);
			cfg.changeValue("test", "AHHHHHHHHHHHHHHHHHHH");
			cfg.addValue("toobig", ConfigDataTypes.INT, "This int is TOO THICC", false, "-2147483648");
			cfg.addValue("buno", ConfigDataTypes.BOOLEAN, "B U N O", false, "false");
			cfg.addValue("litty", ConfigDataTypes.DOUBLE, "Litty Double", false, "-2929.321");
		} catch (IOException | ConfigValueNotFoundException e) {
			e.printStackTrace();
		}
		try {
			ConfigReader c = new ConfigReader(new FileInputStream(new File("Configs/cfg.cfg")));
			c.readAll();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
