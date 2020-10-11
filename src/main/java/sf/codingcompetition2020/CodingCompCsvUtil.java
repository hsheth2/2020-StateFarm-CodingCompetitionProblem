package sf.codingcompetition2020;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import sf.codingcompetition2020.structures.Agent;
import sf.codingcompetition2020.structures.Claim;
import sf.codingcompetition2020.structures.Customer;
import sf.codingcompetition2020.structures.Vendor;

public class CodingCompCsvUtil {

	/* #1
	 * readCsvFile() -- Read in a CSV File and return a list of entries in that file.
	 * @param filePath -- Path to file being read in.
	 * @param classType -- Class of entries being read in. Must have a constructor that accepts String[].
	 * @return -- List of entries being returned.
	 */
	public <T> List<T> readCsvFile(String filePath, Class<T> classType) {
		CsvMapper csvMapper = new CsvMapper();
		CsvSchema schema = CsvSchema.emptySchema().withHeader();

		ObjectReader reader = csvMapper.reader(classType).with(schema);

		ArrayList<T> values = new ArrayList<>();
		try {
			MappingIterator<T> it = reader.readValues(new File(filePath));
			while (it.hasNext()) {
				values.add(it.next());
			}
		} catch (IOException e) {
			throw new IllegalArgumentException("Cannot open the specified file");
		}
		return values;
	}


	/* #2
	 * getAgentCountInArea() -- Return the number of agents in a given area.
	 * @param filePath -- Path to file being read in.
	 * @param area -- The area from which the agents should be counted.
	 * @return -- The number of agents in a given area
	 */
	public int getAgentCountInArea(String filePath, String area) {
		return (int) readCsvFile(filePath, Agent.class).stream().filter(agent -> {
			// Only count agents with the correct area.
			return area.equals(agent.getArea());
		}).count();
	}


	/* #3
	 * getAgentsInAreaThatSpeakLanguage() -- Return a list of agents from a given area, that speak a certain language.
	 * @param filePath -- Path to file being read in.
	 * @param area -- The area from which the agents should be counted.
	 * @param language -- The language spoken by the agent(s).
	 * @return -- The number of agents in a given area
	 */
	public List<Agent> getAgentsInAreaThatSpeakLanguage(String filePath, String area, String language) {
		return readCsvFile(filePath, Agent.class).stream().filter(agent -> {
			// Only include agents with the correct area and language.
			return area.equals(agent.getArea()) && language.equals(agent.getLanguage());
		}).collect(Collectors.toList());
	}


	/* #4
	 * countCustomersFromAreaThatUseAgent() -- Return the number of individuals from an area that use a certain agent.
	 * @param filePath -- Path to file being read in.
	 * @param customerArea -- The area from which the customers should be counted.
	 * @param agentFirstName -- First name of agent.
	 * @param agentLastName -- Last name of agent.
	 * @return -- The number of customers that use a certain agent in a given area.
	 */
	public short countCustomersFromAreaThatUseAgent(Map<String,String> csvFilePaths, String customerArea, String agentFirstName, String agentLastName) {
		// First, we find the correct agent.
		List<Agent> candidateAgents = readCsvFile(csvFilePaths.get("agentList"), Agent.class).stream().filter(agent -> {
			return agentFirstName.equals(agent.getFirstName()) && agentLastName.equals(agent.getLastName());
		}).collect(Collectors.toList());
		if (candidateAgents.size() != 1) {
			throw new IllegalArgumentException("Multiple agents with a matching name");
		}
		Agent agent = candidateAgents.get(0);

		// Then, we find the customers that use this agent in the specified area.
		return (short) readCsvFile(csvFilePaths.get("customerList"), Customer.class).stream().filter(customer -> {
			return customer.getAgentId() == agent.getAgentId() && customerArea.equals(customer.getArea());
		}).count();
	}


	/* #5
	 * getCustomersRetainedForYearsByPlcyCostAsc() -- Return a list of customers retained for a given number of years, in ascending order of their policy cost.
	 * @param filePath -- Path to file being read in.
	 * @param yearsOfService -- Number of years the person has been a customer.
	 * @return -- List of customers retained for a given number of years, in ascending order of policy cost.
	 */
	public List<Customer> getCustomersRetainedForYearsByPlcyCostAsc(String customerFilePath, short yearsOfService) {
		return readCsvFile(customerFilePath, Customer.class).stream().filter(customer -> {
			return customer.getYearsOfService() == yearsOfService;
		}).sorted(Comparator.comparingInt(Customer::getTotalMonthlyPremiumAsInt)).collect(Collectors.toList());
	}


