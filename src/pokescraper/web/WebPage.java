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

	protected List<String> getMultipleElementsAsText(String start, String end, int size)
	{					
		List<String> stringList = new ArrayList<String>();
		
		for(int i = 0; i < size; i ++)
		{
			String xPath = start + (i + 1) + end;
			if(elementIsValid(xPath))
				stringList.add(getSingleElementAsText(xPath));
		}
								
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
		return getSingleElement(xPath) != null;
	}
}
