/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xtrsource.mineadmin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.xtrsource.mineadmin.Metrics;
import java.io.FileNotFoundException;
import org.bukkit.ChatColor;
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

public class MineAdmin extends JavaPlugin {
    
    TCPServer admin_server;
    
    @Override
    public void onLoad(){
        
    }
    
    @Override
    public void onEnable(){
        System.out.println("[MineAdmin] Plugin by "+this.getDescription().getAuthors());
        
        if (this.getConfig().getBoolean("config.debug")){
            System.out.println("[MineAdmin - DEBUG] Debug mode enabled!");
            System.out.println("[MineAdmin - DEBUG] Loading config ...");
        }
        
        loadConfig();
        
        if (this.getConfig().getBoolean("config.debug")){
            System.out.println("[MineAdmin - DEBUG] Loading config finished.");
        }
        
        // Metrics Plugin check start
        Plugin[] plugins = getServer().getPluginManager().getPlugins();
        boolean start_metrics = getConfig().getBoolean("config.allowpluginmetrics");
        for (int i = 0; i<plugins.length; i++) {
            if (plugins[i].getName().equalsIgnoreCase("essentials") || plugins[i].getName().equalsIgnoreCase("lwc") || plugins[i].getName().equalsIgnoreCase("vault") || plugins[i].getName().equalsIgnoreCase("ChestShop") || plugins[i].getName().equalsIgnoreCase("AuthMe") || plugins[i].getName().equalsIgnoreCase("dynmap") || plugins[i].getName().equalsIgnoreCase("LogBlock")) {
                start_metrics = true;
                i = plugins.length;
            }
        }
        
        // Metrics Plugin 
        if (start_metrics) {
            try {
               Metrics metrics = new Metrics(this);                   
               metrics.start();
               System.out.println("[MineAdmin] PluginMetrics enabled.");
            } catch (Exception e) {
                System.out.println("[MineAdmin] Failed to activate PluginMetrics.");
            }
        }
        else {
            System.out.println("[MineAdmin] PluginMetrics disabled.");
        }
        //Metrics PluginUpdate 
        
        
        admin_server = new TCPServer(this.getConfig().getInt("config.server.port"), this);
        getServer().getScheduler().scheduleAsyncDelayedTask(this, admin_server);
        
        getServer().getPluginManager().registerEvents(new MineAdminListener(this, admin_server), this); 
        
        System.out.println("[MineAdmin] Waiting for connections ...");
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        
        if(cmd.getName().equalsIgnoreCase("mineadmin")){
            
            sender.sendMessage(ChatColor.GREEN+"-----------------------------------------------------");
            sender.sendMessage(ChatColor.GREEN+this.getDescription().getFullName() +" by "+this.getDescription().getAuthors());
            sender.sendMessage(ChatColor.GREEN+"-----------------------------------------------------");
            sender.sendMessage(ChatColor.GREEN + "MineAdmin Server is running on port: " + this.getConfig().getString("config.server.port"));
            sender.sendMessage(ChatColor.GREEN+"-----------------------------------------------------");
        }
        
        return true;
    }
    
    @Override
    public void onDisable() {
        admin_server.close();
    }
    
    private void loadConfig(){
       this.getConfig().options().header("MINEADMIN CONFIGURATION");
       this.getConfig().addDefault("config.server.password", generaterandomString(12));
       this.getConfig().addDefault("config.server.port", 1001);
       this.getConfig().addDefault("config.server.iplists.useblacklist", false);
       this.getConfig().addDefault("config.server.iplists.usewhitelist", true);
       this.getConfig().addDefault("config.control.show_commands", true);
       this.getConfig().addDefault("config.log.write_log", true);
       this.getConfig().addDefault("config.log.fileformat.csv", true);
       this.getConfig().addDefault("config.log.fileformat.txt", false);
       this.getConfig().addDefault("config.debug", false);
       this.getConfig().addDefault("config.allowpluginmetrics", true);
       
       this.getConfig().options().copyDefaults(true);
       this.saveConfig();    
       
       FileWriter writer;
        File file = new File(this.getDataFolder() + "\\whitelist.txt");
        if(!file.exists()){
            try {
                writer = new FileWriter(file);
                writer.close();
            }catch (Exception e) {
            }
        }else {

        }
        
        file = new File(this.getDataFolder() + "\\blacklist.txt");
        if(!file.exists()){
            try {
                writer = new FileWriter(file);
                writer.close();
            }catch (Exception e) {
            }
        }else {

        }
       
    }
    
    private static String generaterandomString(int length) {
    Random random = new Random();
    int max = "0123456789abcdefghijklmnopqrstuvwxyz".length();
    StringBuffer buffer = new StringBuffer();
    for (int i=0; i<length; i++) {
        int value = random.nextInt(max);
        buffer.append("0123456789abcdefghijklmnopqrstuvwxyz".charAt(value));
    }
    return buffer.toString();
    } 
}
