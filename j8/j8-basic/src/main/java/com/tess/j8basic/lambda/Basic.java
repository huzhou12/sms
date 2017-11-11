package com.tess.j8basic.lambda;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.tess.j8basic.entity.Person;

@Component
public class Basic {
	
	public List<String> getListFiles(){
		File directory = new File("./src/main/java/com/tess/j8basic/lambda");
	
		String[] names = directory.list((dir,name)->name.endsWith(".java"));
		
		return Arrays.asList(names);
	}
		
	public List<Double> getListRandomNumber(){
		return Stream.generate(Math::random).limit(10).collect(Collectors.toList());
	}
	
	public List<String> getSortedStrings(){
		List<String> strings = Arrays.asList("this", "is", "a", "list", "of", "strings");
		
		//List<String> sorted = strings.stream().sorted((s1,s2)->s1.compareTo(s2)).collect(Collectors.toList());
		List<String> sorted = strings.stream().sorted(String::compareTo).collect(Collectors.toList());
		
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
}
