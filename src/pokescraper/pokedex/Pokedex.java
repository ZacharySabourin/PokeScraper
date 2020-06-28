package pokescraper.pokedex;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayDeque;
import java.util.Deque;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

import pokescraper.web.WebScraper;

public class Pokedex 
{
	private Deque<Pokemon> pokemonList;
	private WebScraper scraper;
	//private DbManager dbManager;
	
	public Pokedex()
	{
		pokemonList = new ArrayDeque<Pokemon>(890);
		scraper = new WebScraper();
		//dbManager = new DbManager();
	}
	
	@Override
	public String toString()
	{
		return "";
	}
	
	public void loadPokemonIntoDex() throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException
	{		
		pokemonList = scraper.getAllPokemonfromWeb();
	}
}
