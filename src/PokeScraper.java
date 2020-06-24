
public class PokeScraper 
{
	public static void main(String[] args) 
	{
		Pokedex pokedex = new Pokedex();
		
		try
		{
			long start = System.currentTimeMillis();
			
			pokedex.loadPokemonIntoDex();
			
			long end = System.currentTimeMillis();
			long elapsedTime = end - start;
			
			System.out.println("ELAPSED TIME:" + elapsedTime);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
