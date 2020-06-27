package pokescraper.pokedex;
import java.util.List;

public class Pokemon 
{
	private int nationalDexNo;
	private String name;
	private String height;
	private String weight;
	private String category;
	private List<String> genders;
	private List<String> abilities;
	private List<String> types;
	private List<String> weaknesses;
	private List<String> descriptions;
	
	public Pokemon()
	{
		this.nationalDexNo = 0;
		this.name = null;
		this.height = null;
		this.weight = null;
		this.category = null;
		this.genders = null;
		this.abilities = null;
		this.types = null;
		this.weaknesses = null;
		this.descriptions = null;
	}
	
	@Override
	public String toString()
	{
		String ret = nationalDexNo + " :: " + name + " :: " + height + " :: " + weight + " :: " + category;		
		
		for(String gender : genders)
			ret += (" :: " + gender);
		
		for(String ability : abilities)
			ret += (" :: " + ability);
		
		for(String type : types)
			ret += (" :: " + type);
		
		for(String weak : weaknesses)
			ret += (" :: " + weak);
		
		for(String description : descriptions)
			ret += ("\n :: " + description);
		
		return ret;
	}
	
	public boolean isValid()
	{
		return 
			(nationalDexNo != 0) &&
			(name != null) &&
			(height != null) &&
			(weight != null) &&
			(category != null) &&
			(genders != null) &&
			(abilities != null) &&
			(types != null) &&
			(weaknesses != null) &&
			(descriptions != null);
	}
	
	public boolean hasTwoTypes()
	{
		return (types.size() > 1);
	}
	
	public int getNationalDexNo()
	{
		return nationalDexNo;
	}
	
	public void setNationalDexNo(int dexNo)
	{
		this.nationalDexNo = dexNo;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getHeight()
	{
		return this.height;
	}
	
	public void setHeight(String height)
	{
		this.height = height;
	}
	
	public String getWeight()
	{
		return this.weight;
	}
	
	public void setWeight(String weight)
	{
		this.weight = weight;
	}
	
	public String getCategory()
	{
		return this.category;
	}
	
	public void setCategory(String category)
	{
		this.category = category;
	}
	
	public List<String> getGenders()
	{
		return this.genders;
	}
	
	public void setGenders(List<String> genders)
	{
		this.genders = genders;
	}
	
	public List<String> getAbilities()
	{
		return this.abilities;
	}
	
	public void setAbilities(List<String> abilities)
	{
		this.abilities = abilities;
	}
	
	public List<String> getTypes()
	{
		return this.types;
	}
	
	public void setTypes(List<String> types)
	{
		this.types = types;
	}
	
	public List<String> getWeaknesses()
	{
		return this.weaknesses;
	}
	
	public void setWeaknesses(List<String> weaknesses)
	{
		this.weaknesses = weaknesses;
	}
	
	public List<String> getDescriptions()
	{
		return this.descriptions;
	}
	
	public void setDescriptions(List<String> descriptions)
	{
		this.descriptions = descriptions;
	}
}
