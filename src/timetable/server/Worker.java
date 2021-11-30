package timetable.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import crypto.rsa.Cryptor;

public class Worker{
	
	BufferedWriter out;
	BufferedReader in;
	Socket socket;
	
	public Worker(Socket socket) throws IOException {
		this.socket = socket;
		out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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
	
	public String receive() {
		try {
			return in.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public void sendUniqueKey() {
		
	}

//	public void run() {
//		
//		try {
//			Cryptor rsaReader = new Cryptor();
//			rsaReader.read();
//			byte[] pubKey = crypto.Converter.objectToBytes(rsaReader.getPublicKey());
//			String encodedPubKey = Base64.getEncoder().encodeToString(pubKey);
//
//			out.write(encodedPubKey+"\r\n");
//			out.flush();
//			
//			String encodedBytes = in.readLine();
//			byte[] encryptedBytes = Base64.getDecoder().decode(encodedBytes);
//			byte[] decryptedBytes = rsaReader.decrypt(encryptedBytes);
//			String originalString = new String(decryptedBytes);
//			System.out.println("original String: "+originalString);
//			
//		} catch (NoSuchAlgorithmException e) {
//			
//			e.printStackTrace();
//		} catch (NoSuchPaddingException e) {
//			
//			e.printStackTrace();
//		} catch (IOException e) {
//			
//			e.printStackTrace();
//		} catch (InvalidKeyException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalBlockSizeException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (BadPaddingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InvalidKeySpecException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
}
	
	