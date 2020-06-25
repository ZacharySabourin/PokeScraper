import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayDeque;
import java.util.Deque;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

public class Pokedex 
{
	private Deque<Pokemon> pokemonList;
	private PokeScraper pokeScraper;
	private DbManager dbManager;
	
	public Pokedex()
	{
		pokemonList = new ArrayDeque<Pokemon>(890);
		pokeScraper = new PokeScraper();
		dbManager = new DbManager();
	}
	
	@Override
	public String toString()
	{
		return "";
	}
	
	public void loadPokemonIntoDex() throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException
	{		
		pokemonList = pokeScraper.extractEveryPokemonInfo();
		for(Pokemon pokemon : pokemonList)
			System.out.println(pokemon.getNationalDexNo() + " : " + pokemon.getName());
	}
}
