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

import org.apache.logging.log4j.Logger; 
import org.apache.logging.log4j.LogManager;

/**
 * Ship model
 * @author rask
 *
 */
public class Ship implements Runnable{
	
	private static final Logger log4j = LogManager.getLogger(Logger.class 
	        .getName());
	
	private boolean isNotified = false;
	private int portNumber;
	private int shipNumber;
    private boolean isFull;
    private boolean toUnloadCargo = true;
    private boolean toGetCargo = true;
    private boolean isMoored = false;
    private int sleepTime;
    private int priority;
    
    /**
     * Class constructor. Runs ship thread and sets its priority
     * @param ship number of ship
     * @param time which ship will stay in port
     * @param toUn if ship will unload cargo
     * @param toGet if ship will upload cargo
     * @param priority of ship
     */
	public Ship(int ship, int time, boolean toUn, boolean toGet, int priority){
		
		Thread t = new Thread(this);
		t.setPriority(priority);
		Thread.currentThread().setPriority(priority);
		
		this.shipNumber = ship;
		this.toUnloadCargo = toUn;
		this.toGetCargo = toGet;
		this.sleepTime = time;
		this.priority = priority;
		
		addToTable(this);
		
		log4j.debug("Ship #" + this.getShipNumber() + " created"); 
		
	}
	
	/**
	 * Sets time ship will stay in port
	 * @param time of moor
	 */
	public void setSleepTime(int time){
		
		this.sleepTime = time;
		
	}
	
	/**
	 * Setting if ship will unload cargo
	 * @param cargo true/false
	 */
	public void setUnload(boolean cargo){
		
		this.toUnloadCargo = cargo;
		
	}
	
	/**
	 * Setting if ship will get cargo from port
	 * @param cargo true/false
	 */
	public void setUpload(boolean cargo){
	
	this.toGetCargo = cargo;
	
	}
	
	/**
	 * Setting if ship if full of cargo
	 * @param full true/false
	 */
	public void setIsFull(boolean full){
		
		this.isFull = full;
		
	}
	
	/**
	 * Getting if ship is full of cargo
	 * @return true/false
	 */
	public boolean getIsFull(){
		
		return this.isFull;
		
	}
	
	/**
	 * Setting ship number
	 * @param number of ship
	 */
	void setShipNumber(int number){
		
		this.shipNumber = number;
		
	}
	
	/**
	 * Getting ship number
	 * @return number
	 */
	public int getShipNumber(){
		
		return this.shipNumber;
		
	}
	
	/**
	 * Synchronized method in which ship thread is searching for free port
	 */
	synchronized void findThePort(){
		
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
	
	/**
	 * Searching port while didn't find it
	 */
	public void run() {

		while(!this.isMoored){
			
			findThePort();
			
		}
		
	}
	
	/**
	 * If ship found the port, it attaches to it and does actions
	 * @param port number of port ship will attach
	 * @throws InterruptedException
	 */
	synchronized void attachToPort(int port) throws InterruptedException{

		
		//this.isMoored = true;
		PortSingleton.getInstance().attachToPort(this, port);
		Thread.sleep(2000);
		log4j.debug("Ship #" + this.getShipNumber() + " attached to port #" + this.getPortNumber()); 
		shipActions();
				
	}
	
	/**
	 * Setting that ship is moored to port or not
	 * @param or true/false
	 */
	public void setIsMoored(boolean or){
		
		this.isMoored = or;
		
	}
	
	/**
	 * Ship performs its tasks according to properties
	 * @throws InterruptedException
	 */
	public void shipActions() throws InterruptedException{
		
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
	
	/**
	 * Getting priority of ship
	 * @return
	 */
	public int getPriority(){
		
		return this.priority;
		
	}
	
	/**
	 * Method connected to GUI. Main idea is a process of getting cargo from port
	 */
	public void getCargo(){
		
		try {
			messageToTable(this, "Getting Cargo");
			Thread.sleep(10000);
			this.toGetCargo = false;
			log4j.debug("Ship #" + this.getShipNumber() + " got cargo"); 
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Method connected to GUI. Main idea is a process of unloading cargo to port
	 */
	public void unloadCargo(){
		
		try {
			messageToTable(this, "Unloading Of Cargo");
			Thread.sleep(6500);
			this.toUnloadCargo = false;
			log4j.debug("Ship #" + this.getShipNumber() + " unloaded cargo"); 
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Method connected to GUI. Main idea is a process of staying in port
	 */
	public void stayInPort(){
		
		try {
			log4j.debug("Ship #" + this.getShipNumber() + " staying in port" + this.getPortNumber()); 
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
	
	/**
	 * Method connected to GUI. Main idea is unmooring from port
	 * @throws InterruptedException
	 */
	public void unmoorFromPort() throws InterruptedException{
		
		messageToTable(this, "Unmooring From Port");
		Thread.sleep(3000);
		PortSingleton.getInstance().removeFromPort(this, this.portNumber - 1);
		log4j.debug("Ship #" + this.getShipNumber() + " unmoored"); 
			
	}
	
	/**
	 * Getting port number ship connects
	 * @return port number
	 */
	public int getPortNumber(){
		
		return this.portNumber;
		
	}
	
	/**
	 * Gui method. Adding ship to table
	 * @param ship
	 */
	void addToTable(Ship ship){
		
		Display.getDefault().syncExec(new Runnable() {
			   public void run() {
				   	
				   TableWindow.addNewShip(ship);
		
			   }
		});
		
	}
	
	/**
	 * GUI method. Adding message to table of current state of ship
	 * @param ship ship
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
	 * GUI method. Removing ship from table
	 * @param ship
	 */
	void removeFromTable(Ship ship){
		
		Display.getDefault().syncExec(new Runnable() {
			   public void run() {
				   	
				   TableWindow.removeShip(ship);
		
			   }
		});
	}
	 
}
