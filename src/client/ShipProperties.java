package client;

import server.PortSingleton;

/**
 * Singleton class which describes properties of ship which is creating
 * @author rask
 *
 */
public class ShipProperties {
	
	private static ShipProperties instance = null;
	private int shipNumber = 0;
	private int sleepTime;
	private boolean toUpload;
	private boolean toUnload;
	private int priority;
	
	/**
	 * Ship properties singleton instance
	 * @return returns current state of singleton
	 */
	public static ShipProperties getInstance() {
		if (instance == null) {
			// Thread Safe. Might be costly operation in some case
			synchronized (ShipProperties.class) {
				if (instance == null) {
					instance = new ShipProperties();
					
				}
			}
		}
		return instance;
	}
	/**
	 * Setting time of staying in port
	 * @param number is a sleep time
	 */
	public void setSleepTime(int number){
		
		this.sleepTime = number;
		
	}
	
	/**
	 * Setting if ship will unload cargo from port
	 * @param number true/false
	 */
	public void setUnload(boolean number){
		
		this.toUnload = number;
		
	}

	/**
	 * Setting if ship will upload cargo to port
	 * @param number true/false
	 */
	public void setUpload(boolean number){
	
	this.toUpload = number;
	
	}
	
	/**
	 * Getting time ship will stay in port
	 * @return time
	 */
	public int getSleepTime(){
		
		return this.sleepTime;
		
	}
	
	/**
	 * Finding out if ship will unload cargo in port
	 * @return true/false
	 */
	public boolean getUnload(){
		
		return this.toUnload;
		
	}
	
	/**
	 * Finding out if ship will upload cargo to port
	 * @return true/false
	 */
	public boolean getUpload(){
		
		return this.toUpload;
		
	}
	
	/**
	 * Setting generated ship number
	 * @param number number
	 */
	public void setShipNumber(int number){
		
		this.shipNumber = number;
		
		}
		
	/**
	 * Getting ship number	
	 * @return ship number
	 */
	public int getShipNumber(){
			
		return this.shipNumber;
			
	}
	
	/**
	 * Getting priority of ship
	 * @return priority
	 */
	public int getPriority(){
		
		return this.priority;
		
	}
	
	/**
	 * Setting priority of ship
	 * @param priority
	 */
	public void setPriority(int priority){
		
		this.priority = priority;
		
	}

}
