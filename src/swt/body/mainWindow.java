package swt.body;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import client.Ship;
import client.ShipNumber;
import client.ShipProperties;

public class mainWindow {
	
	public static void main(String[] args) {
		
		ShipNumber number = new ShipNumber();
		
	    Display display = new Display();
	    Shell shell = new Shell(display);
	    shell.setSize(300, 300);
	    shell.setText("Add Ship!");
	    shell.setLayout(new RowLayout());
	    
	   

	   
	    
	    final Combo c1 = new Combo(shell, SWT.READ_ONLY);
	    c1.setBounds(50, 50, 150, 65);
	    
	    final Combo c2 = new Combo(shell, SWT.READ_ONLY);
	    c2.setBounds(50, 85, 150, 65);
	    c2.setEnabled(true);
	    
	    final Button button = new Button(shell, SWT.PUSH);
	    button.setText("Create Ship");
	    
	    final Text text = new Text(shell, SWT.SHADOW_IN);
	    
	    String items[] = { "5 sec", "10 sec", "15 sec", "20 sec" };
	    String load[] = { "to Unload", "to Upload", "Just Stay" };
	    c1.setItems(items);
	    c1.addSelectionListener(new SelectionAdapter() {
	      public void widgetSelected(SelectionEvent e) {
	        if (c1.getText().equals("5 sec")) {
	        
	        	
	        	ShipProperties.getInstance().setSleepTime(5);
	        	text.setText("");
	        	
	        } else if (c1.getText().equals("10 sec")) {
	          
	        	ShipProperties.getInstance().setSleepTime(10);
	        	text.setText("");
	        	
	        } else if (c1.getText().equals("15 sec")) {
		          
	        	ShipProperties.getInstance().setSleepTime(15);
		        text.setText("");
		        
	        }else {
	        	
	        	ShipProperties.getInstance().setSleepTime(20);
	        	text.setText("");
	        	
	        }
	      }
	    });
	    
	    c2.setItems(load);
	    c2.addSelectionListener(new SelectionAdapter() {
		      public void widgetSelected(SelectionEvent e) {
		        if (c1.getText().equals("to Unload")) {
		        
		        	ShipProperties.getInstance().setUnload(true);
		        	ShipProperties.getInstance().setUpload(false);
		        	text.setText("");
		        	
		        } else if (c1.getText().equals("to Upload")) {
		          
		        	ShipProperties.getInstance().setUnload(false);
		        	ShipProperties.getInstance().setUpload(true);
		        	text.setText("");
		        	
		        }else {
		        	
		        	ShipProperties.getInstance().setUnload(false);
		        	ShipProperties.getInstance().setUpload(false);
		        	text.setText("");
		        	
		        }
		      }
		    });
	    
	    button.addSelectionListener(new SelectionListener() {

	    	
		      public void widgetSelected(SelectionEvent event) {
		    	(new Thread(new Ship(number.getNumber(), ShipProperties.getInstance().getSleepTime(), ShipProperties.getInstance().getUnload()
		    			,ShipProperties.getInstance().getUpload()))).start();
		        text.setText("Ship Created!");
		        number.setNumber();
		      }

		      public void widgetDefaultSelected(SelectionEvent event) {
		    	  
		    	  (new Thread(new Ship(number.getNumber(), ShipProperties.getInstance().getSleepTime(), ShipProperties.getInstance().getUnload()
			    			,ShipProperties.getInstance().getUpload()))).start();
		        text.setText("Ship Created!");
		        number.setNumber();
		      }
		    });

	    
	    

	    
	    shell.open();
	    while (!shell.isDisposed()) {
	      if (!display.readAndDispatch())
	        display.sleep();
	    }
	    display.dispose();
	  }
	
}
