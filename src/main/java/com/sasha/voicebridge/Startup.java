package com.sasha.voicebridge;

import com.sasha.simplecmdsys.SimpleCommandProcessor;
import com.sasha.simplesettings.SettingHandler;
import com.sasha.voicebridge.command.ConnectCommand;
import com.sasha.voicebridge.event.CommandListener;
import com.sasha.voicebridge.party.IDiscord;
import com.sasha.voicebridge.party.impl.HostDiscord;
import com.sasha.voicebridge.party.impl.RemoteDiscord;
import net.dv8tion.jda.core.hooks.AnnotatedEventManager;

public class Startup {

    public static SimpleCommandProcessor processor = new SimpleCommandProcessor("*");
    private static SettingHandler handler = new SettingHandler("VoiceBridge");
    public static Configuration CONFIG = new Configuration();
    public static final String VERSION = "1.0";

    public static IDiscord HOST;
    public static IDiscord REMOTE;

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
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
        processor.register(ConnectCommand.class);
        HOST.getDiscord().setEventManager(new AnnotatedEventManager());
        REMOTE.getDiscord().setEventManager(new AnnotatedEventManager());
        HOST.getDiscord().addEventListener(new CommandListener());
        REMOTE.getDiscord().addEventListener(new CommandListener());
    }


    public static IDiscord getOppositeSide(long id) {
        if (HOST.getDiscord().getSelfUser().getIdLong() == id) return REMOTE;
        return HOST;
    }

}
