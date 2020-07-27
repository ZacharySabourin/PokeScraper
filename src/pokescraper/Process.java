package pokescraper;

import java.io.IOException;
import java.sql.SQLException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

import pokescraper.pokedex.Pokedex;

public class Process 
{
	public static void main(String[] args)
	{
		try 
		{			
			Pokedex pokedex = new Pokedex();			
			pokedex.loadPokemonIntoDex();
			pokedex.populateAllTables();			
		} 
		catch (FailingHttpStatusCodeException | IOException | InterruptedException | ClassNotFoundException | SQLException e)
		{
			e.printStackTrace();
		}
	}
}