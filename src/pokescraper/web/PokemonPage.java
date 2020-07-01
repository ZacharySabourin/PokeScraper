package pokescraper.web;

import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class PokemonPage extends WebPage
{
	protected PokemonPage(HtmlPage page)
	{
		super(page);
	}
	
	protected String getNameElement()
	{
		return getNameAndNumberElements()[0];
	}
	
	protected int getDexNoElement()
	{
		return Integer.parseInt(getNameAndNumberElements()[1]);
	}
	
	protected String getHeightElement()
	{
		String heightXPath = "//div[@class='column-7']/ul[1]/li[1]/span[@class='attribute-value']";	
		String height = getSingleElementAsText(heightXPath);
		
		height = height.replace("'", "ft. ");
		height = height.replace("\"", "in.");
		
		return height;
	}
	
	protected String getWeightElement()
	{
		String weightXPath = "//div[@class='column-7']/ul[1]/li[2]/span[@class='attribute-value']";
		return getSingleElementAsText(weightXPath);
	}
	
	protected String getCategoryElement()
	{
		String categoryXPath = "//div[@class='column-7 push-7']/ul/li/span[@class='attribute-value']";
		return getSingleElementAsText(categoryXPath);
	}
	
	protected List<String> getAbilityElements()
	{		
		return getMultipleElementsAsText("//ul[@class='attribute-list']/li[", "]/a/span", 3);
	}
	
	protected List<String> getTypeElements()
	{
		return getMultipleElementsAsText("//div[@class='dtm-type']/ul/li[", "]/a", 2);
	}
	
	protected List<String> getWeaknessElements()
	{
		return getMultipleElementsAsText("//div[@class='dtm-weaknesses']/ul/li[", "]/a/span", 7);
	}
	
	protected List<String> getDescriptionElements()
	{
		List<String> descriptions = new ArrayList<String>(2);
		
		String versionXXPath = "//div[@class='version-descriptions active']/p[1]";
		descriptions.add(getSingleElementAsText(versionXXPath));
		
		String versionYXPath = "//div[@class='version-descriptions active']/p[2]";
		descriptions.add(getSingleElementAsText(versionYXPath));
		
		return descriptions;
	}
	
	protected List<String> getGenderElements()
	{
		List<String> genderInfo = new ArrayList<String>();
			
		if(hasMaleGender())
			genderInfo.add("Male"); 
		
		if(hasFemaleGender())
			genderInfo.add("Female");
				
		if(hasUnknownGender())
			genderInfo.add("Unknown");
		
		return genderInfo;
	}	
	
	private String[] getNameAndNumberElements()
	{
		String nameAndNoXPath = "//div[@class='pokedex-pokemon-pagination-title']";			
		String nameAndNo = getSingleElementAsText(nameAndNoXPath);
		return nameAndNo.split(" #", 2);
	}
	
	private boolean hasUnknownGender()
	{
		String unknownGenderXPath = "//div[@class='column-7']/ul/li[3]/span[@class='attribute-value']";
		String unknownGenderText = getSingleElementAsText(unknownGenderXPath);
		
		return elementTextContains(unknownGenderText, "Unknown");
	}
	
	private boolean hasMaleGender()
	{
		String maleXPath = "//i[@class='icon icon_male_symbol']";
		return elementIsValid(maleXPath);
	}
	
	private boolean hasFemaleGender()
	{
		String femaleXPath = "//i[@class='icon icon_female_symbol']";
		return elementIsValid(femaleXPath);
	}
}