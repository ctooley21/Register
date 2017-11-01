package com.andrewtooley.register.Listeners;

import com.andrewtooley.register.Register;
import com.andrewtooley.register.Util.IPConfig;
import com.andrewtooley.register.Util.Message;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;

public class PlayerListener implements Listener
{

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent e)
    {
        final Player p = e.getPlayer();
        if (IPConfig.hasRegistered(p))
        {
            if (!IPConfig.checkIP(p, e.getAddress().getHostAddress()))
            {
                Bukkit.getScheduler().runTaskLater(Register.getInstance(), new Runnable()
                {
                    @Override
                    public void run()
                    {
                        p.sendMessage(Message.getMessage("login-required-first"));
                    }
                }, 20);
            }
            else
            {
                Register.getInstance().loggedInPlayers.add(p.getUniqueId());
            }
        }
        else
        {
            Bukkit.getScheduler().runTaskLater(Register.getInstance(), new Runnable()
            {
                @Override
                public void run()
                {
                    p.sendMessage(Message.getMessage("register-required"));
                }
            }, 20);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e)
    {
        Player p = e.getPlayer();
        if (IPConfig.hasRegistered(p))
        {
            if (!Register.getInstance().loggedInPlayers.contains(p.getUniqueId()))
            {
                p.sendMessage(Message.getMessage("login-required-e"));
                e.setCancelled(true);
            }
        }
        else
        {
            p.sendMessage(Message.getMessage("register-required"));
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onCommandPreProcess(PlayerCommandPreprocessEvent e)
    {
        Player p = e.getPlayer();
        if (!Register.getInstance().loggedInPlayers.contains(p.getUniqueId()) &&
                !e.getMessage().split(" ")[0].equalsIgnoreCase("/login") &&
                !e.getMessage().split(" ")[0].equalsIgnoreCase("/register"))
        {
            if (IPConfig.hasRegistered(p))
            {
                p.sendMessage(Message.getMessage("login-required-e"));
                e.setCancelled(true);
            }
            else
            {
                p.sendMessage(Message.getMessage("register-required"));
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e)
    {
        Player p = e.getPlayer();
        if (!Register.getInstance().loggedInPlayers.contains(p.getUniqueId()))
        {
            if (IPConfig.hasRegistered(p))
            {
                p.sendMessage(Message.getMessage("login-required-e"));
                e.setCancelled(true);
            }
            else
            {
                p.sendMessage(Message.getMessage("register-required"));
                e.setCancelled(true);
            }
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e)
    {
        Player p = e.getPlayer();
        if (!Register.getInstance().loggedInPlayers.contains(p.getUniqueId()))
        {
            if (IPConfig.hasRegistered(p))
            {
                p.sendMessage(Message.getMessage("login-required-e"));
                e.setCancelled(true);
            }
            else
            {
                p.sendMessage(Message.getMessage("register-required"));
                e.setCancelled(true);
            }
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e)
    {
        Player p = e.getPlayer();
        if (!Register.getInstance().loggedInPlayers.contains(p.getUniqueId()))
        {
            if (IPConfig.hasRegistered(p))
            {
                p.sendMessage(Message.getMessage("login-required-e"));
                e.setCancelled(true);
            }
            else
            {
                p.sendMessage(Message.getMessage("register-required"));
                e.setCancelled(true);
            }
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e)
    {
        if (!(e.getInventory().getHolder() instanceof Player)) return;
        Player p = (Player) e.getInventory().getHolder();
        if (!Register.getInstance().loggedInPlayers.contains(p.getUniqueId()))
        {
            if (IPConfig.hasRegistered(p))
            {
                p.sendMessage(Message.getMessage("login-required-e"));
                e.setCancelled(true);
            }
            else
            {
                p.sendMessage(Message.getMessage("register-required"));
                e.setCancelled(true);
            }
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e)
    {
        if (Register.getInstance().loggedInPlayers.contains(e.getPlayer().getUniqueId()))
        {
            Register.getInstance().loggedInPlayers.remove(e.getPlayer().getUniqueId());
        }
    }

    @EventHandler
    public void onKick(PlayerKickEvent e)
    {
        if (Register.getInstance().loggedInPlayers.contains(e.getPlayer().getUniqueId()))
        {
            Register.getInstance().loggedInPlayers.remove(e.getPlayer().getUniqueId());
        }
    }
}
