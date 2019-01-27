package com.sasha.voicebridge.voice;

import net.dv8tion.jda.core.audio.AudioReceiveHandler;
import net.dv8tion.jda.core.audio.AudioSendHandler;
import net.dv8tion.jda.core.audio.CombinedAudio;
import net.dv8tion.jda.core.audio.UserAudio;

import java.util.concurrent.ConcurrentLinkedQueue;

public class BridgeAudio implements AudioSendHandler, AudioReceiveHandler {
    public static BridgeAudio sharedInstance;

    private double volume = 1.0;
    private ConcurrentLinkedQueue<byte[]> voiceData = new ConcurrentLinkedQueue<>();


    public BridgeAudio() {
        sharedInstance = this;
    }

    @Override
    public boolean canReceiveCombined() {
        return true;
    }

    @Override
    public boolean canReceiveUser() {
        return false;
    }

    @Override
    public void handleCombinedAudio(CombinedAudio combinedAudio) {
        byte[] datas = combinedAudio.getAudioData(volume);
        voiceData.add(datas);
    }

    @Override
    public void handleUserAudio(UserAudio userAudio) {

    }

    // send

    @Override
    public boolean canProvide() {
        return !voiceData.isEmpty();
    }

    @Override
    public byte[] provide20MsAudio() {
        return voiceData.poll();
    }

    @Override
    public boolean isOpus() {
        return false;
    }

}
