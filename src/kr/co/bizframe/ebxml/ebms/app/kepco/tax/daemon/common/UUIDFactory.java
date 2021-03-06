package kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common;

import java.security.SecureRandom;

/**
 * A <code>UUIDFactory</code> generates a UUID
 */

public class UUIDFactory {
	/**
	* random number generator for UUID generation
	*/
	private final SecureRandom secRand = new SecureRandom();

	/**
	* 	128-bit buffer for use with secRand
	*/
	private final byte[] secRandBuf16 = new byte[16];

	/**
	* 64-bit buffer for use with secRand
	*/
	private final byte[] secRandBuf8 = new byte[8];

	/**
	* @link
	* @shapeType PatternLink
	* @pattern Singleton
	* @supplierRole Singleton factory 
	*/
	/*# private UUIDFactory _uuidFactory; */
	private static UUIDFactory instance = null;

	protected UUIDFactory() {
	}

	public UUID newUUID() {
		secRand.nextBytes(secRandBuf16);
		secRandBuf16[6] &= 0x0f;
		secRandBuf16[6] |= 0x40; /* version 4 */
		secRandBuf16[8] &= 0x3f;
		secRandBuf16[8] |= 0x80; /* IETF variant */
		secRandBuf16[10] |= 0x80; /* multicast bit */
		long mostSig = 0;
		for (int i = 0; i < 8; i++) {
			mostSig = (mostSig << 8) | (secRandBuf16[i] & 0xff);
		}
		long leastSig = 0;
		for (int i = 8; i < 16; i++) {
			leastSig = (leastSig << 8) | (secRandBuf16[i] & 0xff);
		}
		return new UUID(mostSig, leastSig);
	}

	public boolean isValidUUID(String uuid) {
		boolean isValid = true;

		//57d925e0-7ad2-4dc3-ace1-b8a4064abcc7
		//012345678901234567890123456789012345
		int len = uuid.length();
		String hexDigits = "0123456789abcdef";

		for (int i=0; i<len; i++) {
			char c = uuid.charAt(i);
			if ((i==8) || (i==13) || (i==18) || (i==23)) {
				if (c != '-') {
					isValid = false;
					break;
				}
			} else {
				if (hexDigits.indexOf(c) == -1) {
					isValid = false;
					break;
				}									
			}
		}

		return isValid;
	}

	private static void printUsage() {
		System.err.println("...UUIDFactory [help] cnt=<number of uuids required>");   
		System.exit(-1);
	}

	public static void main(String[] args) {
		int cnt = 1;

		for (int i=0; i<args.length; i++) {
			if (args[i].equalsIgnoreCase("help")) {
				printUsage();
			} else if (args[i].startsWith("cnt=")) {
				cnt = Integer.parseInt(args[i].substring(4, args[i].length()));
			} else {
				System.err.println("Unknown parameter: '" + args[i] 
					+ "' at position " + i);
				if (i > 0) {
					System.err.println("Last valid parameter was '" + args[i-1] + "'");
				}
				printUsage();
			}
		}

		UUIDFactory uf = UUIDFactory.getInstance();

		for (int i=0; i<cnt; i++) { 
			UUID id = uf.newUUID();
			System.out.println( "new UUID : " + id.toString() );
		}
	}

	public static UUIDFactory getInstance(){
		if (instance == null) {
			synchronized(UUIDFactory.class) {
				if (instance == null) {
					instance = new UUIDFactory();
				}
			}
		}
		return instance;
	}
}
