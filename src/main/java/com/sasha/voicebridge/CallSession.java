package com.sasha.voicebridge;

import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.managers.AudioManager;

public class CallSession {

    private AudioManager channel1Audio, channel2Audio;
    private long initTextChannelId;
    private long remoteTextChannelId = -1L;

    public CallSession(TextChannel initiatingChannel, VoiceChannel channel1, VoiceChannel channel2) {
        channel1Audio = channel1.getGuild().getAudioManager();
        channel2Audio = channel2.getGuild().getAudioManager();
        initTextChannelId = initiatingChannel.getIdLong();
    }

    public void connect() {

    }

}
