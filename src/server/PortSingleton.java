package server;

import java.util.ArrayList;

import client.Ship;

public class PortSingleton {

	private static ArrayList<Port> port = new ArrayList<Port>();
		
	
	private static PortSingleton instance = null;
	 
	private PortSingleton() {
		
		port.add(new Port(1, 2));
		port.add(new Port(2, 3));
		port.add(new Port(3, 4));
		port.add(new Port(4, 3));
		port.add(new Port(5, 2));
		
	}
 
	// Lazy Initialization (If required then only)
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
	
	synchronized public int getFreePort(){
		
		for(int i = 0; i < this.port.size(); i++){
			
			if(this.port.get(i).getFreePlaces() > 0)
				return this.port.get(i).getPortNumber();
			
		}
		
		return 9999;
		
	}
	
	synchronized public void attachToPort(Ship ship, int port){
		
		this.port.get(port).attachShip(ship);
		
	}
	
	synchronized public void removeFromPort(Ship ship, int port){
		
		this.port.get(port).removeShip(ship);
		
	}
	
}
