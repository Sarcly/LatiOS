package latiOS.util;

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
	private static SizedMessageStack<Message> guildMsgs = new SizedMessageStack<Message>(256);
	private static SizedMessageStack<Message> privateMsgs = new SizedMessageStack<>(256);
	
	public ChatBox(){
		super();
	}
	
	public void sendPrivatePlain(User user, String msg) {
		PrivateChannel pc = user.openPrivateChannel().complete();
		privateMsgs.push(pc.sendMessage(msg).complete());
		log.debug("Sent priavte message to "+user.getName()+"#"+user.getDiscriminator()+": "+msg);
		pc.close().queue();
	}
	
	public void sendPrivateMessage(User user, Message msg) {
		PrivateChannel pc = user.openPrivateChannel().complete();
		privateMsgs.push(pc.sendMessage(msg).complete());
		log.debug("Sent priavte message to "+user.getName()+"#"+user.getDiscriminator()+": "+msg.getContent());
		pc.close().queue();
	}
	
	public void sendGuildPlain(TextChannel tc, String msg) {
		guildMsgs.push(tc.sendMessage(msg).complete());
		log.debug("Sent guild message: "+msg);
	}
	
	public void sendGuildMessage(TextChannel tc, Message msg) {
		guildMsgs.push(tc.sendMessage(msg).complete());
		log.debug("Sent guild message: "+msg.getContent());
	}
	
	public void editLastGuildMessage(String newMsg) {
		guildMsgs.push(guildMsgs.pop().editMessage(newMsg).complete());
		log.debug("Edited guild message: "+newMsg);
	}
	
	public void editLastGuildMessage(Message newMsg) {
		guildMsgs.push(guildMsgs.pop().editMessage(newMsg).complete());
		log.debug("Edited guild message: "+newMsg.getContent());
	}
	
	public void editLastPrivateMessageToUser(User user, Message newMsg) {
		privateMsgs.push(privateMsgs.popAtUser(user.getId()).editMessage(newMsg).complete());
		log.debug("Edited guild message: "+newMsg.getContent());
	}
	
	public void editLastPrivateMessageToUser(User user, String newMsg) {
		privateMsgs.push(privateMsgs.popAtUser(user.getId()).editMessage(newMsg).complete());
		log.debug("Edited guild message: "+newMsg);
	}
}
