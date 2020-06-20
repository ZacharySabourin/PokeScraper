public class PokeScraper 
{
	public static void main(String[] args) 
	{
		Scraper scraper = new Scraper();
		try 
		{
			scraper.scrape();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
