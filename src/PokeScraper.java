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

public class PokeScraper 
{
	private WebClient client;	
	private String startUrl;
	private Deque<String> pokemonPaths;
	
	public PokeScraper()
	{
		this.client = new WebClient();
		this.startUrl = "https://www.pokemon.com/us/pokedex/";
		this.pokemonPaths = new ArrayDeque<String>(890);
	}

	public Deque<Pokemon> extractEveryPokemonInfo() throws FailingHttpStatusCodeException, MalformedURLException, IOException
	{
		Deque<Pokemon> pokemonList = new ArrayDeque<Pokemon>(890);
		
		client.getOptions().setCssEnabled(false);
		client.getOptions().setJavaScriptEnabled(false);		
		
		extractPathsAsText();
			
		while(!pokemonPaths.isEmpty())
		{
			String pokemonName = pokemonPaths.poll();
			Pokemon pokemon = extractSinglePokemonInfo(pokemonName);
			pokemonList.offer(pokemon);
			System.out.println("ADDED : " + pokemon.getName());
		}	
		
		client.close();
		
		return pokemonList;
	}
	
	private void extractPathsAsText() throws FailingHttpStatusCodeException, MalformedURLException, IOException
	{
		HtmlPage startPage = client.getPage(startUrl);	
		String hrefXPath = "//a";
		
		List<HtmlAnchor> anchors = startPage.getByXPath(hrefXPath);
		
		for(HtmlAnchor anchor : anchors)
		{
			String anchorText = anchor.asText();
			if(anchorText.contains(" - "))
			{
				String href = anchor.getHrefAttribute();
				href = href.replace("/us/pokedex/", "");
				pokemonPaths.offer(href);
			}				
		}		
		
		System.out.println(pokemonPaths.size() + " PATHS RETRIEVED");
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
		
		
		List<String> pokemonGenders = pokemonPage.scrapeGenderElements();
		
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
		
		private List<String> scrapeGenderElements()
		{
			List<String> pokemonGenderInfo = new ArrayList<String>(2);
			
			String maleXPath = "//div[@class='column-7']/ul/li[3]/span[@class='attribute-value']/i[@class='icon icon_male_symbol']";	
			if(scrapeSingleElement(maleXPath) != null)
				pokemonGenderInfo.add("Male");
			
			String femaleXPath = "//div[@class='column-7']/ul/li[3]/span[@class='attribute-value']/i[@class='icon icon_female_symbol']";	
			if(scrapeSingleElement(femaleXPath) != null)
				pokemonGenderInfo.add("Female");
			
			String unknownXPath = "//div[@class='column-7']/ul/li[3]/span[@class='attribute-value']";
			if(extractSingleAsText(unknownXPath).contains("Unknown"))
				pokemonGenderInfo.add("Unknown");
			
			return pokemonGenderInfo;
		}	
	}
}
