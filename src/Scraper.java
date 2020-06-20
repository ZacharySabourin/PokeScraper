import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Scraper 
{
	private WebClient startClient;
	private List<String> pokedexPages;
	private Thread[] threads;
	private String startUrl;	
	private String[] pokemonGenerations =
		{
			"Generation I",
			"Generation II",
			"Generation III",
			"Generation IV",
			"Generation V",
			"Generation VI",
			"Generation VII",
			"Generation VIII",
		};
	
	public Scraper()
	{
		this.startClient = new WebClient();
		this.pokedexPages = new ArrayList<String>();
		//this.threads = new Thread[pokemonGenerations.length];		
		this.startUrl = "https://bulbapedia.bulbagarden.net";
	}
	
	public void scrape() throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException
	{
		startClient.getOptions().setCssEnabled(false);
		startClient.getOptions().setJavaScriptEnabled(false);
		
		extractPokemonPages();
		
//		for(int i = 0; i < threads.length; i++)
//		{
//			threads[i] = new Thread(new ScraperThread(i), pokemonGenerations[i]);
//			threads[i].start();
//			System.out.println(pokemonGenerations[i] + " : Thread Started");
//		}	
//		
//		for(int i = 0; i < threads.length; i++)		
//			if(threads[i].isAlive())
//				threads[i].join();		
	}
	
	private void extractPokemonPages() throws FailingHttpStatusCodeException, MalformedURLException, IOException
	{
		String nationalDexPath ="/wiki/List_of_Pokémon_by_National_Pokédex_number";
								
		HtmlPage startPage = startClient.getPage(startUrl + nationalDexPath);	
		
		List<HtmlAnchor> anchors = startPage.getByXPath("//a");
		
		String previousHref = "";
		String nextHref = "";
		
		for(int i = 0; i < anchors.size(); i++)
		{			
			nextHref = anchors.get(i).getHrefAttribute();

			if(!nextHref.contentEquals(previousHref) && nextHref.contains("_(Pok%C3%A9mon)"))
				pokedexPages.add(nextHref);
		
			previousHref = nextHref;	
		}	
	}
	
//	private class ScraperThread implements Runnable
//	{
//		private WebClient client;
//		private String pokedexUrl;
//		private int tableNo;
//		
//		public ScraperThread(int tableNo)
//		{
//			this.client = new WebClient();
//			this.tableNo = tableNo;
//		}
//		
//		public void run()
//		{
//			client.getOptions().setCssEnabled(false);
//			client.getOptions().setJavaScriptEnabled(false);
//			 
//			try
//			{				
//				//extractPokemonPages();
//			}
//			catch(Exception ex)
//			{
//				ex.printStackTrace();
//			}
//		}
//				
//		private void extractPokemonInfo() throws FailingHttpStatusCodeException, MalformedURLException, IOException
//		{
//			HtmlPage pokedexPage = client.getPage(pokedexUrl);
//		}
//	}
}
