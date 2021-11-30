package timetable.server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.NoSuchPaddingException;

import crypto.rsa.Cryptor;


public class Services {
	public static String Base64Encode(byte[] originalBytes) {
		return Base64.getEncoder().encodeToString(originalBytes);
	}
	public static byte[] Base64Decode(String encodedString) {
		return Base64.getDecoder().decode(encodedString);
	}
	
	public static PublicKey getClientPublicKey() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, IOException {
		Cryptor rsa = new Cryptor();
		rsa.read();
		return rsa.getPublicKey();
	}
	public static byte[] objectToBytes(Object obj) throws IOException {
		ByteArrayOutputStream byteArrayOutput = new ByteArrayOutputStream();
		try (ObjectOutputStream objectOutput = new ObjectOutputStream(byteArrayOutput)) {
			objectOutput.writeObject(obj);
			return byteArrayOutput.toByteArray();
		}
	}

	public static Object convertBytesToObject(byte[] bytes) throws IOException, ClassNotFoundException {
		InputStream input = new ByteArrayInputStream(bytes);
		ObjectInputStream objectInput = new ObjectInputStream(input); 
		return objectInput.readObject();
	}
}
