/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xtrsource.mineadmin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.sqlite.SQLiteConfig.Encoding;

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

public class TCPServer extends Thread {

	// the socket used by the server
	private ServerSocket serverSocket;
        private Plugin plugin;
        private int port;
        
        public PrintWriter printwriter;
        
        private IPManager IPMgr;
        
	// server constructor
	TCPServer(int port, Plugin plugin) {
            
            this.plugin = plugin;
            this.port = port;
            this.IPMgr = new IPManager(this.plugin);
            
        }
        
        @Override
	public void run() {
		/* create socket server and wait for connection requests */
		try 
		{
			serverSocket = new ServerSocket(port);
                        System.out.println("[MineAdmin] Started server on port "+serverSocket.getLocalPort()+".");

			while(true) 
			{
				Socket socket = serverSocket.accept();  // accept connection
                                printwriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
				System.out.println("[MineAdmin] New connection from: " + socket.getInetAddress().toString());
                                if(plugin.getConfig().getBoolean("config.server.iplists.useblacklist") && IPMgr.is_ip_blacklisted(socket.getInetAddress())) {
                                    
                                    printwriter.println("BLOCK;;;;BL");
                                    socket.close();
                                    System.out.println("[MineAdmin] Connection from: " + socket.getInetAddress().toString() + " got blocked! Reason: blacklisted");
                                }
                                else {
                                    if (plugin.getConfig().getBoolean("config.server.iplists.usewhitelist") && !IPMgr.is_ip_whitelisted(socket.getInetAddress())) {
                                        
                                        printwriter.println("BLOCK;;;;NWL");
                                        printwriter.flush();
                                        socket.close();
                                        System.out.println("[MineAdmin] Connection from: " + socket.getInetAddress().toString() + " got blocked! Reason: not whitelisted");
                                    }
                                    else {
                                        System.out.println("[MineAdmin] Connection from: " + socket.getInetAddress().toString() + " accepted.");
                                        TcpThread t = new TcpThread(socket, printwriter);    // make a thread of it
                                        //System.out.println("Starting a thread for a new Client");
                                        t.start();
                                    }
                                }
				
			}
		}
		catch (IOException e) {
			System.out.println("[MineAdmin] Exception on new ServerSocket: " + e);
		}
        }
        
        public void close() {
            try 
            {
                serverSocket.close();
            }
            catch (Exception e) 
            {
                
            }
        }
        

	/** One instance of this thread will run for each client */
	class TcpThread extends Thread {
		// the socket where to listen/talk
		Socket socket;
                BufferedReader bufferedreader;
                PrintWriter printwriter;
                
		
		TcpThread(Socket socket, PrintWriter printwriter) {
			this.socket = socket;
                        this.printwriter = printwriter;
		}
		public void run() {
                    
                    clientdata cldata = new clientdata(socket.getInetAddress(), plugin);
                    
                    CommandInterpreter cmdintp = new CommandInterpreter(plugin, cldata);
                    
			/* Creating both Data Stream */
                        if (plugin.getConfig().getBoolean("config.debug")){
                            System.out.println("[MineAdmin - DEBUG] Thread trying to create Object Input/Output Streams");
                        }
                        
			try
			{
                                bufferedreader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                                //printwriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);                               
			}
			catch (IOException e) {
                          
                                if (plugin.getConfig().getBoolean("config.debug")){
                                    System.out.println("[MineAdmin - DEBUG] Exception creating new Input/output Streams: " + e);
                                }
				return;
			}
                        
                        if (plugin.getConfig().getBoolean("config.debug")){
                               System.out.println("[MineAdmin - DEBUG] Thread waiting for a command from the Client");
                        }
                        
                        //reading
			try {
                            String command = "";
                            String result = "";
                            while (!(command = (String) bufferedreader.readLine()).equals("STOP") && socket.isConnected()) {
                                result = "";
				result = cmdintp.gatherData(command);
                                printwriter.println(result);
                            }
                            System.out.println("END");
			}
			catch (IOException e) {
                            
                                if (plugin.getConfig().getBoolean("config.debug")){
                                    System.out.println("[MineAdmin - DEBUG] Exception reading/writing  Streams: " + e);
                                }
				return;				
			}
		}
	}
}
