import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Roster extends KeyableMap<Student> implements Keyable {

    Roster(String name) {
        super(name);
    }

    @Override
    public Roster put(Student item) {
        return (Roster) super.put(item);
    }

    public String getGrade(String id, String mod, String assessment) {
        String noRecordMessage = String.format("No such record: %s %s %s", id, mod, assessment);
        return this.get(id).flatMap(x -> x.get(mod)).flatMap(x -> x.get(assessment)).map(Assessment::getGrade).orElse(noRecordMessage);
    }
}

