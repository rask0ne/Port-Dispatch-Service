package server;

import java.util.ArrayList;

import client.Ship;

/**
 * Singleton class which contains list of ports in sea
 * @author rask
 *
 */
public class PortSingleton {

	private static ArrayList<Port> port = new ArrayList<Port>();
		
	
	private static PortSingleton instance = null;
	 
	/**
	 * Constructor of singleton
	 */
	private PortSingleton() {
		
		port.add(new Port(1, 2));
		port.add(new Port(2, 3));
		port.add(new Port(3, 4));
		port.add(new Port(4, 3));
		port.add(new Port(5, 2));
		
	}
 
	/**
	 * Instance of singleton
	 * @return instance
	 */
	synchronized public static PortSingleton getInstance() {
		if (instance == null) {
			// Thread Safe. Might be costly operation in some case
			synchronized (PortSingleton.class) {
				if (instance == null) {
					instance = new PortSingleton();
					
				}
			}
		}
		return instance;
	}
	
	/**
	 * Getting free port from list of ports
	 * @return free port number
	 */
	synchronized public int getFreePort(){
		
		for(int i = 0; i < this.port.size(); i++){
			
			if(this.port.get(i).getFreePlaces() > 0)
				return this.port.get(i).getPortNumber();
			
		}
		
		return 9999;
		
	}
	
	/**
	 * Attaching ship to port with free places
	 * @param ship
	 * @param port number
	 */
	synchronized public void attachToPort(Ship ship, int port){
		
		this.port.get(port).attachShip(ship);
		
	}
	
	/**
	 * Removing ship from port
	 * @param ship
	 * @param port number
	 */
	synchronized public void removeFromPort(Ship ship, int port){
		
		this.port.get(port).removeShip(ship);
		
	}
	
	/**
	 * Getting amount of all ports in sea
	 * @return number of ports
	 */
	public int getAmountOfPorts(){
		
		return this.port.size();
		
	}
	
	/**
	 * Getting info about all ports at this time
	 * @return string message
	 */
	public String getPortsInfo(){
		
		String info = "";
		for(int i = 0; i < this.port.size(); i++){
			
			info += this.port.get(i).getPortInfo();
			
			}
			
		return info;
		
	}
	
}
