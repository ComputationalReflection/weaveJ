package profilerPackage;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/*
 * class allows to get the time that a function takes time execute.
 */
public class Profiler {

	private long time;
	private long timeDemo;
	//private static PrintWriter writer; 
	private Logger logger ;
	
	
	public Profiler(){
		//PropertyConfigurator.configure("../log4j.properties");
		time = 0;
		timeDemo = 0;
		/*try {
			writer = new PrintWriter("c:/ProfilerJasco.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */
		logger = Logger.getLogger("MyProfiler");
	}
	
	
	public long getTime(){
		return time;
	}
	
	public long getTimeDemo(){
		return timeDemo;
	}
	
	
	public void initTime(){
		time = System.currentTimeMillis();
	}
	
	public void initTimeDemo(){
		timeDemo = System.currentTimeMillis();
	}
	
	
	public long endTime(){
		long aux = System.currentTimeMillis();
		long devolver = aux - time;
		time = 0;
		return devolver;
	}
	
	
	public long endTimeDemo(){
		long aux = System.currentTimeMillis();
		long devolver = aux - timeDemo;
		timeDemo = 0;
		return devolver;
	}
	
	
	public void writeProfiler(String message){
		//writer.println(message);
		logger.info(message);
	}
	
	/*
	public void closeProfiler(){
		writer.close();
	}*/


		
}
