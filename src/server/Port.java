package server;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.swt.widgets.Display;

import client.Ship;
import swt.body.TableWindow;

/**
 * Model of one port
 * @author rask
 *
 */
public class Port {

	private static final Logger log4j = LogManager.getLogger(Logger.class 
	        .getName());
	private ArrayList<Ship> ship= new ArrayList<Ship>();
	private final int portNumber;
	private boolean pierIsEmpty;
	private int stock;
	private int shipNumber = ship.size();
	private final int shipsLimit;	
	
	/**
	 * Constructor
	 * @param port - number of port
	 * @param limit - limit of ships
	 */
	Port(int port, int limit){
		
		this.portNumber = port;
		this.pierIsEmpty = true;
		this.stock = 0;
		this.shipsLimit = limit;
		
	}
	
	/**
	 * Only one ship may moor to this port at one period of time
	 * @param ship - ship which moores to port
	 */
	synchronized void gotMoored(Ship ship){
		
		if(this.ship.size() >= this.shipsLimit)
			return;
		else{
			
			this.pierIsEmpty = false;
			this.shipNumber = ship.getShipNumber();
			this.ship.add(ship);
		
			if(this.ship.size() == this.shipsLimit)
				this.pierIsEmpty = false;
			log4j.debug("Ship #" + ship.getShipNumber() + " moored to port #" + this.getPortNumber()); 
		}
		
	}
	/**
	 * Unmooring ship from this port
	 * @param ship - ship to unmoor
	 */
	synchronized void gotUnmoored(Ship ship){
		
		this.ship.remove(ship);
		this.pierIsEmpty = true;
		log4j.debug("Ship #" + ship.getShipNumber() + " unmoored from port #" + this.getPortNumber()); 
			
	}
	
	/**
	 * Getting port number
	 * @return port number
	 */
	public int getPortNumber(){
		
		return this.portNumber;
		
	}
	
	/**
	 * Getting if some pier is empty
	 * @return
	 */
	public boolean getPierIsEmpty(){
		
		return this.pierIsEmpty;
		
	}
	
	/**
	 * Getting free places in port
	 * @return how much free places
	 */
	synchronized public int getFreePlaces(){
		
		return (this.shipsLimit - this.ship.size());
		
	}
	
	/**
	 * Attaching ship to port
	 * @param ship
	 */
	synchronized void attachShip(Ship ship){
		
		this.ship.add(ship);
		messageToTable(ship, "Is Mooring To Port");
		ship.setIsMoored(true);
		log4j.debug("Ship #" + ship.getShipNumber() + " moored to port #" + this.getPortNumber()); 
			
	}
	
	/**
	 * Removing ship from port
	 * @param ship
	 */
	synchronized void removeShip(Ship ship){
		
		this.ship.remove(ship);
		ship.setIsMoored(false);
		log4j.debug("Ship #" + ship.getShipNumber() + " unmoored from port #" + this.getPortNumber());
			
	}
	
	/**
	 * Adding message to table
	 * @param ship
	 * @param str message
	 */
	void messageToTable(Ship ship, String str){
		
		Display.getDefault().syncExec(new Runnable() {
			   public void run() {
				   	
				   TableWindow.ShipAction(ship, str);
		
			   }
		});
	}
	
	/**
	 * Getting port info for FileDispatcher
	 * @return
	 */
	public String getPortInfo(){
		
		String info = ( "Port #" + String.valueOf(this.getPortNumber()) + ", Free Piers: " + 
				String.valueOf(this.shipsLimit - this.ship.size()) + "                                                                     " );
		return info;
		
	}
	
}
