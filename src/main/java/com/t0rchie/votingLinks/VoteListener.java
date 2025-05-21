package com.t0rchie.votingLinks;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class VoteListener implements Listener {

    private final VotingLinks plugin;

    public VoteListener(VotingLinks plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onVote(VotifierEvent event) {
        Vote vote = event.getVote();
        String username = vote.getUsername();
        Player player = Bukkit.getPlayerExact(username);
        UUID uuid = (player != null) ? player.getUniqueId() : UUID.nameUUIDFromBytes(username.getBytes());

        plugin.getDatabase().recordVote(uuid.toString(), username);

        int totalVotes = plugin.getDatabase().getVoteCount(uuid.toString());
        if (totalVotes == 100) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + username + " parent add voted100");
        }

        Bukkit.broadcastMessage(ChatColor.GREEN + username + " voted! Total votes: " + totalVotes);
    }
}
