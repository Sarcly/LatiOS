package latiOS;

import java.io.IOException;

import javax.security.auth.login.LoginException;

import com.jagrosh.jdautilities.commandclient.CommandClientBuilder;
import com.jagrosh.jdautilities.waiter.EventWaiter;

import latiOS.commands.admin.BanCommand;
import latiOS.commands.admin.DeafenCommand;
import latiOS.commands.admin.KickCommand;
import latiOS.commands.admin.MuteCommand;
import latiOS.commands.admin.ShutdownCommand;
import latiOS.commands.admin.UndeafenCommand;
import latiOS.commands.admin.UnmuteCommand;
import latiOS.commands.music.JoinCommand;
import latiOS.commands.music.LeaveCommand;
import latiOS.commands.music.NowPlayingCommand;
import latiOS.commands.music.PauseCommand;
import latiOS.commands.music.PlayCommand;
import latiOS.commands.music.QueueCommand;
import latiOS.commands.music.RepeatCommand;
import latiOS.commands.music.ResetCommand;
import latiOS.commands.music.RestartCommand;
import latiOS.commands.music.ShuffleCommand;
import latiOS.commands.music.SkipCommand;
import latiOS.commands.music.StopCommand;
import latiOS.commands.music.VolumeCommand;
import latiOS.commands.user.ColorCommand;
import latiOS.commands.user.PingCommand;
import latiOS.config.Config;
import latiOS.exceptions.ConfigValueNotFoundException;
import latiOS.listeners.ReadyListener;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Game.GameType;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

public class Main {
	
	public static void main(String[] args) {
		try {
			loadConfigs();
		} catch (ConfigValueNotFoundException | IOException e) {
			e.printStackTrace();
		}
		try {
			@SuppressWarnings("unused")
			JDA LatiOS = startBot(buildBot());
		} catch (ConfigValueNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static JDABuilder buildBot() throws ConfigValueNotFoundException {
		EventWaiter commandWaiter = new EventWaiter();
		return new JDABuilder(AccountType.BOT)
				.addEventListener(commandWaiter)
				.addEventListener(addCommands().build())
				.addEventListener(new ReadyListener())
				.setStatus(OnlineStatus.DO_NOT_DISTURB)
				.setGame(Game.of(GameType.DEFAULT, "Loading"));
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
		 c.addCommands(
				 //Admin Commands
				 new ShutdownCommand(),
				 new BanCommand(),
				 new KickCommand(),
				 new MuteCommand(),
				 new UnmuteCommand(),
				 new DeafenCommand(),
				 new UndeafenCommand(),
				 //User Commands
				 new ColorCommand(),
		 		 new PingCommand(),
		 		 //Music Commands
		 		 new PlayCommand(),
		 		 new PauseCommand(),
		 		 new SkipCommand(),
		 		 new StopCommand(),
		 		 new QueueCommand(),
		 		 new JoinCommand(),
		 		 new NowPlayingCommand(),
		 		 new ShuffleCommand(),
		 		 new LeaveCommand(),
		 		 new VolumeCommand(),
		 		 new StopCommand(),
		 		 new ResetCommand(),
		 		 new RestartCommand(),
		 		 new RepeatCommand());
		 return c;
	}
}
