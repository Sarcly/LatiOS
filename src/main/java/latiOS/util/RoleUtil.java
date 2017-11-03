package latiOS.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.utils.SimpleLog;

public class RoleUtil {
	
	private static final SimpleLog log = SimpleLog.getLog("LatiOS");
	
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
		log.trace("P1=userRoles");
		mems.forEach(i->{
			log.trace("P2=loop::"+i.getRoles().stream().anyMatch(k->k.getName().equals(i.getUser().getName())));
			/*if (!i.getRoles().stream().anyMatch(k->k.getName().equals(i.getUser().getName()))) {
				Role r = guild.getController().createRole().complete();
				r.getManager().setName(i.getUser().getName()).complete();
				guild.getController().addSingleRoleToMember(i, r);
			}*/
		});
	}
}
