package com.ctooley.plugins.listeners;

import com.ctooley.plugins.Register;
import com.ctooley.plugins.util.IPConfig;
import com.ctooley.plugins.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener
{

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event)
    {
        final Player player = event.getPlayer();
        if (IPConfig.hasRegistered(player))
        {
            if (!IPConfig.checkIP(player, event.getAddress().getHostAddress()))
            {
                Bukkit.getScheduler().runTaskLater(Register.getInstance(), new Runnable()
                {
                    @Override
                    public void run()
                    {
                        player.sendMessage(Message.getMessage("login-required-first"));
                    }
                }, 20);
            }
            else
            {
                Register.getInstance().loggedInPlayers.add(player.getUniqueId());
            }
        }
        else
        {
            Bukkit.getScheduler().runTaskLater(Register.getInstance(), new Runnable()
            {
                @Override
                public void run()
                {
                    player.sendMessage(Message.getMessage("register-required"));
                }
            }, 20);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();
        if (IPConfig.hasRegistered(player))
        {
            if (!Register.getInstance().loggedInPlayers.contains(player.getUniqueId()))
            {
                player.sendMessage(Message.getMessage("login-required-e"));
                event.setCancelled(true);
            }
        }
        else
        {
            player.sendMessage(Message.getMessage("register-required"));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onCommandPreProcess(PlayerCommandPreprocessEvent event)
    {
        Player player = event.getPlayer();
        if (!Register.getInstance().loggedInPlayers.contains(player.getUniqueId()) &&
                !event.getMessage().startsWith("/login") &&
                !event.getMessage().startsWith("/register"))
        {
            checkRegistration(player, event);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        if (!Register.getInstance().loggedInPlayers.contains(player.getUniqueId()))
        {
            checkRegistration(player, event);
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event)
    {
        Player player = event.getPlayer();
        if (!Register.getInstance().loggedInPlayers.contains(player.getUniqueId()))
        {
            checkRegistration(player, event);
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event)
    {
        Player player = event.getPlayer();
        if (!Register.getInstance().loggedInPlayers.contains(player.getUniqueId()))
        {
            checkRegistration(player, event);
        }
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent event)
    {
        if (!(event.getInventory().getHolder() instanceof Player))
        {
            return;
        }
        Player player = (Player) event.getInventory().getHolder();

        if (!Register.getInstance().loggedInPlayers.contains(player.getUniqueId()))
        {
            checkRegistration(player, event);
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event)
    {
        if (Register.getInstance().loggedInPlayers.contains(event.getPlayer().getUniqueId()))
        {
            Register.getInstance().loggedInPlayers.remove(event.getPlayer().getUniqueId());
        }
    }

    @EventHandler
    public void onKick(PlayerKickEvent event)
    {
        if (Register.getInstance().loggedInPlayers.contains(event.getPlayer().getUniqueId()))
        {
            Register.getInstance().loggedInPlayers.remove(event.getPlayer().getUniqueId());
        }
    }

    private void checkRegistration(Player player, Cancellable event)
    {
        if (IPConfig.hasRegistered(player))
        {
            player.sendMessage(Message.getMessage("login-required-e"));
            event.setCancelled(true);
        }
        else
        {
            player.sendMessage(Message.getMessage("register-required"));
            event.setCancelled(true);
        }
    }
}
