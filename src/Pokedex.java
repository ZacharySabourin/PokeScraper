import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayDeque;
import java.util.Deque;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

public class Pokedex 
{
	private Deque<Pokemon> pokemonList;
	private Scraper scraper;
	private DbManager dbManager;
	
	public Pokedex()
	{
		pokemonList = new ArrayDeque<Pokemon>(890);
		scraper = new Scraper();
		dbManager = new DbManager();
	}
	
	@Override
	public String toString()
	{
		return "";
	}
	
	public void loadPokemonIntoDex() throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException
	{		
//		pokemonList = scraper.scrapeIntoDeque();
//		for(Pokemon pokemon : pokemonList)
//			System.out.println(pokemon.getNationalDexNo() + " : " + pokemon.getName());
	}
}
