import java.io.* ;
import java.util.*;

public class Playfair{
	// return  index of row 
	public static int rowreturn(char t[][],char ch)
	{
		int i,j;
		if(ch=='j')
			ch='i';
		for(i=0;i<5;i++)
		{
			for(j=0;j<5;j++)
			{
				if(t[i][j]==ch)
					return i;
			}
		}
		return -1;
	}
	// return  index of column
	public static int colreturn(char t[][],char ch)
	{
		int i,j;
		if(ch=='j')
			ch='i';
		for(i=0;i<5;i++)
		{
			for(j=0;j<5;j++)
			{
				if(t[i][j]==ch)
					return j;
			}

		}
		return -1;
	}
	//displaying
    public static void display(char t[][])
    {
    	int i,j;
			for(i=0;i<5;i++)
			{
				for(j=0;j<5;j++)
				{
					System.out.print(t[i][j]+" ");
				}
				System.out.println("-----------");
			}
	}
	public  static void main(String args[]) throws IOException
    {
			// Read the Plain text
			System.out.println("Enter the PLAIN TEXT....");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String temp1;
			String temp = br.readLine();
			char plain[];
			plain = temp.toCharArray();
			char p[][] = new char[10][2];
			
			int i,j,count= 0,alcount=0;
			//for(i=0;i<temp.length();i++)
			//	plain[i]=temp.charAt(i);

			plain=temp.toCharArray();
			
			System.out.println("Enter the Key....");
			temp = br.readLine();
			String key="";
			
			int  visited[] = new int[26];
			for(i=0;i<26;i++)
				visited[i] = 0;
			j=0;
 			for(i=0;i<temp.length();i++)
			{
				int r = (int)temp.charAt(i);
				r = (r-97)%26;
				if(visited[r]!=1)
				{
				 key+=temp.charAt(i);
			     visited[r]=1;
				}
			}
			

			String result ="";
			char table[][] = new char[5][5];
			char alpha[] = new char[]{'a','b','c','d','e','f','g','h','i','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
			int row1,col1,row2,col2;
           // filling the 5 * 5 matrices  
		   System.out.println(temp+"");
			for(i=0;i<5;i++)
			{
				for(j=0;j<5;j++)
				{
					if(count<key.length())
						table[i][j]=key.charAt(count++);
					else
					{
						
						if(key.indexOf(alpha[alcount])>=0)
						{
							alcount++;
							j--;
						}				
						else
							table[i][j] =alpha[alcount++];
					}
				}
			}	
			//Display 
			display(table);

			//Seperating Plain Text msg
			count=0;
			for(i=0;i<plain.length;i++)
			{
				if(i==plain.length-1)
				{
					p[count][0] = plain[i];
					p[count][1] = 'x';	
				}
				else if(plain[i]!=plain[i+1])
				{
					p[count][0] = plain[i];
					p[count][1] = plain[i+1];
					i++;
				}
				else if(plain[i]==plain[i+1])
				{
					p[count][0] = plain[i];
					p[count][1] = 'x';
				}
				count++;
			}
			// Calculating Cipher Text
			for(i=0;i<count;i++)
			{
					row1=rowreturn(table,p[i][0]);
					col1=colreturn(table,p[i][0]);
					row2=rowreturn(table,p[i][1]);
					col2=colreturn(table,p[i][1]);
					if(row1==row2)
					{
						
						if(col1==4)
							 col1=0;
						 else
							 col1++;
						 
						 if(col2==4)
							 col2=0;
						 else 
							 col2++;
 
					   
					}
					if(col1==col2)
					{
					 	if(row1==4)
							 row1=0;
						else
							 row1++;
						 
						if(row2==4)
							 row2=0;
						else 
							 row2++;		
					}
					result =result + table[row1][col2];
					result =result + table[row2][col1];
			}
			System.out.println("Cipher Messagen is "+result);
	}
}	
