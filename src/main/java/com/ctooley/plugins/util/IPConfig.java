package com.ctooley.plugins.util;

import com.ctooley.plugins.Register;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class IPConfig
{

    private static File getIPFile()
    {
        return new File(Register.getInstance().getDataFolder() + File.separator + "ips.yml");
    }

    public static void newIPFile()
    {
        File file = getIPFile();
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        saveFile(file, config);
    }

    private static void saveFile(File file, FileConfiguration fileConfiguration)
    {
        try
        {
            fileConfiguration.save(file);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static boolean checkIP(Player p, String ip)
    {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(getIPFile());

        String lastIP = config.getString(p.getUniqueId().toString() + ".last-ip");

        return ip.equalsIgnoreCase(lastIP);
    }

    public static String getPassword(Player p)
    {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(getIPFile());

        return config.getString(p.getUniqueId().toString() + ".password");
    }

    public static void savePassowrd(Player p, String password)
    {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(getIPFile());

        config.set(p.getUniqueId().toString() + ".password", password);
        saveFile(getIPFile(), config);
    }

    public static void saveIP(Player p, String ip)
    {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(getIPFile());

        config.set(p.getUniqueId().toString() + ".last-ip", ip);
        saveFile(getIPFile(), config);
    }

    public static boolean hasRegistered(Player p)
    {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(getIPFile());
        return config.getString(p.getUniqueId().toString() + ".password") != null;
    }
}