	/* #6
	 * getLeadsForInsurance() -- Return a list of individuals who’ve made an inquiry for a policy but have not signed up.
	 * *HINT* -- Look for customers that currently have no policies with the insurance company.
	 * @param filePath -- Path to file being read in.
	 * @return -- List of customers who’ve made an inquiry for a policy but have not signed up.
	 */
	public List<Customer> getLeadsForInsurance(String filePath) {
		return readCsvFile(filePath, Customer.class).stream().filter(customer -> {
			return customer.getTotalMonthlyPremiumAsInt() == 0;
		}).collect(Collectors.toList());
	}


	/* #7
	 * getVendorsWithGivenRatingThatAreInScope() -- Return a list of vendors within an area and include options to narrow it down by:
			a.	Vendor rating
			b.	Whether that vendor is in scope of the insurance (if inScope == false, return all vendors in OR out of scope, if inScope == true, return ONLY vendors in scope)
	 * @param filePath -- Path to file being read in.
	 * @param area -- Area of the vendor.
	 * @param inScope -- Whether or not the vendor is in scope of the insurance.
	 * @param vendorRating -- The rating of the vendor.
	 * @return -- List of vendors within a given area, filtered by scope and vendor rating.
	 */
	public List<Vendor> getVendorsWithGivenRatingThatAreInScope(String filePath, String area, boolean inScope, int vendorRating) {
		return readCsvFile(filePath, Vendor.class).stream().filter(vendor -> {
			// TODO debug
			return vendorRating == vendor.getVendorRating() &&
					area.equals(vendor.getArea()) &&
					(!inScope || vendor.isInScope());
		}).collect(Collectors.toList());
	}


	/* #8
	 * getUndisclosedDrivers() -- Return a list of customers between the age of 40 and 50 years (inclusive), who have:
			a.	More than X cars
			b.	less than or equal to X number of dependents.
	 * @param filePath -- Path to file being read in.
	 * @param vehiclesInsured -- The number of vehicles insured.
	 * @param dependents -- The number of dependents on the insurance policy.
	 * @return -- List of customers filtered by age, number of vehicles insured and the number of dependents.
	 */
	public List<Customer> getUndisclosedDrivers(String filePath, int vehiclesInsured, int dependents) {
		return readCsvFile(filePath, Customer.class).stream().filter(customer -> {
			return 40 <= customer.getAge() && customer.getAge() <= 50
					&& customer.getVehiclesInsured() > vehiclesInsured
					&& customer.getDependentsCount() <= dependents;
		}).collect(Collectors.toList());
	}


	/* #9
	 * getAgentIdGivenRank() -- Return the agent with the given rank based on average customer satisfaction rating.
	 * *HINT* -- Rating is calculated by taking all the agent rating by customers (1-5 scale) and dividing by the total number
	 * of reviews for the agent.
	 * @param filePath -- Path to file being read in.
	 * @param agentRank -- The rank of the agent being requested.
	 * @return -- Agent ID of agent with the given rank.
	 */
	public int getAgentIdGivenRank(String filePath, int agentRank) {
		List<Customer> customers = readCsvFile(filePath, Customer.class);

		Map<Integer, Double> agentRatings = new HashMap<>();
		customers.stream().map(Customer::getAgentId).distinct().forEach(agentId -> {
			// Compute average ratings for each agent.
			Double rating = customers.stream().filter(customer -> customer.getAgentId() == agentId)
					.collect(Collectors.averagingDouble(Customer::getAgentRating));
			// Negate to reverse order when sorting.
			agentRatings.put(agentId, -rating);
		});

		// Sort by ratings, and then get the agent at agentRank.
		List<Entry<Integer, Double>> rankings = agentRatings.entrySet().stream()
				.sorted(Entry.comparingByValue())
				.collect(Collectors.toList());
		return rankings.get(agentRank - 1).getKey();
	}


	/* #10
	 * getCustomersWithClaims() -- Return a list of customers who’ve filed a claim within the last <numberOfMonths> (inclusive).
	 * @param filePath -- Path to file being read in.
	 * @param monthsOpen -- Number of months a policy has been open.
	 * @return -- List of customers who’ve filed a claim within the last <numberOfMonths>.
	 */
	public List<Customer> getCustomersWithClaims(Map<String,String> csvFilePaths, short monthsOpen) {
		List<Claim> claims = readCsvFile(csvFilePaths.get("claimList"), Claim.class);

		return readCsvFile(csvFilePaths.get("customerList"), Customer.class).stream().filter(customer -> {
			// Check if any claims filed with the last X months.
			return claims.stream().filter(claim -> {
				return claim.getCustomerId() == customer.getCustomerId();
			}).anyMatch(claim -> {
				return claim.getMonthsOpen() <= monthsOpen;
			});
		}).collect(Collectors.toList());
	}

}
