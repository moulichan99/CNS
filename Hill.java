class Hill {

  // ---------------------------------------------- GENERAL MATRIX
  // FUNCTIONS---------------------------------------------------//

  public static int[][] getCofactor(int[][] matrix, int n, int p, int q) {

    int[][] temp = new int[n - 1][n - 1];
    int x = 0;
    int y = 0;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (i != p && j != q) {
          temp[x][y] = matrix[i][j];
          if (y + 1 == n - 1) {
            y = 0;
            x = x + 1;
          } else {
            y = y + 1;
          }
        }
      }
    }
    return temp;
  }

  public static int getDeterminant(int[][] matrix, int n) {
    if (n == 1) {
      return matrix[0][0];
    }
    int[][] temp = new int[n - 1][n - 1];
    int D = 0;
    int sign = 1;

    for (int i = 0; i < n; i++) {
      temp = getCofactor(matrix, n, 0, i);

      D = D + sign * matrix[0][i] * getDeterminant(temp, n - 1);
      sign = -1 * sign;
    }
    return D;
  }

  public static int[][] getAdjacencyMatrix(int[][] matrix, int n) {
    int[][] adjMat = new int[n][n];
    int[][] temp = new int[n - 1][n - 1];
    int sign;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        temp = getCofactor(matrix, n, i, j);
        sign = ((i + j) % 2 == 0) ? 1 : -1;
        adjMat[j][i] = sign * getDeterminant(temp, n - 1);
      }
    }
    return adjMat;
  }

  public static int[][] getInverseMatrix(int[][] matrix, int n) {
    int[][] inverseMat = new int[n][n];
    int[][] adjMat = new int[n][n];

    adjMat = getAdjacencyMatrix(matrix, n);

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        inverseMat[j][i] = adjMat[i][j];
      }
    }

    return inverseMat;
  }

  public static void printMatrix(int[][] matrix, int m, int n) {
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        System.out.print(matrix[i][j] + " ");
      }
      System.out.println();
    }
  }

  public static int[][] multiplyMatrix(int[][] matrix1, int[][] matrix2, int m, int n, int p, int q) {
    int[][] result = new int[m][q];
    if (n != p) {
      System.out.println("Cannot Multiply Matrices");
      return result;
    }
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < q; j++) {
        int sum = 0;
        for (int k = 0; k < n; k++)
          sum = matrix1[i][k] * matrix2[k][j] + sum;
        result[i][j] = sum;
      }
    }
    return result;
  }

  // -----------------------------------------------------ALGO SPECIFIC
  // CODE-------------------------------------------------------//

  // finds 1mod26 of a given number
  public static int getInverse(int det) {
    for (int i = 0;; i++) {
      if ((det * i) % 26 == 1) {
        return i;
      }
    }
  }

  // returns mod26 inverse of a matrix
  public static int[][] getInverseMatrix(int[][] adjMat, int n, int inv) {
    int[][] invMatrix = new int[n][n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        invMatrix[i][j] = (adjMat[i][j] * inv) % 26;
        if (invMatrix[i][j] < 0) {
          invMatrix[i][j] = invMatrix[i][j] + 26;
        }
      }

    }
    return invMatrix;
  }

  // accepts plaintext and key matrix as input and returns encrypted text
  public static String getCipherText(String message, int matrix[][], int n) {
    int[][] messageCode = new int[n][50];
    int[][] cipherCode = new int[n][50];
    StringBuilder cipherText = new StringBuilder();

    if (message.length() % n != 0) {
      int padding = n - message.length() % n;
      for (int i = 0; i < padding; i++) {
        message+='x';
      }
    } 
    char[] charMessage = message.toCharArray();
    System.out.println(charMessage);
    int j = 0;
    int k = 0;
    for (int i = 0; i < charMessage.length; i++) {
      messageCode[j][k] = (int) charMessage[i] -97 ;
      if (j + 1 == n) {
        j = 0;
        k = k + 1;
      } else {
        j = j + 1;
      }
    }
    printMatrix(messageCode, n, k);
    cipherCode = multiplyMatrix(matrix, messageCode, n, n, n, k);
    printMatrix(cipherCode, n, k);

    for (int i = 0; i < k; i++) {
      for (int y = 0; y < n; y++) {
        cipherText.append((char) (cipherCode[y][i] % 26 + 97 )  );
      }
    }
    return cipherText.toString();
  }

  public static String getDecipherText(String message, int[][] matrix, int n) {
    int[][] messageCode = new int[n][50];
    int[][] decipherCode = new int[n][50];
    StringBuilder decipherText = new StringBuilder();
    char[] charMessage = message.toCharArray();

    System.out.println(charMessage);
    // charMessage = message.toCharArray();
    int j = 0;
    int k = 0;
    for (int i = 0; i < charMessage.length; i++) {
      messageCode[j][k] = (int) charMessage[i] - 97;
      if (j + 1 == n) {
        j = 0;
        k = k + 1;
      } else {
        j = j + 1;
      }
    }
    printMatrix(messageCode, n, k);
    decipherCode = multiplyMatrix(matrix, messageCode, n, n, n, k);
    printMatrix(decipherCode, n, k);

    for (int i = 0; i < k; i++) {
      for (int y = 0; y < n; y++) {
        decipherText.append((char) (decipherCode[y][i] % 26 + 97));
      }
    }
    return decipherText.toString();
  }

  public static void main(String[] args) {
    int[][] matrix = { { 6, 24, 1 }, { 13, 16, 10 }, { 20, 17, 15 } };
    // int[][] matrix2 = { { 0 }, { 2 }, { 19 } };
    int n = 3;
    int[][] inverseMat = new int[n][n];
    int[][] adjMat = new int[n][n];
    int determinant;
    int inv;

    adjMat = getAdjacencyMatrix(matrix, n);
    determinant = getDeterminant(matrix, n);
    System.out.println("determinant is "+determinant);
    inv = getInverse(determinant);
    inverseMat = getInverseMatrix(adjMat, n, inv);
    // printMatrix(matrix, n, n);
    System.out.println("INVERSE MATRIX...........");
    printMatrix(inverseMat, n, n);

    String message = "pokemon";
    // int cipherColSize = message.length() % n == 0 ? message.length() / n :
    // message.length() / n + 1;
    String cipherText;
    cipherText = getCipherText(message, matrix, n);
    System.out.println(cipherText);
    String decipherText;
    
    decipherText = getDecipherText(cipherText, inverseMat, n);
    System.out.println(decipherText);
  }
}
