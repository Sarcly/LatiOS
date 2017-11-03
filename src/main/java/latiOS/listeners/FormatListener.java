package latiOS.listeners;

import latiOS.util.ChannelUtil;
import latiOS.util.RoleUtil;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.channel.category.GenericCategoryEvent;
import net.dv8tion.jda.core.events.channel.text.GenericTextChannelEvent;
import net.dv8tion.jda.core.events.channel.voice.GenericVoiceChannelEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.events.role.GenericRoleEvent;
import net.dv8tion.jda.core.events.user.UserNameUpdateEvent;
import net.dv8tion.jda.core.hooks.EventListener;

public class FormatListener implements EventListener{
	
	public void onEvent(Event event) {
		if (event instanceof GenericCategoryEvent){
			ChannelUtil cb = new ChannelUtil(((GenericCategoryEvent) event).getGuild());
			cb.formatCatagories();
		}else if (event instanceof GenericTextChannelEvent){
			ChannelUtil cb = new ChannelUtil(((GenericTextChannelEvent) event).getGuild());
			cb.formatTextChannel();
		}else if (event instanceof GenericVoiceChannelEvent){
			ChannelUtil cb = new ChannelUtil(((GenericVoiceChannelEvent) event).getGuild());
			cb.formatVoiceChannel();
		}else if (event instanceof GenericRoleEvent||event instanceof UserNameUpdateEvent||event instanceof UserNameUpdateEvent||event instanceof GuildMemberJoinEvent||event instanceof GuildMemberLeaveEvent) {
			RoleUtil ru = new RoleUtil(((GenericRoleEvent) event).getGuild());
			ru.formatRoles();
			ru.checkUserRoles();
		}
	}
}
