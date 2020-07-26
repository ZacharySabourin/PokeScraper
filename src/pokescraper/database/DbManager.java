package pokescraper.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import pokescraper.pokedex.ElementType;
import pokescraper.pokedex.Pokemon;

public class DbManager 
{
	private String connectionURL;
	private String username;
	private String password;
	private Connection dbConnection;
	
	public DbManager()
	{
		connectionURL = "jdbc:mysql://localhost:3306/pokedex_dev?autoReconnect=true&useSSL=false";
		username = "root";
		password = "root";
		dbConnection = null;
	}
	
	public void establishConnection() throws SQLException, ClassNotFoundException
	{
		dbConnection = DriverManager.getConnection(connectionURL, username, password);
	}
	
	public void closeConnection() throws SQLException
	{
		if(dbConnection != null)
			dbConnection.close();
	}
	
	public void writeIntoAbilityTable(String ability) throws SQLException
	{
		String insertSQL = "INSERT INTO abilities (ability_name) VALUES (?)";
		
		try(PreparedStatement statement = dbConnection.prepareStatement(insertSQL))  
		{	
			statement.setString(1, ability);
			statement.execute();	
		}		
	}
	
	public void writePokemon(Pokemon pokemon) throws SQLException
	{
		String insertSQL = "INSERT INTO pokemon "
				+ "(dex_no, pokemon_name, pokemon_height, pokemon_weight, pokemon_category, pokemon_gender_one, "
				+ "pokemon_gender_two, pokemon_description_one, pokemon_description_two) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try(PreparedStatement statement = dbConnection.prepareStatement(insertSQL))
		{
			statement.setInt(1, pokemon.getNationalDexNo());
			statement.setString(2, pokemon.getName());
			statement.setString(3, pokemon.getHeight());
			statement.setString(4, pokemon.getWeight());
			statement.setString(5, pokemon.getCategory());
			statement.setString(6, pokemon.getGenders().get(0));			
			statement.setString(7, checkStringForNull(pokemon.getGenders(), 1));
			statement.setString(8, pokemon.getDescriptions().get(0));
			statement.setString(9, pokemon.getDescriptions().get(1));
			statement.execute();
		}
	}
	
//	public void writePokemonAbilities(Pokemon pokemon) throws SQLException
//	{
//		String insertSQL = "";
//		
//		try(PreparedStatement statement = dbConnection.prepareStatement(insertSQL))
//		{
//			
//			statement.execute();
//		}
//	}
	
	public void writePokemonTypes(Pokemon pokemon) throws SQLException
	{
		String insertSQL = "UPDATE pokemon SET pokemon_type_one = ?, pokemon_type_two = ? WHERE dex_no = ?";
		
		fillAndExecuteStatement(insertSQL, getForeignKeys(pokemon.getTypes()), 2, pokemon.getNationalDexNo());
	}
	
	public void writePokemonWeaknesses(Pokemon pokemon) throws SQLException
	{
		String insertSQL = "UPDATE pokemon SET pokemon_weakness_one = ?, pokemon_weakness_two = ?, pokemon_weakness_three = ?,"
				+ " pokemon_weakness_four = ?, pokemon_weakness_five = ?, pokemon_weakness_six = ?, pokemon_weakness_seven = ?"
				+ " WHERE dex_no = ?";
		
		fillAndExecuteStatement(insertSQL, getForeignKeys(pokemon.getWeaknesses()), 7, pokemon.getNationalDexNo());		
	}
	
	private void fillAndExecuteStatement(String insertSQL, List<Integer> fKeys, int maxKeys, int dexNo) throws SQLException
	{
		try(PreparedStatement statement = dbConnection.prepareStatement(insertSQL))
		{
			for(int i = 0; i < maxKeys; i++)
			{
				if(fKeys.size() >= (i + 1))			
					statement.setInt(i + 1, fKeys.get(i));
				
				else
					statement.setNull(i + 1, Types.NULL);
			}
			
			statement.setInt(maxKeys + 1, dexNo);
			statement.execute();
		}
	}
	
	private List<Integer> getForeignKeys(List<String> attributes)
	{
		List<Integer> foreignKeys = new ArrayList<Integer>();
		
		for(String attribute : attributes)
			for(ElementType element : ElementType.values())
				if(element.name().equalsIgnoreCase(attribute))
					foreignKeys.add(element.ordinal() + 1);
		
		return foreignKeys;
	}
	
	private String checkStringForNull(List<String> list, int index)
	{
		if(list.size() > index)
			return list.get(index);
		
		return null;
	}	
	
//	public Map<Integer, String> getMapOfAbilities() throws SQLException
//	{
//		String query = "SELECT ability_id, ability_name FROM abilities";
//		Map<Integer, String> map = new HashMap<Integer, String>();
//		
//		try(Statement statement = dbConnection.createStatement(); 
//				ResultSet result = statement.executeQuery(query);)
//		{
//			while(result.next())
//				map.put(result.getInt(1), result.getString(2));
//		}
//			
//		return map;
//	}
}
