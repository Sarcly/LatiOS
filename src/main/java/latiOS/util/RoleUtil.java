package latiOS.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.jagrosh.jdautilities.utils.FinderUtil;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;

public class RoleUtil {
	
	private Guild guild;
	
	public RoleUtil(Guild guild) {	
		this.guild=guild;
	}
	
	public void formatRoles() {
		List<Role> roles = guild.getRoles();
		Set<String> temp = new HashSet<String>();
		for (Role r : roles) {
			 if(!temp.add(r.getName())) {
				 r.getManager().setName(r.getName()+"_").complete();
			 }
		}
	}
	
	public void checkUserRoles() {
		List<Member> mems = guild.getMembers();
		mems.forEach(i->{
			if (!guild.getRoles().containsAll(FinderUtil.findRoles(i.getUser().getName(), guild))) {

			}
			if (guild.getMembersWithRoles(FinderUtil.findRoles(i.getUser().getName(), guild)) != null) {
				
			}
		});
	}
}
