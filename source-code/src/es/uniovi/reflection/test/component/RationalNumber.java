package es.uniovi.reflection.test.component;

 public class RationalNumber {

	private static int gcd(int a, int b) {
		return a == b ? a : a > b ? gcd(b, a) : gcd(a, b - a);
	}
	public int denominator;

	public int numerator;

	public RationalNumber(int numerator, int denominator) {
		this.numerator = numerator;
		if (denominator == 0)
			throw new ZeroAsDenominatorException();
		this.denominator = denominator;
	}

	public double getValue() {
		return (double) numerator / denominator;
	}

	public RationalNumber simplify() {
		int gcd = gcd(numerator, denominator);
		if (gcd == 1)
			throw new AlreadySimplifiedException();
		return new RationalNumber(numerator / gcd, denominator / gcd);
	}

	public int[][] toMatrix(int factor, RationalNumber... rationals) {
		int[][] matrix = new int[2][rationals.length + 1];
		matrix[0][0] = numerator * factor;
		matrix[1][0] = denominator;
		for (int i = 0; i < rationals.length; i++) {
			matrix[0][i + 1] = rationals[i].numerator * factor;
			matrix[1][i + 1] = rationals[i].denominator;
		}
		return matrix;
	}

	@Override
	public String toString() {
		return numerator + "/" + denominator;
	}


}
