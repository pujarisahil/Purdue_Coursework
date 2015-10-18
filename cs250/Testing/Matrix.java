import java.util.Random;

/*
 * CS180 - Project 2: Matrix
 * Construct matrices and perform operations as prescribed
 * by the  user.
 * @author Andrew Blejde, ablejde@purdue.edu, L01
 */

public class Matrix
{
    // Declare instance variables
    final static int BOUND = 11;    // Bounds for random variable
    int[][] matrix;                 // 2D integer matrix
    int size;                       // Size int for matrix
    Random random = new Random();   // Use generate random numbers [-10, 10]
    
    public Matrix(int size)
    {
        // Constructor
        // Initialize size and matrix, default variables to 0;
        this.size = size;
        this.matrix = new int[size][size];
        
        // initialize values to 0
        for (int i = 0; i < this.matrix.length; i++)
        {
            for (int j = 0; j < this.matrix[i].length; j++)
                this.matrix[i][j] = 0;
        }
    }
    public void constructMatrix(String type)
    {
        // Make sure type is uppercase to have best change at
        // finding a match
        type = type.toUpperCase();

        // Series of switch statements with cases compared to 
        // different matrice types. Compatible Java JDK 7+

        switch (type)
        {
            case "DIAGONAL":
                constructDiagonal();
                break;
            case "IDENTITY":
                constructIdentity();
                break;
            case "SYMMETRIC":
                constructSymmetric();
                break;
            case "SKEWSYMMETRIC":
                constructSkewSymmetric();
                break;
            case "GENERAL":
                constructGeneral();
                break;
            default:
                System.out.println("There was an error, no match found for matrix type.");
        }
    }
    public void constructDiagonal()
    {
        /* 
         * Construct a diagonal matrix, that is a matrix where the
         * diagonal indices are non zero and all other values are 0.
         * Indices where i is equal to j are diagonal indices that must be
         * set to 0, so using 2 nested for loops checking for equivalent i
         * and j variables. Diagonal indices can be of any value within range
         * [-10, 10] hence the call to random.nextInt() modulo BOUND.
        */

        for (int i = 0; i < this.matrix.length; i++)
        {
            for (int j = 0; j < this.matrix.length; j++)
            {
                if (i == j)
                    this.matrix[i][j] = random.nextInt() % BOUND;
            }
        }
    }
    public void constructIdentity()
    {
        /* 
         * Construct a diagonal matrix, that is a matrix where the
         * diagonal indices are non zero and all other values are 0.
         * Indices where i is equal to j are diagonal indices that must be
         * set to 0, so using 2 nested for loops checking for equivalent i
         * and j variables. Diagonal indices must be value 1.
        */

        for (int i = 0; i < this.matrix.length; i++)
        {
            for (int j = 0; j < this.matrix.length; j++)
            {
                if (i == j)
                    this.matrix[i][j] = 1;
            }
        }
    }
    public void constructSymmetric()
    {
        /* 
         * Construct a symmetric matrix, that is a matrix where the entries
         * opposite each other across the diagonal are equal to each other.
         * Use random numbers to populate values along the diagonal and above
         * (j >= i), then map [i][j] to [j][i] when i is greater than j
        */

        for (int i = 0; i < this.matrix.length; i++)
        {
            for (int j = 0; j < this.matrix.length; j++)
            {
                if (j >= i)
                    this.matrix[i][j] = random.nextInt() % BOUND;
                else
                    this.matrix[i][j] = this.matrix[j][i];
            }
        }
    }
    public void constructSkewSymmetric()
    {
        /* 
         * Construct a skew-symmetric matrix, that is a matrix where the
         * diagonal contains all 0's and, unlike a symmetric matrix, the
         * values opposite the diagonal sum to 0. Therefore, instead of
         * duplicating [j][i] into [i][j], we multiply element [j][i] by
         * -1 in order to ensure a summation of 0.
        */

        for (int i = 0; i < this.matrix.length; i++)
        {
            for ( int j = 0; j < this.matrix.length; j++)
            {
                if (j > i)
                    this.matrix[i][j] = random.nextInt() % BOUND;
                else if (j == i)
                    this.matrix[i][j] = 0;
                else
                    this.matrix[i][j] = -1 * this.matrix[j][i];
            }
        }
    }
    public void constructGeneral()
    {
        /*
         * Construct a general matrix, that is a matrix where
         * all elements of the matrix are randomized.
        */

        for (int i = 0; i < this.matrix.length; i++)
        {
            for (int j = 0; j < this.matrix[i].length; j++)
                this.matrix[i][j] = random.nextInt() % BOUND;
        }
    }
    public int computeFrobeniusOneNorm()
    {
        /*
         * Compute Frobenius One Norm on matrix object calling
         * this method. This is found by usmming the absolute value
         * of all elements in the matrice, accomplished using nested
         * for loops accessing each indice [i][j].
        */

        int sum = 0;
        for (int i = 0; i < this.matrix.length; i++)
        {
            for (int j = 0; j < this.matrix[i].length; j++)
                sum = sum + Math.abs(this.matrix[i][j]);
        }
        
        return sum;
    }
    public int computeOneNorm()
    {
        /*
         * Compute One Norm on matrix object calling this method. This 
         * is found by summing the column entries and placing them into
         * a column total matix "arr." This is accomplished with nested for
         * loops that place the values [i][j] into the [j] colum of array,
         * ensuring the column totals are added. 
        */

        int largest = 0;
        int[] arr = new int[this.size];
        for (int i = 0; i < this.matrix.length; i++)
        {
            for (int j = 0; j < this.matrix[i].length; j++)
            {
                arr[j] += Math.abs(this.matrix[i][j]);
            }
        }

        /*
         * Using array "arr" which houses the column totals, iterate
         * through, comparing the value of each indice to the previous
         * largest value held in integer largest.
        */

        for (int n = 0; n < arr.length; n++)
        {
            if (arr[n] > largest)
                largest = arr[n];
        }
        
        // Return largest value
        return largest;
    }
    public int computeInfinityNorm()
    {
        /* 
         * Comptue the largest row sum of values. Similar to computeOneNorm,
         * this instead places values into column i of array (arr[i]) in order
         * to increment with each new row value.
        */

        int largest = 0;
        int[] arr = new int[this.size];
        for (int i = 0; i < this.matrix.length; i++)
        {
            for (int j = 0; j < this.matrix[i].length; j++)
            {
                arr[i] += Math.abs(this.matrix[i][j]);
            }
        }
        /*
         * Using array "arr" which houses the row totals, iterate
         * through, comparing the value of each indice to the previous
         * largest value held in integer largest.
        */

        for (int n = 0; n < arr.length; n++)
        {
            if (arr[n] > largest)
                largest = arr[n];
        }
        
        // Return largest value
        return largest;
    }
    public void printMatrix()
    {
        /*
         * Using a nested for loop, print through each indice of the matrix
         * where the outer loop starts the iteration of a new row, and the
         * inner loop uses printf to print each column element of row [i].
         * At the end of the inner for loop, use println() to move add new line.
        */
        
        for (int i = 0; i < this.matrix.length; i++)
        {
            for (int j = 0; j < this.matrix[i].length; j++)
            {
                System.out.printf("%d ", this.matrix[i][j]);
            }
            System.out.println();
        }
    }
}