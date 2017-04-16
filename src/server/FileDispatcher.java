package server;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Class which gets current state of ports
 * @author rask
 *
 */
public class FileDispatcher implements Runnable{

	//private FileWriter writer;
	private int time = 0;
	
	/**
	 * Constructor
	 * @throws IOException
	 */
	public FileDispatcher() throws IOException{
		
		/*this.writer = new FileWriter("note.txt", true);
		writer.write("sdasdadasdad");*/
		
	}
	@Override
	/**
	 * Run method. Every 5 second writes state of ports in txt file
	 */
	public void run() {
		
		FileWriter writer;
		try {
			writer = new FileWriter("note.txt", false);
			writer.write("");
			writer.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		while(true){
				
			try(FileWriter mainWriter = new FileWriter("note.txt", true)) {	
				
				Thread.sleep(5000);
				this.time += 5;
			
				String str = ( "================= " + String.valueOf(this.time) + " sec =================" );
				mainWriter.write(str);
				mainWriter.flush();
				mainWriter.append(System.lineSeparator());
				mainWriter.flush();
				mainWriter.append(System.lineSeparator());
				
				str = null;
				str = ( PortSingleton.getInstance().getPortsInfo());
			
				mainWriter.flush();
				mainWriter.write(str);
				mainWriter.flush();
				mainWriter.append(System.lineSeparator());
				mainWriter.flush();
				mainWriter.append(System.lineSeparator());
				
				mainWriter.flush();
				mainWriter.close();
										
			} catch (InterruptedException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
