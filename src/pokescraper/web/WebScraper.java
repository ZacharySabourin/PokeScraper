package pokescraper.web;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayDeque;
import java.util.Deque;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;

import pokescraper.pokedex.Pokemon;

public class WebScraper 
{
	private WebClient client;	
	private String startUrl;
	
	public WebScraper()
	{
		this.client = new WebClient();
		this.startUrl = "https://www.pokemon.com/us/pokedex/";
	}

	public Deque<Pokemon> getAllPokemonfromWeb() throws FailingHttpStatusCodeException, MalformedURLException, IOException
	{
		disableCssAndJavaScript();
		
		Deque<Pokemon> pokemonList = new ArrayDeque<Pokemon>(890);			
		
		Deque<String> paths = getPokemonPaths();		
		while(!paths.isEmpty())
			if(pokemonList.offer(getSinglePokemonFromWeb(paths.poll())))
				System.out.println("SCRAPED : " + pokemonList.peekLast().getNationalDexNo() + " - " + pokemonList.peekLast().getName());
					
		client.close();
		
		return pokemonList;
	}
	
	private void disableCssAndJavaScript()
	{
		client.getOptions().setCssEnabled(false);
		client.getOptions().setJavaScriptEnabled(false);
	}
	
	private Deque<String> getPokemonPaths() throws FailingHttpStatusCodeException, MalformedURLException, IOException
	{
		StartPage startPage = new StartPage(client.getPage(startUrl));
		return startPage.getAllHrefAttributes();
	}
	
	private Pokemon getSinglePokemonFromWeb(String path) throws FailingHttpStatusCodeException, MalformedURLException, IOException
	{
		PokemonPage page = new PokemonPage(client.getPage(startUrl + path));
		
		Pokemon pokemon = new Pokemon();		
		pokemon.setNationalDexNo(page.getDexNoElement());
		pokemon.setName(page.getNameElement());
		pokemon.setHeight(page.getHeightElement());
		pokemon.setWeight(page.getWeightElement());
		pokemon.setCategory(page.getCategoryElement());
		pokemon.setGenders(page.getGenderElements());
		pokemon.setAbilities(page.getAbilityElements());
		pokemon.setTypes(page.getTypeElements());
		pokemon.setWeaknesses(page.getWeaknessElements());
		pokemon.setDescriptions(page.getDescriptionElements());
		
		return pokemon;	
	}
}
