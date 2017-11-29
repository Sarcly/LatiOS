package latiOS.commands.music;

import java.util.Arrays;
import java.util.concurrent.BlockingQueue;

import com.jagrosh.jdautilities.commandclient.Command;
import com.jagrosh.jdautilities.commandclient.CommandEvent;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import latiOS.music.AudioUtil;
import net.dv8tion.jda.core.MessageBuilder;

public class QueueCommand extends Command{

	public QueueCommand() {
		this.name = "queue";
		this.aliases = new String[] {"q"};
		this.help = "displays the queue";
		this.category = new Category("Music Control");
	}
	
	@Override
	protected void execute(CommandEvent event) {
		AudioUtil au = new AudioUtil();
		BlockingQueue<AudioTrack> queue = au.getQueue(event);
		System.out.println(Arrays.toString(queue.toArray()));
		MessageBuilder msg = new MessageBuilder().append("The current queue is:");
		queue.forEach(k->msg.appendCodeBlock(k.getInfo().title+"\n","http"));
		event.reply(msg.build());
	}

}
