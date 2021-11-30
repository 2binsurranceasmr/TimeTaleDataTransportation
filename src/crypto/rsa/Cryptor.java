package crypto.rsa;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
public class Cryptor {
	public static final String INSTANCE = "RSA";
	public static final int KEY_LENGTH = 2048;
	public static final String PUBLICKEY_URL = FileSystems.getDefault().getPath("").toAbsolutePath().toString()+"\\publickey.rsa";
	public static final String PRIVATEKEY_URL = FileSystems.getDefault().getPath("").toAbsolutePath().toString()+"\\privatekey.rsa";
	private Cipher cip = null;
	private KeyPairGenerator generator = null;
	private KeyPair holder = null;
	private PublicKey publicKey = null;
	private PrivateKey privateKey = null;
	private byte[] encryptedBytes = null;
	private byte[] decryptedBytes = null;
	
	
	public Cryptor() throws NoSuchAlgorithmException, NoSuchPaddingException {
		cip = Cipher.getInstance(INSTANCE);
	}
	
	public void generate() throws IOException {
		SecureRandom sr = new SecureRandom();
		try {
			generator = KeyPairGenerator.getInstance(INSTANCE);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		generator.initialize(KEY_LENGTH,sr);
		holder = generator.genKeyPair();
		publicKey = holder.getPublic();
		privateKey = holder.getPrivate();
		
		File publicKeyFile = createKeyFile(new File(PUBLICKEY_URL));
		File privateKeyFile = createKeyFile(new File(PRIVATEKEY_URL));
		
		FileOutputStream fos = new FileOutputStream(publicKeyFile);
		fos.write(publicKey.getEncoded());
		fos.close();
		
		fos = new FileOutputStream(privateKeyFile);
		fos.write(privateKey.getEncoded());
		fos.close();
	}
	
	public void read() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		FileInputStream fis = null;
		byte[] b = null;
		X509EncodedKeySpec pubSpec = null;
		PKCS8EncodedKeySpec privSpec = null;
		KeyFactory factory = KeyFactory.getInstance(INSTANCE);
		
		// read public key file
		fis = new FileInputStream(PUBLICKEY_URL);
		b = new byte[fis.available()];
		fis.read(b);
		fis.close();

		// recreate public key
		pubSpec = new X509EncodedKeySpec(b);
		publicKey = factory.generatePublic(pubSpec);
		
		// read private key file
		fis = new FileInputStream(PRIVATEKEY_URL);
		b = new byte[fis.available()];
		fis.read(b);
		fis.close();

		// recreate private key
		privSpec = new PKCS8EncodedKeySpec(b);
		privateKey = factory.generatePrivate(privSpec);
	}
	
	public byte[] encrypt(byte[] plain) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		cip.init(Cipher.ENCRYPT_MODE, publicKey);
		encryptedBytes = cip.doFinal(plain);
		return encryptedBytes;
	}
	
	public byte[] decrypt(byte[] cipher) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		cip.init(Cipher.DECRYPT_MODE, privateKey);
		decryptedBytes = cip.doFinal(cipher);
		return decryptedBytes;
	}
	
	public byte[] encrypt(byte[] plain, PublicKey pubKey) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		setPublicKey(pubKey);
		return encrypt(plain);
	}
	
	public byte[] decrypt(byte[] ciphertext, PrivateKey privKey) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		setPrivateKey(privKey);
		return decrypt(ciphertext);
	}
	
	private static File createKeyFile(File file) throws IOException {
		if (!file.exists()) {
			file.createNewFile();
		} else {
			file.delete();
			file.createNewFile();
		}
		return file;
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
	}

	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}

	public byte[] getEncryptedBytes() {
		return encryptedBytes;
	}

	public void setEncryptedBytes(byte[] encryptedBytes) {
		this.encryptedBytes = encryptedBytes;
	}

	public byte[] getDecryptedBytes() {
		return decryptedBytes;
	}

	public void setDecryptedBytes(byte[] decryptedBytes) {
		this.decryptedBytes = decryptedBytes;
	}
	
}
