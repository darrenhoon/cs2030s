import java.util.Optional;
import java.util.Scanner;
import java.util.List;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class Main {

    /**
     * Reads user's inputs via scan and creates lists for servers and customers.
     * @param args unused
     */
    public static void main(String[] args) {
    
        Roster roster = new Roster("myClass");    

        Scanner sc = new Scanner(System.in);
        int numOfRecords = sc.nextInt();
         
        for (int i = 0; i < numOfRecords; i++) {
            String studentName = sc.next();
            String modName = sc.next();
            String testName = sc.next();
            String testGrade = sc.next();
            
            Student student = new Student(studentName);
            Assessment test = new Assessment(testName, testGrade);
            Module module = new Module(modName).put(test);
                       
            Optional<Student> currStudent = roster.get(studentName);
            Optional<Module> currModule = currStudent.flatMap(x -> x.get(modName));

            //case1: student not inside
            currStudent.ifPresentOrElse(x -> x.get(modName),
                () -> roster.put(student.put(module)));
 
            //case2: student inside, mod not inside
            currModule.ifPresentOrElse(x -> x.get(testName),
                () -> roster.get(studentName).map(x -> x.put(module)));

            //case3: student and mod both inside, but test not inside
            roster.get(studentName).flatMap(x -> x.get(modName))
                .map(x -> x.put(test));
        }
        
        try {
            while (sc.hasNext()) {
                String msg = roster.getGrade(sc.next(), sc.next(), sc.next());
                System.out.println(msg);
            }
        } catch (NoSuchElementException e) {
            return;
        }
    }
}
