package pokescraper.pokedex;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

import pokescraper.database.DbManager;
import pokescraper.web.WebScraper;

public class Pokedex 
{
	private Deque<Pokemon> pokemonList;
	private WebScraper scraper;
	private DbManager dbManager;
	
	public Pokedex()
	{
		pokemonList = new ArrayDeque<Pokemon>(890);
		scraper = new WebScraper();
		dbManager = new DbManager();
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
	
	public void populateAllTables() throws ClassNotFoundException, SQLException
	{
		dbManager.establishConnection();
		
		populateAbilitiesTable();
		populatePokemonTable();		
		
		dbManager.closeConnection();
	}
	
	private void populateAbilitiesTable() throws SQLException
	{		
		for(String ability : siftForUniqueAbilities())
			dbManager.writeIntoAbilityTable(ability);
	}
	
	private void populatePokemonTable() throws SQLException
	{
		while(!pokemonList.isEmpty())
			uploadAllPokemonFields(pokemonList.poll());
	}
	
	private void uploadAllPokemonFields(Pokemon pokemon) throws SQLException
	{
		dbManager.writePokemon(pokemon);
		//dbManager.writePokemonAbilities(pokemon);
		dbManager.writePokemonTypes(pokemon);
		//dbManager.writePokemonWeaknesses(pokemon);
	}
		
	private Set<String> siftForUniqueAbilities()
	{
		Set<String> pokemonAbilities = new HashSet<String>(260);
		
		for(Pokemon pokemon : pokemonList)
			for(String ability : pokemon.getAbilities())
				pokemonAbilities.add(ability);
		
		return pokemonAbilities;
	}	
}
