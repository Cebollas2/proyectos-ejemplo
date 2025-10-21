package com.magnolia.cione.utils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

public class HMAC {
  static public byte[] calcHmacSha256(byte[] secretKey, byte[] message) {
    byte[] hmacSha256 = null;
    try {
      Mac mac = Mac.getInstance("HmacSHA256");
      SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey, "HmacSHA256");
      mac.init(secretKeySpec);
      hmacSha256 = mac.doFinal(message);
    } catch (Exception e) {
      throw new RuntimeException("Failed to calculate hmac-sha256", e);
    }
    
    return hmacSha256;
  }
  
  static public String calcHmacSha256_str(byte[] secretKey, byte[] message) {
    String result ="";
    Mac sha256_HMAC;
	try {
		sha256_HMAC = Mac.getInstance("HmacSHA256");
		SecretKeySpec secret_key = new SecretKeySpec(secretKey, "HmacSHA256");
	    sha256_HMAC.init(secret_key);

	    result = Hex.encodeHexString(sha256_HMAC.doFinal(message));
	    
	} catch (NoSuchAlgorithmException | InvalidKeyException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
    return result;
  }
  
  static public String calSHA256(String secret, String data) {
     Mac sha256_HMAC;
     String hash = null;
	 try {
		sha256_HMAC = Mac.getInstance("HmacSHA256");
		SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
	    sha256_HMAC.init(secret_key);

	    hash = Base64.encodeBase64String(sha256_HMAC.doFinal(data.getBytes()));
	 } catch (NoSuchAlgorithmException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	 } catch (InvalidKeyException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 return hash;
  }
}