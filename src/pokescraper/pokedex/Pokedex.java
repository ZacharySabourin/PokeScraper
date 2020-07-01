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
	
	public void upLoadAllPokemonToDb() throws ClassNotFoundException, SQLException
	{
		dbManager.establishConnection();
		
		while(!pokemonList.isEmpty())
			uploadSinglePokemonToDb(pokemonList.poll());
		
		dbManager.closeConnection();
	}
	
	private void uploadSinglePokemonToDb(Pokemon pokemon) throws SQLException
	{
		StringBuffer query = new StringBuffer(300);
		
		query.append("INSERT INTO POKEMON ("
				+ "dex_no, pokemon_name, pokemon_height, pokemon_weight, "
				+ "pokemon_category, pokemon_gender_one, pokemon_ability_one, pokemon_type_one, "
				+ "pokemon_description_one, pokemon_description_two) "
				+ "VALUES (");
		
		query.append("" + pokemon.getNationalDexNo() + ", '" + pokemon.getName() + "', '" + pokemon.getHeight() + "', '");
		query.append(pokemon.getWeight() + "', '" + pokemon.getCategory() + "', ");
		query.append("'NA', 1");
		
		for(String desc : pokemon.getDescriptions())
			query.append(", '" + desc + "'");
		
		query.append(")");
		
		dbManager.write(query.toString());
	}
	
	public void uploadAllAbilitiesToDb() throws ClassNotFoundException, SQLException
	{		
		dbManager.establishConnection();
		
		for(String ability : getAllAbilities())
			uploadPokemonAbility(ability);
		
		dbManager.closeConnection();
	}
	
	private Set<String> getAllAbilities()
	{
		Set<String> pokemonAbilities = new HashSet<String>(260);
		
		for(Pokemon pokemon : pokemonList)
			for(String ability : pokemon.getAbilities())
				pokemonAbilities.add(ability);
		
		return pokemonAbilities;
	}
	
	private void uploadPokemonAbility(String ability) throws SQLException
	{
		String query = "INSERT INTO ABILITIES (ability_name) VALUES ('" + ability +"')";
		dbManager.write(query);
	}
}
