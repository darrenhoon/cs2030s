
class DormsWithShn extends Dorms {

    DormsWithShn(boolean verbose) {
        super(verbose);
    }
    
    //////To implement DormsWithShn, you will need the following methods//////

    /**
     * Handles the sick person.
     * @param person The latest state of the sick Person
     * @param time   The time this is handled
     */
    @Override
    void handleSickPerson(Person person, double time) {
        List<? extends Contact> contactList = super.queryContacts(person.getName(), time);

        if (!person.test(SimulationParameters.TARGET_VIRUS)) {
            logNegativeTest(person, time);
        } else {
            logPositiveTest(person, time);
        }
        super.updatePerson(person);

        for (Person p: contactList) {

        }
    }


}
