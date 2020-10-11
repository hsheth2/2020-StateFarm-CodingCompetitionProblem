package sf.codingcompetition2020.structures;

public class Vendor {
	private int vendorId;
	private String area;
	private int vendorRating;
	private boolean inScope;

	public Vendor(String[] tokens) {
		if (tokens.length != 4) {
			throw new IllegalArgumentException("Vendor: tokens not the appropriate length");
		}

		vendorId = Integer.parseInt(tokens[0]);
		area = tokens[1];
		vendorRating = Integer.parseInt(tokens[2]);
		inScope = tokens[3].equals("true");
	}

	public int getVendorId() {
		return vendorId;
	}

	public String getArea() {
		return area;
	}

	public int getVendorRating() {
		return vendorRating;
	}

	public boolean isInScope() {
		return inScope;
	}
}
