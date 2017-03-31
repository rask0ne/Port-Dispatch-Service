package client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

import javax.swing.SwingUtilities;

import org.eclipse.swt.widgets.Display;

import server.PortSingleton;

import swt.body.TableWindow;

public class Ship implements Runnable{
	
	
	private int portNumber;
	private int shipNumber;
    private boolean isFull;
    private boolean toUnloadCargo = true;
    private boolean toGetCargo = true;
    private boolean isMoored = false;
    private int sleepTime;
    
    
	public Ship(int ship, int time, boolean toUn, boolean toGet){
		
		this.shipNumber = ship;
		this.toUnloadCargo = toUn;
		this.toGetCargo = toGet;
		this.sleepTime = time;
		
		TableWindow.addNewShip(this);
		
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
		PortSingleton.getInstance().attachToPort(this, port);
		Thread.sleep(2000);
		shipActions();
				
	}
	
	public void setIsMoored(boolean or){
		
		this.isMoored = or;
		
	}
	
	public void shipActions(){
		
		if(this.toGetCargo == true){
			
			getCargo();
			
		}
		
		if(this.toUnloadCargo == true){
			
			unloadCargo();
			
		}
		
		if(this.sleepTime > 0){
			
			stayInPort();
			
		}
		
		unmoorFromPort();
		removeFromTable(this);
		this.isMoored = true;
		Thread.interrupted();
		
				
	}
	
	
	public void getCargo(){
		
		try {
			messageToTable(this, "Getting Cargo");
			Thread.sleep(10000);
			this.toGetCargo = false;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		
	public void unloadCargo(){
		
		try {
			messageToTable(this, "Unloading Of Cargo");
			Thread.sleep(6500);
			this.toUnloadCargo = false;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void stayInPort(){
		
		try {
			messageToTable(this, "Staying In Port");
			long timeout= System.currentTimeMillis();
			Thread.sleep(this.sleepTime * 1000);
			timeout = System.currentTimeMillis() - timeout;
			if(timeout > sleepTime * 1000)
				System.out.println("Time-out Ship #" + String.valueOf(this.getShipNumber()));
			this.sleepTime = 0;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void unmoorFromPort(){
		
		try {
			messageToTable(this, "Unmooring From Port");
			Thread.sleep(3000);
			PortSingleton.getInstance().removeFromPort(this, this.portNumber - 1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	public int getPortNumber(){
		
		return this.portNumber;
		
	}
	
	void messageToTable(Ship ship, String str){
		
		Display.getDefault().syncExec(new Runnable() {
			   public void run() {
				   	
				   TableWindow.ShipAction(ship, str);
		
			   }
		});
	}
	
	void removeFromTable(Ship ship){
		
		Display.getDefault().syncExec(new Runnable() {
			   public void run() {
				   	
				   TableWindow.removeShip(ship);
		
			   }
		});
	}
	 
}
