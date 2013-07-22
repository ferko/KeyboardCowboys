package bcit.ca.infosys.KeyboardCowboys.util;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;

/**
 * 
 * @author Jitin
 *
 */
public class KeyGen {

	/**
	 * @author Jitin
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			//Creates a keygenerator, the arguments specify which algorithms to use, these were
			//the ones specified in the oracle example.
			KeyPairGenerator keygen = KeyPairGenerator
					.getInstance("DSA", "SUN");
			
			// SH1PRNG pseudo-random-number generation algorithim for the keygenerator
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
			
			//intialize the keygenerator with the randomnumber generator and key length of 1024 bits.
			keygen.initialize(1024, random);
			
			//Generate a keypair.
			KeyPair pair = keygen.generateKeyPair();
			
			//Get private key and store in PrivateKey variable.
			PrivateKey priv = pair.getPrivate();
			
			//Get public key and store in PublicKey Variable.
			PublicKey pub = pair.getPublic();
			System.out.println("Created Keys!");

			//Signature is used to sign content. Again, arguments specify algorithms to use.
			Signature dsa = Signature.getInstance("SHA1withDSA", "SUN");
			
			//Initialize signature with private key.
			dsa.initSign(priv);
			
			//Sample test object.
			TestObject obj = new TestObject(117, "Jitin", "Supervisor", 50);
			
			//Serialize the object into a byte array.
			Serializer.serialize(obj);
			byte[] data = Serializer.serialize(obj);
			
			//Supply data to be signed.
			dsa.update(data);			
			
			//Sign data and store signature in byte array.
			byte[] realSig = dsa.sign();
			System.out.println("Data Signed.");

			/** Verifying the signature */
			Signature sig = Signature.getInstance("SHA1withDSA", "SUN");
			
			//Initialize with public key.
			sig.initVerify(pub);
			obj.name = "Jitin"; /** You can change the name to something else to make it fail validation */
			
			//Serialize object.
			data = Serializer.serialize(obj);
			
			//Supply data
			sig.update(data);
			
			//Verify Signature.
			boolean verified = sig.verify(realSig);
			
			if(verified){
				System.out.println("Signature is verified.");
			} else {
				
			System.out.println("Signature failed verification.");
			}
			
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}

class Serializer {
    public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        ObjectOutputStream o = new ObjectOutputStream(b);
        o.writeObject(obj);
        return b.toByteArray();
    }
 }
 
/**
 * 
 * @author Jitin
 *
 */
class TestObject implements Serializable{
	public TestObject(int id, String name, String role, int hours){
		this.id = id;
		this.name = name;
		this.role = role;
		this.hours = hours;
	}
	
	int id;
	String name;
	String role;
	int hours;
}
