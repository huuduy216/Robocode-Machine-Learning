
public class Trait
{
	private String[] subTraits;
	public Trait()
	{
		this.subTraits = new String[7];
	}
	public String getSubTraits(int i)
	{
		return subTraits[i];
	}
	public void setSubTrait(int i, String subTrait)
	{
		this.subTraits[i] = subTrait;
	}
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < this.subTraits.length; i++)
		{
			sb.append(subTraits[i]);
		}
		return sb.toString();
	}
}
