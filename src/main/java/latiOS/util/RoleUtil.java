package latiOS.util;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.utils.SimpleLog;

public class RoleUtil {
	
	private static final SimpleLog log = SimpleLog.getLog("LatiOS");
	
	private Guild guild;
	
	public RoleUtil(Guild guild) {	
		this.guild=guild;
	}
}
