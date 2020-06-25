
public class PokeScraper 
{
	public static void main(String[] args) 
	{
		Scraper scraper = new Scraper();
		
		try
		{
			long start = System.currentTimeMillis();
			
			scraper.scrape();
			
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
