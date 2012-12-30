/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xtrsource.mineadmin;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
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

public class IPManager {
    
    private Plugin plugin;
    
    private List<InetAddress> whitelist = new ArrayList<InetAddress>();
    private List<InetAddress> blacklist;
    
    
    public IPManager(Plugin plugin) {
        
        this.plugin = plugin;
        
        BufferedReader reader;
        
        //read in WHITELIST
        try {
		reader = new BufferedReader(new FileReader(this.plugin.getDataFolder() + "\\whitelist.txt"));
		String zeile = null;
		while ((zeile = reader.readLine()) != null) {
                        try {
			whitelist.add(InetAddress.getByName(zeile));
                        }
                        catch (Exception ex) {   
                            if (plugin.getConfig().getBoolean("config.debug")){
                                System.out.println("[MineAdmin - DEBUG] Clouldn't parse whitelist-string to IP");
                            }
                        }
		}
                reader.close();
	} catch (Exception e) {
            if (plugin.getConfig().getBoolean("config.debug")){
                System.out.println("[MineAdmin - DEBUG] Clouldn't read whitelist.");
            }
	}
        
        //read in BLACKLIST
        try {
		reader = new BufferedReader(new FileReader(this.plugin.getDataFolder() + "\\blacklist.txt"));
		String zeile = null;
		while ((zeile = reader.readLine()) != null) {
                        try {
			blacklist.add(InetAddress.getByName(zeile));
                        }
                        catch (Exception ex) {   
                            if (plugin.getConfig().getBoolean("config.debug")){
                                System.out.println("[MineAdmin - DEBUG] Clouldn't parse blacklist-string to IP");
                            }
                        }
		}
                reader.close();
	} catch (Exception e) {
            if (plugin.getConfig().getBoolean("config.debug")){
                System.out.println("[MineAdmin - DEBUG] Clouldn't read blacklist.");
            }
	}
    }
    
    public boolean is_ip_whitelisted(String IP) {
        
        try {
            return whitelist.contains(InetAddress.getByName(IP));
        }
        catch(Exception e) {
            if (plugin.getConfig().getBoolean("config.debug")){
                System.out.println("[MineAdmin - DEBUG] Faild to check if whitelist conatins");
            }
        }
        return false;
    }
    
    public boolean is_ip_blacklisted(String IP) {
        
        try {
            return blacklist.contains(InetAddress.getByName(IP));
        }
        catch(Exception e) {
            if (plugin.getConfig().getBoolean("config.debug")){
                System.out.println("[MineAdmin - DEBUG] Faild to check if blacklist conatins");
            }
        }
        return false;
    }
    
    public boolean is_ip_whitelisted(InetAddress IP) {
        
        return whitelist.contains(IP);
    }
    
    public boolean is_ip_blacklisted(InetAddress IP) {
        
        return blacklist.contains(IP);
    }
}
