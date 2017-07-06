package encryption.chat;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.EnumVariant;
import com.jacob.com.Variant;


public class Memory {

	public static int getPeakMemoryUsage (String process) {
		String host = "localhost"; //Technically you should be able to connect to other hosts, but it takes setup
		String connectStr = String.format("winmgmts:\\\\%s\\root\\CIMV2", host);
		String query = "Select * from Win32_Process"; //Started = 1 means the service is running.
		ActiveXComponent axWMI = new ActiveXComponent(connectStr); 
		//Execute the query
		Variant vCollection = axWMI.invoke("ExecQuery", new Variant[] { new Variant(query) });
		int peakMemorySize = 0;

		//Our result is a collection, so we need to work though the.
		EnumVariant enumVariant = new EnumVariant(vCollection.toDispatch());
		Dispatch item = null;
		while (enumVariant.hasMoreElements()) { 
			item = ((Variant) enumVariant.nextElement()).toDispatch();
			
			//Dispatch.call returns a Variant which we can convert to a java form.
			String serviceName = Dispatch.call(item,"Name").toString();
			if (serviceName.equalsIgnoreCase(process)) {
				String commandLine = Dispatch.call(item,"CommandLine").toString();
				System.out.println(commandLine);
				peakMemorySize = Dispatch.call(item,"PeakWorkingSetSize").getInt();
				System.out.println(commandLine + " " + peakMemorySize );
			}
		} 
		return peakMemorySize;

	}

	//public static void main(String[] args) {
	//	System.out.println(Memory.getPeakMemoryUsage("java.exe"));
	//}
	
}
