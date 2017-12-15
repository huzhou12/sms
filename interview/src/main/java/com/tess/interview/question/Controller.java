package com.tess.interview.question;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("iv")
public class Controller {
	
	@Autowired
	RemoveDuplicate removeDupes;

	@RequestMapping("/rm")
	public List<String> removeDuplicate(){
		return removeDupes.removeDuplicates();
	}
}
