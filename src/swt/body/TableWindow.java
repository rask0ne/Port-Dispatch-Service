package swt.body;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import client.Ship;

public class TableWindow {
	
	// Display d;

	  Shell s;
	  static Table t;

	  void TableShell(Shell s) {
	    
		//this.d = d;
	    this.s = s;

	    s.setSize(390, 500);
	    
	    s.setText("A Table Shell Example");
	    s.setLayout(new FillLayout());

	    t = new Table(s, SWT.BORDER);

	    TableColumn tc1 = new TableColumn(t, SWT.CENTER);
	    TableColumn tc2 = new TableColumn(t, SWT.CENTER);
	    TableColumn tc3 = new TableColumn(t, SWT.CENTER);
	    tc1.setText("Ship Number");
	    tc2.setText("Action");
	    tc3.setText("Port Number");
	    tc1.setWidth(100);
	    tc2.setWidth(180);
	    tc3.setWidth(100);
	    t.setHeaderVisible(true);


	    s.open();
	   /* while (!s.isDisposed()) {
	      if (!d.readAndDispatch())
	        d.sleep();
	    }
	    d.dispose();*/
	  }
	  
	  public static void addNewShip(Ship ship){
		  
		  TableItem item = new TableItem(t, SWT.NONE);
		  
		  String num = String.valueOf(ship.getShipNumber());
		  String action = "Searching Port";
		  
		  item.setText(new String[] { num, action, ""});
		  
	  }
	  
	  public static void ShipAction(Ship ship, String update){
		  
		  String num = String.valueOf(ship.getShipNumber());
		  TableItem[] items = t.getItems ();
		  
	        for(int i=0; i < items.length; i++) {
	        	
	            TableItem item = items[i];
	            String text = items[i].getText(0);  
	            if(text.equals(num)){
	            	
	            	  String numShip = String.valueOf(ship.getShipNumber());
	        		  String numPort = String.valueOf(ship.getPortNumber());
	        		  
	        		  item.setText(new String[] { numShip, update, numPort});
	        		  break;
	        		         		
	            }
	        }
		  
		
	  }
	  
	  public static void removeShip(Ship ship){
		  	
		  String num = String.valueOf(ship.getShipNumber());
		  TableItem[] items = t.getItems ();
		  
	        for(int i=0; i < items.length; i++) {
	        	
	            TableItem item = items[i];
	            String text = items[i].getText(0);  
	            if(text.equals(num)){
	            	
	            	t.remove(i);
	            	break;
	            		
	            }
	        }
		  
	  }
}
