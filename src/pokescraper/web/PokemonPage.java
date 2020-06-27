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
		return getSingleElementAsText(heightXPath);
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
		String abilitiesXPath = "//ul[@class='attribute-list']";
		return getMultipleElementsAsText(abilitiesXPath);
	}
	
	protected List<String> getTypeElements()
	{
		String typesXPath = "//div[@class='dtm-type']//a";
		return getMultipleElementsAsText(typesXPath);
	}
	
	protected List<String> getWeaknessElements()
	{
		String weaknessesXPath = "//div[@class='dtm-weaknesses']/ul/li/a";
		return getMultipleElementsAsText(weaknessesXPath);
	}
	
	protected List<String> getDescriptionElements()
	{
		String descriptionsXPath = "//div[@class='version-descriptions active']";
		return getMultipleElementsAsText(descriptionsXPath);
	}
	
	protected List<String> getGenderElements()
	{
		List<String> genderInfo = new ArrayList<String>(2);
			
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
		String maleXPath = "//div[@class='column-7']/ul/li[3]/span[@class='attribute-value']/i[@class='icon icon_male_symbol']";
		return elementIsValid(maleXPath);
	}
	
	private boolean hasFemaleGender()
	{
		String femaleXPath = "//div[@class='column-7']/ul/li[3]/span[@class='attribute-value']/i[@class='icon icon_female_symbol']";
		return elementIsValid(femaleXPath);
	}
}