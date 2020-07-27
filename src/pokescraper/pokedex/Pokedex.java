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
	
	public void loadPokemonIntoDex() throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException
	{		
		pokemonList = scraper.getAllPokemonfromWeb();
	}
	
	public void populateAllTables() throws ClassNotFoundException, SQLException
	{
		dbManager.establishConnection();
		
		populateTypesTable();
		populateAbilitiesTable();
		populatePokemonTable();		
		
		dbManager.closeConnection();
	}
	
	private void populateTypesTable() throws SQLException
	{
		for(ElementType element : ElementType.values())
			dbManager.writeIntoTypesTable(element.name());
	}
	
	private void populateAbilitiesTable() throws SQLException
	{		
		for(String ability : siftForUniqueAbilities())
			dbManager.writeIntoAbilitiesTable(ability);
	}
	
	private void populatePokemonTable() throws SQLException
	{
		while(!pokemonList.isEmpty())
			writeAllPokemonFields(pokemonList.poll());
	}
	
	private void writeAllPokemonFields(Pokemon pokemon) throws SQLException
	{
		dbManager.writePokemon(pokemon);
		dbManager.writePokemonAbilities(pokemon);
		dbManager.writePokemonTypes(pokemon);
		dbManager.writePokemonWeaknesses(pokemon);
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
