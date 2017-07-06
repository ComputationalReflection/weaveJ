package encryption.jasco;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import jasco.runtime.aspect.*;

class ChangeClientCommunicationAspect {
	
	private boolean isEnabled=true;
	
	
	public boolean isEnabled() {
		return isEnabled;
	} 
	
	
	
	public void isEnabled(boolean enabled) {
		isEnabled=enabled;
	}
     
	
	hook ClientAround {
		
		private static Socket tcpServer;
	    protected static DataOutputStream swSender;
	    protected static DataInputStream srReceiver;
	    protected static boolean ACKSended;

	     private static void Connect() throws NumberFormatException, UnknownHostException, IOException
	     {
	    	 //System.out.println("Connect initial");
	         //System.out.println("Connecting the client aspect");
	         //Load WSDL
	         //XmlDocument config = new XmlDocument();
	         //config.Load("aspect.config");
	         String ip = "127.0.0.1"; //config.SelectSingleNode("//config/ip").InnerText;
	         String port = "5000"; //config.SelectSingleNode("//config/port").InnerText;
	         tcpServer = new Socket(InetAddress.getByName(ip), Integer.parseInt(port));
	         //System.out.println("(Aspect Client)Connected to " + ip + ":" + port);
	         swSender = new DataOutputStream(tcpServer.getOutputStream());
	         srReceiver = new DataInputStream(tcpServer.getInputStream());
	     }
		
	     isApplicable() {
				return global.isEnabled();
			}
	     
		ClientAround(method(..args)) {
			execution(method);
		}
		
		around() {
			//System.out.println("Client.............");
			try
	    	{
	    		
	           if (!ACKSended)
	             {
	        	   
	                 Class myType = thisJoinPoint.getCalledObject().getClass();
	                 Field[] myFields = myType.getDeclaredFields();
	               
	                 for (int i = 0; i < myFields.length; i++)
	                 {
	                     if (myFields[i].getName().equals("swSender"))
	                     {
	                    	 DataOutputStream originalSwSender;
	                         originalSwSender = (DataOutputStream)myFields[i].get(thisJoinPoint.getCalledObject());
							 originalSwSender.writeChars("\n");	
							 originalSwSender.flush();
	                         ACKSended = true;
	                     }
	                 }
	                 
	             }
	           
	           
	             if (thisJoinPoint.getName().equals("getSwSender"))
	             {
	            	 if (swSender == null)
	                     Connect();
	                 return swSender;
	             }
	             if (thisJoinPoint.getName().equals("getSrReceiver"))
	             {
	            	 if (swSender == null)
	                     Connect();
	                 return srReceiver;
	             }
	         
	    	
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
	}
}
