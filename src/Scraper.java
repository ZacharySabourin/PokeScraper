import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Scraper 
{
	private WebClient startClient;
	private String startUrl;	
	private final String[] pokedexPages = {
			"/wiki/List_of_Pok%C3%A9mon_by_Kanto_Pok%C3%A9dex_number",
			"/wiki/List_of_Pok%C3%A9mon_by_New_Pok%C3%A9dex_number",
			"/wiki/List_of_Pok%C3%A9mon_by_Hoenn_Pok%C3%A9dex_number_(Generation_III)",
			"/wiki/List_of_Pok%C3%A9mon_by_Sinnoh_Pok%C3%A9dex_number#Platinum_expansion",
			"/wiki/List_of_Pok%C3%A9mon_by_Unova_Pok%C3%A9dex_number_(Black_2_and_White_2)",
			"/wiki/List_of_Pok%C3%A9mon_by_Kalos_Pok%C3%A9dex_number",
			"/wiki/List_of_Pok%C3%A9mon_by_Kalos_Pok%C3%A9dex_number#Central_Kalos_Pok.C3.A9dex",
			"/wiki/List_of_Pok%C3%A9mon_by_Kalos_Pok%C3%A9dex_number#Coastal_Kalos_Pok.C3.A9dex",
			"/wiki/List_of_Pok%C3%A9mon_by_Kalos_Pok%C3%A9dex_number#Mountain_Kalos_Pok.C3.A9dex",
			"/wiki/List_of_Pok%C3%A9mon_by_Alola_Pok%C3%A9dex_number_(Ultra_Sun_and_Ultra_Moon)",
			"/wiki/List_of_Pok%C3%A9mon_by_Galar_Pok%C3%A9dex_number",
			"/wiki/List_of_Pok%C3%A9mon_by_National_Pok%C3%A9dex_number"
	};
	
	public Scraper()
	{
		this.startClient = new WebClient();
		this.startUrl = "https://bulbapedia.bulbagarden.net";
	}
	
	public void scrape()
	{
		startClient.getOptions().setCssEnabled(false);
		startClient.getOptions().setJavaScriptEnabled(false);
		
				
	}
	
	private void getPokedexPages() throws MalformedURLException, IOException
	{
		ScraperThread[] threads = new ScraperThread[pokedexPages.length];
		
		for(int i = 0; i < threads.length; i++)
		{
			threads[i] = new ScraperThread(pokedexPages[i]);
			threads[i].start();
		}
		
//		HtmlPage startPage = startClient.getPage(startUrl);	
//			
//		List<HtmlAnchor> anchors = startPage.getByXPath(".//a[@href]");
//			
//		for(HtmlAnchor anchor : anchors)
//		{			
//			String href = anchor.getHrefAttribute();
//			
//			if(href.startsWith("/wiki/List_of_"))
//			{
//				System.out.println(href);
//			}
//		}			
	}
	
	private class ScraperThread extends Thread
	{
		private WebClient subClient;
		private String pokedexUrl;
		
		public ScraperThread(String path)
		{
			this.subClient = new WebClient();
			this.pokedexUrl = startUrl + path;
		}
		
		public void Run()
		{
			subClient.getOptions().setCssEnabled(false);
			subClient.getOptions().setJavaScriptEnabled(false);
			 
			try
			{
				HtmlPage pokedexPage = subClient.getPage(pokedexUrl);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}
}
