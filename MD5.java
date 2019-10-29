import java.math.BigDecimal;
import java.math.BigInteger;


public class MD5{

	static BigInteger[] t = new BigInteger[64];
	static BigDecimal BD2 = BigDecimal.valueOf(2), BD2P32 = BD2.pow(32);
	static BigInteger BI2 = BigInteger.valueOf(2), BI2P32 = BI2.pow(32), BI2P64 = BI2.pow(64);

	public static String hexToBin(String hex){
		String bin = "";
		for(int i = 0; i < hex.length(); ++i)
			switch(hex.charAt(i)){
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

	static String littleEndian(String word){
		return word.substring(24, 32) + word.substring(16, 24) + 
		       word.substring(8, 16) + word.substring(0, 8); 
	}

	static int[][] s = { {7, 12, 17, 22}, {5, 9, 14, 20}, {4, 11, 16, 23}, {6, 10, 15, 21} };

	public static void main(String[] args){
	
		String message = "The quick brown fox jumps over the lazy dog";

		//Lets calculate the T table.

		for(int i = 0; i < 64; i++){
			BigDecimal value = BD2P32.multiply(BigDecimal.valueOf(Math.abs(Math.sin(i + 1)))); // 
			t[i] = value.toBigInteger().mod(BI2P32);  // 
		}
		
		//Convert Message to BitString from Hexadecimal then to Binary.
		
		String binString = hexToBin(String.format("%x", new BigInteger(message.getBytes())));
		BigInteger messageLength = new BigInteger(String.format("%d", binString.length())).mod(BI2P64); // %s 
		//System.out.print("messageLength is = "+messageLength);
		String lengthString = messageLength.toString(2);
		lengthString = (lengthString.length() < 64 ? new String(new char[64 - lengthString.length()]).replace('\0', '0'): "") + lengthString;
		System.out.print("\nLength = " + messageLength.toString() + " mString = " + lengthString.length());

		//Appending Padding Bits.
		
		int padLength = (messageLength.intValue() % 512 < 448 ? 
				 448 - messageLength.intValue() % 512 :
				 512 + 448 - messageLength.intValue() % 512
				);

		binString += "1" + new String(new char[padLength - 1]).replace('\0', '0');
		binString += littleEndian(lengthString.substring(32)) + littleEndian(lengthString.substring(0, 32));
		System.out.print("\nmString=  " + binString.length());

		System.out.print(new BigInteger(lengthString, 2));
		
		//Process Blocks.
		
		BigInteger a0 = new BigInteger("67452301", 16), b0 = new BigInteger("efcdab89", 16),
			   c0 = new BigInteger("98badcfe", 16), d0 = new BigInteger("10325476", 16);

		for(int i = 0; i < binString.length(); i += 512){
			String block = binString.substring(i, i + 512);
			BigInteger[] m = new BigInteger[16];
			for(int j = i; j < i + 512; j += 32)
				m[j/32] = new BigInteger(littleEndian(block.substring(j, j + 32)), 2);
			
			BigInteger a = a0, b = b0, c = c0, d = d0;
			for(int j = 0, p = 0; j < 64; ++j){
				BigInteger f = BigInteger.ZERO;
				switch(j/16){
					case 0:
						f = (b.and(c)).or(b.not().and(d));
						p = j;
						break;
					case 1:
						f = (d.and(b)).or(d.not().and(c));
						p = (1 + 5*j) % 16;
						break;
					case 2:
						f = b.xor(c).xor(d);
						p = (5 + 3*j) % 16;
						break;
					case 3:
						f = c.xor(b.or(d.not()));
						p = (7*j) % 16;
				}

				f = f.add(a).add(t[j]).add(m[p]).mod(BI2P32);
				a = d;
				d = c;
				c = b;
				b = b.add(f.shiftLeft(s[j/16][j%4]).or(f.shiftRight(32 - s[j/16][j%4]))).mod(BI2P32);

				System.out.print(String.format("\n%d %s %s %s %s", s[j/16][j%4], a.toString(), b.toString(), c.toString(), d.toString()));
			}
			
			a0 = a0.add(a).mod(BI2P32);
			b0 = b0.add(b).mod(BI2P32);
			c0 = c0.add(c).mod(BI2P32);
			d0 = d0.add(d).mod(BI2P32);			
		}

		String a = new BigInteger(littleEndian(hexToBin(a0.toString(16))), 2).toString(16),
		       b = new BigInteger(littleEndian(hexToBin(b0.toString(16))), 2).toString(16),
		       c = new BigInteger(littleEndian(hexToBin(c0.toString(16))), 2).toString(16),
		       d = new BigInteger(littleEndian(hexToBin(d0.toString(16))), 2).toString(16);
		System.out.print("\n" + a + b + c + d);

			
	}
}
