import java.math.BigInteger;

public class DES{

	static int[] PC1 = { 57,49,41,33,25,17,9,1,58,50,42,34,26,18,
		             10,2,59,51,43,35,27,19,11,3,60,52,44,36,
			     63,55,47,39,31,23,15,7,62,54,46,38,30,22,
			     14,6,61,53,45,37,29,21,13,5,28,20,12,4
       	};

	static int[] PC2 = { 14,17,11,24,1,5,3,28,15,6,21,10,
		             23,19,12,4,26,8,16,7,27,20,13,2,
			     41,52,31,37,47,55,30,40,51,45,33,48,
			     44,49,39,56,34,53,46,42,50,36,29,32					
	};

	static int[] keySchedule = {1,1,2,2,2,2,2,2,1,2,2,2,2,2,2,1};

	static int[] IP = { 58,50,42,34,26,18,10,2,60,52,44,36,28,20,12,4,
		            62,54,46,38,30,22,14,6,64,56,48,40,32,24,16,8,
			    57,49,41,33,25,17,9,1,59,51,43,35,27,19,11,3,
			    61,53,45,37,29,21,13,5,63,55,47,39,31,23,15,7
    	};

	static int[] FP = { 40,8,48,16,56,24,64,32,39,7,47,15,55,23,63,31,
	  	            38,6,46,14,54,22,62,30,37,5,45,13,53,21,61,29,
			    36,4,44,12,52,20,60,28,35,3,43,11,51,19,59,27,
			    34,2,42,10,50,18,58,26,33,1,41,9,49,17,57,25
	};

	static int[] E = { 32,1,2,3,4,5,4,5,6,7,8,9,8,9,10,11,12,13,12,
			   13,14,15,16,17,16,17,18,19,20,21,20,21,22,23,
			   24,25,24,25,26,27,28,29,28,29,30,31,32,1
	};

	static int[][] S={	{14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7,0,15,7,4,14,2,13,1,10,6,12,11,9,5,3,8,
		  		 4,1,14,8,13,6,2,11,15,12,9,7,3,10,5,0,15,12,8,2,4,9,1,7,5,11,3,14,10,0,6,13
	},		     	{15,1,8,14,6,11,3,4,9,7,2,13,12,0,5,10,3,13,4,7,15,2,8,14,12,0,1,10,6,9,11,5,
		 		 0,14,7,11,10,4,13,1,5,8,12,6,9,3,2,15,13,8,10,1,3,15,4,2,11,6,7,12,0,5,14,9
	},			{10,0,9,14,6,3,15,5,1,13,12,7,11,4,2,8,13,7,0,9,3,4,6,10,2,8,5,14,12,11,15,1,
				 13,6,4,9,8,15,3,0,11,1,2,12,5,10,14,7,1,10,13,0,6,9,8,7,4,15,14,3,11,5,2,12
	},			{7,13,14,3,0,6,9,10,1,2,8,5,11,12,4,15,13,8,11,5,6,15,0,3,4,7,2,12,1,10,14,9,
				 10,6,9,0,12,11,7,13,15,1,3,14,5,2,8,4,3,15,0,6,10,1,13,8,9,4,5,11,12,7,2,14
	},			{2,12,4,1,7,10,11,6,8,5,3,15,13,0,14,9,14,11,2,12,4,7,13,1,5,0,15,10,3,9,8,6,
				 4,2,1,11,10,13,7,8,15,9,12,5,6,3,0,14,11,8,12,7,1,14,2,13,6,15,0,9,10,4,5,3
	},			{12,1,10,15,9,2,6,8,0,13,3,4,14,7,5,11,10,15,4,2,7,12,9,5,6,1,13,14,0,11,3,8,
				 9,14,15,5,2,8,12,3,7,0,4,10,1,13,11,6,4,3,2,12,9,5,15,10,11,14,1,7,6,0,8,13
	},			{4,11,2,14,15,0,8,13,3,12,9,7,5,10,6,1,13,0,11,7,4,9,1,10,14,3,5,12,2,15,8,6,
				 1,4,11,13,12,3,7,14,10,15,6,8,0,5,9,2,6,11,13,8,1,4,10,7,9,5,0,15,14,2,3,12
	},			{13,2,8,4,6,15,11,1,10,9,3,14,5,0,12,7,1,15,13,8,10,3,7,4,12,5,6,11,0,14,9,2,
				 7,11,4,1,9,12,14,2,0,6,10,13,15,3,5,8,2,1,14,7,4,10,8,13,15,12,9,0,3,5,6,11			} };

	static int[] P = { 16,7,20,21,29,12,28,17,1,15,23,26,5,18,31,10,
		 	   2,8,24,14,32,27,3,9,19,13,30,6,22,11,4,25							
	};

	static String hexToBin(String hex, int n){
		hex  = (hex.length() < n ? new String(new char[n - hex.length()]).replace('\0','0') : "") + hex; // important
		String bin = "";
		for(int i = 0; i < hex.length(); ++i)
		{
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
		}                                                                                                               
		return bin;                                                                                        
	}
	
