package latiOS;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.security.auth.login.LoginException;

import latiOS.config.ConfigWriter;
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
			ConfigWriter cfg = new ConfigWriter(new FileOutputStream(new File("Configs/cfg.txt")));
			cfg.makeHeader("my name jeff\nyeetyeet\nuhhhhhhhhhhhhhhhh yeet");
			cfg.addBoolean("nameJeff", "is my name jeff\nwho knows?", true);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
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

	}

}
