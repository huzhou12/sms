package com.tess.j8basic.lambda;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

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
}
