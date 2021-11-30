package crypto.aes;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.SecretKeySpec;

public class Decryptor {
	public static final String INSTANCE = "AES/ECB/PKCS5PADDING";
	private SecretKeySpec ukeySpec = null;
	private Cipher cip = null;
	private byte[] decryptedBytes = null;
	
	public Decryptor() {}
	
	public Decryptor(SecretKeySpec uniqueKey) {
		this.ukeySpec = uniqueKey;
		try {
			this.cip = Cipher.getInstance(INSTANCE);
			cip.init(Cipher.DECRYPT_MODE, ukeySpec);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public Decryptor(byte[] uniqueKey) {
		this.ukeySpec = new SecretKeySpec(uniqueKey, "AES");
		try {
			this.cip = Cipher.getInstance(INSTANCE);
			cip.init(Cipher.DECRYPT_MODE, ukeySpec);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public SecretKeySpec getUKeySpec() {
		return ukeySpec;
	}
	
	public void setUKeySpec(SecretKeySpec ukeySpec) {
		this.ukeySpec = ukeySpec;
	}
	
	public void setUKeySpec(byte[] ukeySpec) {
		this.ukeySpec = new SecretKeySpec(ukeySpec, "AES");
	}
	
	public byte[] decrypt(byte[] ciphertext) throws IllegalBlockSizeException, BadPaddingException {
		decryptedBytes = cip.doFinal(ciphertext);
		return decryptedBytes;
	}

	public byte[] getDecryptedBytes() {
		return decryptedBytes;
	}

	public static String getInstance() {
		return INSTANCE;
	}
	
	
}
