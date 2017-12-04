package com.tess.j8basic.lambda;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.tess.j8basic.entity.Person;

@Component
public class Basic {
	
//	private static final Logger logger = LoggerFactory.getLogger(Basic.class); 
	private static final Logger logger = Logger.getLogger(Basic.class.getName());
	public List<String> getListFiles(){
		File directory = new File("./src/main/java/com/tess/j8basic/lambda");
	
		String[] names = directory.list((dir,name)->name.endsWith(".java"));
		
		return Arrays.asList(names);
	}
	
	//get num of words of each length, using groupingBy, map sorting
	public Map<Integer, Long> getNumOfWords(){
		String fileName = "/usr/share/dict/words";
	//	logger.info("\n Number of words of each length ");
		logger.info(()->"\\n Number of words of each length");
		
		try (Stream<String> lines = Files.lines(Paths.get(fileName))) {
			
			Map<Integer, Long> map =  lines.filter(s -> s.length() > 20)
					 .collect(Collectors.groupingBy(String::length, Collectors.counting()));
			
		logger.info(()->"/n before sorting" + map.toString());	
			
			// approch 1 - using a new LinkedHashMap -- not recommended
			/*
			Map<Integer, Long> sortedMap = new LinkedHashMap<>();
			map.entrySet().stream()
					.sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
					.forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
			return sortedMap;
			*/
			// approch 2
			return	map.entrySet().stream()
			.sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, 
					(oldValue, newValue) -> oldValue, LinkedHashMap::new));
	
		}catch (IOException e) {
			e.printStackTrace();
		}
		return new HashMap<Integer, Long>();
	}
	
	public Map<Integer, List<String>> groupingByLength(){
		List<String> strings = Arrays.asList("this", "is", "a", "long", "list", "of", "strings", "to", "use", "as", "a", "demo");
		
		return strings.stream()
				.collect(Collectors.groupingBy(String::length));
	}
		
	public List<Double> getListRandomNumber(){
		return Stream.generate(Math::random).limit(10).collect(Collectors.toList());
	}
	
	public List<String> getSortedStrings(){
		List<String> strings = Arrays.asList("this", "is", "a", "list", "of", "strings");
		
		//List<String> sorted = strings.stream().sorted((s1,s2)->s1.compareTo(s2)).collect(Collectors.toList());
		
		//Sorted with String::compareTo
		//List<String> sorted = strings.stream().sorted(String::compareTo).collect(Collectors.toList());
		
		// sorted 
		List<String> sorted = strings.stream()
		//.sorted(Comparator.naturalOrder())						//natualOrder()
		//.sorted(Comparator.reverseOrder()) 					//reverseOrder()
		//.sorted(Comparator.comparing(String::toLowerCase))		//toLowerCase()
		.sorted(Comparator.comparingInt(String::length)			//sorted by length and natualOrder
				.thenComparing(Comparator.naturalOrder()))
		.collect(Collectors.toList());
		
		return sorted;
	}
	
	public List<Integer> getStringLength(){
		List<String> strings = Arrays.asList("this", "is", "a", "list", "of", "strings");
		
		//List<Integer> lengths = strings.stream().map(s->s.length).collect(Collectors.toList());
		List<Integer> lengths = strings.stream().map(String::length).collect(Collectors.toList());
		
		return lengths;
	}
	
	
	public List<Person> getPeople(){
		
		List<String> names = Arrays.asList("Grace Hopper", "Barbara Liskov", "Ada Lovelace", "Karen Jones");
		
		//List<Person> people = names.stream()
		//		.map(name-> new Person(name)).collect(Collectors.toList());
		List<Person> people = names.stream()
				.map(Person::new).collect(Collectors.toList());
		
		return people;
	}
	
	public List<String> getRemovedList(){
		/*
		 * List<String> nums = Arrays.asList("1","3");
		 * is not able to be used here with removeId().
		 * it throws Collection UnmodifiableRandomAccess exception.
		 * 
		 */
		
		List<String> nums = new ArrayList<>();
		nums.add("1");
		nums.add("3");
		nums.add("1");
		nums.add("4");
		nums.add("5");
		nums.add("9");
		
		String id = "1";

		Predicate<String> predicate = p -> p.equals(id);
		boolean removed = nums.removeIf(predicate);
/*		if (removed == true) {
			nums = Arrays.asList();
		}
*/		return nums;
	}
	
	public String getFirst() {

		List<String> names = Arrays.asList("Mal", "Wash", "Kaylee","Inara","Zoe", "Jayne", "Simon","River","Shepherd Book");
		
		Optional<String> first = names.stream()
				.filter(name -> name.startsWith("C"))
				.findFirst();
		String name =
			//	first.orElse("None");
			//	first.orElse(String.format("No result found in %s", names.stream().collect(Collectors.joining(", "))));
				first.orElseGet(() -> String.format("No result found in %s !", names.stream().collect(Collectors.joining(", "))));
		
		return name;
	}
	
	public List<LocalDate> getDates(){
		List<LocalDate> days = Stream.iterate(LocalDate.now(), ld -> ld.plusDays(1L))
		.limit(10).collect(Collectors.toList());
		
		return days;
	}
	
	public List<Double> generateRandom() {
		return Stream.generate(Math::random).limit(10).collect(Collectors.toList());
	}
	
	public List<Integer> range(){
		return IntStream
				//.range(10, 20)
				.rangeClosed(10, 21)
				//.boxed()
				//.mapToObj(Integer::valueOf)
				//.collect(Collectors.toList());
				.collect(ArrayList<Integer>::new, ArrayList::add, ArrayList::addAll);
	}
	
	public long getCount() {
		String[] strings = "this is an array of strings".split(" ");
		long count = Arrays.stream(strings)
					.map(String::length)
					.count();
		return count;
	}
	
	public Map<Boolean, Long> numberLength(){
		List<String> names = Arrays.asList("Mal", "Wash", "Kaylee","Inara","Zoe", "Jayne", "Simon","River","Shepherd Book");
		
		Map<Boolean, Long> numberLengthMap = names.stream()
				.collect(Collectors.partitioningBy(s -> s.length() % 2 == 0, Collectors.counting()));
		return numberLengthMap;
				
	}
	
	public int getSum() {
	/*	String[] strings = "this is an array of strings".split(" ");
		int sum = Arrays.stream(strings)
				.mapToInt(String::length)
				.sum();
	*/	
		int sum = IntStream.rangeClosed(1, 10)
		//.reduce((x,y) -> x+y).orElse(0);
		//		.reduce((x,y)->x+2*y).orElse(0);
		//		.reduce(Integer.MIN_VALUE, Integer::sum);
				.peek(n -> System.out.printf("original: %d%n", n))
				.map(n -> n*2)
				.peek(n -> System.out.printf("doubled : %d%n", n))
				.filter(n -> n % 3 == 0)
				.peek(n -> System.out.printf("filtered : %d%n", n))
				.sum();
		return sum;
	}
	
	public OptionalDouble getAverage() {
		String[] strings = "this is an array of strings".split(" ");
		OptionalDouble ave = Arrays.stream(strings)
				.mapToInt(String::length)
				.average();
		return ave;		
	}
	
	public OptionalInt getMaxOrMin(String id) {
		String[] strings = "this is an array of strings".split(" ");
		OptionalInt maxOrMin;
		
		if(id.equals("x")) {
			maxOrMin = Arrays.stream(strings)
				.mapToInt(String::length)
				.max();
		}else {
			maxOrMin = Arrays.stream(strings)
					.mapToInt(String::length)
					.min();
		}
		return maxOrMin;		
	}
	
	public String concatString() {
		String s = Stream.of("this", "is", "a", "list")
				//.reduce("", String::concat);
				.collect(Collectors.joining(","));
		return s;
	}
	
	public boolean isPalindrome(String s) {
	/*	
		StringBuilder sb = new StringBuilder();
		for (char c : s.toCharArray()) {
			if (Character.isLetterOrDigit(c)) {
				sb.append(c);
			}
		}
		
		String forward = sb.toString().toLowerCase();
		String backward = sb.reverse().toString().toLowerCase();
	*/
		
	// String forward = 
		String forward = s.toLowerCase().codePoints()
				.filter(Character::isLetterOrDigit)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
				.toString();
		String backward = new StringBuilder(forward).reverse().toString();
		//String backward = reverseRecursively(forward);
		return forward.equals(backward);
	}
	
	// reverse a string 
	public String reverse(String str) {
		StringBuilder sb = new StringBuilder();
		
		char[] strChars = str.toCharArray();
		for (int i = str.length()-1; i>=0; i--) {
			sb.append(strChars[i]);
		}
		return sb.toString();
	}
	
	// Reverse a string using recursive function
	public String reverseRecursively(String str) {
		if (str == null || str.length() < 1) {
			return str;
		}
	
		return reverseRecursively(str.substring(1)) + str.charAt(0);
	}

	public DoubleSummaryStatistics summaryStat() {
		DoubleSummaryStatistics stats = DoubleStream.generate(Math::random)
				.limit(1_000_000)
				.summaryStatistics();
		return stats;
	}
	
	public boolean isPrime(int num) {
		
		int limit = (int) (Math.sqrt(num) + 1);
		return num == 2 || num > 1 && IntStream.range(2, limit)
				.noneMatch(divisor -> num % divisor == 0);
	}
	
	public List<String> concatStream() {
		Stream<String> str1 = Stream.of("a","b","c");
		Stream<String> str2 = Stream.of("X","Y","Z");
		Stream<String> str3 = Stream.of("al","be","ga");
		Stream<String> str4 = Stream.of("test");
		
		//return Stream.concat(Stream.concat(str1, str2),str3).collect(Collectors.toList());
		
		//return Stream.of(str1, str2, str3).reduce(Stream.empty(), Stream::concat).collect(Collectors.toList());
		
		return Stream.of(str1, str2, str3, str4).flatMap(Function.identity()).parallel().collect(Collectors.toList());
	}

	public int multByTwo(int n) {
		System.out.printf("Inside multByTwo with arg %d%n", n);
		return n*2;
	}
	public boolean DivByThree(int n) {
		System.out.printf("Inside divByThree with arg %d%n", n);
		return n%3 == 0;
	}	
	public int getNum() {
		OptionalInt first = IntStream.rangeClosed(100, 200)
				.map(this::multByTwo)  	//.map(n -> n*2)
				.filter(this::DivByThree) 	//.filter(num -> num % 3 == 0)
				.findFirst();
		
		return first.orElse(0);
	}
	
	public Optional<AtomicInteger> getOptional(){
		AtomicInteger counter = new AtomicInteger();
		Optional<AtomicInteger> opt = Optional.ofNullable(counter);
		
		logger.info("1 - " + opt.toString());
		
		counter.incrementAndGet();
		
		logger.info("2- after increment " + opt.toString());
		
		opt.get().incrementAndGet();
		
		logger.info("3 - after get and increment " + opt.toString());
		
		//opt = Optional.ofNullable(new AtomicInteger());
		
		return opt;
		
	}
	
	public LocalDateTime getDateTime() {
		logger.info("Instant.now() : " + Instant.now());
		logger.info("LocalDate.now() : " + LocalDate.now());
		logger.info("LocalTime.now() : " + LocalTime.now());
		logger.info("LocalDateTime.now() : " + LocalDateTime.now());
		logger.info("ZonedDateTime.now() : " + ZonedDateTime.now());
		
		LocalDate moonLandingDate = LocalDate.of(1969, Month.JULY, 20);
		LocalTime moonLandingTime = LocalTime.of(20, 18);
		
		LocalTime walkTime = LocalTime.of(20, 2, 56, 150_000_000);
		LocalDateTime walk = LocalDateTime.of(moonLandingDate, walkTime);
		
		Set<String> regionNames = ZoneId.getAvailableZoneIds();
		logger.info("There are " + regionNames.size() + " region names");
		
		logger.info("moonLandingDate + 3 days: " + moonLandingDate.plusDays(3));
		logger.info("moonLandingDate + 1 week: " + moonLandingDate.plusWeeks(1));
		logger.info("moonLandingDate + 1 month: " + moonLandingDate.plusMonths(1));

		logger.info("moonLandingDate + 1 month: " + moonLandingDate.withMonth(4));
		
		logger.info("moonLandingTime + 3 mins: " + moonLandingTime.plusMinutes(3));
		logger.info("moonLandingTime + 3 hours: " + moonLandingTime.plusHours(3));
		
		
		
		
		
		return walk;
	}
}
