package com.sasha.voicebridge;

import com.sasha.voicebridge.exception.VoiceBridgeException;
import com.sasha.voicebridge.voice.BridgeAudio;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.managers.AudioManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.sasha.voicebridge.Startup.HOST;
import static com.sasha.voicebridge.Startup.REMOTE;

public class CallSession {

    private AudioManager channel1Audio, channel2Audio;
    private long initTextChannelId, channel1id, channel2id;
    private long remoteTextChannelId = -1L;

    public static List<CallSession> activeSessionList = new ArrayList<>();

    public CallSession(TextChannel initiatingChannel, VoiceChannel channel1, VoiceChannel channel2) {
        channel1Audio = channel1.getGuild().getAudioManager();
        channel2Audio = channel2.getGuild().getAudioManager();
        channel1id = channel1.getIdLong();
        channel2id = channel2.getIdLong();
        initTextChannelId = initiatingChannel.getIdLong();
    }

    public void connect() {
        post("Finding channels...");
        this.disconnect();
        activeSessionList.add(this);
        VoiceChannel channel1 = HOST.getDiscord().getVoiceChannelById(channel1id);
        VoiceChannel channel2 = REMOTE.getDiscord().getVoiceChannelById(channel2id);
        if (channel1 == null || channel2 == null) {
            throw new VoiceBridgeException("Invalid voice channels!");
        }
        post("Connecting to " + channel1.getGuild().getName() + "#" + channel1.getName() + " and " +
                channel2.getGuild().getName() + "#" + channel2.getName());
        BridgeAudio mirrorA = new BridgeAudio();
        BridgeAudio sourceA = new BridgeAudio();
        channel1Audio.setSendingHandler(mirrorA);
        channel1Audio.setReceivingHandler(sourceA);
        channel1Audio.openAudioConnection(channel1);
        channel2Audio.setReceivingHandler(mirrorA);
        channel2Audio.setSendingHandler(sourceA);
        channel2Audio.openAudioConnection(channel2);
        post("Connected.");
    }

    public void disconnect() {
        channel1Audio.closeAudioConnection();
        channel2Audio.closeAudioConnection();
        activeSessionList.remove(this);
    }

    public void post(String s) {
        TextChannel channel = HOST.getDiscord().getTextChannelById(initTextChannelId);
        if (channel != null) {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("VoiceBridge");
            builder.setDescription(s);
            builder.setColor(Color.PINK);
            channel.sendMessage(builder.build()).submit();
        }
        if (remoteTextChannelId != -1L) {
            TextChannel rchannel = HOST.getDiscord().getTextChannelById(initTextChannelId);
            if (rchannel == null) return;
            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("VoiceBridge");
            builder.setDescription(s);
            builder.setColor(Color.PINK);
            rchannel.sendMessage(builder.build()).submit();
        }
    }

}
