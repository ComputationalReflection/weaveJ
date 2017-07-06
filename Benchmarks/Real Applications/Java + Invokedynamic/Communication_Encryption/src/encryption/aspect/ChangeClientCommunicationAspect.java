package encryption.aspect;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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

	public static void traceBefore(Program p) {
		System.out.println("BEFORE");
	}

	public static DataOutputStream getSwSenderJAVA(Program p) {
		// System.out.println("Client JAVA.............");
		try {

			if (!ACKSended) {

				DataOutputStream originalSwSender;
				originalSwSender = p.swSender;
				originalSwSender.writeChars("\n");
				originalSwSender.flush();
				ACKSended = true;

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
		}  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}
}
