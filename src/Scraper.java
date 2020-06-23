import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Scraper 
{
	private WebClient client;	
	private String startUrl;
	private Set<String> pokemonNames;
	
	public Scraper()
	{
		this.client = new WebClient();
		this.startUrl = "https://www.pokemon.com/us/pokedex/";
		this.pokemonNames = new HashSet<String>();
	}
	
	public void scrape() throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException
	{
		client.getOptions().setCssEnabled(false);
		client.getOptions().setJavaScriptEnabled(false);
		
		scrapeForPokemonNames();
		scrapeEachPokemonInfo();
	}
	
	private void scrapeForPokemonNames() throws FailingHttpStatusCodeException, MalformedURLException, IOException
	{		
		HtmlPage startPage = client.getPage(startUrl);	
		
		List<HtmlAnchor> anchors = startPage.getByXPath("//a");	
		
		for(HtmlAnchor anchor : anchors)			
			if(anchor.asText().contains(" - "))
			{
				String href = anchor.getHrefAttribute();
				href = href.replace("/us/pokedex/", "");
				pokemonNames.add(href);
			}
						
		System.out.println(pokemonNames.size() + " RETRIEVED");
	}
	
	private List<Pokemon> scrapeEachPokemonInfo() throws FailingHttpStatusCodeException, MalformedURLException, IOException
	{	
		List<Pokemon> pokemonList = new ArrayList<Pokemon>();
		
		for(String name : pokemonNames)
		{
			Pokemon pokemon = scrapePokemonInfo(name);
			if(pokemonIsValid(pokemon)) 
			{
				pokemonList.add(pokemon);				
				System.out.println(pokemon.getName() + " ADDED");
			}				
		}
		
		return pokemonList;
	}
	
	private Pokemon scrapePokemonInfo(String path) throws FailingHttpStatusCodeException, MalformedURLException, IOException
	{
		HtmlPage dexPage = client.getPage(startUrl + path);
		
		String nameAndNoXPath = "//div[@class='pokedex-pokemon-pagination-title']";			
		String nameAndNo = scrapeSingleElement(dexPage, nameAndNoXPath);
		
		String[] splitNameAndNo = nameAndNo.split(" #", 2);
		
		String pokemonName = splitNameAndNo[0];
		int pokemonDexNo = Integer.parseInt(splitNameAndNo[1]);
				
		String heightXPath = "//div[@class='column-7']/ul[1]/li[1]/span[@class='attribute-value']";
		String pokemonHeight = scrapeSingleElement(dexPage, heightXPath);
		
		String weightXPath = "//div[@class='column-7']/ul[1]/li[2]/span[@class='attribute-value']";
		String pokemonWeight = scrapeSingleElement(dexPage, weightXPath);
		
		String categoryXPath = "//div[@class='column-7 push-7']/ul/li/span[@class='attribute-value']";
		String pokemonCategory = scrapeSingleElement(dexPage, categoryXPath);
		
		String genderXPath = "//div[@class='column-7']/ul/li[3]/span[@class='attribute-value']";
		List<String> pokemonGenders = scrapeGenderElements(dexPage, genderXPath);
		
		String abilitiesXPath = "//ul[@class='attribute-list']";
		List<String> pokemonAbilities = scrapeMultipleElements(dexPage, abilitiesXPath);
		
		String typesXPath = "//div[@class='dtm-type']//a";
		List<String> pokemonTypes = scrapeMultipleElements(dexPage, typesXPath);
		
		String weaknessesXPath = "//div[@class='dtm-weaknesses']/ul/li/a";
		List<String> pokemonWeaknesses = scrapeMultipleElements(dexPage, weaknessesXPath);
		
		String descriptionsXPath = "//div[@class='version-descriptions active']";
		List<String> pokemonDescriptions = scrapeMultipleElements(dexPage, descriptionsXPath);
		
		Pokemon pokemon = new Pokemon();
		
		pokemon.setNationalDexNo(pokemonDexNo);
		pokemon.setName(pokemonName);
		pokemon.setHeight(pokemonHeight);
		pokemon.setWeight(pokemonWeight);
		pokemon.setCategory(pokemonCategory);
		pokemon.setGenders(pokemonGenders);
		pokemon.setAbilities(pokemonAbilities);
		pokemon.setTypes(pokemonTypes);
		pokemon.setWeaknesses(pokemonWeaknesses);
		pokemon.setDescriptions(pokemonDescriptions);
		
		return pokemon;
	}
	
	private List<String> scrapeMultipleElements(HtmlPage page, String xPath)
	{
		List<String> pokemonInfo = new ArrayList<String>();
		
		List<HtmlElement> infoElements = page.getByXPath(xPath); 
		for(HtmlElement element : infoElements)
			pokemonInfo.add(element.asText());
		
		return pokemonInfo;
	}
	
	private String scrapeSingleElement(HtmlPage page, String xPath)
	{
		HtmlElement infoElement = page.getFirstByXPath(xPath);
		return infoElement.asText();
	}
	
	private List<String> scrapeGenderElements(HtmlPage page, String xPath)
	{
		List<String> pokemonGenderInfo = new ArrayList<String>();
		
		List<HtmlElement> genderElements = page.getByXPath(xPath);
		for(HtmlElement element : genderElements)
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
	
	private boolean pokemonIsValid(Pokemon pokemon)
	{
		return 
			(pokemon.getNationalDexNo() != 0) &&
			(pokemon.getName() != null) &&
			(pokemon.getHeight() != null) &&
			(pokemon.getWeight() != null) &&
			(pokemon.getCategory() != null) &&
			(pokemon.getGenders() != null) &&
			(pokemon.getAbilities() != null) &&
			(pokemon.getTypes() != null) &&
			(pokemon.getWeaknesses() != null) &&
			(pokemon.getDescription() != null);
	}
}
