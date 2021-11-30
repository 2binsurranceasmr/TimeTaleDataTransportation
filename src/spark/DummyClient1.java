package spark;

import java.awt.image.RescaleOp;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import crypto.aes.Decryptor;
import crypto.aes.Encryptor;
import crypto.rsa.Cryptor;
import timetable.server.Services;

public class DummyClient1 {
	public static void main(String[] args) throws IOException, ClassNotFoundException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InterruptedException {
		System.out.println("this is client 1");
		Client thisClient = new Client("127.0.0.1", 8000);
		thisClient.start();
		
		// send public key to server
		thisClient.sendClientPublicKey();
		System.out.println("pubkey sent");
		// receive unique key
		String encodedUniqueKey = thisClient.in.readLine();
		byte[] encryptedUniqueKeyAsBytes = Services.Base64Decode(encodedUniqueKey);
		Cryptor rsa = new Cryptor();
		rsa.read();
		byte[] decryptedUniqueKeyAsBytes = rsa.decrypt(encryptedUniqueKeyAsBytes);
		SecretKeySpec uniqueKey = (SecretKeySpec) Services.convertBytesToObject(decryptedUniqueKeyAsBytes);
		System.out.println("uniquekey received");
		//send request
		String request = "/api/......";
		byte[] encryptedRequest = new Encryptor(uniqueKey).encrypt(request.getBytes());
		String encodedRequest = Services.Base64Encode(encryptedRequest);
		thisClient.out.write(encodedRequest+"\r\n");
		thisClient.out.flush();
		System.out.println("request sent");
		//
		Thread.sleep(10000);
		//
		//receive respone
		String encodedRespone = thisClient.in.readLine();
		byte[] encryptedRespone = Services.Base64Decode(encodedRespone);
		byte[] decryptedRespone = new Decryptor(uniqueKey).decrypt(encryptedRespone);
		String originalRespone = new String(decryptedRespone);
		System.out.println("respone received");
		
		System.out.println(originalRespone);
				
	}
}
