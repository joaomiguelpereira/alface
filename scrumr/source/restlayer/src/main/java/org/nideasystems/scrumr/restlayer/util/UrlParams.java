package org.nideasystems.scrumr.restlayer.util;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class UrlParams {

	/**
	 * Create a Map with key/value pairs from the remainding Part.
	 * The format of the remaining part should be ?key1=val1&key2=val2
	 * @param remainingPart The remaining Part
	 * @return A Map with Key/value pairs extracted from the remaining part
	 */
	public static Map<String, String> getRemainindPartParams(String remainingPart) {
		Map<String, String> map = new HashMap<String, String>();
		if ( remainingPart.startsWith("?") ) {
			StringTokenizer tokenizer = new StringTokenizer(remainingPart.substring(1),"&");
			while (tokenizer.hasMoreTokens() ) {
				String token = tokenizer.nextToken();
				String key = token.substring(0,token.indexOf("="));
				String value = token.substring(token.indexOf("=")+1,token.length());

				map.put(key, value);
				
			}
		}
		return map;
	}
}
