import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Scraper 
{
	private WebClient client;	
	private String startUrl;
	private Deque<String> pokemonPaths;
	
	public Scraper()
	{
		this.client = new WebClient();
		this.startUrl = "https://www.pokemon.com/us/pokedex/";
		this.pokemonPaths = new ArrayDeque<String>(890);
	}
	
	public void scrape() throws FailingHttpStatusCodeException, MalformedURLException, IOException
	{		
		client.getOptions().setCssEnabled(false);
		client.getOptions().setJavaScriptEnabled(false);		
		
		extractEveryPokemonInfo();
		
		client.close();
	}
	
	private Deque<Pokemon> extractEveryPokemonInfo() throws FailingHttpStatusCodeException, MalformedURLException, IOException
	{
		getAllPokemonPaths();
		
		Deque<Pokemon> pokemonList = new ArrayDeque<Pokemon>(890);
		
		while(!pokemonPaths.isEmpty())
		{
			String pokemonName = pokemonPaths.poll();
			Pokemon pokemon = extractSinglePokemonInfo(pokemonName);
			pokemonList.offer(pokemon);
			System.out.println("ADDED : " + pokemon.getName());
		}	
		
		return pokemonList;
	}
	
	private void getAllPokemonPaths() throws FailingHttpStatusCodeException, MalformedURLException, IOException
	{
		PokemonPage startPage = new PokemonPage(startUrl);	
		String hrefXPath = "//a";
		
		List<String> hrefs = startPage.extractAnchorsAsText(hrefXPath);
		for(String href : hrefs)
			pokemonPaths.offer(href);		
	}
	
	private Pokemon extractSinglePokemonInfo(String path) throws FailingHttpStatusCodeException, MalformedURLException, IOException
	{
		PokemonPage pokemonPage = new PokemonPage(startUrl + path);
		
		String nameAndNoXPath = "//div[@class='pokedex-pokemon-pagination-title']";			
		String nameAndNo = pokemonPage.extractSingleAsText(nameAndNoXPath);
				
		String[] splitNameAndNo = nameAndNo.split(" #", 2);
		
		String pokemonName = splitNameAndNo[0];
		int pokemonDexNo = Integer.parseInt(splitNameAndNo[1]);
				
		String heightXPath = "//div[@class='column-7']/ul[1]/li[1]/span[@class='attribute-value']";	
		String pokemonHeight = pokemonPage.extractSingleAsText(heightXPath);
		
		String weightXPath = "//div[@class='column-7']/ul[1]/li[2]/span[@class='attribute-value']";
		String pokemonWeight = pokemonPage.extractSingleAsText(weightXPath);
		
		String categoryXPath = "//div[@class='column-7 push-7']/ul/li/span[@class='attribute-value']";
		String pokemonCategory = pokemonPage.extractSingleAsText(categoryXPath);
		
		String genderXPath = "//div[@class='column-7']/ul/li[3]/span[@class='attribute-value']";
		List<String> pokemonGenders = pokemonPage.scrapeGenderElements(genderXPath);
		
		String abilitiesXPath = "//ul[@class='attribute-list']";
		List<String> pokemonAbilities = pokemonPage.extractMultipleAsText(abilitiesXPath);
		
		String typesXPath = "//div[@class='dtm-type']//a";
		List<String> pokemonTypes = pokemonPage.extractMultipleAsText(typesXPath);
		
		String weaknessesXPath = "//div[@class='dtm-weaknesses']/ul/li/a";
		List<String> pokemonWeaknesses = pokemonPage.extractMultipleAsText(weaknessesXPath);
		
		String descriptionsXPath = "//div[@class='version-descriptions active']";
		List<String> pokemonDescriptions = pokemonPage.extractMultipleAsText(descriptionsXPath);
				
		Pokemon pokemon = new Pokemon(
			pokemonDexNo, 
			pokemonName, 
			pokemonHeight, 
			pokemonWeight, 
			pokemonCategory, 
			pokemonGenders, 
			pokemonAbilities, 
			pokemonTypes, 
			pokemonWeaknesses, 
			pokemonDescriptions
		);
		
		return pokemon;
	}
			
	private class PokemonPage 
	{
		private HtmlPage page;
				
		public PokemonPage(String url) throws FailingHttpStatusCodeException, MalformedURLException, IOException
		{
			this.page = client.getPage(url);
		}
		
		private List<String> extractAnchorsAsText(String xPath)
		{
			List<String> hrefs = new ArrayList<String>();
			
			List<HtmlAnchor> anchors = scrapeMultipleAnchors(xPath);
			
			for(HtmlAnchor anchor : anchors)
			{
				String anchorText = anchor.asText();
				if(anchorText.contains(" - "))
				{
					String href = anchor.getHrefAttribute();
					href = href.replace("/us/pokedex/", "");
					hrefs.add(href);
				}				
			}				
			
			return hrefs;
		}
		
		private List<HtmlAnchor> scrapeMultipleAnchors(String xPath)
		{
			return page.getByXPath(xPath);
		}
				
		private List<String> extractMultipleAsText(String xPath)
		{					
			List<String> stringList = new ArrayList<String>();
			
			List<HtmlElement> elements = scrapeMultipleElements(xPath);
			
			for(HtmlElement element : elements)			
				stringList.add(element.asText());
									
			return stringList;
		}
		
		private String extractSingleAsText(String xPath)
		{
			HtmlElement element = scrapeSingleElement(xPath);
			return element.asText();
		}
		
		private List<HtmlElement> scrapeMultipleElements(String xPath)
		{				
			return page.getByXPath(xPath);
		}
			
		private HtmlElement scrapeSingleElement(String xPath)
		{
			return page.getFirstByXPath(xPath);
		}
		
		private List<String> scrapeGenderElements(String xPath)
		{
			List<String> pokemonGenderInfo = new ArrayList<String>(2);
				
			List<HtmlElement> elements = scrapeMultipleElements(xPath);
			
			for(HtmlElement element : elements)
			{
				if(element.getFirstByXPath("//i[@class='icon icon_male_symbol']") != null)
					pokemonGenderInfo.add("Male");
				
				if(element.getFirstByXPath("//i[@class='icon icon_female_symbol']") != null)
					pokemonGenderInfo.add("Female");
				
				if(element.asText().equalsIgnoreCase("Unknown"))
					pokemonGenderInfo.add("Unknown");
			}	
			
			return pokemonGenderInfo;
		}	
	}
}
