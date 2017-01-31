package com.zenvia;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SpringBootBenchmarkApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	private Integer numberOfRequests = 500;

	private Integer maxConcurrentThreads = 4000;

	private String hostUrl = "http://192.168.1.76:8080";

	@Before
	public void setUp() throws Exception {
		restTemplate.delete("/boot/truncate");
	}

	@Test
	public void testInserts() {
		ExecutorService executor = Executors.newFixedThreadPool(maxConcurrentThreads);

		List<Callable<Integer>> tasks = new LinkedList<>();

		for (int i=0; i<maxConcurrentThreads; i++) {
			Callable<Integer> task = () -> {
				for (int j=0; j<numberOfRequests; j++) {
					String name = "PersonName-" + j;
					Integer age = 20 + j;
					Integer weight = 50 + j;
					String address = "City-" + j;
					Person person = new Person(name, age, weight, address);
					HttpHeaders headers = new HttpHeaders();
					headers.setContentType(MediaType.APPLICATION_JSON);
					HttpEntity<Person> httpEntity = new HttpEntity<>(person, headers);
					ResponseEntity<Person> rateResponse = restTemplate.exchange(hostUrl + "/boot/insert", HttpMethod.POST,
							httpEntity, Person.class);
				}

				return 0;
			};

			tasks.add(task);
		}

		StopWatch watch = new StopWatch("Insert");
		watch.start();

		try {
			executor.invokeAll(tasks);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		executor.shutdown();
		while(!executor.isTerminated()){}

		watch.stop();
		showMetrics("Inserts", watch);

		List<Person> people = list();
		assertNotNull(people);
		assertEquals(numberOfRequests.intValue(), people.size());
	}

	private List<Person> list() {
		ParameterizedTypeReference<List<Person>> responseType = new ParameterizedTypeReference<List<Person>>() {};
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<?> httpEntity = new HttpEntity<>(headers);
		ResponseEntity<List<Person>> rateResponse = restTemplate.exchange(hostUrl + "/boot/list", HttpMethod.GET, httpEntity,
				responseType);
		return rateResponse.getBody();
	}

	private void showMetrics(String list, StopWatch watch) {
		System.out.println("---------------------------------------");
		System.out.println("Operation: " + list);
		System.out.println(watch.prettyPrint());
		System.out.println("---------------------------------------");
	}
}
