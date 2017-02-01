
public class Song 
{
	private String name, url;
	private int year, difficulty;
	
	public Song (String name, String url, int year, int difficulty)
	{
		this.name = name;
		this.url = url;
		this.year = year;
		this.difficulty = difficulty;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getUrl()
	{
		return url;
	}
	
	public int getYear()
	{
		return year;
	}
	
	public int getDifficulty()
	{
		return difficulty;
	}
}
