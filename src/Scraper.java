import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Scraper 
{
	private WebClient startClient;
	private Set<String> pokedexPages;
	private String startUrl;	
	
	public Scraper()
	{
		this.startClient = new WebClient();
		this.pokedexPages = new HashSet<String>();	
		this.startUrl = "https://bulbapedia.bulbagarden.net";
	}
	
	public void scrape() throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException
	{
		startClient.getOptions().setCssEnabled(false);
		startClient.getOptions().setJavaScriptEnabled(false);
		
		extractPokemonPages();
		extractPokemonInfo();	
	}
	
	private void extractPokemonPages() throws FailingHttpStatusCodeException, MalformedURLException, IOException
	{
		String nationalDexPath ="/wiki/List_of_Pokémon_by_National_Pokédex_number";
								
		HtmlPage startPage = startClient.getPage(startUrl + nationalDexPath);	
		
		List<HtmlAnchor> anchors = startPage.getByXPath("//a");	
		
		String href = "";
		
		for(int i = 0; i < anchors.size(); i++)
		{			
			href = anchors.get(i).getHrefAttribute();
			
			if(href.endsWith("_(Pok%C3%A9mon)"))
				pokedexPages.add(href);
		}	
		
		System.out.println(pokedexPages.size() + " RETRIEVED");
	}
	
	private void extractPokemonInfo() throws FailingHttpStatusCodeException, MalformedURLException, IOException
	{
		HtmlPage dexPage;
		String[] dexArray = (String[]) pokedexPages.toArray();
		
		for(int i = 0; i < dexArray.length; i ++)
		{
			dexPage = startClient.getPage(startUrl + dexArray[i]);
			
		}
	}
}
