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
            Module module = new Module(modName);
            Assessment test = new Assessment(testName, testGrade);
            
            Optional<Student> currStud = roster.get(studentName);
            Optional<Module> currMod = currStud.flatMap(stud -> stud.get(modName));

            currStud.ifPresentOrElse(stud -> stud.get(modName), () -> roster.put(student));
            currMod.ifPresentOrElse(mod -> mod.get(testName), () -> roster.get(studentName).map(stud -> stud.put(mod)))
                .map(mod -> mod.put(test));
            
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
