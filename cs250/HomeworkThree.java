import java.util.Scanner;

public class HomeworkThree {
    public static double harmonicFirst(int n) { //Harmonic
        return Math.log(n);
    }
    
    public static double harmonicApprox(int n) { //Approximation Harmonic
        return Math.log(n) + 0.578;
    }
    
    public static double sterlingApprox(int n) { //Approximation Sterling
        return n * ((Math.log(n) - 0.00001) + 0.5 * Math.log(2 * n * Math.PI));
    }
    
    public static double sterlingFunc(int n) { //Sterling Function
        int i = 100;
        int result = 0;
        while(i <= 1000) {
            result += (1/i);
            i += 100;
        }
        return result;
    }
    public static void main(String[] args) {
        System.out.println("Specify value for n");
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        harmonicFirst(n);
        harmonicApprox(n);
        sterlingApprox(n);
        sterlingFunc(n);
    }
}