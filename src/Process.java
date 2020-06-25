import java.io.IOException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

public class Process {

	public static void main(String[] args)
	{
		Pokedex pokedex = new Pokedex();
		try 
		{
			pokedex.loadPokemonIntoDex();
		} 
		catch (FailingHttpStatusCodeException | IOException | InterruptedException e)
		{
			e.printStackTrace();
		}
	}

}
