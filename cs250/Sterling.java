public class HomeworkThree {
    public static double functionFirst(int n) { //Harmonic
        return Math.log(n);
    }
    
    public static double functionSecond(int n) { //Approximation Harmonic
        return Math.log(n) + 0.578;
    }
    
    public static double functionThird(int n) { //Approximation Sterling
        return n((Math.log(n) - Math.e) + 0.5 * Math.log(2 * n * Math.PI));
    }
    
    public static double functionForth(int n) { //Sterling Function
        int i = 100;
        int result = 0;
        while(i <= 1000) {
            result += (1/i);
            i += 100;
        }
    }
    public static void main(String[] args) {
        System.out.println("Specify value for n");
        int n = System.in();
        functionFirst(n);
        functionSecond(n);
        functionThird(n);
        functionForth(n);
    }
}