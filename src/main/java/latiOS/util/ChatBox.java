package latiOS.util;

import java.io.File;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.utils.SimpleLog;

public class ChatBox {

	private static final SimpleLog log = SimpleLog.getLog("LatiOS");
	
	private static SizedMessageStack<Message> guildMsgs = new SizedMessageStack<Message>(256);
	private static SizedMessageStack<Message> privateMsgs = new SizedMessageStack<>(256);
	
	public ChatBox(){
		super();
	}
	
	public void sendPrivatePlain(User user, String msg) {
		PrivateChannel pc = user.openPrivateChannel().complete();
		privateMsgs.push(pc.sendMessage(msg).complete());
		log.debug("Sent priavte message to "+user.getName()+"#"+user.getDiscriminator()+" with content: "+msg);
		pc.close().queue();
	}
	
	public void sendPrivateMessage(User user, Message msg) {
		PrivateChannel pc = user.openPrivateChannel().complete();
		privateMsgs.push(pc.sendMessage(msg).complete());
		log.debug("Sent priavte message to "+user.getName()+"#"+user.getDiscriminator()+" with content: "+msg.getContent());
		pc.close().queue();
	}
	
	public void sendGuildPlain(TextChannel tc, String msg) {
		guildMsgs.push(tc.sendMessage(msg).complete());
		log.debug("Sent guild message with content: "+msg);
	}
	
	public void sendGuildMessage(TextChannel tc, Message msg) {
		guildMsgs.push(tc.sendMessage(msg).complete());
		log.debug("Sent guild message with content: "+msg.getContent());
	}
	
	public void editLastGuildMessage(String newMsg) {
		guildMsgs.push(guildMsgs.pop().editMessage(newMsg).complete());
		log.debug("Edited guild message to: "+newMsg);
	}
	
	public void editLastGuildMessage(Message newMsg) {
		guildMsgs.push(guildMsgs.pop().editMessage(newMsg).complete());
		log.debug("Edited guild message to: "+newMsg.getContent());
	}
	
	public void editLastPrivateMessageToUser(User user, Message newMsg) {
		privateMsgs.push(privateMsgs.popAtUser(user.getId()).editMessage(newMsg).complete());
		log.debug("Edited private message to: "+newMsg.getContent());
	}
	
	public void editLastPrivateMessageToUser(User user, String newMsg) {
		privateMsgs.push(privateMsgs.popAtUser(user.getId()).editMessage(newMsg).complete());
		log.debug("Edited private message to: "+newMsg);
	}
	
	public void deleteLastGuildMessage() {
		Message m = guildMsgs.pop();
		m.delete().complete();
		log.debug("Deleted Guild message with content: "+m.getContent());
	}
	
	public void deleteLastPrivateMessage() {
		Message m = guildMsgs.pop();
		m.delete().complete();
		log.debug("Deleted private message with content: "+m.getContent());
	}
	
	public void sendFileGuild(TextChannel tc, File file, Message msg) {
		guildMsgs.push(tc.sendFile(file, msg).complete());
		log.debug("Sent guild message with content of \""+msg.getContent()+"\" and file "+file.getName());
	}
	
	public void sendFilePrivate(User user, File file, Message msg) {
		PrivateChannel pc = user.openPrivateChannel().complete();
		pc.sendFile(file, msg).complete();
		log.debug("Sent private message with content of \""+msg.getContent()+"\" and file "+file.getName());
		pc.close().queue();
	}
}
