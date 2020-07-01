package pokescraper.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DbManager 
{
	private String connectionURL;
	private String username;
	private String password;
	private Connection dbConnection;
	
	public DbManager()
	{
		connectionURL = "jdbc:mysql://localhost:3306/pokedex_dev";
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
	
	public void write(String query) throws SQLException
	{
		try(Statement statement = dbConnection.createStatement())  
		{	
			statement.executeUpdate(query);		
		}	
	}
	
//	public Map<String> read(String query)
//	{
//		
//	}
}
