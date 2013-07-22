package bcit.ca.infosys.KeyboardCowboys.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHAHash {

	public static String hash(String password) {

		if (null == password)
			return null;
		try {

			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(new String(password).getBytes());
			byte byteData[] = md.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
						.substring(1));
			}
			return sb.toString();			
		} catch (NoSuchAlgorithmException e) {
		}
		return null;
	}
	
}