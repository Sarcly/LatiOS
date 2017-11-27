package latiOS.commands.music;

import java.util.HashMap;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import latiOS.music.GuildMusicManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.VoiceChannel;

/**
 * Created by Adair on 04/22/17.
 */
public class MusicCommand {

    public String[] names = {"music","m"};

    private static final AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
    private static HashMap<Guild,GuildMusicManager> guildMusicManagers = new HashMap<>();

    @Override
    public String[] getNames() {
        return names;
    }

    @Override
    public boolean safe(CommandContainer info) {
        /*if(info.args.length<=1){
            return false;
        }*/
        return true;
    }

    public MusicCommand() {
        super();
    }

    @Override
    public void action(CommandContainer info) {

        AudioSourceManagers.registerRemoteSources(playerManager);
        Guild g = info.event.getGuild();
        latiOS.music.GuildMusicManager manager = getGuildMusicManager(g);
        for(VoiceChannel c: g.getVoiceChannels()){
            g.getAudioManager().openAudioConnection(c);
            break;
        }
        if(info.allArgs.contains("https://youtube.com/watch?v=")) {
            getTrack(info, info.allArgs);
        }
        else{
            getTrack(info,new YoutubeSearcher(info.allArgs).search());
        }
    }

    @Override
    public Message help() {
        return null;
    }

    private static GuildMusicManager getGuildMusicManager(Guild g){
        if(!guildMusicManagers.containsKey(g)){
            guildMusicManagers.put(g, new GuildMusicManager(playerManager));
            g.getAudioManager().setSendingHandler(guildMusicManagers.get(g).getSendHandler());
        }
        return guildMusicManagers.get(g);
    }

    private static void getTrack(CommandContainer info,String url){
        System.out.println("url is : "+url);
        Guild g = info.event.getGuild();
        playerManager.loadItem(url, new AudioLoadResultHandler() {
                    @Override
                    public void trackLoaded(AudioTrack audioTrack) {
                        MessageEmbed msg = new EmbedBuilder().setAuthor(info.event.getMessage().getAuthor().getName(),null,info.event.getMessage().getAuthor().getAvatarUrl())
                                .addField("Song Added","added "+audioTrack.getInfo().title,true).build();
                        info.event.getMessage().getChannel().sendMessage(msg).queue((message)->{
                            message.addReaction("\uD83E\uDD14").queue();
                            info.event.getMessage().delete().complete();
                        });
                        play(g,audioTrack);
                    }

                    @Override
                    public void playlistLoaded(AudioPlaylist audioPlaylist) {
                        System.out.println("playlist");
                    }

                    @Override
                    public void noMatches() {
                        System.out.println("no matches");
                    }

                    @Override
                    public void loadFailed(FriendlyException e) {
                        System.out.println("load failed");
                        System.out.println(e.severity);
                        System.out.println(e.getCause().getMessage());
                    }
                });

    }


    public static void play(Guild g,AudioTrack track){
        getGuildMusicManager(g).scheduler.queue(track);
        System.out.println("playing "+getGuildMusicManager(g).player.getPlayingTrack().getInfo().title);
    }
}
