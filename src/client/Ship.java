package client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

import javax.swing.SwingUtilities;

import server.PortSingleton;

public class Ship implements Runnable{
	
	
	private int portNumber;
	private int shipNumber;
    private boolean isFull;
    private boolean toUnloadCargo;
    private boolean toGetCargo;
    private boolean isMoored = false;
    private int sleepTime;
    
    
	public Ship(int ship, int time, boolean toUn, boolean toGet){
		
		this.shipNumber = ship;
		this.toUnloadCargo = toUn;
		this.toGetCargo = toGet;
		this.sleepTime = time;
		
	}
	
	public void setSleepTime(int time){
		
		this.sleepTime = time;
		
	}
	
	public void setUnload(boolean cargo){
		
		this.toUnloadCargo = cargo;
		
	}
	
	public void setUpload(boolean cargo){
	
	this.toGetCargo = cargo;
	
	}
	
	
	public void setIsFull(boolean full){
		
		this.isFull = full;
		
	}
	
	public boolean getIsFull(){
		
		return this.isFull;
		
	}
	
	void setShipNumber(int number){
		
		this.shipNumber = number;
		
	}
	
	public int getShipNumber(){
		
		return this.shipNumber;
		
	}
	
	void findThePort(){
		
		try {
			int portNumber = PortSingleton.getInstance().getFreePort() - 1;
			if(portNumber != 9998){
				
				this.portNumber = portNumber + 1;
				attachToPort(portNumber);
				
			}
			else{
				
				System.out.print("Ship # " + this.shipNumber + " is waiting\n");
				return;	
			}
		} catch (InterruptedException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		
		while(!this.isMoored){
			
			findThePort();
			
		}
		
	}
	
	void attachToPort(int port) throws InterruptedException{
		
		//this.isMoored = true;
		Thread.sleep(2000);
		PortSingleton.getInstance().attachToPort(this, port);
		shipActions();
				
	}
	
	public void setIsMoored(boolean or){
		
		this.isMoored = or;
		
	}
	
	public void shipActions(){
		
		if(this.toGetCargo){
			
			getCargo();
			
		}
		
		if(this.toUnloadCargo){
			
			unloadCargo();
			
		}
		
		if(this.sleepTime > 0){
			
			stayInPort();
			
		}
		
		unmoorFromPort();
		this.isMoored = true;
		
		Thread.interrupted();
		System.out.print("Ship # " + this.shipNumber + " is done\n");
		
				
	}
	
	
	public void getCargo(){
		
		try {
			Thread.sleep(10000);
			this.toGetCargo = false;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		
	public void unloadCargo(){
		
		try {
			Thread.sleep(6500);
			this.toUnloadCargo = false;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void stayInPort(){
		
		try {
			Thread.sleep(this.sleepTime * 1000);
			this.sleepTime = 0;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void unmoorFromPort(){
		
		try {
			Thread.sleep(3000);
			PortSingleton.getInstance().removeFromPort(this, this.portNumber - 1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	 
}
