package spark;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import crypto.*;
import crypto.aes.*;
import crypto.rsa.*;

public class debugger {
	public static void main(String[] args) throws IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, IOException, InvalidKeyException, ClassNotFoundException, InvalidKeySpecException {
//		Generator gen = new Generator();
//		byte[] uniqueKey = gen.getKeyAsByteArray();
//		String original = "hello world!";
//		Encryptor enc = new Encryptor((SecretKeySpec)Converter.convertBytesToObject(uniqueKey));
//		byte[] encBytes = enc.encrypt(original.getBytes());
//		byte[] decBytes = new Decryptor((SecretKeySpec)Converter.convertBytesToObject(uniqueKey)).decrypt(encBytes);
//		System.out.println("original: "+original);
//		System.out.println("enc: "+new String(encBytes));
//		System.out.println("dec: "+new String(decBytes));
		
		Cryptor rsa = new Cryptor();
		rsa.generate();
		String original = "hello world!";
		String tmp = null;
		byte[] encBytes = rsa.encrypt(original.getBytes());
		String textEncBytes = Base64.getEncoder().encodeToString(encBytes);
		byte[] decBytes = rsa.decrypt(encBytes);
		String textDecBytes = new String(decBytes);
		System.out.println("org: "+ original);
		System.out.println("enc: "+ textEncBytes);
		System.out.println("dec: "+ textDecBytes);
	}
}
