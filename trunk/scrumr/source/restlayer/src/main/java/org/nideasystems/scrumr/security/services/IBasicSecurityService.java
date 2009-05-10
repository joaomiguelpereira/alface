package org.nideasystems.scrumr.security.services;

public interface IBasicSecurityService extends ISecurityService {
	

	/**
	 * Given a String, return a MD5 hash 
	 * @param string
	 * @return
	 */
	String getHashFromValue(String string) throws SecurityException;
	

	/**
	 * Given an hash, return the value 
	 * @param hash
	 * @return
	 */
	boolean checkIntegrity(String hash, String value) throws SecurityException;


	/**
	 * Get a String and encoded it to Base64
	 * @param valueToEncode
	 * @return
	 * @throws SecurityException
	 */
	String getValueTo64BasedEncoded(String valueToEncode) throws SecurityException;;


	String getValueFrom64BasedEncoded(String encodedValue) throws SecurityException;;

	
}
