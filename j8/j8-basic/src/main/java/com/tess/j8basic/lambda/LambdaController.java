package com.tess.j8basic.lambda;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tess.j8basic.entity.Person;

@RestController
public class LambdaController {
	
	Logger logger = Logger.getLogger(LambdaController.class.getName());
	
	@Autowired
	private Basic basic;
	
	@RequestMapping("/basic/listfiles")
	public List<String> getListString() {
		//return "Hello Lambda";
		return basic.getListFiles();
	}
	
	@RequestMapping("/basic/numOfWords")
	public Map<Integer, Long> getNumOfWords() {
		//return "Hello Lambda";
		return basic.getNumOfWords();
	}
	
	@RequestMapping("/basic/groupingBy")
	public Map<Integer, List<String>> groupingByLenth() {
		//return "Hello Lambda";
		return basic.groupingByLength();
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
	
	@RequestMapping(value="/basic/people", method=RequestMethod.GET)
	public List<Person> getPeople(){
		return basic.getPeople();
	}
	
	@RequestMapping(value="/basic/removedInt", method=RequestMethod.GET)
	public List<String> getRemovedInt(){
		List<String> nums = basic.getRemovedList();
		return nums;
	}
	
	@RequestMapping(value="/basic/first", method=RequestMethod.GET)
	public String getFirst(){
		String first = basic.getFirst();
		return first;
	}
	
	@RequestMapping(value="/stream/dates", method=RequestMethod.GET)
	public List<LocalDate> getDates(){
		return basic.getDates();
	}
	
	@RequestMapping("stream/random")
	public List<Double> generateRandom(){
		return basic.generateRandom();
	}
	
	@RequestMapping("stream/range")
	public List<Integer> intStreamRange(){
		return basic.range();
	}
	
	@RequestMapping("stream/count")
	public long getCount() {
		return basic.getCount();
	}
	
	@RequestMapping("stream/countMap")
	public Map<Boolean, Long> countMap(){
		return basic.numberLength();
	}
	
	@RequestMapping("stream/sum")
	public int getSum() {
		return basic.getSum();
	}
	
	@RequestMapping("stream/average")
	public OptionalDouble getAverage() {
		return basic.getAverage();
	}
	
	@RequestMapping("stream/maxormin/{id}")
	public OptionalInt getMaxOrMin(@PathVariable("id") String id) {
		return basic.getMaxOrMin(id);
	}

	@RequestMapping("stream/concatString")
	public String concatString() {
		return basic.concatString();
	}
	
	@RequestMapping("stream/isPalindrome/{s}")
	public boolean isPalindrome(@PathVariable("s") String s) {
		return basic.isPalindrome(s);
	}
	
	@RequestMapping("stream/stats")
	public DoubleSummaryStatistics getStats() {
			
		return basic.summaryStat();
	}
	
	@RequestMapping("stream/isPrime/{num}")
	public boolean isPrime(@PathVariable("num") int num) {
			
		return basic.isPrime(num);
	}
	
	@RequestMapping("stream/concatStream")
	public List<String> getConcatStream(){
		return basic.concatStream();
	}
	
	@RequestMapping("stream/num")
	public int getNum(){
		return basic.getNum();
	}
	
	@RequestMapping("stream/opt")
	public AtomicInteger getOpt() {
		
		Optional<AtomicInteger> opt = basic.getOptional();
		
		//return opt.isPresent()? opt.get(): new AtomicInteger(0);
		return opt.orElse(new AtomicInteger());
	}
	
	@RequestMapping("date/dt")
	public LocalDateTime getDateTime() {
		return basic.getDateTime();
	}
	
	@RequestMapping("date/adj")
	public Temporal payDayAdjuster() {
		Temporal date = LocalDate.of(2017,12, 2);
		Temporal date2 = LocalDate.of(2017,12, 20);
		logger.info("Date 2: " + basic.payDayAdjuster(date2).toString());
		return basic.payDayAdjuster(date);
	}
	
	@RequestMapping("concurrent/isParallel")
	public boolean isParallel() {
		return basic.isParallel();
	}
	
	@RequestMapping("concurrent/duration")
	public Duration getDoubleIt() {
		int total = 0;
		
		
		//set pool size - default size = the number of processors on the machine
		//but both approaches below do not work
		
		//System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "8");
		
		//ForkJoinPool pool = new ForkJoinPool(4);
		//logger.info("Pool 2 size: " + pool.getPoolSize());
		
		Instant before = Instant.now();
		total = IntStream.of(3,1,4,1,5,9,7,8)
				.parallel()
				.map(Basic::doubleIt)
				.sum();

/*		ForkJoinTask<?> task = pool.submit(
				() -> IntStream.of(3,1,4,1,5,9,7,8)
				.parallel()
				.map(Basic::doubleIt)
				.sum());

		try {
			 total = (int) task.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}finally {
			pool.shutdown();
		}
*/		
		Instant after = Instant.now();
		Duration duration = Duration.between(before, after);
		logger.info("Total of doubles: " + total );
		logger.info("Time = " + duration.toMillis());
		logger.info("Total processors: " + Runtime.getRuntime().availableProcessors());
		logger.info("Pool size: " + ForkJoinPool.commonPool().getPoolSize());

		return duration;
	}
	
	@RequestMapping("concurrent/future")
	public String future() {
		return basic.future();
	}
}
