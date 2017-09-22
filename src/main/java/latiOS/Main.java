package latiOS;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.security.auth.login.LoginException;

import latiOS.config.ConfigDataTypes;
import latiOS.config.ConfigReader;
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
			cfg.addBoolean("bool1", "This is bool", true);
			cfg.addDouble("double1", "This is Double", 37628.3828);
			cfg.addInt("int1", "This is int", 42);
			cfg.addString("string1", "This is String", "my name jeff");
			Object[] b = {true,false,true};
			cfg.addArray("BoolArray", "this is bool array", ConfigDataTypes.BOOLEAN_ARRAY, b);
			Object[] d = {721.23,77.2,10.0,1.32};
			cfg.addArray("DoubleArray", "this is double array", ConfigDataTypes.DOUBLE_ARRAY, d);
			Object[] i = {8382,3828,573,1};
			cfg.addArray("IntArray", "This is int array", ConfigDataTypes.INT_ARRAY, i);
			Object[] s = {"my","name","jeff"};
			cfg.addArray("StringArray", "this is string array", ConfigDataTypes.STRING_ARRAY, s);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		FileInputStream fis;
		try {
			fis = new FileInputStream(new File("Configs/cfg.cfg"));
			ConfigReader cfgr = new ConfigReader(fis);
			cfgr.read();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		
		/*try {
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
		}*/

	}

}
