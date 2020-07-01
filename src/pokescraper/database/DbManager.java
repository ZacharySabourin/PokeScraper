package pokescraper.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
		dbConnection.close();
	}
	
	public boolean isClosed() throws SQLException
	{
		return dbConnection.isClosed();
	}	
	
	public void writeAbility(String ability) throws SQLException
	{
		String query = "INSERT INTO ABILITIES (ability_name) VALUES (?)";
		
		try(PreparedStatement statement = dbConnection.prepareStatement(query))  
		{	
			statement.setString(1, ability);
			statement.execute();	
		}		
	}
	
	public void writePokemon(Pokemon pokemon) throws SQLException
	{
		String query = "INSERT INTO POKEMON (dex_no, pokemon_name, pokemon_height, pokemon_weight, pokemon_category) "
				+ "VALUES (?, ?, ?, ?, ?)";
		
		try(PreparedStatement statement = dbConnection.prepareStatement(query))
		{
			statement.setInt(1, pokemon.getNationalDexNo());
			statement.setString(2, pokemon.getName());
			statement.setString(3, pokemon.getHeight());
			statement.setString(4, pokemon.getWeight());
			statement.setString(5, pokemon.getCategory());
			statement.execute();
		}
	}
		
//	public Map<String> read(String query)
//	{
//		
//	}
}
