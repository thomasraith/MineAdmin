/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xtrsource.mineadmin;

import java.security.MessageDigest;
import java.util.List;
import org.bukkit.GameMode;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

/**
    MineAdmin Plugin offers the API for MineAdmin Client to comunicate with the Bukkit Server
    Copyright (C) 2012/13  Raith Thomas
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
public class CommandInterpreter {
    
    Plugin plugin;
    Server server;
    clientdata cldata;
    
    public CommandInterpreter(Plugin plugin){
        this.plugin = plugin;
        this.server = this.plugin.getServer();
    }
    
    public CommandInterpreter(Plugin plugin, clientdata cldata){
        this.plugin = plugin;
        this.server = this.plugin.getServer();
        this.cldata = cldata;
    }
    
    public String gatherData(String command) {
              
        
        String result = "";
        
        String[] args = command.split(";;;;");
        
        cldata.add_to_log(command);
        
        command = args[0];
        
        result = command+";;;;";
        
        if (plugin.getConfig().getBoolean("config.control.show_commands")) {
            System.out.println("[MineAdmin] Got command: " + command);
        }
        
        
        if (command.equals("LOGIN")) {
            if (args[1].equals(plugin.getConfig().getString("config.server.password"))) {
                cldata.user_logedin();
                result = result + "ULI";
                
            }
            else
            {
                cldata.user_logedout();
                result = result + "UWPW";
            }
            
            return result;
        }
        
        if (command.equals("LOGOUT")) {
             cldata.user_logedout();
             result = result + "ULO";
        }
        
        if (cldata.is_logedin()) {
            
        //Player list
        if (command.equals("PLLIST")) {
            Player[] players = server.getOnlinePlayers();
            players = server.getOnlinePlayers();
            for (int i = 0; i < players.length; i++) {
                result = result + players[i].getName() + ";;;;" + players[i].getAddress().toString() + ";;;;" + players[i].getHealth() + ";;;;" + players[i].getFoodLevel() + ";;;;" + players[i].getLocation().getX() + ";;;;" + players[i].getLocation().getY() + ";;;;" + players[i].getLocation().getZ()  + ";;;;" + players[i].getExp() + ";;;;" + players[i].isFlying() + ";;;;" + players[i].getGameMode() + ";;;;";
            }
            return result;
        }
        
        //Player data
        if (command.equals("PLDATA")) {
            Player[] players = server.getOnlinePlayers();
            players = server.getOnlinePlayers();
            for (int i = 0; i < players.length; i++) {
                if (players[i].getName().equals(args[1])) {
                    result = result + players[i].getName()+ ";;;;" + players[i].getDisplayName()+ ";;;;" + players[i].getPlayerListName()+ ";;;;" + players[i].getAllowFlight()+ ";;;;" + players[i].getFirstPlayed()+ ";;;;" + players[i].getGameMode().name()+ ";;;;" + players[i].getFlySpeed()+ ";;;;" + players[i].getWalkSpeed()+ ";;;;" + players[i].getWorld().getName() + ";;;;" + players[i].getAddress().toString() + ";;;;" + players[i].getHealth() + ";;;;" + players[i].getFoodLevel() + ";;;;" + players[i].getLocation().getX() + ";;;;" + players[i].getLocation().getY() + ";;;;" + players[i].getLocation().getZ()  + ";;;;" + players[i].getExp() + ";;;;" + players[i].isSleeping() + ";;;;" + players[i].isSneaking() + ";;;;" + players[i].isSprinting() + ";;;;" + players[i].isWhitelisted() + ";;;;" + players[i].getUniqueId().toString() + ";;;;" + players[i].isFlying() + ";;;;" + players[i].getMaxHealth();
                }
            }
            return result;
        }
        
        //Server data
        if (command.equals("SRVDATA")) {
            result = result + server.getAllowNether() + ";;;;" + server.getAllowFlight() + ";;;;" + server.getPort() + ";;;;" + server.getWorldType() + ";;;;" + server.getIp() + ";;;;" + server.getOnlineMode() + ";;;;" + server.getServerName() + ";;;;" + server.getDefaultGameMode() + ";;;;" + server.getMaxPlayers() + ";;;;" + server.getViewDistance() + ";;;;" + server.getMotd() + ";;;;" + server.getVersion() + ";;;;" + server.getOnlinePlayers().length;
            return result;
        }
        
        //Plugin data
        if (command.equals("PLGDATA")) {
            Plugin[] plugins = server.getPluginManager().getPlugins();
            for (int i = 0; i < plugins.length; i++) {
                result = result + plugins[i].getDescription().getFullName() + ";;;;" + plugins[i].isEnabled() + ";;;;" + plugins[i].isNaggable() + ";;;;" + plugins[i].getDescription().getVersion() + ";;;;" + plugins[i].getDescription().getDescription() + ";;;;" + plugins[i].getDescription().getAuthors().get(0) + ";;;;" + plugins[i].getDescription().getWebsite();
            }
            return result;
        }
        
        //WORLDS
        if (command.equals("WORLDLIST")) {
            List<World> world = server.getWorlds();
            for (int i = 0; i < world.size(); i++) {
                result = result + world.get(i).getName() + ";;;;";
            }
            return result;
        }
        
        //WORLDATA
        if (command.equals("WORLDDATA")) {
            World world = server.getWorld(args[1]);
            result = result + world.getName() + ";;;;" + world.getAllowAnimals() + ";;;;" + world.getAllowMonsters() + ";;;;" + world.getAnimalSpawnLimit() + ";;;;" + world.getDifficulty().getValue() + ";;;;" + world.getFullTime() + ";;;;" + world.getMaxHeight() + ";;;;" + world.getMonsterSpawnLimit() + ";;;;" + world.getPVP() + ";;;;" + world.getSeaLevel() + ";;;;" + world.getSeed() + ";;;;" + world.getThunderDuration() + ";;;;" + world.getTicksPerAnimalSpawns() + ";;;;" + world.getTicksPerMonsterSpawns() + ";;;;" + world.getTime() + ";;;;" + world.getUID() + ";;;;" + world.getWaterAnimalSpawnLimit() + ";;;;" + world.getWeatherDuration() + ";;;;" + world.getWorldType().getName() + ";;;;" + world.hasStorm() + ";;;;" + world.hashCode();
            return result;
        }
        
        //RUN PLAYERCOMMAND
        if (command.equals("PLCOMMAND")) {
            if (args[2].equals("DISPLAYNAME")) {
                plugin.getServer().getPlayer(args[1]).setDisplayName(args[3]);
                return "OK";
            }
            
            
            if (args[2].equals("WHITELIST")) {
                if (args[3].equals("true")) {
                    plugin.getServer().getPlayer(args[1]).setWhitelisted(true);
                }
                else {
                    plugin.getServer().getPlayer(args[1]).setWhitelisted(false);
                }
                return "OK";
            }
            
            
            if (args[2].equals("GAMEMODE")) {
                if (args[3].equals("SURVIVAL")) {
                    plugin.getServer().getPlayer(args[1]).setGameMode(GameMode.SURVIVAL);
                }
                else {
                    if (args[3].equals("CREATIVE")) {
                        plugin.getServer().getPlayer(args[1]).setGameMode(GameMode.CREATIVE);
                    }
                    else {
                        plugin.getServer().getPlayer(args[1]).setGameMode(GameMode.ADVENTURE);
                    }
                }
                return "OK";
            }
            
            
            if (args[2].equals("FLYSPEED")) {
                plugin.getServer().getPlayer(args[1]).setFlySpeed(Float.parseFloat(args[3]));
                return "OK";
            }
            
            
            if (args[2].equals("WALKSPEED")) {
                plugin.getServer().getPlayer(args[1]).setWalkSpeed(Float.parseFloat(args[3]));
                return "OK";
            }
            
            
            if (args[2].equals("ALLOW_FLIGHT")) {
                if (args[3].equals("true")) {
                    plugin.getServer().getPlayer(args[1]).setAllowFlight(true);
                    return "OK";
                }
                else {
                    plugin.getServer().getPlayer(args[1]).setAllowFlight(false);
                    return "OK";
                }
            }
            
            
            if (args[2].equals("SETHEALTH")) {
                plugin.getServer().getPlayer(args[1]).setHealth(Integer.parseInt(args[3]));
                return "OK";
            }
            
            
            if (args[2].equals("SETFOOD_LEVEL")) {
                plugin.getServer().getPlayer(args[1]).setFoodLevel(Integer.parseInt(args[3]));
                return "OK";
            }
            
            
            if (args[2].equals("SETEXP")) {
                plugin.getServer().getPlayer(args[1]).setExp(Float.parseFloat(args[3]));
                return "OK";
            }
        }
        
        //RUN WORLDCOMMAND
        if (command.equals("WORLDCOMMAND")) {
            
            if (args[2].equals("WORLDTIME")) {
                plugin.getServer().getWorld(args[1]).setTime(Long.parseLong(args[3]));
                return "OK";
            }
            
            if (args[2].equals("PVP")) {
                if (args[3].equals("true")){
                    plugin.getServer().getWorld(args[1]).setPVP(true);
                }
                else
                {
                    plugin.getServer().getWorld(args[1]).setPVP(false);
                }
                return "OK";
            }
            
            if (args[2].equals("ASPL")) {
                plugin.getServer().getWorld(args[1]).setAnimalSpawnLimit(Integer.parseInt(args[3]));
                return "OK";
            }
            
            if (args[2].equals("WASPL")) {
                plugin.getServer().getWorld(args[1]).setWaterAnimalSpawnLimit(Integer.parseInt(args[3]));
                return "OK";
            }
            
            if (args[2].equals("MSPL")) {
                plugin.getServer().getWorld(args[1]).setMonsterSpawnLimit(Integer.parseInt(args[3]));
                return "OK";
            }
            
            if (args[2].equals("TPASP")) {
                plugin.getServer().getWorld(args[1]).setTicksPerAnimalSpawns(Integer.parseInt(args[3]));
                return "OK";
            }
            
            if (args[2].equals("TPMSP")) {
                plugin.getServer().getWorld(args[1]).setTicksPerMonsterSpawns(Integer.parseInt(args[3]));
                return "OK";
            }
            
            if (args[2].equals("WEATHERDURATION")) {
                plugin.getServer().getWorld(args[1]).setWeatherDuration(Integer.parseInt(args[3]));
                return "OK";
            }
            
            if (args[2].equals("THUNDERDURATION")) {
                plugin.getServer().getWorld(args[1]).setThunderDuration(Integer.parseInt(args[3]));
                return "OK";
            }
            
            if (args[2].equals("STORM")) {
                if (args[3].equals("true")){
                    plugin.getServer().getWorld(args[1]).setStorm(true);
                }
                else
                {
                    plugin.getServer().getWorld(args[1]).setStorm(false);
                }
                return "OK";
            }
        }
        
        //CHAT PUBLIC
        if (command.equals("CHATPUB")) {
            Player[] player = plugin.getServer().getOnlinePlayers();
            for (int i = 0; i < player.length; i++)
            {
                plugin.getServer().getPlayer(player[i].getName()).sendMessage("<MineAdmin Client> " + args[1]);
            }
        }
        
        //CHAT PRIVATE
        if (command.equals("CHATPRI")) {
            plugin.getServer().getPlayer(args[1]).sendMessage("<MineAdmin Client> " + args[2]);
        }
        
        //PLUGIN VERSION
        if (command.equals("MAPLUGVER")) {
            return result + plugin.getDescription().getVersion();
        }
        
        if (command.equals("SERVCOMMAND")) {
            if (args[1].equals("RESTART")){
                    plugin.getServer().reload();
                    return "OK";
                }
            if (args[1].equals("STOP")){
                    plugin.getServer().shutdown();
                    return "OK";
                }
        }
        
        return "ERROR";
        }
        else
        {
            return "LOGIN;;;;UNLI";
        }
    }
   
    
    public static String sha256(String base) {
    try{
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(base.getBytes("UTF-8"));
        StringBuffer hexString = new StringBuffer();

        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }

        return hexString.toString();
    } catch(Exception ex){
       throw new RuntimeException(ex);
    }
}
    
}
