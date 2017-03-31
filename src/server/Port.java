package server;

import java.util.ArrayList;

import org.eclipse.swt.widgets.Display;

import client.Ship;
import swt.body.TableWindow;

public class Port {

	
	private ArrayList<Ship> ship= new ArrayList<Ship>();
	private final int portNumber;
	private boolean pierIsEmpty;
	private int stock;
	private int shipNumber = ship.size();
	private final int shipsLimit;	
	
	Port(int port, int limit){
		
		this.portNumber = port;
		this.pierIsEmpty = true;
		this.stock = 0;
		this.shipsLimit = limit;
		
	}
	
	synchronized void gotMoored(Ship ship){
		
		if(this.ship.size() >= this.shipsLimit)
			return;
		else{
			
			this.pierIsEmpty = false;
			this.shipNumber = ship.getShipNumber();
			this.ship.add(ship);
		
			if(this.ship.size() == this.shipsLimit)
				this.pierIsEmpty = false;
		}
		
	}
	
	synchronized void gotUnmoored(Ship ship){
		
		this.ship.remove(ship);
		this.pierIsEmpty = true;
			
	}
	
	synchronized void gotUnload(Ship ship){
		
		if(ship.getIsFull() == true){
			
			if(this.stock < 3){
				
				ship.setIsFull(false);
				this.stock += 1;
			}
			else return;
		}
		
	}
	
	synchronized void gotUpload(Ship ship){
		
		if(this.pierIsEmpty == false && ship.getIsFull() == false){
			
			if(this.stock != 0){
				
				ship.setIsFull(true);
				this.stock -= 1;
			}
		}
		
	}
	
	public int getPortNumber(){
		
		return this.portNumber;
		
	}
	
	public boolean getPierIsEmpty(){
		
		return this.pierIsEmpty;
		
	}
	
	public int getStock(){
		
		return this.stock;
		
	}
	
	void setStock(int stock){
		
		this.stock = stock;
		
	}
	
	synchronized public int getFreePlaces(){
		
		return (this.shipsLimit - this.ship.size());
		
	}
	
	synchronized void attachShip(Ship ship){
		
		this.ship.add(ship);
		messageToTable(ship, "Is Mooring To Port");
		System.out.print("Ship # " + ship.getShipNumber() + " is moored to port " + this.portNumber + "\n");
		ship.setIsMoored(true);
			
	}
	
	
	synchronized void removeShip(Ship ship){
		
		this.ship.remove(ship);
		System.out.print("Ship # " + ship.getShipNumber() + " is removed from port " + this.portNumber + "\n");
		ship.setIsMoored(false);
			
	}
	
	void messageToTable(Ship ship, String str){
		
		Display.getDefault().syncExec(new Runnable() {
			   public void run() {
				   	
				   TableWindow.ShipAction(ship, str);
		
			   }
		});
	}
	
}
