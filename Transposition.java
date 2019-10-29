import java.util.*;
import java.lang.*;

class Transposition {
  public static void printMatrix(char[][] matrix, int m, int n) {
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        System.out.print(matrix[i][j] + " ");
      }
      System.out.println();
    }
  }

  public static String cipher(String text, String keyString) {

    char[] key = keyString.toCharArray();
    char[] plainText = text.toCharArray();
    int col = key.length;
    int row = plainText.length % col == 0 ? plainText.length / col : plainText.length / col + 1;
    char[][] textMatrix = new char[row][col];

    Arrays.sort(key);
    StringBuilder cipherText = new StringBuilder();
    int x = 0, k = 0;
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col; j++) {
        k = keyString.indexOf(key[j]);
        // System.out.println(k);

        if (x >= plainText.length) {
          textMatrix[i][k] = 'x';

        } else {
          textMatrix[i][k] = plainText[x];

          x++;
        }
      }
    }
    printMatrix(textMatrix, row, col);

    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col; j++) {

        // use string builder here or initialize a char array with size row*columnn and
        // set the array in previous loops itself
        cipherText.append(textMatrix[i][j]);
      }
    }

    return String.valueOf(cipherText);
  }

  public static String decipher(String text, String keyString) {

    char[] key = keyString.toCharArray();
    System.out.println(text.length());

    char[] plainText = text.toCharArray();
    int col = key.length;
    int row = plainText.length % col == 0 ? plainText.length / col : plainText.length / col + 1;
    char[][] textMatrix = new char[row][col];

    Arrays.sort(key);
    int k = 0;
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col; j++) {
        k = keyString.indexOf(key[j]);
        // System.out.println(i * col + k);
        textMatrix[i][j] = plainText[i * col + k];
      }
    }

    printMatrix(textMatrix, row, col);
    char[] decipherText = new char[text.length()];

    k = 0;
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col; j++) {
        decipherText[k] = textMatrix[i][j];
        k++;
      }
    }
    return String.valueOf(decipherText);
  }

  public static void main(String[] args) {
    String text = "theyareattackingfromthenorth";

    // should not have repeating letters
    String keyString = "castle";
    String cipherText = cipher(text, keyString);
    System.out.println(cipherText);
    String decipherText = decipher(cipherText, keyString);
    System.out.println(decipherText);
  }
}