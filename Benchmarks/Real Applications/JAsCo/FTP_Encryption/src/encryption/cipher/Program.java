package encryption.cipher;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Program {

	 private DataOutputStream swSenderServer;
     private DataInputStream srReceiverServer;
     private Socket tcpServer;
     private boolean connectedServer;
     private Thread thrMessagingServer;

     private DataOutputStream swSenderClient;
     private DataInputStream srReceiverClient;
     private Socket tcpClient;
     private boolean connectedClient;
     private Thread thrMessagingClient;

     
     private ServerSocket serverSocket;


     public Program()
     {
         
     }
     private boolean encrypt = false;

     


     
     public boolean isEncrypt() {
		return encrypt;
	}

	public void setEncrypt(boolean encrypt) {
		this.encrypt = encrypt;
	}

	public void ConectToServer(String ip, String port) throws NumberFormatException, UnknownHostException, IOException
     {
		System.out.println("Trying " +  ip + " : " + port);
         tcpServer = new Socket(InetAddress.getByName(ip), Integer.parseInt(port));
         System.out.println("Connected to " +  ip + " : " + port);
         connectedServer = true;
         swSenderServer = new DataOutputStream(tcpServer.getOutputStream());
     }

     /// <summary>
     /// Method for connecting with client. Listen in a port, for the client, and connect to a server.
     /// </summary>
     /// <param name="port">Port to listen </param>
     /// <param name="serverIp">Ip of the server.</param>
     /// <param name="serverPort">Port of the server.</param>
     public void ConectToClient(String port, String serverIp, String serverPort) throws IOException
     {
    	 serverSocket = new ServerSocket();
         serverSocket.bind(new InetSocketAddress(InetAddress.getByName(serverIp), Integer.parseInt(port)));
         //tcpListener.Start();
         //tcpClient = tcpListener.AcceptTcpClient();
         tcpClient = serverSocket.accept();
    	 System.out.println("Connected in " + port);
         ConectToServer(serverIp, serverPort);
         connectedClient = true;
         swSenderClient = new DataOutputStream(tcpClient.getOutputStream());
     }

     private void ReceiveMessagesServer() throws IOException
     {
    	 srReceiverServer = new DataInputStream(tcpServer.getInputStream());
    	 
         while (connectedServer)
         {
        	 UpdateLogServer(srReceiverServer.readUTF());
         }
         this.RunClosed();
     }

     private void ReceiveMessagesClient() throws IOException
     {
    	 srReceiverClient = new DataInputStream(tcpClient.getInputStream());
    	
         while (connectedClient)
         {
        	 UpdateLogClient(srReceiverClient.readUTF());
         }
         this.RunClosed(); 
     }


     private void UpdateLogServer(String message) throws IOException
     {
         String messageToSend = message;
         //System.out.println(" (clean Server) >> " + message);
         if (encrypt)
             messageToSend = encryption.cipher.Cipher.Decrypt(message);
         if (encrypt && messageToSend.equals("endsdecrypt"))
         {
             CloseServerConnection();
             swSenderClient.writeUTF("\n");
             swSenderClient.flush();
             CloseClientConnection();
             //this.Dispose();
         }
         else
         {
             SendMessageToClient(messageToSend);
             //tbServer.AppendText(">> " + message + "\n");
             if (message.equals("exit") || encryption.cipher.Cipher.Decrypt(message).equals("exit"))
                 CloseServerConnection();
         }
     }

     private void UpdateLogClient(String message) throws IOException
     {
         String messageToSend = message;
         //System.out.println("(clean Client) >> " + message);
         if (!encrypt)
             messageToSend = encryption.cipher.Cipher.Decrypt(message);         
         if (!encrypt && messageToSend.equals("endsdecrypt"))
         {

             CloseClientConnection();
             swSenderServer.writeUTF("\n");
             swSenderServer.flush();
             CloseServerConnection();
             //this.Dispose();
         }
         else
         {
             SendMessageToServer(messageToSend);
             //tbClient.AppendText(">> " + message + "\n");
             //System.out.println(">> " + message);
             if (message.equals("exit") || encryption.cipher.Cipher.Decrypt(message).equals("exit"))
                 CloseClientConnection();
         }
     }

     private void SendMessageToClient(String message) throws IOException
     {
    	 if (!connectedClient) return;            
         if (!encrypt)
             message = encryption.cipher.Cipher.Encrypt(message);
         
         swSenderClient.writeUTF(message);
         swSenderClient.flush();
         //tbClient.AppendText("<< " + message + "\n");
         if (message.equals("exit") || encryption.cipher.Cipher.Decrypt(message).equals("exit"))
             CloseClientConnection();
     }

     private void SendMessageToServer(String message) throws IOException
     {
    	 if (!connectedServer) return;
         if (encrypt)
             message = encryption.cipher.Cipher.Encrypt(message);         
         //System.out.println("(send Server) >> " + message + " ___ " + encryption.cipher.Cipher.Decrypt(message));
         swSenderServer.writeUTF(message);
         swSenderServer.flush();
         //tbServer.AppendText("<< " + message + "\n");
         if (message.equals("exit") || encryption.cipher.Cipher.Decrypt(message).equals("exit"))
             CloseServerConnection();
     }

     private void CloseClientConnection() throws IOException
     {
         System.out.println( "Disconnected");
         connectedClient = false;
         //thrMessagingClient.join();
         swSenderClient.close();
         srReceiverClient.close();
         tcpClient.close();         
     }

     private void CloseServerConnection() throws IOException
     {
         System.out.println("Disconnected");
         connectedServer = false;
         //thrMessagingServer.join()();
         swSenderServer.close();
         //srReceiverServer.close();
         tcpServer.close();
         
     }

     public void run() throws IOException
     {
         //if (!encrypt)
         //{
             //thrMessagingServer = new Thread(new ThreadStart(ReceiveMessagesServer));
             //thrMessagingServer.start();
        	// System.out.println("xxxxxxx");
        	// ReceiveMessagesServer();
         //}
         //if (encrypt)
         //{
             //thrMessagingClient = new Thread(new ThreadStart(ReceiveMessagesClient));
             //thrMessagingClient.Start();
        	 ReceiveMessagesClient();
             
         //}

     }

     public void RunClosed() throws IOException
     {
         if (encrypt && connectedClient)
         {
             swSenderClient.writeUTF("\n");
             swSenderClient.flush();
             CloseClientConnection();
             SendMessageToServer("endsdecrypt");
             CloseServerConnection();
         }
         else if (!encrypt && connectedServer)
         {
             swSenderServer.writeUTF("\n");
             swSenderServer.flush();
             CloseServerConnection();
             SendMessageToClient("endsdecrypt");
             CloseClientConnection();
         }
     }


     /// <summary>
     /// The main entry point for the application.
     /// </summary>
    
     public static void main(String[] args) throws IOException
     {
         
         System.out.println(".........");
         Program program = new Program();
         if (args.length == 4)
         {
             if(args[0].equals("-d"))
                 program.setEncrypt(false);
             else if (args[0].equals("-e"))
                 program.setEncrypt( true);
             program.ConectToClient(args[1], args[2], args[3]);
         }
        
         program.run();
         //program.RunClosed();
     }
	
}
