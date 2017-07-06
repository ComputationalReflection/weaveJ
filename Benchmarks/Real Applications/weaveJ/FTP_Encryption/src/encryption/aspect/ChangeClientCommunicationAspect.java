package encryption.aspect;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import encryption.chat.Program;

public class ChangeClientCommunicationAspect {
	protected static boolean ACKSended;

	protected static DataInputStream srReceiver;

	protected static DataOutputStream swSender;

	private static Socket tcpServer;
	private static void Connect() throws NumberFormatException, UnknownHostException, IOException {
		// System.out.println("Connect initial");
		// System.out.println("Connecting the client aspect");
		// Load WSDL
		// XmlDocument config = new XmlDocument();
		// config.Load("aspect.config");
		String ip = "127.0.0.1"; // config.SelectSingleNode("//config/ip").InnerText;
		String port = "5000"; // config.SelectSingleNode("//config/port").InnerText;
		tcpServer = new Socket(InetAddress.getByName(ip), Integer.parseInt(port));
		// System.out.println("(Aspect Client)Connected to " + ip + ":" + port);
		swSender = new DataOutputStream(tcpServer.getOutputStream());
		srReceiver = new DataInputStream(tcpServer.getInputStream());

	}

	public static DataOutputStream getSwSenderAround(MethodHandle m, Program p) {
			//System.out.println("Client.............");

		try
	    	{
	    		
	           if (!ACKSended)
	             {
	        	   
				Class myType = p.getClass();
	                 Field[] myFields = myType.getDeclaredFields();
	               
	                 for (int i = 0; i < myFields.length; i++)
	                 {
	                     if (myFields[i].getName().equals("swSender"))
	                     {
	                    	 DataOutputStream originalSwSender;
						originalSwSender = (DataOutputStream) myFields[i].get(p);
							 originalSwSender.writeChars("\n");	
							 originalSwSender.flush();
	                         ACKSended = true;
	                     }
	                 }
	                 
	             }
	           
	           
	            	 if (swSender == null)
	                     Connect();
	                 return swSender;

			/*
			 * if (methodName.equals("getSrReceiver")) { if (swSender == null)
			 * Connect(); return srReceiver; }
			 */
	         
	    	
	     	} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	        return null;
		
		
	}
	private boolean isEnabled = true;

	public boolean isEnabled() {
		return isEnabled;
	}



	public void isEnabled(boolean enabled) {
		isEnabled = enabled;
	}
}
