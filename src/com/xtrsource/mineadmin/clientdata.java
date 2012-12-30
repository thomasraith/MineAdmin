/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xtrsource.mineadmin;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import javax.sound.sampled.Line;
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

public class clientdata {
    
    private Plugin plugin;
    private boolean logedin = false;
    private InetAddress IP;
    private PrintWriter pw;
    private File logfile;
    private Writer writer;
    
    //enum event {usercommand, systemcommand, client_recive, server_sent};
    //enum state {ok, failed};
    
    public clientdata(InetAddress IP, Plugin plugin){
        this.IP = IP;
        this.plugin = plugin;
        
        this.pw = null;
        
        this.logfile = new File(plugin.getDataFolder() + "\\log\\");
        
        if(!this.logfile.exists()) {
            this.logfile.mkdir();
        }
    }
    
    public void user_logedin() {
        this.logedin = true;
    }
    
    public void user_logedout() {
        this.logedin = false;
    }
    
    public boolean is_logedin() {
        return this.logedin;
    }
    
    public void add_to_log(logentry.event ev, String event, logentry.state state) {
        logentry temp_entry = new logentry();
        temp_entry.ev = ev;
        temp_entry.event = event; 
        temp_entry.st = state;
        
        try {
            this.writer = new BufferedWriter( new FileWriter(plugin.getDataFolder() + "\\log\\" + IP.toString() + ".txt"));
            this.pw = new PrintWriter( this.writer );
            
            this.pw.println(temp_entry.timestamp + "     " + temp_entry.ev.toString() + "     " + temp_entry.event + "     " + temp_entry.st.toString());
        } catch ( Exception e ){

        } finally {
            if ( this.pw != null )
                this.pw.close();
        }
        
    }  
    
    public void add_to_log(String event) {
        if(plugin.getConfig().getBoolean("config.log.write_log")) {
        logentry temp_entry = new logentry();
        temp_entry.event = event; 
        
        try {
            this.writer = new FileWriter(plugin.getDataFolder() + "\\log\\" + IP.toString(), true);
            //this.pw = new PrintWriter( this.writer, true );
            BufferedWriter bw = new BufferedWriter(this.writer);
            if (plugin.getConfig().getBoolean("config.log.fileformat.csv")) {
                bw.write(temp_entry.timestamp + ";" + this.IP.getHostAddress() + ";" + temp_entry.event);
            }
            
            if (plugin.getConfig().getBoolean("config.log.fileformat.txt")) {
                bw.write(temp_entry.timestamp + "     " + this.IP.getHostAddress() + "     " + temp_entry.event);
            }
		
            bw.newLine();
		
            bw.close();
            writer.close();
        } catch ( Exception e ){
                System.out.println(e);
        } finally {
            if ( this.pw != null ) {
                
            }
            
        }
        }
        
    } 
    
}
