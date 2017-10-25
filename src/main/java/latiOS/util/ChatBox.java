package latiOS.util;

import java.util.Stack;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.utils.SimpleLog;

public class ChatBox {

	private static final SimpleLog log = SimpleLog.getLog("LatiOS");
	
	/**TODO
	 * Limit the size of this stack.
	 * if the bot is running for a long time, this stack will just keep growing... 
	 */
	private static Stack<Message> guildMsgs = new Stack<Message>();
	
	public ChatBox(){
		super();
	}
	
	public void sendPrivatePlain(User user, String msg) {
		PrivateChannel pc = user.openPrivateChannel().complete();
		pc.sendMessage(msg).queue(i->{
			log.debug("Sent priavte message to "+user.getName()+"#"+user.getDiscriminator()+": "+msg);
		});
	}
	
	public void sendPrivateMessage(User user, Message msg) {
		PrivateChannel pc = user.openPrivateChannel().complete();
		pc.sendMessage(msg).queue(i->{
			log.debug("Sent priavte message to "+user.getName()+"#"+user.getDiscriminator()+": "+msg.getContent());
		});
	}
	
	public void sendGuildPlain(TextChannel tc, String msg) {
		tc.sendMessage(msg).queue(i->{
			log.debug("Sent guild message: "+msg);
			guildMsgs.push(i);
		});
	}
	
	public void sendGuildPlain(TextChannel tc, Message msg) {
		tc.sendMessage(msg).queue(i->{
			log.debug("Sent guild message: "+msg.getContent());
			guildMsgs.push(i);
		});
	}
	
	public void editLastGuildMessage(String newMsg) {
		guildMsgs.pop().editMessage(newMsg).queue(i->{
			log.debug("Edited guild message: "+newMsg);
			guildMsgs.push(i);
		});
	}
	
	public void editLastGuildMessage(Message newMsg) {
		guildMsgs.pop().editMessage(newMsg).queue(i->{
			log.debug("Edited guild message: "+newMsg.getContent());
			guildMsgs.push(i);
		});
	}
}
