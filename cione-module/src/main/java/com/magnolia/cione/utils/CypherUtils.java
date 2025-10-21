package com.magnolia.cione.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CypherUtils {

	private static final Logger log = LoggerFactory.getLogger(CypherUtils.class);

	private static final String KEY = "YFRQB4Ey2B4wzxK6";

	private CypherUtils() {
		throw new IllegalStateException("Utility class");
	}

	public static String encode(String data) {

		try {
			Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
			SecretKeySpec secret_key = new SecretKeySpec(KEY.getBytes("UTF-8"), "HmacSHA256");
			sha256_HMAC.init(secret_key);

			return Hex.encodeHexString(sha256_HMAC.doFinal(data.getBytes("UTF-8")));
			
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException e) {
			log.error("Error cifrando los datos.", e);
		}

		return null;
	}

}
