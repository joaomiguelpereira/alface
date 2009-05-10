package org.nideasystems.scrumr.security.services;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

public class BasicSecurityService implements IBasicSecurityService {

	private static final Logger log = Logger
			.getLogger(BasicSecurityService.class);

	/**
	 * Given a String value, create a MD5 hash
	 */
	public String getHashFromValue(String value) throws SecurityException {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			log.fatal("Error Getting an MD5 Instance of MessageDisgester", e);
			throw new SecurityException("Error instantiating Message disgest");
		}

		byte[] messageToHash = null;

		try {
			messageToHash = value.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.fatal("Error converting the message to UTF-8", e);
			throw new SecurityException("Error converting the message to UTF-8");
		}
		md.update(messageToHash);

		byte[] hashBytes = md.digest();

		return createNormalizedHash(hashBytes);
	}

	public boolean checkIntegrity(String hash, String value)
			throws SecurityException {
		// create a hash for the value
		String hashForTheValue = getHashFromValue(value);

		// Compare the hashes

		return (hashForTheValue.equals(hash));
	}

	private String createNormalizedHash(byte[] hashBytes) {

		BigInteger bigInt = new BigInteger(1, hashBytes);
		String hashAsString = bigInt.toString(16);
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < (32 - hashAsString.length()); i++) {
			sb.append("0");
		}
		sb.append(hashAsString);

		return hashAsString;

	}

	/**
	 * Decdode from base 64 a value
	 */
	public String getValueFrom64BasedEncoded(String encodedValue) {
		byte[] encodedBinaryDate = null;
		
		
		
		byte[] decodedBinaryDate = null;
		
		try {
			encodedBinaryDate = encodedValue.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			log.fatal("Error while encoding value ",e1);
			throw new SecurityException(e1);
		}
		
		try {
			decodedBinaryDate = Base64.decodeBase64(encodedBinaryDate);
		} catch (Exception e) {
			log.fatal("Error while deencoding value ",e);
			throw new SecurityException(e);
		}
		String retDataAsString = new String(decodedBinaryDate);
		return retDataAsString;
	}

	/**
	 * Enconde in base 64 a given value
	 */
	public String getValueTo64BasedEncoded(String valueToEncode) {
		byte[] binaryData = null;
		
		try {
			binaryData = valueToEncode.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.fatal("Error while encoding value ",e);
			throw new SecurityException(e);
		}

		byte[] encodedData = Base64.encodeBase64(binaryData);
		String returnedDataAsString = new String(encodedData);
		return returnedDataAsString;
	}

}
