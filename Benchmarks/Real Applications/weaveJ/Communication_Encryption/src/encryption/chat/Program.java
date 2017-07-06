package encryption.chat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import es.uniovi.reflection.weaver.Pointcut;
import es.uniovi.reflection.weaver.Weaver;

public class Program {

	private static boolean areWeDone(List<Double> executionTimes, int k, double CoV) {
		if (executionTimes.size() < k)
			return false;
		double sum = 0;
		double mean = getMean(executionTimes, k);
		for (int i = executionTimes.size() - k; i < executionTimes.size(); i++)
			sum += Math.pow(executionTimes.get(i) - mean, 2);
		double stdDeviation = Math.sqrt(sum / k);
		return stdDeviation / mean < CoV;
	}

	private static double getMean(List<Double> executionTimes, int k) {

		double sum = 0;
		for (int i = executionTimes.size() - k; i < executionTimes.size(); i++)
			sum += executionTimes.get(i);
		return sum / k;
	}

	public static void main(String[] args) throws Throwable {

		Program program = new Program();
		boolean steady = false;
		boolean memory = false;
		if (args[0].contains("X")) {
			steady = true;
			args[0] = args[0].substring(1);
		}
		if (args[0].contains("M")) {
			memory = true;
			args[0] = args[0].substring(1);
		}

		if (args.length == 1)
			program.ConnectAsServer(args[0]);

		else if (args.length == 2)
			program.ConnectAsClient(args[0], args[1]);

		int maxIterations = steady ? 30 : 1, k = steady ? 8 : 1;
		int minIter = steady ? 30 : 1;
		// int maxIterations = 1, k = 1;
		double CoV = 0.02;
		List<Double> executionTimes = new ArrayList<Double>();
		for (int i = 1; i < minIter || (i <= maxIterations && !areWeDone(executionTimes, k, CoV)); i++) {

			long now = System.currentTimeMillis();
			program.runConversation2(args.length);
			long end = System.currentTimeMillis();
			long aux = end - now;
			System.out.println("Iter" + i);
			System.out.println("Time without aspects:" + aux);
			Pointcut pointcut = args.length == 1
					? Weaver.weaveAspectForMethodAround("encryption.chat.Program", "getSrReceiver",
							"encryption.aspect.ChangeServerCommunicationAspect", "getSrReceiverAround",
							DataInputStream.class)
					: Weaver.weaveAspectForMethodAround("encryption.chat.Program", "getSwSender",
							"encryption.aspect.ChangeClientCommunicationAspect", "getSwSenderAround",
							DataOutputStream.class);

			// oldfile = new
			// File(".\\Connector\\ChangeServerCommunicationConnector.class.old");
			// newfile = new
			// File(".\\Connector\\ChangeServerCommunicationConnector.class");
			// oldfile.renameTo(newfile);

			Thread.sleep(1000);
			// for(int j=0;j<50;j++)
			for (int j = 0; j < 20; j++)
				program.runConversation2(args.length);

			pointcut.unweave();

			Thread.sleep(1000);

			end = System.currentTimeMillis();

			System.out.println("Time with aspects:" + (end - now - aux));
			aux = (end - now);
			program.runConversation2(args.length);
			end = System.currentTimeMillis();

			System.out.println("Time without aspects:" + (end - now - aux));
			System.out.println("Total time:" + (end - now));

			executionTimes.add((double) (end - now));
		}

		int res = (int) getMean(executionTimes, k);
		BufferedWriter bw;
		if (args.length == 1)
			if (memory)

				System.out.println("memory test");
			else
				try {
					bw = new BufferedWriter(new FileWriter("temp/output.txt"));
					bw.write("" + res);
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		System.exit(0);
	}

	// private Thread thrMessaging;
	public boolean connected;
	public ServerSocket serverSocket;
	// long now = 0, end = 0, totalDuration = 0;
	public DataInputStream srReceiver;
	public DataOutputStream swSender;

	public Socket tcpServer;

	public Program() {

	}

	private void CloseConnection() throws IOException {
		System.out.println("Disconnected");
		connected = false;
		// thrMessaging.Abort();
		// getSwSender().close();
		getSrReceiver().close();
		tcpServer.close();
	}

	// / <summary>
	// / Method that makes run the chat as client.
	// / </summary>
	// / <param name="ip">Ip of the server.</param>
	// / <param name="port">Port of the server</param>
	public void ConnectAsClient(String ip, String port)
			throws NumberFormatException, UnknownHostException, IOException {
		// System.out.println( "Connecting to server in " + ip + " : " + port);
		tcpServer = new Socket(InetAddress.getByName(ip), Integer.parseInt(port));
		connected = true;
		swSender = new DataOutputStream(tcpServer.getOutputStream());
		srReceiver = new DataInputStream(tcpServer.getInputStream());
		// thrMessaging = new Thread(new ThreadStart(ReceiveMessages));
		// thrMessaging.Start();

	}

	// / <summary>
	// / Method that makes run the chat as server.
	// / </summary>
	// / <param name="port">Port to listen</param>
	public void ConnectAsServer(String port) throws IOException {
		// System.out.println( "Listening in " + port);
		serverSocket = new ServerSocket();
		serverSocket.bind(new InetSocketAddress(InetAddress.getByName("127.0.0.1"), Integer.parseInt(port)));
		tcpServer = serverSocket.accept();
		connected = true;
		// System.out.println( "Connected in " + port);
		srReceiver = new DataInputStream(tcpServer.getInputStream());
		swSender = new DataOutputStream(tcpServer.getOutputStream());
		// thrMessaging = new Thread(new ThreadStart(ReceiveMessages));
		// thrMessaging.Start();

	}

	public DataInputStream getSrReceiver() {
		return srReceiver;
	}

	public DataOutputStream getSwSender() {
		return swSender;
	}

	public boolean isConnected() {
		return connected;
	}

	private void ReceiveMessages(String i) throws IOException {

		// FileOutputStream fstream = new
		// FileOutputStream("TheCountOfMonteCristo_copy.txt");
		// DataOutputStream out = new DataOutputStream(fstream);
		BufferedWriter out = new BufferedWriter(new FileWriter("ConversationFiller_copy" + i + ".txt"));
		DataInputStream aux = null;
		boolean connectedFile = true;
		while (connectedFile) {
			// now = System.currentTimeMillis();
			aux = getSrReceiver();
			// end = System.currentTimeMillis();
			// totalDuration = totalDuration + (end - now);
			String message = aux.readUTF();
			// System.out.println(" >>> " + message);
			if (message.equals("exit11"))
				// CloseConnection();
				connectedFile = false;
			out.write(message);
			// out.newLine();
			out.flush();

		}
		out.close();

	}

	// / <summary>
	// / The main entry point for the application.
	// / </summary>

	public void runConversation(int tipo) throws IOException {
		if (tipo == 1) // server
		{
			ReceiveMessages("1");
			// CloseConnection();
			// // System.out.println("Push a key");
			// // System.in.read();

		} else // client
		{
			FileInputStream fstream = new FileInputStream("ConversationFiller.txt");
			DataInputStream in1 = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in1));
			String line;
			while ((line = br.readLine()) != null) {
				line = line + "\n";
				SendMessage(line);
				// System.out.println(">> " + line);
			}
			SendMessage("exit11");
			in1.close();
			// System.out.println("Push a key");
			// System.in.read();
			// System.out.println("Push a key");
			// System.in.read();
			// System.out.println("Push a key");
			// System.in.read();

		}
		// System.out.println("The duration is .... " + totalDuration);
	}

	public void runConversation1(int tipo) throws IOException {
		if (tipo == 1) // server
		{
			ReceiveMessages("1");
			// CloseConnection();
			// System.out.println("Sent file 1");
			// SendMessage("Sent file 1");
			ReceiveMessages("2");
			// SendMessage("Sent file 2");
			// System.out.println("Send file 3");
			{
				FileInputStream fstream = new FileInputStream("ConversationFiller.txt");
				DataInputStream in1 = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(new InputStreamReader(in1));
				String line;
				while ((line = br.readLine()) != null) {
					line = line + "\n";
					SendMessage(line);
					// System.out.println(">> " + line);
				}
				SendMessage("exit11");
				// System.out.println("antes leer");
				// ring message = getSrReceiver().readUTF();
				// System.out.println("# " + message + " ----");
				in1.close();
			}
			// System.out.println("Sent file 3");

		} else // client
		{
			{
				FileInputStream fstream = new FileInputStream("ConversationFiller.txt");
				DataInputStream in1 = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(new InputStreamReader(in1));
				String line;
				while ((line = br.readLine()) != null) {
					line = line + "\n";
					SendMessage(line);
					// System.out.println(">> " + line);
				}
				SendMessage("exit11");
				// System.out.println("antes leer");
				// String message = getSrReceiver().readUTF();
				// System.out.println("# " + message + " ----");
				in1.close();
			}
			{
				FileInputStream fstream = new FileInputStream("ConversationFiller.txt");
				DataInputStream in1 = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(new InputStreamReader(in1));
				String line;
				while ((line = br.readLine()) != null) {
					line = line + "\n";
					SendMessage(line);
					// System.out.println(">> " + line);
				}
				SendMessage("exit11");
				// String message = getSrReceiver().readUTF();
				// System.out.println("# " + message);
				in1.close();
			}
			ReceiveMessages("3");
			// System.out.println("Push a key");
			// System.in.read();
			// System.out.println("Push a key");
			// System.in.read();
			// System.out.println("Push a key");
			// System.in.read();

		}
		// System.out.println("The duration is .... " + totalDuration);
	}

	public void runConversation2(int tipo) throws IOException {

		if (tipo == 1) // server
		{
			ReceiveMessages("1");
			// CloseConnection();
			ReceiveMessages("2");
			{
				FileInputStream fstream = new FileInputStream("ConversationFiller.txt");
				DataInputStream in1 = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(new InputStreamReader(in1));
				String line;
				while ((line = br.readLine()) != null) {
					line = line + "\n";
					SendMessage(line);
					// System.out.println(">> " + line);
				}
				SendMessage("exit11");
				in1.close();
			}
			{
				FileInputStream fstream = new FileInputStream("ConversationFiller.txt");
				DataInputStream in1 = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(new InputStreamReader(in1));
				String line;
				while ((line = br.readLine()) != null) {
					line = line + "\n";
					SendMessage(line);
					// System.out.println(">> " + line);
				}
				SendMessage("exit11");
				in1.close();
			}

		} else // client
		{
			{
				FileInputStream fstream = new FileInputStream("ConversationFiller.txt");
				DataInputStream in1 = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(new InputStreamReader(in1));
				String line;
				while ((line = br.readLine()) != null) {
					line = line + "\n";
					SendMessage(line);
					// System.out.println(">> " + line);
				}
				SendMessage("exit11");
				in1.close();
			}
			{
				FileInputStream fstream = new FileInputStream("ConversationFiller.txt");
				DataInputStream in1 = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(new InputStreamReader(in1));
				String line;
				while ((line = br.readLine()) != null) {
					line = line + "\n";
					SendMessage(line);
					// System.out.println(">> " + line);
				}
				SendMessage("exit11");
				in1.close();
			}
			ReceiveMessages("3");
			// CloseConnection();
			ReceiveMessages("4");
			// System.out.println("Push a key");
			// System.in.read();
			// System.out.println("Push a key");
			// System.in.read();
			// System.out.println("Push a key");
			// System.in.read();

		}
		// System.out.println("The duration is .... " + totalDuration);
	}

	private void SendMessage(String message) throws IOException {
		DataOutputStream aux = null;
		if (!connected)
			return;
		if (!message.equals("")) {
			// System.out.println(" 11111 >>> " + message);
			// now = System.currentTimeMillis();
			aux = getSwSender();
			// end = System.currentTimeMillis();
			// totalDuration = totalDuration + (end - now);
			aux.writeUTF(message);
			aux.flush();
			// if (message.equals("exit"))
			// CloseConnection();
		}
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	public void setSrReceiver(DataInputStream srReceiver) {
		this.srReceiver = srReceiver;
	}

	public void setSwSender(DataOutputStream swSender) {
		this.swSender = swSender;
	}

}
