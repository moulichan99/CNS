import java.math.BigInteger;

public class SimpleHashAlgorithm {
	static BigInteger BI2 = BigInteger.valueOf(2), BI2P32 = BI2.pow(32), BI2P64 = BI2.pow(64);
	public static String hexToBin(String hex) {
		String bin = "";
		for(int i = 0; i < hex.length(); ++i)
			switch(hex.charAt(i)) {
			case '0': bin += "0000"; break;
			case '1': bin += "0001"; break;
			case '2': bin += "0010"; break;
			case '3': bin += "0011"; break;
			case '4': bin += "0100"; break;
			case '5': bin += "0101"; break;
			case '6': bin += "0110"; break;
			case '7': bin += "0111"; break;
			case '8': bin += "1000"; break;
			case '9': bin += "1001"; break;
			case 'a': case 'A': bin += "1010"; break;
			case 'b': case 'B': bin += "1011"; break;
			case 'c': case 'C': bin += "1100"; break;
			case 'd': case 'D': bin += "1101"; break;
			case 'e': case 'E': bin += "1110"; break;
			case 'f': case 'F': bin += "1111"; break;
			}
		return bin;
	}
	
	public static BigInteger rotateLeft(BigInteger value, int shift, int bitSize) {
	    BigInteger topBits = value.shiftRight(bitSize - shift);
	    BigInteger mask = BigInteger.ONE.shiftLeft(bitSize).subtract(BigInteger.ONE);
	    return value.shiftLeft(shift).or(topBits).and(mask);
	}
	
	public static void main(String[] args) {
		
		String plainText;
		plainText = "The quick brown fox jumps over the lazy dog";//"";//
		String hexString = (plainText.length() == 0 ? "" : String.format("%x", new BigInteger(1, plainText.getBytes())));;
		String message = (plainText.length() == 0 ? "" : hexToBin(hexString));
		
		int n = message.length(), padLength = (448 - n % 512 <= 0 ? 448 - n % 512 + 512 : 448 - n % 512);
		message += "1" + new String(new char[padLength - 1]).replace('\0', '0');
		
		String lengthm64 = BigInteger.valueOf(n).mod(BI2P64).toString(2);
		lengthm64 = String.valueOf(new char[64 - lengthm64.length()]).replace('\0', '0') + lengthm64;
		message += lengthm64;
		
		//BigInteger two_32 = BI2P32;
		
		BigInteger h0 = new BigInteger("67452301", 16), h1 = new BigInteger("efcdab89", 16), 
				   h2 = new BigInteger("98badcfe", 16), h3 = new BigInteger("10325476", 16),
				   h4 = new BigInteger("c3d2e1f0", 16);
		
		System.out.println("Original Buffer Values:");
		System.out.println(h0 + "\n" + h1 + "\n" + h2 + "\n" + h3 + "\n" + h4 + "\n\n");
		
		for(int i = 0; i < message.length(); i += 512) {
			String block = message.substring(i, i + 512);
			BigInteger[] words = new BigInteger[80];
			
			System.out.println("Block " + i/512 + ":");
			for(int j = 0; j < block.length(); j += 32) {
				words[j/32] = new BigInteger(block.substring(j, j + 32), 2);
				System.out.println(String.format("Word %d = %d", j/32, words[j/32]));
			}
			
			for(int j = 16; j < 80; ++j) {
				words[j] = rotateLeft((words[j - 3].xor(words[j - 8]).xor(words[j - 14]).xor(words[j - 16])), 1, 32).mod(BI2P32);
				System.out.println(String.format("Word %d = %d", j, words[j]));
			}

			BigInteger a = h0, b = h1, c = h2, d = h3, e = h4;
			
			for(int j = 0; j < 80; ++j) {
				BigInteger f = BigInteger.ZERO, k = BigInteger.ZERO;
				System.out.println("Round " + j/20 + " Step " + j%20);
				switch(j/20) {
				case 0:
					k = new BigInteger("5A827999", 16);;
					f = (b.and(c)).or(b.not().and(d));
					break;
				case 1:
					k = new BigInteger("6ED9EBA1", 16);;
					f = (b.xor(c)).xor(d);
					break;
				case 2: 
					k = new BigInteger("8F1BBCDC", 16);;
					f = ((b.and(c)).or(b.and(d))).or(c.and(d));
					break;
				case 3:
					k = new BigInteger("CA62C1D6", 16);;
					f = (b.xor(c)).xor(d);
					break;
				}

				BigInteger temp = rotateLeft(a, 5, 32).add(f).add(e).add(k).add(words[j]).mod(BI2P32); 
				e = d;
				d = c;
				c = rotateLeft(b, 30, 32).mod(BI2P32);
				b = a;
				a = temp;
				
				System.out.println("Round " + j + "\n--------------------------\n");
				System.out.println(a + "\n" + b + "\n" + c + "\n" + d + "\n" + e + "\n");
			}


			h0 = h0.add(a).mod(BI2P32);
			h1 = h1.add(b).mod(BI2P32);
			h2 = h2.add(c).mod(BI2P32);
			h3 = h3.add(d).mod(BI2P32);
			h4 = h4.add(e).mod(BI2P32);
		
			System.out.println("Block " + i/512 + "\n--------------------------\n");
			System.out.println(h0 + "\n" + h1 + "\n" + h2 + "\n" + h3 + "\n" + h4 + "\n\n");
		}
		
		String hash = h0.toString(16) + h1.toString(16) + h2.toString(16) + h3.toString(16) + h4.toString(16);
		System.out.println(String.format("SHA1(\"%s\") = %s", plainText, hash));
	}
}