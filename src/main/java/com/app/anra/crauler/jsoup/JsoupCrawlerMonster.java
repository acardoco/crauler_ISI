package com.app.anra.crauler.jsoup;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupCrawlerMonster {
	

	/** The monster_url. */
	public static String monster_url = "http://www.monster.es/trabajo/buscar/?cy=ES";

	public static void main(String[] args) {
		

		try {
			Document doc = Jsoup.connect(monster_url).timeout(5*1000).get();
			Elements results = doc.select("article.js_result_row");
			for(Element e: results){
				System.out.print(e.select(".jobTitle").text());
			}
			
			System.out.print(results.text());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
