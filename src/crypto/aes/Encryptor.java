package crypto.aes;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.SecretKeySpec;

public class Encryptor {
	
	public static final String INSTANCE = "AES/ECB/PKCS5PADDING";
	private SecretKeySpec ukeySpec = null;
	private Cipher cip = null;
	private byte[] encryptedBytes = null;
	
	public Encryptor() {}
	
	public Encryptor(SecretKeySpec key) {
		ukeySpec = key;
		try {
			this.cip = Cipher.getInstance(INSTANCE);
			cip.init(Cipher.ENCRYPT_MODE, ukeySpec);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public Encryptor(byte[] key) throws ClassNotFoundException, IOException {
		SecretKeySpec s = new SecretKeySpec(key, "AES");
		ukeySpec = s;
		try {
			this.cip = Cipher.getInstance(INSTANCE);
			cip.init(Cipher.ENCRYPT_MODE, ukeySpec);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public byte[] encrypt(byte[] plaintext) throws IllegalBlockSizeException, BadPaddingException{
		encryptedBytes = cip.doFinal(plaintext);
		return encryptedBytes;
	}

	public SecretKeySpec getUkeySpec() {
		return ukeySpec;
	}

	public void setUkeySpec(SecretKeySpec ukeySpec) {
		this.ukeySpec = ukeySpec;
	}
	public void setUkeySpec(byte[] ukeySpec) {
		this.ukeySpec = new SecretKeySpec(ukeySpec, "AES");
	}

	public byte[] getEncryptedBytes() {
		return encryptedBytes;
	}

	public static String getInstance() {
		return INSTANCE;
	}
}
