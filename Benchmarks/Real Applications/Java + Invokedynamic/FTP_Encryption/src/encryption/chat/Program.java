package encryption.chat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
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

import encryption.aspect.ChangeClientCommunicationAspect;
import encryption.aspect.ChangeServerCommunicationAspect;

public class Program {
	private static boolean server = false;
	private static boolean woven = false;

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
		System.out.println("NEW VERSION DURATION STEADY/STAR-FIXED:");
		long totalNow = 0, now = 0, end = 0;
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
		// System.out.println("Push a key");
		// System.in.read();
		int maxIterations = steady ? 30 : 1, k = steady ? 10 : 1, minIter = steady ? 15 : 1;
		// double CoV = 0.02;
		// int maxIterations = 1, k = 1, minIter = 1;
		double CoV = 0.02;

		List<Double> executionTimes = new ArrayList<Double>();
		for (int j = 1; j < minIter || j <= maxIterations && !areWeDone(executionTimes, k, CoV); j++) {
			System.out.println("Iter " + j);
			totalNow = now = System.currentTimeMillis();
			// for (int i = 0; i < 10; i++) {
			program.run2(args.length);
			end = System.currentTimeMillis();
			System.out.println("The duration without aspects is.... " + (end - now));
			now = System.currentTimeMillis();
			server = args.length == 1;
			woven = true;

			program.run2(args.length);

			woven = false;

			end = System.currentTimeMillis();
			System.out.println("The duration with aspect is  .... " + (end - now));
			now = System.currentTimeMillis();
			program.run2(args.length);
			end = System.currentTimeMillis();
			executionTimes.add((double) (end - totalNow));
			System.out.println("The duration without aspect is  .... " + (end - now));
			System.out.println("The total duration is  .... " + (end - totalNow));
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
		// System.out.println(Memory.getPeakMemoryUsage("java.exe"));
		// System.out.println("Push a key");
		// System.in.read();
	}

	/*
	 * public static void mainSteady(String[] args) throws Throwable {
	 * System.out.println("NEW VSTEAD:"); long totalNow = 0, now = 0, end = 0,
	 * totalDuration = 0, totalDurationTotal = 0; totalNow = now =
	 * System.currentTimeMillis(); Program program = new Program(); if
	 * (args.length == 1) program.ConnectAsServer(args[0]); else if (args.length
	 * == 2) program.ConnectAsClient(args[0], args[1]); // System.out.println(
	 * "Push a key"); // System.in.read(); int maxIterations = 30, k = 8,
	 * minIter = 8; double CoV = 0.02; List<Double> executionTimes = new
	 * ArrayList<Double>(); for (int i = 1; i < minIter || i <= maxIterations &&
	 * !areWeDone(executionTimes, k, CoV); i++) { System.out.println("Iter " +
	 * i); totalNow = System.currentTimeMillis(); program.run2(args.length); end
	 * = System.currentTimeMillis(); totalDuration = end - now;
	 * totalDurationTotal += totalDuration; System.out.println(
	 * "The duration without aspects is.... " + totalDuration); now =
	 * System.currentTimeMillis(); Pointcut pointcut = args.length == 1 ?
	 * Weaver.weaveAspectForMethodAround("encryption.chat.Program",
	 * "getSrReceiver", "encryption.aspect.ChangeServerCommunicationAspect",
	 * "getSrReceiverAround", DataInputStream.class) :
	 * Weaver.weaveAspectForMethodAround("encryption.chat.Program",
	 * "getSwSender", "encryption.aspect.ChangeClientCommunicationAspect",
	 * "getSwSenderAround", DataOutputStream.class); Thread.sleep(2000);
	 * program.run2(args.length);
	 * 
	 * pointcut.unweave(); pointcut = null;
	 * 
	 * end = System.currentTimeMillis(); totalDuration = end - now;
	 * totalDurationTotal += totalDuration; System.out.println(
	 * "The duration with aspect is  .... " + totalDuration); now =
	 * System.currentTimeMillis(); Thread.sleep(1500);
	 * program.run2(args.length); end = System.currentTimeMillis();
	 * executionTimes.add((double) (end - totalNow)); totalDuration = end - now;
	 * totalDurationTotal += totalDuration; } if (args.length == 1) {
	 * BufferedWriter bw = new BufferedWriter(new FileWriter(new
	 * File("output.txt"), true)); bw.append(("\n" + getMean(executionTimes,
	 * k)).replace(".", ",")); bw.close(); } System.out.println(
	 * "The duration without aspect is  .... " + totalDuration);
	 * System.out.println("The total duration is  .... " + totalDurationTotal);
	 * // System.out.println(Memory.getPeakMemoryUsage("java.exe")); //
	 * System.out.println("Push a key"); // System.in.read(); }
	 */
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

