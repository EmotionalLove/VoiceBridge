package com.sasha.voicebridge.command;

import com.sasha.simplecmdsys.SimpleCommand;
import com.sasha.voicebridge.CallSession;
import com.sasha.voicebridge.Startup;
import com.sasha.voicebridge.party.IDiscord;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.VoiceChannel;

import javax.annotation.Nullable;

import java.util.stream.Collectors;

import static com.sasha.voicebridge.event.CommandListener.LAST_MSG;
import static com.sasha.voicebridge.event.CommandListener.YOU;

public class ConnectCommand extends SimpleCommand {

    public ConnectCommand() {
        super("connect");
    }

    @Override
    public void onCommand() {
        if (this.getArguments() == null || this.getArguments().length != 2) {
            LAST_MSG.getChannel().sendMessage(LAST_MSG.getAuthor().getAsMention() + " Expected two arguments! ;-;").submit();
            return;
        }
        VoiceChannel current = getCurrentUserVoiceChannel(LAST_MSG.getMember());
        if (current == null) {
            LAST_MSG.getChannel().sendMessage(LAST_MSG.getAuthor().getAsMention() + " You aren't connected to a voice channel! >.<").submit();
            return;
        }
        try {
            String servName = this.getArguments()[0];
            String channelName = this.getArguments()[1];
            IDiscord side = Startup.getOppositeSide(YOU.getIdLong());
            Guild guild = side.getDiscord().getGuildsByName(servName, true).get(0);
            VoiceChannel channel = guild.getVoiceChannelsByName(channelName, true).get(0);
            CallSession session = new CallSession(LAST_MSG.getTextChannel(), current, channel);
            session.connect();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Nullable
    private VoiceChannel getCurrentUserVoiceChannel(Member member) {
        for (VoiceChannel voiceChannel : member.getGuild().getVoiceChannels()) {
            for (Long aLong : voiceChannel.getMembers().stream().map(e -> e.getUser().getIdLong()).collect(Collectors.toList())) {
                if (aLong == member.getUser().getIdLong()) {
                    return voiceChannel;
                }
            }
        }
        return null;
    }
}
