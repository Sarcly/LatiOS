package latiOS.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.dv8tion.jda.core.entities.Category;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.managers.GuildController;
import net.dv8tion.jda.core.requests.restaction.AuditableRestAction;
import net.dv8tion.jda.core.requests.restaction.ChannelAction;
import net.dv8tion.jda.core.requests.restaction.order.ChannelOrderAction;

public class ChannelUtil {
	
	private Guild guild;
	private GuildController gc;
	
	public ChannelUtil(Guild guild) {
		this.guild=guild;
		this.gc=guild.getController();
	}
	
	public ChannelAction createCatagory(String name) {
		return gc.createCategory(name);
	}
	
	public ChannelOrderAction<Category> moveCatagory(int pos) {
		return gc.modifyCategoryPositions().moveTo(pos);
	}
	
	public void formatCatagories() {
		List<Category> cats = guild.getCategories();
		Set<String> temp = new HashSet<String>();
		for (Category c : cats) {
			 if(!temp.add(c.getName())) {
				 c.getManager().setName(c.getName()+"_").complete();
			 }
		}
	}
	
	public void formatTextChannel() {
		List<TextChannel> tcs = guild.getTextChannels();
		Set<String> temp = new HashSet<>();
		for (TextChannel t:tcs) {
			if (!temp.add(t.getName())) {
				t.getManager().setName(t.getName()+"_").complete();
			}
		}
	}
	
	public void formatVoiceChannel() {
		List<VoiceChannel> vcs = guild.getVoiceChannels();
		Set<String> temp = new HashSet<>();
		for (VoiceChannel v:vcs) {
			if (!temp.add(v.getName())) {
				v.getManager().setName(v.getName()+"_").complete();
			}
		}
	}
	
	public Category getCatagoryByName(String name, boolean ignoreCase) {
		return guild.getCategoriesByName(name, ignoreCase).get(0);
	}
	
	public ChannelAction createTextChannel(String name) {
		return gc.createTextChannel(name);
	}
	
	public AuditableRestAction<Void> addTextChannelToCatagory(TextChannel tc, Category c) {
		return tc.getManager().setParent(c);
	}
	
	public ChannelAction createVoiceChannel(String name) {
		return gc.createVoiceChannel(name);
	}
	
	public AuditableRestAction<Void> addVoiceChannelToCatagory(VoiceChannel vc, Category c) {
		return vc.getManager().setParent(c);
	}
}
