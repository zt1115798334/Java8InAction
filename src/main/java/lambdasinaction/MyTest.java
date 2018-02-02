package lambdasinaction;

import com.sun.deploy.util.ArrayUtil;
import lambdasinaction.entity.Hosting;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

import java.time.*;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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

    @Test
    public void test3() {
        List<Hosting> list = new ArrayList<>();
        list.add(new Hosting(1, "liquidweb.com", new Date()));
        list.add(new Hosting(2, "linode.com", new Date()));
        list.add(new Hosting(3, "digitalocean.com", new Date()));

        Map<String, Integer> map1 = list.stream()
                .collect(Collectors.toMap(Hosting::getName, Hosting::getId));
        System.out.println("map1 = " + map1);
        Map<String, Integer> map2 = list.stream()
                .collect(Collectors.toMap(x -> x.getName(), x -> x.getId()));
        System.out.println("map2 = " + map2);
    }

    @Test
    public void test4() {

        String[][] data = new String[][]{{"a", "b"}, {"c", "d"}, {"e", "f"}};
        Stream<String[]> temp = Arrays.stream(data);
        Stream<String> stringStream = temp.flatMap(x -> Arrays.stream(x));
        Stream<String> stream = stringStream.filter(x -> "a".equals(x.toString()));
        stream.forEach(System.out::println);
    }

    @Test
    public void test5() {
        int[] intArray = {1, 2, 3, 4, 5};
        Stream<int[]> streamArray = Stream.of(intArray);
        IntStream intStream = streamArray.flatMapToInt(Arrays::stream);
        intStream.forEach(System.out::println);
    }

    @Test
    public void test6() {
        Map<Integer, String> map = new HashMap<>();
        map.put(10, "apple");
        map.put(20, "orange");
        map.put(30, "banana");
        map.put(40, "watermelon");
        map.put(50, "dragonfruit");
        System.out.println("\n1. Export Map Key to List...");
        List<Integer> result = map.entrySet().stream()
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        result.forEach(System.out::println);
        List<String> result2 = map.entrySet().stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
        result2.forEach(System.out::println);
    }

    @Test
    public void test7() {
        Optional<String> gender = Optional.of("MALE");
        String answer1 = "Yes";
        String answer2 = null;

        System.out.println("Non-Empty Optional:" + gender);
        System.out.println("Non-Empty Optional: Gender value : " + gender.get());
        System.out.println("Empty Optional: " + Optional.empty());

        System.out.println("ofNullable on Non-Empty Optional: " + Optional.ofNullable(answer1));
        System.out.println("ofNullable on Empty Optional: " + Optional.ofNullable(answer2));

        // java.lang.NullPointerException
        System.out.println("ofNullable on Non-Empty Optional: " + Optional.of(answer2));
    }

    @Test
    public void test8() {
        Optional<String> nonEmptyGender = Optional.of("male");
        Optional<String> emptyGender = Optional.empty();

        System.out.println("Non-Empty Optional:: " + nonEmptyGender.map(String::toUpperCase));
        System.out.println("Empty Optional    :: " + emptyGender.map(String::toUpperCase));

        Optional<Optional<String>> nonEmptyOptionalGender = Optional.of(Optional.of("male"));
        System.out.println("Optional value   :: " + nonEmptyOptionalGender);
        System.out.println("Optional.map     :: " + nonEmptyOptionalGender.map(gender -> gender.map(String::toUpperCase)));
        System.out.println("Optional.flatMap :: " + nonEmptyOptionalGender.flatMap(gender -> gender.map(String::toUpperCase)));

    }

    @Test
    public void test9() {
        Optional<String> gender = Optional.of("MALE");
        Optional<String> emptyGender = Optional.empty();

        //Filter on Optional
        System.out.println(gender.filter(g -> g.equals("male"))); //Optional.empty
        System.out.println(gender.filter(g -> g.equalsIgnoreCase("MALE"))); //Optional[MALE]
        System.out.println(emptyGender.filter(g -> g.equalsIgnoreCase("MALE"))); //Optional.empty

    }

    @Test
    public void test10() {
        for (int i = 0; i < 10; i++) {
            System.out.println(getRandomNumberInRange1(5, 10));
            System.out.println(getRandomNumberInRange2(5, 10));
            System.out.println(getRandomNumberInRange3(5, 10));
            System.out.println(getRandomNumberInRange4(5, 10));
        }
    }

    public static int getRandomNumberInRange1(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    public static int getRandomNumberInRange2(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        return (int) (Math.random() * (max - min) + 1) + min;
    }

    public static int getRandomNumberInRange3(int min, int max){
       Random random = new Random();
       return random.ints(min, (max+1)).findFirst().getAsInt();
    }

    public static int getRandomNumberInRange4(int min, int max){
        Random random = new Random();
        return random.ints(min, (max+1)).limit(1).findFirst().getAsInt();
    }

    @Test
    public void test11(){
        StringJoiner sj = new StringJoiner(",");
        sj.add("aaa");
        sj.add("bbb");
        sj.add("ccc");
        sj.add("ddd");
        System.out.println(sj.toString());
    }

    @Test
    public void test12(){
        StringJoiner sj = new StringJoiner("/","prefix-","-suffix");
        sj.add("2018");
        sj.add("02");
        sj.add("01");
        System.out.println(sj.toString());

        String s = String.join("-","2018","02","20");
        System.out.println(s);

        List<String> list = Arrays.asList("aaa","bbb","ccc","ddd","eee");
        String l = String.join("-",list);
        System.out.println(l);

        String result = list.stream().collect(Collectors.joining("---"));
        System.out.println("result = " + result);
    }

    @Test
    public void test13(){
        String[] str1 = new String[]{"1","2","3","4"};
        String[] str2 = new String[]{"1s","2s","3s","4s"};
        String[] str = ArrayUtils.addAll(str1,str2);
        int[] num = {1,23,3,4,5,6,7,8,9,88,23};
        List<Integer> list = Arrays.stream(num).boxed().collect(Collectors.toList());
        System.out.println(list);
    }

    @Test
    public void test14(){
        Map<String, String> sortedMap = new LinkedHashMap<>();

        List<String> zoneList = new ArrayList<>(ZoneId.getAvailableZoneIds());

        //Get all ZoneIds
        Map<String, String> allZoneIds = getAllZoneIds(zoneList);

        //sort map by key
        /*allZoneIds.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEachOrdered(e -> sortedMap.put(e.getKey(), e.getValue()));*/

        //sort by value, descending order
        allZoneIds.entrySet().stream()
                .sorted(Map.Entry.<String, String>comparingByValue().reversed())
                .forEachOrdered(e -> sortedMap.put(e.getKey(), e.getValue()));

        // print map
        sortedMap.forEach((k, v) ->
        {
            String out = String.format("%35s (UTC%s) %n", k, v);
            System.out.printf(out);
        });

        System.out.println("\nTotal Zone IDs " + sortedMap.size());
    }
    private static Map<String, String> getAllZoneIds(List<String> zoneList) {

        Map<String, String> result = new HashMap<>();

        LocalDateTime dt = LocalDateTime.now();

        for (String zoneId : zoneList) {

            ZoneId zone = ZoneId.of(zoneId);
            ZonedDateTime zdt = dt.atZone(zone);
            ZoneOffset zos = zdt.getOffset();

            //replace Z to +00:00
            String offset = zos.getId().replaceAll("Z", "+00:00");

            result.put(zone.toString(), offset);

        }

        return result;

    }

    @Test
    public void test15(){
        //Asia/Kuala_Lumpur +8
        ZoneId defaultZoneId = ZoneId.systemDefault();
        System.out.println("System Default TimeZone : " + defaultZoneId);

        //toString() append +8 automatically.
        Date date = new Date();
        System.out.println("date : " + date);

        //1. Convert Date -> Instant
        Instant instant = date.toInstant();
        System.out.println("instant : " + instant); //Zone : UTC+0

        //2. Instant + system default time zone + toLocalDate() = LocalDate
        LocalDate localDate = instant.atZone(defaultZoneId).toLocalDate();
        System.out.println("localDate : " + localDate);

        //3. Instant + system default time zone + toLocalDateTime() = LocalDateTime
        LocalDateTime localDateTime = instant.atZone(defaultZoneId).toLocalDateTime();
        System.out.println("localDateTime : " + localDateTime);

        //4. Instant + system default time zone = ZonedDateTime
        ZonedDateTime zonedDateTime = instant.atZone(defaultZoneId);
        System.out.println("zonedDateTime : " + zonedDateTime);

    }
}