	static BigInteger circularShiftLeft(BigInteger a, int shift, int n_bits){
		//BigInteger mask = new BigInteger("1111111111111111111111111111",2);
		BigInteger mask = BigInteger.ONE.shiftLeft(n_bits).subtract(BigInteger.ONE);
		BigInteger topBits = a.shiftRight(n_bits - shift);
		BigInteger result = a.shiftLeft(shift).and(mask).or(topBits);
		return result;	
	}	

	static BigInteger[] generateKeys(String key){
		String keyString64B = hexToBin(key, 16);
		//Apply PC1
		String keyString56B_PC1 = "";
		for(int i = 0; i < 56; ++i)
			keyString56B_PC1 += keyString64B.charAt(PC1[i]-1);

		
		BigInteger keyBI56B = new BigInteger(keyString56B_PC1,2);

		//System.out.println(keyBI56B);
		//Generate RoundKeys.
		BigInteger[] roundKeys = new BigInteger[16];
		BigInteger C = keyBI56B.shiftRight(28), D = keyBI56B.subtract(C.shiftLeft(28));
		

		for(int i = 0; i < 16; ++i){
			//Perform Circular Left Shift.
			C = circularShiftLeft(C, keySchedule[i], 28);
			D = circularShiftLeft(D, keySchedule[i], 28);

			String roundKey = hexToBin(C.toString(16), 7) + hexToBin(D.toString(16), 7); //  4 * 7 = 28
			String roundKey_PC2 = "";
		
			//Apply PC2.
			for(int j = 0; j < 48; ++j)
				roundKey_PC2 += roundKey.charAt(PC2[j]-1);
			roundKeys[i] = new BigInteger(roundKey_PC2, 2);
		}
		return roundKeys;
	}

	public static void main(String[] args){
		String key = "0E329232EA6D0D73";
		BigInteger[] roundKeys = generateKeys(key); 
		String plaintext = "The quick brown fox jumps over the lazy dog";
	
		String cipherText = encrypt(plaintext, roundKeys, false);
	}

	static String encrypt(String plaintext, BigInteger[] roundkeys, boolean decrypt){
		if(decrypt == true)
			for(int i = 0; i < 16; ++i){
				BigInteger temp = roundkeys[i];
				roundkeys[i] = roundkeys[16 - i - 1];
				roundkeys[16 - i - 1] = temp;			
			}

		//Convert plaintext to 64bit Blocks.
		String hexString = String.format("%x", new BigInteger(plaintext.getBytes()));
		int padLength = (hexString.length() % 16 == 0 ? 0 : 16 - hexString.length() % 16); // trailing zeroes
		String binString = hexToBin(hexString + new String(new char[padLength]).replace("\0","0"), hexString.length()+padLength), ciphertext = "";
	
		//System.out.println(binString);
		
		for(int i = 0; i < binString.length(); i += 64){
			//Apply IP.
			String block = binString.substring(i, i + 64), ipBlock = "";
			for(int j = 0; j < 64; ++j)
				ipBlock += block.charAt(IP[j] - 1);
		
			//Round Operations.
			BigInteger prevL = new BigInteger(ipBlock.substring(0, 32), 2);
		    BigInteger prevR = new BigInteger(ipBlock.substring(32, 64), 2);
			BigInteger currL = BigInteger.ZERO, currR = currL;
			String preoutput = "";

			for(int round = 0; round < 16; ++round, prevL = currL, prevR = currR){
				currL = prevR;
				currR = prevL.xor(feistel(prevR, roundkeys[round]));
			}
			
			//Swap.
			preoutput = hexToBin(currR.toString(16), 8) + hexToBin(currL.toString(16), 8);

			//Apply FP.
			String cipher = "";
			for(int j = 0; j < 64; ++j)
				cipher += preoutput.charAt(FP[j] - 1);

			ciphertext += new BigInteger(cipher, 2).toString(16);
		}
		return ciphertext;
	}

	static BigInteger feistel(BigInteger r, BigInteger key){
		//Expansion.
		String R32B = hexToBin(r.toString(16), 8), expandedR = ""; // 4 * 8 = 32
		for(int i = 0; i < 48; ++i)
			expandedR += R32B.charAt(E[i] - 1);

		//XOR Operation.
		String xorR_K = hexToBin(key.xor(new BigInteger(expandedR, 2)).toString(16), 12); // 4 * 12 = 48
		
		//Substitution.
		String substitutedResult = "";
		for(int i = 0, box = 0; i < 48; i += 6, box = i/6){
			int row = Integer.parseInt(xorR_K.substring(i, i + 1) + xorR_K.substring(i + 5, i + 6), 2);
			int col = Integer.parseInt(xorR_K.substring(i + 1, i + 5), 2);
			substitutedResult += hexToBin(String.format("%x", S[box][row*16 + col]),1);
		}

		//Permutation.
		String permutedResult = "";
		for(int i = 0; i < 32; ++i)
			permutedResult += substitutedResult.charAt(P[i] - 1);
		
		return new BigInteger(permutedResult, 2);
	}
}
          