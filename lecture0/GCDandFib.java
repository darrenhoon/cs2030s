import java.util.Scanner;

class GCDandFib{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int a = sc.nextInt();
        double b = sc.nextDouble();
        System.out.printf("value of a is %d, while b is %f \n", a,b);
        myMethod(a,b);
    }
    static void myMethod(int a, double b){
        if (a<0 || b<0){
            return;
        }

        double powered = Math.pow(a,b);
        System.out.printf("A to the power of B is %f \n", powered);
        int gcd = 0;
        int counter=0;
        int b_floored = (int) b/1;
        while(counter<=a || counter <= b_floored){
            if (counter%a==0 && counter%b_floored==0){
                gcd = counter;
            }
            counter++;
        }
        System.out.printf("GCD of A and B is %d \n", gcd);

        int fib1=1;
        int fib2=1;
        int fib_counter = 0;
        int[] fib_seq= new int[b_floored];
        while(fib_counter<b_floored){
            if (fib2>a){
                fib_seq[fib_counter] = fib2;
                fib_counter++;
            }
            int previous = fib2;
            fib2 = fib1 + fib2;
            fib1 = fib2;
        }
        String fibonaccis = "[ ";
        for(int i=0; i<b_floored; i++){
            fibonaccis += fib_seq[i];
            if (i==b_floored-1){
                fibonaccis+="]";
                break;
            }
            fibonaccis+= ", ";
        }
        System.out.println(fibonaccis);
    }
}
