import java.util.List;
import java.util.ArrayList;

class MaskedPerson extends Person {
    
    MaskedPerson(String name) {
        super(name);
    }

    List<Virus> transmit(double random) {
        List<Virus> tempList = new ArrayList<Virus>();

        if (random <= SimulationParameters.MASK_EFFECTIVENESS) {
            return tempList;
        } else {
            return super.transmit(random);
        }
    }

    MaskedPerson infectWith(List<Virus> listOfViruses, double random) {
        if (random <= SimulationParameters.MASK_EFFECTIVENESS) {
            return this;
        } else {
            Person p = super.infectWith(listOfViruses, random);
            MaskedPerson mp = new MaskedPerson(p.getName());
            mp.addViruses(p.getVirusList());
            return mp;
        }
    }


}
