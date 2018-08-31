package com.gmail.filoghost.holographicdisplays.bridge.bungeecord;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.Collection;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.gmail.filoghost.holographicdisplays.HolographicDisplays;
import com.gmail.filoghost.holographicdisplays.disk.Configuration;
import com.gmail.filoghost.holographicdisplays.util.ConsoleLogger;
import com.gmail.filoghost.holographicdisplays.util.NMSVersion;

public class BungeeChannel implements PluginMessageListener {

	private static BungeeChannel instance;
	
	public static BungeeChannel getInstance() {
		if (instance == null) {
			instance = new BungeeChannel(HolographicDisplays.getInstance());
		}
		return instance;
	}
	
	private BungeeChannel(Plugin plugin) {
		Bukkit.getMessenger().registerOutgoingPluginChannel(plugin, "BungeeCord");
        Bukkit.getMessenger().registerIncomingPluginChannel(plugin, "BungeeCord", this);
        
        if (NMSVersion.isGreaterEqualThan(NMSVersion.v1_13_R1)) {
        	// TODO implement when RedisBungee will be updated
        } else {
        	Bukkit.getMessenger().registerOutgoingPluginChannel(plugin, "RedisBungee");
        	Bukkit.getMessenger().registerIncomingPluginChannel(plugin, "RedisBungee", this);
        }
	}

	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
		String targetChannel = Configuration.useRedisBungee ? "RedisBungee" : "BungeeCord";
		
		if (channel.equals(targetChannel)) {
			DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));
			
			try {
				String subChannel = in.readUTF();
				 
				if (subChannel.equals("PlayerCount")) {
					String server = in.readUTF();
					 
					if (in.available() > 0) {
						int online = in.readInt();
						
						BungeeServerInfo serverInfo = BungeeServerTracker.getOrCreateServerInfo(server);
						serverInfo.setOnlinePlayers(online);
					}
				}
			 
			} catch (EOFException e) {
				// Do nothing.
			} catch (IOException e) {
				// This should never happen.
				e.printStackTrace();
			}
		}
	}
	
	
	public void askPlayerCount(String server) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);

		try {
			out.writeUTF("PlayerCount");
			out.writeUTF(server);
		} catch (IOException e) {
			// It should not happen.
			ConsoleLogger.log(Level.WARNING, "I/O Exception while asking for player count on server '" + server + "'.", e);
		}

		// OR, if you don't need to send it to a specific player
		Collection<? extends Player> players = Bukkit.getOnlinePlayers();
		if (players.size() > 0) {
			players.iterator().next().sendPluginMessage(HolographicDisplays.getInstance(), Configuration.useRedisBungee ? "RedisBungee" : "BungeeCord", b.toByteArray());
		}
	}
}
