package com.zenvia;

import com.zenvia.dao.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by leonardo on 25/01/17.
 */
@RestController
@RequestMapping("/boot")
public class PersonController {

	@Autowired
	private PersonRepository personRepository;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public List<Person> list() {
		return personRepository.findAll();
	}

	@RequestMapping(value = "/find", method = RequestMethod.GET)
	public Person find(@RequestParam(value="name", defaultValue="Leo") String name) {
		return personRepository.findByName(name);
	}

	@RequestMapping(value = "/remove", method = RequestMethod.DELETE)
	public void remove(@RequestParam(value="name", defaultValue="Leo") String name) {
		personRepository.deleteByName(name);
	}

	@RequestMapping(value = "/truncate", method = RequestMethod.DELETE)
	public void truncate() {
		personRepository.deleteAll();
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public Person insert(@RequestBody Person person) {
		return personRepository.insert(person);
	}
}
