package latiOS;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.security.auth.login.LoginException;

import latiOS.config.Config;
import latiOS.config.ConfigReader;
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
		Config cfg = new Config();
		if (!cfg.configExsists()) {
			cfg.openGui();
			cfg.waitForGui(true);
			System.out.println("Done");
		}
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
		/*try {
			cfg.addValue("IntTest", ConfigDataTypes.INT, "MAX VALUE", false, "2147483647");
			String[] ints = {"This","is","a","test"};
			cfg.addValue("StringArrayTest", ConfigDataTypes.STRING_ARRAY, "String Array Test", true, ints);
			String[] doubles = {"82374.282","99999.999999999999","9243987238479823.89234894","1.0"};
			cfg.addValue("DoubleArrayTest", ConfigDataTypes.DOUBLE_ARRAY, "Double Array", true, doubles);
			cfg.addValue("test", ConfigDataTypes.STRING, "This is a String", false, "I am a String");
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		try {
			ConfigReader c = new ConfigReader(new FileInputStream(new File("Configs/cfg.cfg")));
			c.readAll();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
