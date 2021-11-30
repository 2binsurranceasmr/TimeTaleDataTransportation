package timetable.server;

import java.io.IOException;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import crypto.aes.Decryptor;
import crypto.aes.Encryptor;
import crypto.aes.Generator;
import crypto.rsa.Cryptor;

public class Controller implements Runnable {
	public Socket socket;
	@Override
	public void run() {
		System.out.println("this is thread");
		Worker singleWorker = setWorker();
		// receive client public key
		String encodedClientPublicKey = singleWorker.receive();	
		System.out.println("client pubkey received");
		try {	
			// send encrypted unique_key to client
			PublicKey clientPubKey = (PublicKey) Services.convertBytesToObject(Services.Base64Decode(encodedClientPublicKey));
			SecretKeySpec uniqueKey = generateUniqueKey();
			singleWorker.send(encryptUniqueKey(uniqueKey, clientPubKey));
			System.out.println("unique key sent");
			// receive request data
			String encodedRequest = singleWorker.receive();
			byte[] encryptedRequest = Services.Base64Decode(encodedRequest);
			byte[] decryptedRequest = new Decryptor(uniqueKey).decrypt(encryptedRequest);
			System.out.println("request received");
			//*****************
			//handle request
			String originalRequest = new String(decryptedRequest);
			String result = originalRequest;
			//*****************
			
			// send respone
			singleWorker.send(new Encryptor(uniqueKey).encrypt(result.getBytes()));
			System.out.println("respone sent");
			
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public Worker setWorker () {
		try {
			return new Worker(this.socket);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public SecretKeySpec generateUniqueKey() {
		return new Generator().ukeySpec;
	}
	
	public byte[] encryptUniqueKey(SecretKeySpec uniqueKey, PublicKey clientPublicKey) {
		try {
			byte[] uniqueKeyAsBytes = Services.objectToBytes(uniqueKey);
			Cryptor rsa = new Cryptor();
			byte[] encryptedUniqueKeyAsBytes = rsa.encrypt(uniqueKeyAsBytes, clientPublicKey);
			return encryptedUniqueKeyAsBytes;
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
