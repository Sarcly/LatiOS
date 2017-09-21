package latiOS;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.security.auth.login.LoginException;

import latiOS.config.ConfigDataTypes;
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
		FileOutputStream fop;
		try {
			fop = new FileOutputStream(new File("Configs/cfg.cfg"));
			ConfigWriter cfg = new ConfigWriter(fop);
			cfg.makeHeader("YEET");
			/*
			cfg.addBoolean("", "This is bool", true);
			cfg.addDouble("Double", "This is Double", 37628.3828);
			cfg.addInt("Int", "This is int", 42);
			cfg.addString("string", "This is String", "my name jeff");
			Object[] b = {true,false,true};
			cfg.addArray("BoolArray", "this is bool array", ConfigDataTypes.BOOLEAN, b);
			Object[] d = {721.23,77.2,10.0,1.32};
			cfg.addArray("DoubleArray", "this is double array", ConfigDataTypes.DOUBLE, d);
			Object[] i = {8382,3828,573,1};
			cfg.addArray("IntArray", "This is int array", ConfigDataTypes.INT, i);
			Object[] s = {"my","name","jeff"};
			cfg.addArray("StringArray", "this is string array", ConfigDataTypes.STRING, s);
			*/
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
