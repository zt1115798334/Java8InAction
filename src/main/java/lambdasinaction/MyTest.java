package lambdasinaction;

import org.junit.Test;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MyTest {
    public static void main(String[] args) {
        //3 apple, 2 banana, others 1
        List<String> items =
                Arrays.asList("apple", "apple", "banana",
                        "apple", "orange", "banana", "papaya");

        Map<String, Long> result =
                items.stream().collect(
                        Collectors.groupingBy(
                                Function.identity(), Collectors.counting()
                        )
                );

        Map<String, Long> finalMap = new LinkedHashMap<>();

        //Sort a map and add to finalMap
        result.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .forEachOrdered(e -> finalMap.put(e.getKey(), e.getValue()));
        System.out.println(finalMap);

    }

    @Test
    public void test1() {
        String[] array = {"a", "b", "c", "d"};
        Supplier<Stream<String>> streamSupplier = () -> Stream.of(array);
        streamSupplier.get().forEach(c -> System.out.println(c));
        long count = streamSupplier.get().filter(x -> x.equals("c")).count();
        System.out.println("count = " + count);
    }

    @Test
    public void test2() {
        Map<String, Integer> unsortMap = new HashMap<>();
        unsortMap.put("z", 10);
        unsortMap.put("b", 5);
        unsortMap.put("a", 6);
        unsortMap.put("c", 20);
        unsortMap.put("d", 1);
        unsortMap.put("e", 7);
        unsortMap.put("y", 8);
        unsortMap.put("n", 99);
        unsortMap.put("j", 50);
        unsortMap.put("m", 2);
        unsortMap.put("f", 9);
        System.out.println("Original...");
        System.out.println(unsortMap);

        System.out.println("Sort By Key...");
        Map<String, Integer> resultKey = compareByKey(unsortMap);
        System.out.println(resultKey);

        System.out.println("Sort By Value...");
        Map<String, Integer> resultValue = compareByValue(unsortMap);
        System.out.println(resultValue);
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> compareByValue(Map<K, V> map) {
        Map<K, V> result = new LinkedHashMap<>();
        map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .forEachOrdered(x -> result.put(x.getKey(), x.getValue()));
        return result;
    }

    public static <K extends Comparable<? super K>, V> Map<K, V> compareByKey(Map<K, V> map) {
        Map<K, V> result = new LinkedHashMap<>();
        Stream<Map.Entry<K, V>> mapInStream = map.entrySet().stream();
        mapInStream.sorted(Map.Entry.comparingByKey())
                .forEachOrdered(x -> result.put(x.getKey(), x.getValue()));
        return result;
    }
}
