package other;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.security.SecureRandom;
import java.util.Random;
import com.sun.org.apache.xml.internal.security.utils.Base64;


public class PasswordUtil {
	
	public static String hashPassword(String password) 
			throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		
		/*
		 * Convert the password string into an array of bytes (getBytes method)
		 * Then, specify the array of bytes that you want to hash.
		 */
		md.update(password.getBytes());
		
		/*
		 * Call the digest method of the MessageDigest class 
		 * to hash the input and return a fixed-length array 
		 * of bytes for the hashed input.
		 */
		byte[] mdArray = md.digest();
		
		// Note that the above mdArray contains hashed password in bytes
		// Now convert this array of bytes to a String
		
		/* Code a for loop to convert the array of bytes (which are 8 bits) 
		 * to a string of characters (which are 16 bits in Java).
         */
		StringBuilder sb = new StringBuilder(mdArray.length * 2);
		
		for (byte b : mdArray) {
			/* 0xff is 00000000 00000000 00000000 11111111 (equal to int 255).
			 * Taking the AND of byte b with 0xff returns the lowest 8 bits of b.
			 * Therefore, the lowest 8 bits of b is assigned to the int v.   
			 */
			int v = b & 0xff;
			if (v < 16) {
				sb.append('0');
			}
			sb.append(Integer.toHexString(v));
		}
		return sb.toString();
	}
	
	// Uses Base64 class to convert bytes into String
	public static String hashPasswordAlternative(String password) 
			throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256"); 
		md.update(password.getBytes());
		byte[] mdArray = md.digest();
		String st = Base64.encode(mdArray);
		return st;
	}
	
	public static String getSalt() {		 
		Random r = new SecureRandom();
		byte[] saltBytes = new byte[32];
		r.nextBytes(saltBytes);
		return Base64.encode(saltBytes);
	}
	
	public static String hashAndSaltPassword(String password) 
			throws NoSuchAlgorithmException {
		String salt = getSalt();
		return hashPassword(password + salt);
	}
}
