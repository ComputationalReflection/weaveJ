package encryption.jasco;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

import jasco.runtime.aspect.*;

class ChangeServerCommunicationAspect {
	private boolean isEnabled=true;
	
	
	public boolean isEnabled() {
		return isEnabled;
	} 
	

	
	
	public void isEnabled(boolean enabled) {
		isEnabled=enabled;
	}
	 
     
     
	hook ServerAround {
		private static Socket tcpServer;
	    private static DataOutputStream swSender;
	    private static DataInputStream srReceiver;
	    private static boolean ACKSended;
		
		

	     private static ServerSocket tlsClient;
	     
	     isApplicable() {
				return global.isEnabled();
			}

	     private static void Connect() throws IOException
	     {
	         //System.out.println("Connecting the server aspect");
	         //Load WSDL
	         //XmlDocument config = new XmlDocument();
	         //config.Load("aspect.config");            
	         String port = "6000"; //config.SelectSingleNode("//config/port").InnerText;
	         tlsClient =  new ServerSocket();
	         tlsClient.bind(new InetSocketAddress(InetAddress.getByName("127.0.0.1"), Integer.parseInt("6000")));
	         //System.out.println("Waiting for clients");
	         tcpServer = tlsClient.accept();
	         //System.out.println("Connected on port " + port);
	         swSender = new DataOutputStream(tcpServer.getOutputStream());
	         srReceiver = new DataInputStream(tcpServer.getInputStream());
	     }
		
		ServerAround(method(..args)) {
			execution(method);
		}
		
		around() {
			//System.out.println("Server ................");
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
	                    	 DataOutputStream originalSwSender = (DataOutputStream)myFields[i].get(thisJoinPoint.getCalledObject());
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
