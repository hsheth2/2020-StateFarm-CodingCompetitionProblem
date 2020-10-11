package sf.codingcompetition2020.structures;

public class Agent {
	
	private int agentId;
	private String area;
	private String language;
	private String firstName;
	private String lastName;

	public Agent(String[] tokens) {
		if (tokens.length != 5) {
			throw new IllegalArgumentException("Agent: tokens not the appropriate length");
		}

		agentId = Integer.parseInt(tokens[0]);
		area = tokens[1];
		language = tokens[2];
		firstName = tokens[3];
		lastName = tokens[4];
	}

	public int getAgentId() {
		return agentId;
	}

	public String getArea() {
		return area;
	}

	public String getLanguage() {
		return language;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}
}
