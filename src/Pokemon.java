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
	
	public Pokemon(int dexNo, String name, String height, String weight,
		String category, List<String> genders, List<String> abilities, 
		List<String> types, List<String> weaknesses, List<String> descriptions)
	{
		this.nationalDexNo = dexNo;
		this.name = name;
		this.height = height;
		this.weight = weight;
		this.category = category;
		this.genders = genders;
		this.abilities = abilities;
		this.types = types;
		this.weaknesses = weaknesses;
		this.descriptions = descriptions;
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
	
	public String getName()
	{
		return this.name;
	}
	
	public String getHeight()
	{
		return this.height;
	}
	
	public String getWeight()
	{
		return this.weight;
	}
	
	public String getCategory()
	{
		return this.category;
	}
	
	public List<String> getGenders()
	{
		return this.genders;
	}
	
	public List<String> getAbilities()
	{
		return this.abilities;
	}
	
	public List<String> getTypes()
	{
		return this.types;
	}
	
	public List<String> getWeaknesses()
	{
		return this.weaknesses;
	}
	
	public List<String> getDescriptions()
	{
		return this.descriptions;
	}
}
