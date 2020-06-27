package pokescraper.web;

import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class WebPage 
{
	protected HtmlPage page;
	
	protected WebPage(HtmlPage page)
	{
		this.page = page;
	}

	protected List<String> getMultipleElementsAsText(String xPath)
	{					
		List<String> stringList = new ArrayList<String>();
		
		for(HtmlElement element : getMultipleElements(xPath))			
			stringList.add(element.asText());
								
		return stringList;
	}
	
	protected String getSingleElementAsText(String xPath)
	{
		return getSingleElement(xPath).asText();
	}
	
	protected List<HtmlElement> getMultipleElements(String xPath)
	{				
		return page.getByXPath(xPath);
	}
	
	protected HtmlElement getSingleElement(String xPath)
	{
		return page.getFirstByXPath(xPath);
	}
	
	protected boolean elementTextContains(String original, String condition)
	{
		return original.contains(condition);
	}
	
	protected boolean elementIsValid(String xPath)
	{			
		return getSingleElement(xPath) == null;
	}
}
