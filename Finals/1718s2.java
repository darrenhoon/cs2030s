import java.util.stream.Stream;

class sem21718 {
    void foo() {
        int sum = 0;
        Stream.of(1,2,3,4,5).parallel()
            .forEach(i -> {
                sum = sum + i;
            });
        System.out.println(sum);
    }
}
