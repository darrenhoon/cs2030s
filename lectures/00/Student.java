public class Student {
    private static int size = 0;

    Student(int id, String name){
        this.id = id;
        this.name = name;
        size++;
    }

    private int id;
    private final String name;
    private Student friend;

    public void sayHello(){
        System.out.printf("%s says hello world! \n",this.name);
    }
}
