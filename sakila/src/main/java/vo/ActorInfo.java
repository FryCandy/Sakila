package vo;

public class ActorInfo {
	private int actorId;
	private String firstName;
	private String lastName;
	private String filmInfo;
	
	/* Object 클래스를 오버라이딩 클래스 안에 그 필드의 객체들을 나오는거 toString메서드 라고 약속 - 소스에 generate toString()이라고 있다.
	@Override 
	public String toString(){
		return this.actorId + this.firstName + this.lastName + this.filmInfo;
	}
	*/
	@Override
	public String toString() {
		return "ActorInfo [actorId=" + actorId + ", firstName=" + firstName + ", lastName=" + lastName + ", filmInfo="
				+ filmInfo + "]";
	}

	public int getActorId() {
		return actorId;
	}
	public void setActorId(int actorId) {
		this.actorId = actorId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFilmInfo() {
		return filmInfo;
	}
	public void setFilmInfo(String filmInfo) {
		this.filmInfo = filmInfo;
	}
}
