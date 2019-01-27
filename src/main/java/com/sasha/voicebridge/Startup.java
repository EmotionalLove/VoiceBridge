package com.sasha.voicebridge;

import com.sasha.simplesettings.SettingHandler;
import com.sasha.voicebridge.party.IDiscord;
import com.sasha.voicebridge.party.impl.HostDiscord;
import com.sasha.voicebridge.party.impl.RemoteDiscord;

public class Startup {

    private static SettingHandler handler = new SettingHandler("VoiceBridge");
    public static Configuration CONFIG = new Configuration();
    public static final String VERSION = "1.0";

    public static IDiscord HOST;
    public static IDiscord REMOTE;

    public static void main(String[] args) {
        System.out.println("Starting VoiceBridge " + VERSION);
        handler.read(CONFIG);
        HOST = new HostDiscord();
        HOST.login(CONFIG.hostAccount, CONFIG.hostAccountIsBot);
        System.out.println("Host connected as " + HOST.getDiscord().getSelfUser().getName() + "#" +
                HOST.getDiscord().getSelfUser().getDiscriminator());
        REMOTE = new RemoteDiscord();
        REMOTE.login(CONFIG.remoteAccount, CONFIG.remoteAccountIsBot);
        System.out.println("Remote connected as " + HOST.getDiscord().getSelfUser().getName() + "#" +
                HOST.getDiscord().getSelfUser().getDiscriminator());
        //
    }

}
