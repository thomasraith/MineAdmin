/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xtrsource.mineadmin;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.event.world.WorldEvent;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldSaveEvent;
import org.bukkit.event.world.WorldUnloadEvent;
import org.bukkit.plugin.Plugin;

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

public class MineAdminListener implements Listener {
    
    Plugin plugin;
    TCPServer tcpserver;
    
    public MineAdminListener(MineAdmin p, TCPServer tcpserver) {
        
        plugin = p;
        this.tcpserver = tcpserver;
        
    }
    
    
    @EventHandler
    public void getChatEvent (PlayerChatEvent event) {
        try {
            tcpserver.printwriter.println("EVENT;;;;PlayerChatEvent;;;;"+event.getPlayer().getName()+";;;;"+event.getMessage()+";;;;");
        } catch(Exception e) {}
    }
    
    @EventHandler
    public void getJoinEvent (PlayerJoinEvent event) {
        try {
            tcpserver.printwriter.println("EVENT;;;;PlayerJoinEvent;;;;"+event.getPlayer().getName()+";;;;"+event.getJoinMessage()+";;;;");
        } catch(Exception e) {}
    }
    
    @EventHandler
    public void getQuitEvent (PlayerQuitEvent event) {
        try {
            tcpserver.printwriter.println("EVENT;;;;PlayerQuitEvent;;;;"+event.getPlayer().getName()+";;;;"+event.getQuitMessage()+";;;;");
        } catch(Exception e) {}
    }
    
    @EventHandler
    public void getKickEvent (PlayerKickEvent event) {
        try {
            tcpserver.printwriter.println("EVENT;;;;PlayerKickEvent;;;;"+event.getPlayer().getName()+";;;;"+event.getReason()+";;;;");
        } catch(Exception e) {}
    }
    
    @EventHandler
    public void getDathEvent (PlayerDeathEvent event) {
        try {
            tcpserver.printwriter.println("EVENT;;;;PlayerDeathEvent;;;;"+event.getEntity().getName()+";;;;"+event.getDeathMessage()+";;;;");
        } catch(Exception e) {}
    }
    
    @EventHandler
    public void getWorldChangedEvent (PlayerChangedWorldEvent event) {
        try {
            tcpserver.printwriter.println("EVENT;;;;PlayerChangedWorldEvent;;;;"+event.getPlayer().getName()+";;;;"+event.getFrom().getName()+";;;;"+event.getPlayer().getWorld().getName()+";;;;");
        } catch(Exception e) {}
    }
    
    @EventHandler
    public void getExpChangedEvent (PlayerExpChangeEvent event) {
        try {
            tcpserver.printwriter.println("EVENT;;;;PlayerExpChangeEvent;;;;"+event.getPlayer().getName()+";;;;"+event.getAmount()+";;;;"+event.getPlayer().getExp()+";;;;");
        } catch(Exception e) {}
    }
    
    @EventHandler
    public void getGamemodeChangedEvent (PlayerGameModeChangeEvent event) {
        try {
            tcpserver.printwriter.println("EVENT;;;;PlayerGameModeChangeEvent;;;;"+event.getPlayer().getName()+";;;;"+event.getNewGameMode()+";;;;");
        } catch(Exception e) {}
    }
    
    @EventHandler
    public void getPortalEvent (PlayerPortalEvent event) {
        try {
            tcpserver.printwriter.println("EVENT;;;;PlayerPortalEvent;;;;"+event.getPlayer().getName()+";;;;"+event.getFrom().getWorld().getName()+";;;;"+event.getTo().getWorld().getName()+";;;;");
        } catch(Exception e) {}
    }
    
    @EventHandler
    public void getRespawnEvent (PlayerRespawnEvent event) {
        try {
            tcpserver.printwriter.println("EVENT;;;;PlayerRespawnEvent;;;;"+event.getPlayer().getName()+";;;;"+event.getRespawnLocation().getWorld().getName()+";;;;");
        } catch(Exception e) {}
    }
    
    @EventHandler
    public void getTeleportEvent (PlayerTeleportEvent event) {
        try {
            tcpserver.printwriter.println("EVENT;;;;PlayerTeleportEvent;;;;"+event.getPlayer().getName()+";;;;"+event.getFrom().getWorld().getName()+";;;;"+event.getTo().getWorld().getName()+";;;;");
        } catch(Exception e) {}
    }
    
    @EventHandler
    public void onCommand (ServerCommandEvent event) {
        try {
            tcpserver.printwriter.println("EVENT;;;;ServerCommandEvent;;;;"+event.getSender().getName()+";;;;"+event.getCommand());
        } catch(Exception e) {}
    }
    
    @EventHandler
    public void onWorldInitEvent (WorldInitEvent event) {
        try {
            tcpserver.printwriter.println("EVENT;;;;WorldInitEvent;;;;"+event.getWorld().getName()+";;;;" + " ");
        } catch(Exception e) {}
    }
    
    @EventHandler
    public void onWorldLoadEvent (WorldLoadEvent event) {
        try {
            tcpserver.printwriter.println("EVENT;;;;WorldLoadEvent;;;;"+event.getWorld().getName()+";;;;" + " ");
        } catch(Exception e) {}
    }
    
    @EventHandler
    public void onWorldUnloadEvent (WorldUnloadEvent event) {
        try {
            tcpserver.printwriter.println("EVENT;;;;WorldUnloadEvent;;;;"+event.getWorld().getName()+";;;;" + " ");
        } catch(Exception e) {}
    }
    
    @EventHandler
    public void onWorldSaveEvent (WorldSaveEvent event) {
        try {
            tcpserver.printwriter.println("EVENT;;;;WorldSaveEvent;;;;"+event.getWorld().getName()+";;;;" + " ");
        } catch(Exception e) {}
    }

}
