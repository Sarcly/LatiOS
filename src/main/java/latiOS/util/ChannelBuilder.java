package latiOS.util;

import java.util.List;

import net.dv8tion.jda.core.entities.Category;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.managers.GuildController;

public class ChannelBuilder {
	
	private Guild guild;
	private GuildController gc;
	
	public ChannelBuilder(Guild guild) {
		this.guild=guild;
		this.gc=guild.getController();
	}
	
	public void createCatagory(String name) {
		gc.createCategory(name).complete();
	}
	
	public void moveCatagory(int pos) {
		gc.modifyCategoryPositions().moveTo(pos).complete();
	}
	
	//TODO cant complete due to there being no catagory manager
	public void formatCatagories() {
		List<Category> cats = guild.getCategories();
		for (int i=cats.size();i>0;i--) {
			for (int k=i-1;k>0;k--) {
				System.out.print(cats.get(i)+"==="+cats.get(k)+"->"+cats.get(k).getName().equals(cats.get(i).getName())+":::");
				if (cats.get(k).getName().equals(cats.get(i).getName())) {
					cats.get(k).getManager().setName(cats.get(k).getName()+"(1)").complete();
					cats = guild.getCategories();
					System.out.println(cats.get(k));
				}
			}
		}
	}
	
	public Category getCatagoryByName(String name, boolean ignoreCase) {
		return guild.getCategoriesByName(name, ignoreCase).get(0);
	}
	
	public void createVoiceChannel(String name, Category cat) {
		gc.createTextChannel(name).setParent(cat).complete();
	}
}
