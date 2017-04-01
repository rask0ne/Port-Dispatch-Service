package client;

import server.PortSingleton;

public class ShipProperties {
	
	private static ShipProperties instance = null;
	private int shipNumber = 0;
	private int sleepTime;
	private boolean toUpload;
	private boolean toUnload;
	private int priority;
	
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
	
	public void setSleepTime(int number){
		
		this.sleepTime = number;
		
	}
	
	public void setUnload(boolean number){
		
		this.toUnload = number;
		
	}

	public void setUpload(boolean number){
	
	this.toUpload = number;
	
	}
	
	
	public int getSleepTime(){
		
		return this.sleepTime;
		
	}
	
	public boolean getUnload(){
		
		return this.toUnload;
		
	}
	
	public boolean getUpload(){
		
		return this.toUpload;
		
	}
	
	public void setShipNumber(int number){
		
		this.shipNumber = number;
		
		}
		
		
	public int getShipNumber(){
			
		return this.shipNumber;
			
	}
	
	public int getPriority(){
		
		return this.priority;
		
	}
	
	public void setPriority(int priority){
		
		this.priority = priority;
		
	}

}
