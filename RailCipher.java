class RailCipher {

	public static String cipher(String text, int key) {
		char[] plainText = text.toCharArray();
		char[][] rail = new char [key][text.length()];
    	for (int i=0; i < key; i++) 
        	for (int j = 0; j < text.length(); j++) 
            	rail[i][j] = '\n';
        boolean dir_down = false; 
   		int row = 0, col = 0; 
    	for (int i=0; i < text.length(); i++) 
    	{ 
        if (row == 0 || row == key-1) 
            dir_down = !dir_down; 
  
        	rail[row][col++] = plainText[i]; 
  
        	if(dir_down)
        		row++;
        	else
        		row--; 
    	} 
	    String result=""; 
    	for (int i=0; i < key; i++) 
        	for (int j=0; j < text.length(); j++) 
            	if (rail[i][j]!='\n') 
                	result+=rail[i][j]; 
  
    	return result; 
} 
public static String decipher(String text, int key) {
		String res="";
		char[] cipherText = text.toCharArray();
		char[][] rail = new char [key][text.length()];
    	for (int i=0; i < key; i++) 
        	for (int j = 0; j < text.length(); j++) 
            	rail[i][j] = '\n';
        
        boolean dir_down = false; 
   		int row = 0, col = 0; 
    	for (int i=0; i < text.length(); i++) 
    	{ 
        if (row == 0 || row == key-1) 
            dir_down = !dir_down; 
  
        	rail[row][col++] = '*'; 
  
        	if(dir_down)
        		row++;
        	else
        		row--; 
    	}
    	int index = 0; 
    	for (int i=0; i<key; i++) 
        	for (int j=0; j<text.length(); j++) 
            	if (rail[i][j] == '*' && index<text.length()) 
                	rail[i][j] = cipherText[index++];

        row = 0;
        col = 0; 
    	for (int i=0; i < text.length(); i++) 
    	{ 
        if (row == 0 || row == key-1) 
            dir_down = !dir_down; 
  
        	if (rail[row][col] != '*')
        		 res += rail[row][col++];
        	if(dir_down)
        		row++;
        	else
        		row--; 
    	}        	 
		return res;
}
public static void main(String[] args) {
		int key = 2;
		String text = "thisisasecretmessage";
		String cipherText;
		String decipherText;
	    cipherText = cipher(text, key);
		System.out.println(cipherText);
		decipherText = decipher(cipherText, key);
		System.out.println(decipherText);

	}
}
