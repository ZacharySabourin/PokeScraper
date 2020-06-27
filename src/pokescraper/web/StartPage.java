package pokescraper.web;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class StartPage extends WebPage
{
	protected StartPage(HtmlPage page) 
	{
		super(page);
	}
	
	protected Deque<String> getAllHrefAttributes()
	{
		Deque<String> hrefs = new ArrayDeque<String>(890);
		
		for(HtmlAnchor anchor : getMultipleAnchors())		
			if(isPokemonAnchor(anchor.asText()))
				hrefs.offer(convertToValidHref(anchor));
		
		return hrefs;
	}
	
	private List<HtmlAnchor> getMultipleAnchors()
	{
		return page.getByXPath("//a");
	}
	
	private boolean isPokemonAnchor(String anchorText)
	{
		return elementTextContains(anchorText, " - ");
	}
	
	private String convertToValidHref(HtmlAnchor anchor)
	{
		String href = anchor.getHrefAttribute();
		return href.replace("/us/pokedex/", "");
	}
}