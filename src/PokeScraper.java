import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class PokeScraper {
	public static void main(String[] args) 
	{
		Scraper scraper = new Scraper();
		scraper.scrape();
	}
}
