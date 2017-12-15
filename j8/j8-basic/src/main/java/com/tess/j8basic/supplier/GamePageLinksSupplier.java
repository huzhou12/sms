package com.tess.j8basic.supplier;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class GamePageLinksSupplier implements Supplier<List<String>> {
	Logger logger = Logger.getLogger(this.getClass().getName());
	private static final String BASE = "http://http://gd2.mlb.com/components/game/mlb/";
	private LocalDate startDate;
	private int days;
	
	public GamePageLinksSupplier() {
		this(LocalDate.now().minusWeeks(1), 3);
	}
	
	public GamePageLinksSupplier(LocalDate startDate, int days) {
		this.startDate = startDate;
		this.days = days;
	}
	
	public List<String> getGamePageLinks(LocalDate localDate){
		String formattedDate = String.format("year_%4s/month_02d/day_%02d%n",	localDate.getYear(), 
				localDate.getMonthValue(), localDate.getDayOfMonth());
		try {
			Document doc = Jsoup.connect(BASE + formattedDate).get();
			Elements links = doc.select("a");
			return links.stream()
					.filter(link -> link.attr("href").contains("gid"))
					.map(link -> link.attr("href"))
					.collect(Collectors.toList());
			
		}catch (IOException e) {
			logger.info(e.getMessage());
			return new ArrayList<>();
		}
	}
	
	@Override
	public List<String>	get(){
		return Stream.iterate(startDate, d -> d.plusDays(1))
				.limit(days)
				.map(this::getGamePageLinks)
				.flatMap(list -> list.isEmpty() ? Stream.empty() : list.stream())
				.collect(Collectors.toList());
	}
}
