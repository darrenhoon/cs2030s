import java.util.List;
import java.util.ArrayList;

class Contact {
    private final Person first;
    private final Person second;
    private final double time;
    
    Contact(Person first, Person second, double time) {
        this.first = first;
        this.second = second;
        this.time = time;
    }

    List<Person> transmit(double random) {
        List<Person> tempList = new ArrayList<Person>();
        List<Virus> firstViruses = this.first.transmit(random);
        List<Virus> secondViruses = this.second.transmit(random);

        Person newFirst = this.first.infectWith(secondViruses, random); 
        Person newSecond = this.second.infectWith(firstViruses, random);
        
        tempList.add(newFirst);
        tempList.add(newSecond);
        return tempList;
    }

    List<Person> getPeople() {
        List<Person> list = new ArrayList<Person>();
        list.add(this.first);
        list.add(this.second);
        return list;
    }

    double timeOfContact() {
        return this.time;
    }
}
