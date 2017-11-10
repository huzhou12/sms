package com.tess.j8basic.lambda;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LambdaController {
	
	@Autowired
	private Basic basic;
	
	@RequestMapping("/basic/listfiles")
	public List<String> getListString() {
		//return "Hello Lambda";
		return basic.getListFiles();
	}
	
	@RequestMapping("/basic/RandomNums")
	public List<Double> getListDouble() {
		//return "Hello Lambda";
		return basic.getListRandomNumber();
	}
	
	@RequestMapping("/basic/sortedstrings")
	public List<String> getSortedStrings() {
		return basic.getSortedStrings();
	}
	
	@RequestMapping("/basic/stringlength")
	public List<Integer> getStringLength() {
		return basic.getStringLength();
	}
}