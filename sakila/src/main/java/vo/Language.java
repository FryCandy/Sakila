package vo;
//language table vo
public class Language {
	private int languageId;
	private String name;
	private String lastUpdage;
	public int getLanguageId() {
		return languageId;
	}
	public void setLanguageId(int languageId) {
		this.languageId = languageId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastUpdage() {
		return lastUpdage;
	}
	public void setLastUpdage(String lastUpdage) {
		this.lastUpdage = lastUpdage;
	}
	@Override
	public String toString() {
		return "Language [languageId=" + languageId + ", name=" + name + ", lastUpdage=" + lastUpdage + "]";
	}
	
	

}
