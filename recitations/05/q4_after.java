import java.util.*;


class Main {
    static void processQuestion(QA question) {
        try {
            question.getAnswer();
        } catch (InvalidQuestionException ex) {
            System.err.println(ex);
        }
    }
    public static void main(String[] args) {
        QA mcq = new MCQ("What is the answer to this MCQ?");
        QA tfq = new TFQ("What is the answer to this TFQ?");
        processQuestion(mcq);
        processQuestion(tfq);
    }
}

abstract class QA {
    String question;
    char answer;

    public QA(String question) {
        this.question = question;
    }

    abstract void getAnswer();
    char askQuestion() {
        System.out.print(question + " ");
        return new Scanner(System.in).next().charAt(0);
    }
}

class MCQ extends QA {
    public MCQ(String question) {
        super(question);
    }
    void getAnswer() {
        answer = askQuestion();
        if (answer < 'A' || answer > 'E') {
            throw new InvalidMCQException("Invalid MCQ answer");
        }
    }
}

class TFQ extends QA {
    public TFQ(String question) {
        super(question);
    }
    void getAnswer() {
        answer = askQuestion();
        if (answer != 'T' && answer != 'F') {
            throw new InvalidTFQException("Invalid TFQ answer");
        }
    }
}

class InvalidQuestionException extends IllegalArgumentException {
    public InvalidQuestionException(String mesg) {
        super(mesg);
    }
}
class InvalidMCQException extends InvalidQuestionException {
    public InvalidMCQException(String mesg) {
        super(mesg);
    }
}
class InvalidTFQException extends InvalidQuestionException {
    public InvalidTFQException(String mesg) {
        super(mesg);
    }
}


