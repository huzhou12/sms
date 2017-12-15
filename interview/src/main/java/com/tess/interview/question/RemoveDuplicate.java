package com.tess.interview.question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class RemoveDuplicate {

	private static final Logger logger = Logger.getLogger(RemoveDuplicate.class.getName());
	
	public List<String> removeDuplicates(){
		List<String> noDupes = new ArrayList<>();
		List<String> dupes = Arrays.asList("Android", "ios", "ios", "Android", "test", "test");
		logger.info("Dupes: " + dupes.toString());
		
		// no order
		//Set<String> listToSet = new HashSet<>(dupes);
		
		// ordered
		//Set<String> listToSet = new LinkedHashSet<>(dupes);
		
		//logger.info("set without dupes: " + listToSet.toString());
		//noDupes = new ArrayList<>(listToSet);
		
		List<String> sorted = dupes.stream().sorted().collect(Collectors.toList());
		String prev = "";
		for (String s : sorted) {
			if (!s.equals(prev)) {
				noDupes.add(s);
			}
			prev = s;
		}

		logger.info("no dupes: "+ noDupes.toString());
		return noDupes;
	}
	
}
