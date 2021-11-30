package crypto.aes;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.crypto.spec.SecretKeySpec;


public class Generator {
	public static final int KEY_LENGTH = 16;
	public static final String INSTANCE = "AES/ECB/PKCS5PADDING";
	public SecretKeySpec ukeySpec = null;
	
	public Generator() {
		Random random = ThreadLocalRandom.current();
		byte[] r = new byte[KEY_LENGTH];
		random.nextBytes(r);
		ukeySpec = new SecretKeySpec(r, "AES");
	}
	
	public SecretKeySpec getKeyAsObject() {
		return ukeySpec;
	}
	
}
