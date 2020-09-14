import java.util.Scanner;
import java.io.FileReader;
import java.io.FileNotFoundException;

class Main {
    public static void main(String[] args) {

        try { 
            FileReader file = new FileReader(args[0]);

            Scanner sc = new Scanner(file);
            int x = sc.nextInt();
            System.out.println("HelloSekai" + x);


        } catch (FileNotFoundException ex) {
            System.err.println("File is missing");
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.err.print("No file specified");
        } finally {
            System.out.println("Finally Done!");
        }

    }
}
