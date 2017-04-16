package client;

/**
 * Class which generates number(name) of new ship
 * @author rask
 *
 */
public class ShipNumber {
	
	/**
	 * Initialization of number
	 */
	private int shipNumber = 1;
	
	/**
	 * Increment of number
	 */
	public void setNumber(){
		
		this.shipNumber += 1;
		
	}
	
	/**
	 * Getting number
	 * @return number
	 */
	public int getNumber(){
		
		return this.shipNumber;
		
	}
	
	

}
