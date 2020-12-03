package by.thm.listeners;

import by.thm.AnimeServerSelector;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class BungeeMessageListener implements PluginMessageListener {

    public static HashMap<String, Integer> serverCount = new HashMap<>();

    @Override
    public void onPluginMessageReceived(String s, Player player, byte[] bytes) {
        if (!s.equals("BungeeCord")) {
            return;
        }

        ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
        String subchannel = in.readUTF();

        if (subchannel.equals("PlayerCount")) {
            String server = in.readUTF();
            serverCount.put(server, in.readInt());
        }
    }

    public static void getCount(Player player, String server) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("PlayerCount");
        out.writeUTF(server);

        player.sendPluginMessage(AnimeServerSelector.getInstance(), "BungeeCord", out.toByteArray());
    }

    public static int getServerCount(Player p, String server) {
        getCount(p, server);
        if (serverCount.get(server) != null) {
            return serverCount.get(server);
        } else return -1;

    }

    public static void teleportServer(Player p, String server) {

        Bukkit.getMessenger().registerOutgoingPluginChannel(AnimeServerSelector.getInstance(), "BungeeCord");

        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);

        try {
            out.writeUTF("Connect");
            out.writeUTF(server);
        } catch (IOException e) {
            e.printStackTrace();
        }

        p.sendPluginMessage(AnimeServerSelector.getInstance(), "BungeeCord", b.toByteArray());
    }
}
