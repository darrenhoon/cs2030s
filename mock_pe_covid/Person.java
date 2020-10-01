import java.util.List;
import java.util.ArrayList;

class Person {
    private final String name;
    private final List<Virus> virusList;
    private final boolean notice = false;

    Person(String name) {
        this.name = name;
        this.virusList = new ArrayList<Virus>();
    }

    @Override
    public String toString() {
        return this.name;
    }

    List<Virus> transmit(double random) {
        List<Virus> tempList = new ArrayList<Virus>();
        for (Virus v: this.virusList) {
            Virus nextVirus = v.spread(random);
            tempList.add(nextVirus);
        }
        return tempList;
    }

    Person infectWith(List<Virus> listOfViruses, double random) {
        Person newPerson = new Person(this.name);
        newPerson.addViruses(this.virusList);
        newPerson.addViruses(listOfViruses);
        return newPerson;
    }

    boolean test(String name) {
        for (Virus v: this.virusList) {
            if (v.getName() == name) {
                return true;
            }
        }
        return false;
    }
    
    List<Virus> getVirusList() {
        return this.virusList;
    }

    void addViruses(List<Virus> list) {
        for (Virus v: list) {
            String virusName =  v.getName();
            if (this.test(virusName) == false) { 
                this.virusList.add(v);
            }
        }
    }

    String getName() {
        return this.name;
    }
    /*
    boolean onSHN(double currentTime) {

    }
    */
}