	/// <summary>
	/// Method that makes run the chat as client.
	/// </summary>
	/// <param name="ip">Ip of the server.</param>
	/// <param name="port">Port of the server</param>
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

	/// <summary>
	/// Method that makes run the chat as server.
	/// </summary>
	/// <param name="port">Port to listen</param>
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
		return server ? woven ? ChangeServerCommunicationAspect.getSrReceiverJAVA(this) : srReceiver : srReceiver;
	}

	public DataOutputStream getSwSender() {
		return server ? swSender : woven ? ChangeClientCommunicationAspect.getSwSenderJAVA(this) : swSender;
	}

	public boolean isConnected() {
		return connected;
	}

	private void ReceiveMessages(String i) throws IOException {

		// FileOutputStream fstream = new
		// FileOutputStream("TheCountOfMonteCristo_copy.txt");
		// DataOutputStream out = new DataOutputStream(fstream);
		BufferedWriter out = new BufferedWriter(new FileWriter("TheCountOfMonteCristo_copy" + i + ".txt"));
		DataInputStream aux = null;
		boolean connectedFile = true;
		while (connectedFile) {
			// now = System.currentTimeMillis();
			aux = getSrReceiver();
			// end = System.currentTimeMillis();
			// totalDuration = totalDuration + (end - now);
			String message = aux.readUTF();
			if (message.equals("exit11"))
				// CloseConnection();
				connectedFile = false;
			out.write(message);
			// out.newLine();
			out.flush();

		}
		out.close();

	}

	/// <summary>
	/// The main entry point for the application.
	/// </summary>

	public void run(int tipo) throws IOException {
		if (tipo == 1) // server
		{
			ReceiveMessages("1");
			// CloseConnection();
			//// System.out.println("Push a key");
			//// System.in.read();

		} else // client
		{
			FileInputStream fstream = new FileInputStream("TheCountOfMonteCristo.txt");
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

	public void run1(int tipo) throws IOException {
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
				FileInputStream fstream = new FileInputStream("TheCountOfMonteCristo.txt");
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
				FileInputStream fstream = new FileInputStream("TheCountOfMonteCristo.txt");
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
				FileInputStream fstream = new FileInputStream("TheCountOfMonteCristo.txt");
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

	public void run2(int tipo) throws IOException {

		if (tipo == 1) // server
		{
			ReceiveMessages("1");
			ReceiveMessages("2");
			{
				FileInputStream fstream = new FileInputStream("TheCountOfMonteCristo.txt");
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
				FileInputStream fstream = new FileInputStream("TheCountOfMonteCristo.txt");
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
				FileInputStream fstream = new FileInputStream("TheCountOfMonteCristo.txt");
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
				FileInputStream fstream = new FileInputStream("TheCountOfMonteCristo.txt");
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
			ReceiveMessages("4");

		}
		// System.out.println("Push a key");
		// System.in.read();
		// System.out.println("Push a key");
		// System.in.read();
		// System.out.println("Push a key");
		// System.in.read();

	}

	// System.out.println("The duration is .... " + totalDuration);

	public void run2Tuned(int tipo) throws IOException {

		if (tipo == 1) // server
		{
			for (int i = 1; i < 11; i++) {
				ReceiveMessages(i + "");
				System.out.println("Server received." + i);
			}
			for (int i = 1; i < 11; i++) {
				FileInputStream fstream = new FileInputStream("TheCountOfMonteCristo.txt");
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
				System.out.println("Server sended." + i);
			}

		} else // client
		{
			for (int i = 1; i < 11; i++) {
				FileInputStream fstream = new FileInputStream("TheCountOfMonteCristo.txt");
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
				System.out.println("Client sended." + i);
			}
			for (int i = 11; i < 21; i++) {
				ReceiveMessages(i + "");

				System.out.println("Client received." + i);
			}
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
