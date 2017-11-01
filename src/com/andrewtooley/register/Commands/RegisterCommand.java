package com.andrewtooley.register.Commands;

import com.andrewtooley.register.Register;
import com.andrewtooley.register.Util.IPConfig;
import com.andrewtooley.register.Util.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RegisterCommand implements CommandExecutor
{

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (!(sender instanceof Player))
        {
            sender.sendMessage(Message.getMessage("no-console"));
            return true;
        }

        Player p = (Player) sender;

        if (!p.hasPermission("register.register") && !p.isOp())
        {
            p.sendMessage(Message.getMessage("no-permission"));
            return true;
        }

        switch (args.length)
        {
            case 0:
                p.sendMessage(Message.getMessage("register-help"));
                break;
            case 1:
                p.sendMessage(Message.getMessage("register-help"));
                break;
            case 2:
                if (IPConfig.hasRegistered(p))
                {
                    p.sendMessage(Message.getMessage("already-registered"));
                }
                else
                {
                    if (args[0].equalsIgnoreCase(args[1]))
                    {
                        IPConfig.savePassowrd(p, args[0]);
                        Register.getInstance().loggedInPlayers.add(p.getUniqueId());
                        p.sendMessage(Message.getMessage("register-success").replace("%password%", args[0]));
                        IPConfig.saveIP(p, p.getAddress().getAddress().getHostAddress());
                    }
                    else
                    {
                        p.sendMessage(Message.getMessage("register-invalid"));
                    }
                }
                break;
            default:
                p.sendMessage(Message.getMessage("register-help"));
                break;
        }
        return true;
    }
}
