package com.t0rchie.votingLinks;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;

public final class VotingLinks extends JavaPlugin {
    private DatabaseManager database;

    @Override
    public void onEnable() {
        getLogger().info(ChatColor.GREEN + "Voting Links Enabled");

        getServer().getScheduler().runTaskTimer(this, () -> {
            getServer().broadcastMessage(ChatColor.AQUA + "Please vote for the server!"
                + ChatColor.YELLOW + " Use " + ChatColor.WHITE + "/vote" + ChatColor.YELLOW + " to get cool rewards.");
        }, 200L, 12000L);

        getServer().getPluginManager().registerEvents(new VoteListener(this), this);

        saveDefaultConfig();

        String host = getConfig().getString("database.host");
        int port = getConfig().getInt("database.port");
        String db = getConfig().getString("database.name");
        String user = getConfig().getString("database.user");
        String pass = getConfig().getString("database.password");

        this.database = new DatabaseManager(getLogger(), host, port, db, user, pass);
        database.connect();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("vote")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                player.sendMessage(ChatColor.GREEN + "Vote for our server:");
                player.sendMessage(ChatColor.AQUA + "1. " + ChatColor.YELLOW + "https://www.planetminecraft.com/server/justicesmp/vote/");
                player.sendMessage(ChatColor.AQUA + "2. " + ChatColor.YELLOW + "https://minecraft-mp.com/server/344130/vote/");
                player.sendMessage(ChatColor.AQUA + "3. " + ChatColor.YELLOW + "https://minecraftservers.org/vote/674207");
            } else {
                sender.sendMessage(ChatColor.RED + "You must be a player to use this command!");
            }
            return true;
        }
        return false;
    }

    public DatabaseManager getDatabase() {
        return database;
    }
}
