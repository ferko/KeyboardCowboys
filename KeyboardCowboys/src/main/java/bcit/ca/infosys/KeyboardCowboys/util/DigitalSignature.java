package bcit.ca.infosys.KeyboardCowboys.util;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;

/**
 * Focus of class is to digitally sign and verify timesheets.
 * @author Jitin Dhillon
 */
public class DigitalSignature {

	/**
	 * Generates a random public and private keypair.
	 * @return Keypair containing a Public and Private key.
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 */
	public static KeyPair generateKeys() throws NoSuchAlgorithmException, NoSuchProviderException{
		
			//Creates a keygenerator, the arguments specify which algorithms to use, these were
			//the ones specified in the oracle example.
			KeyPairGenerator keygen;

				keygen = KeyPairGenerator
						.getInstance("DSA", "SUN");
				// SH1PRNG pseudo-random-number generation algorithim for the keygenerator
				SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
				
				//intialize the keygenerator with the randomnumber generator and key length of 1024 bits.
				keygen.initialize(1024, random);
				
				//Generate a keypair.
				KeyPair pair = keygen.generateKeyPair();
				
				return pair;
	}
	/**
	 * Generates byte array containing the digital signature for the given timesheet.
	 * @param sheetData Serialized timesheet that is in the form of a byte array.
	 * @param priv Private Key for the owner of the timsheet.
	 * @return Byte array containing the digital signature of the supplied timesheet generated from the public and private keys.
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws InvalidKeyException
	 * @throws SignatureException
	 */
	public static byte[] genSignatureFromSheet(byte[] sheetData, PrivateKey priv) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException{
		Signature dsa = Signature.getInstance("SHA1withDSA", "SUN");
		
		//Initialize signature with private key.
		dsa.initSign(priv);
		
		//Supply data to be signed.
		dsa.update(sheetData);			
		
		//Sign data and store signature in byte array.
		byte[] realSig = dsa.sign();
		System.out.println("Data Signed.");
		
		return realSig;
	}
	
	/**
	 * Will verify timsheet data with a supplied signature and return a boolean value if signature is verified.
	 * @param sheetData Serialized time sheet in the form of a byte array.
	 * @param pub Public key for owner of timesheet
	 * @param signature Digital Signature of the timesheet.
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws InvalidKeyException
	 * @throws SignatureException
	 */
	static boolean verifyTimeSheetSig(byte[] sheetData, PublicKey pub, byte[] signature) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException{
		Signature sig = Signature.getInstance("SHA1withDSA", "SUN");
		
		//Initialize with public key.
		sig.initVerify(pub);
		
		//Supply data
		sig.update(sheetData);
		
		//Verify Signature.
		boolean verified = sig.verify(signature);
				
		return verified;
	}
}
