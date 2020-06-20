import java.sql.SQLException;

public class PokeScraper 
{
	public static void main(String[] args) 
	{
		Scraper scraper = new Scraper();
		DbManager manager = new DbManager();
		
		try
		{
			scraper.scrape();
			manager.establishConnection();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(!manager.isClosed())
					manager.closeConnection();
			}
			catch(SQLException sqlEx)
			{
				sqlEx.printStackTrace();
			}
		}
	}
}
