package sf.codingcompetition2020.structures;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Customer {
	private int customerId;
	private String firstName;
	private String lastName;
	private int age;
	private String area;
	private int agentId;
	private short agentRating;
	private String primaryLanguage;
	private List<Dependent> dependents;
	private boolean homePolicy;
	private boolean autoPolicy;
	private boolean rentersPolicy;
	private String totalMonthlyPremium;
	private short yearsOfService;
	private Integer vehiclesInsured;

	public Customer(String[] tokens) {
		if (tokens.length != 15) {
			throw new IllegalArgumentException("Customer: tokens not the appropriate length");
		}

		customerId = Integer.parseInt(tokens[0]);
		firstName = tokens[1];
		lastName = tokens[2];
		age = Integer.parseInt(tokens[3]);
		area = tokens[4];
		agentId = Integer.parseInt(tokens[5]);
		agentRating = Short.parseShort(tokens[6]);
		primaryLanguage = tokens[7];
		// TODO dependents
		homePolicy = tokens[9].equals("true");
		autoPolicy = tokens[10].equals("true");
		rentersPolicy = tokens[11].equals("true");
		totalMonthlyPremium = tokens[12];
		yearsOfService = Short.parseShort(tokens[13]);
		vehiclesInsured = Integer.parseInt(tokens[14]);
	}

	public int getCustomerId() {
		return customerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public int getAge() {
		return age;
	}

	public String getArea() {
		return area;
	}

	public int getAgentId() {
		return agentId;
	}

	public short getAgentRating() {
		return agentRating;
	}

	public String getPrimaryLanguage() {
		return primaryLanguage;
	}

	public List<Dependent> getDependents() {
		return dependents;
	}

	public boolean isHomePolicy() {
		return homePolicy;
	}

	public boolean isAutoPolicy() {
		return autoPolicy;
	}

	public boolean isRentersPolicy() {
		return rentersPolicy;
	}

	public String getTotalMonthlyPremium() {
		return totalMonthlyPremium;
	}

	public int getTotalMonthlyPremiumAsInt() {
		// TODO: cache this value?
		return Integer.parseInt(totalMonthlyPremium.substring(1));
	}

	public short getYearsOfService() {
		return yearsOfService;
	}

	public Integer getVehiclesInsured() {
		return vehiclesInsured;
	}
}
