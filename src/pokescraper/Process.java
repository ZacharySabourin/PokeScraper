package pokescraper;

import java.io.IOException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

import pokescraper.web.WebScraper;

public class Process {

	public static void main(String[] args)
	{
		try 
		{
			long start = System.currentTimeMillis();
			
			WebScraper pokeScraper = new WebScraper();
			pokeScraper.getAllPokemonfromWeb();
			
			long end = System.currentTimeMillis();
			long elapsedTime = end - start;
			System.out.println("ELAPSED TIME : " + elapsedTime + "ms");
		} 
		catch (FailingHttpStatusCodeException | IOException e)
		{
			e.printStackTrace();
		}
	}

}
