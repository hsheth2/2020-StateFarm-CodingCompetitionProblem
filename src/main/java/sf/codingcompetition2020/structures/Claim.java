package sf.codingcompetition2020.structures;

public class Claim {
	private int claimId;
	private int customerId;
	private boolean closed;
	private int monthsOpen;

	public Claim(String[] tokens) {
		if (tokens.length != 4) {
			throw new IllegalArgumentException("Claim: tokens not the appropriate length");
		}

		claimId = Integer.parseInt(tokens[0]);
		customerId = Integer.parseInt(tokens[1]);
		closed = tokens[2].equals("true");
		monthsOpen = Integer.parseInt(tokens[3]);
	}

	public int getClaimId() {
		return claimId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public boolean isClosed() {
		return closed;
	}

	public int getMonthsOpen() {
		return monthsOpen;
	}
}
