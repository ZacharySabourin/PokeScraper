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
			long start = System.currentTimeMillis();
			
			Pokedex pokedex = new Pokedex();
			pokedex.loadPokemonIntoDex();
			pokedex.uploadAllAbilitiesToDb();
			pokedex.upLoadAllPokemonToDb();
			
			long end = System.currentTimeMillis();
			long elapsedTime = end - start;
			System.out.println("ELAPSED TIME : " + elapsedTime + "ms");
		} 
		catch (FailingHttpStatusCodeException | IOException | InterruptedException | ClassNotFoundException | SQLException e)
		{
			e.printStackTrace();
		}
	}
}