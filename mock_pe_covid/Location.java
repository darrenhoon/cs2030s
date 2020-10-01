import java.util.List;
import java.util.ArrayList;

class Location {
    private final String name;
    private final List<Person> occupants;

    Location(String name) {
        this.name = name;
        this.occupants = new ArrayList<Person>();
    }

    List<Person> getOccupants() {
        return this.occupants;
    }

    Location accept(Person person) {
        Location l = new Location(this.name);
        for (Person p: this.occupants) {
            l.addPerson(p);
        }
        l.addPerson(person);
        return l;
    }

    Location remove(String name) {
        Location l = new Location(this.name);
        for (Person p: this.occupants) {
            l.addPerson(p);
        }

        l.removePerson(name);
        return l;
    }

    void addPerson(Person p) {
        this.occupants.add(p);
    }

    void removePerson(String pname) {
        for (int i = 0; i < this.occupants.size(); i++) {
            Person p = this.occupants.get(i);
            String name = p.getName();
            if (name == pname) {
                this.occupants.remove(i);
                break;
            }
        }
    }
}
