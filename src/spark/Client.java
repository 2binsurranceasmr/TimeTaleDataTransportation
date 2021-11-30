package spark;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import crypto.rsa.Cryptor;
import timetable.server.Services;


public class Client {
	int port;
	String address;
	Socket socket;
	BufferedReader in;
	BufferedReader input;
	BufferedWriter out;
	
	public Client(String address, int port) {
		this.port = port;
		this.address = address;
		in = null;
		input = null;
		out = null;
	}
	public void start() {
		try {
			socket = new Socket(address, port);
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			input = new BufferedReader(new InputStreamReader(System.in));

		} catch (UnknownHostException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	public void send(byte[] msg) {
		String encodedBytes = Services.Base64Encode(msg);
		try {
			out.write(encodedBytes+"\r\n");
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void sendClientPublicKey() {
		try {
			PublicKey pubKey = Services.getClientPublicKey();
			byte[] pubKeyAsBytes = Services.objectToBytes(pubKey);
			send(pubKeyAsBytes);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeySpecException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}











