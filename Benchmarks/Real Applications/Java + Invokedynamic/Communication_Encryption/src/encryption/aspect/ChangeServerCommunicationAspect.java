package encryption.aspect;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import encryption.chat.Program;

public class ChangeServerCommunicationAspect {
	private static boolean ACKSended;

	private static DataInputStream srReceiver;

	private static DataOutputStream swSender;

	private static Socket tcpServer;
	private static ServerSocket tlsClient;

	private static void Connect() throws IOException {
		// System.out.println("Connecting the server aspect");
		// Load WSDL
		// XmlDocument config = new XmlDocument();
		// config.Load("aspect.config");
		String port = "6000"; // config.SelectSingleNode("//config/port").InnerText;
		tlsClient = new ServerSocket();
		tlsClient.bind(new InetSocketAddress(InetAddress.getByName("127.0.0.1"), Integer.parseInt("6000")));
		// System.out.println("Waiting for clients");
		tcpServer = tlsClient.accept();
		// System.out.println("Connected on port " + port);
		swSender = new DataOutputStream(tcpServer.getOutputStream());
		srReceiver = new DataInputStream(tcpServer.getInputStream());
	}

	public static DataInputStream getSrReceiverJAVA(Program p) {
		// System.out.println("Server Java................");
		try {
			if (!ACKSended) {
				DataOutputStream originalSwSender = p.swSender;
				originalSwSender.writeChars("\n");
				originalSwSender.flush();
				ACKSended = true;

			}

			if (swSender == null)
				Connect();
			return srReceiver;

		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}
