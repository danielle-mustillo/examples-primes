public class PrintPrimes {
	// calculation variables
	int numberOfPrimes; // the number of primes to find
	int listOfPrimes[]; // the primes are stored here
	int maxMultipleOfPrimesIndex; // used to calculate primes, should be fairly
									// large. Depends on input.

	// output format
	int numberOfRows; // the number of rows to be printed per page
	int numberOfColumns; // the number of columns to print.

	// constructor
	public PrintPrimes(int numberOfPrimes, int numberOfRows, int numberOfColumns) {
		// user defined input
		this.numberOfPrimes = numberOfPrimes;
		this.numberOfRows = numberOfRows;
		this.numberOfColumns = numberOfColumns;
		// nitty gritty details (hidden)
		this.maxMultipleOfPrimesIndex = numberOfPrimes / 10;
		this.listOfPrimes = new int[numberOfPrimes + 1];
	}

	public static void main(String[] args) {
		PrintPrimes printPrimes = new PrintPrimes(300, 50, 4);
		printPrimes.calculatePrimes();
		printPrimes.printPrimes();
	}

	/**
	 * Calculates all the first number of prime numbers the user specified in
	 * numberOfPrimes. This function only does one thing (adds 2) and then calls
	 * another function to do the rest of the work. The function puts the
	 * calculated values in the class Array listOfPrimes.
	 */
	public void calculatePrimes() {
		/*
		 * Two is the only even prime. All other prime numbers are odd. To
		 * simplify the code, we simply add 2 as a prime number, and delegate
		 * the task of finding all odd prime numbers to a helper function.
		 */
		listOfPrimes[1] = 2;
		calculateOddPrimes();
	}

	/**
	 * Calculates all the odd prime numbers up to the value the user specified.
	 * Does this in a two step fashion: While only considering odd numbers, it
	 * checks if the prime number is equal to the previous primes square. If so,
	 * its not a prime number. Subsequently, it compares multiples of the
	 * current prime and the square of the previous one with the current prime
	 * number. If they are equal, it is certainly a prime number.
	 */
	private void calculateOddPrimes() {
		boolean isPrime; // contains whether the value is prime or not.
		int tempIndex;
		// this contains multiples of prime numbers, therefore non prime
		// numbers.
		int multipleOfPrimes[] = new int[maxMultipleOfPrimesIndex + 1];

		int numToCheck = 1; // the number were checking
		int indexToFill = 2; // the index of the place were trying to fill
		int lastPrimeSquared = 9; // first square possible is 3, since 2 is
									// already a prime number

		for (int primesFoundSoFar = 2; primesFoundSoFar <= numberOfPrimes; primesFoundSoFar++) {
			do { // until we found a prime number

				// all remaining primes are odd, so add 2 to the next check
				numToCheck = numToCheck + 2;

				/*
				 * If the number were checking equals the square of the last
				 * non-prime number, then the number were checking is definitely
				 * not prime. Add this squared number to the list of
				 * multiplesOfPrime, which contain multiples of prime numbers
				 * that are certainly not prime numbers themselves.
				 */
				if (numToCheck == lastPrimeSquared) {
					indexToFill = indexToFill + 1; // go to next multiple to
													// fill
					lastPrimeSquared = listOfPrimes[indexToFill]
							* listOfPrimes[indexToFill];
					multipleOfPrimes[indexToFill - 1] = numToCheck;
				}

				tempIndex = 2;
				isPrime = true;
				/*
				 * Here we check Checks every multipleOfPrime number found for
				 * the number were looking at, if that number is found, it is
				 * certainly not a prime number. This works on the basis that
				 * the square of a prime numbers can only ever be
				 */
				while (tempIndex < indexToFill && isPrime) {
					while (multipleOfPrimes[tempIndex] < numToCheck) {
						/*
						 * We only add even multiplies of the prime number to
						 * its square because the odd multiples are always even
						 * numbers So for example: 25 + 5 or 15 (5*1 or 5*3) is
						 * always even. This holds for all prime numbers as odd
						 * * odd = even always by mathematics. However, even *
						 * odd = odd always for the same reason. Therefore the
						 * smallest even number (which is a multiple of every
						 * other even number) is 2.
						 */
						multipleOfPrimes[tempIndex] = multipleOfPrimes[tempIndex]
								+ listOfPrimes[tempIndex] * 2;
					}
					/*
					 * if the current value were checking is equal to any of the
					 * multiplesOfPrime calculated before then it is definitely
					 * not prime.
					 */
					if (multipleOfPrimes[tempIndex] == numToCheck)
						isPrime = false;
					// check all indexes of multiple of prime for this.
					tempIndex = tempIndex + 1;
				}
			} while (!isPrime);
			// add this number to list of prime numbers found.
			listOfPrimes[primesFoundSoFar] = numToCheck;
		}
	}

	/**
	 * Prints out the primes that have already been found. Prints it in a row by
	 * column format defined when the user instantiates the PrintPrimes object.
	 * 
	 */
	public void printPrimes() {
		int pageNumber = 1; // the page number currently at
		int startRow = 1; // the index to commence printing this page at

		while (checkIfMorePagesToPrint(startRow)) {
			printThisPage(pageNumber, startRow);
			startRow = startRow + numberOfRows * numberOfColumns;
			pageNumber = pageNumber + 1; // next page
		}
	}

	/**
	 * This function returns if another page must be printed
	 * 
	 * @param primeIndex
	 * @return True if another page must be printed, false if not.
	 */
	private boolean checkIfMorePagesToPrint(int primeIndex) {
		return primeIndex <= numberOfPrimes;
	}

	/**
	 * This function prints the current page, in a pretty column format.
	 * 
	 * @param pageNumber
	 * @param startRow
	 */
	private void printThisPage(int pageNumber, int startRow) {
		// update user
		System.out.println("The First " + numberOfPrimes
				+ " Prime Numbers --- Page " + pageNumber);
		System.out.println("");

		// print each prime number row by row
		int endRow = startRow + numberOfRows; // last row to print
		for (int row = startRow; row <= endRow; row++) {
			// print every column in page
			for (int column = 0; column < numberOfColumns; column++) {
				printThisPrime(row, column);
			}
			System.out.println(""); // next row
		}
		System.out.println(""); // skip a line, to be pretty.
	}

	/**
	 * Prints the current prime number, ignores the prime numbers in the index
	 * that doesn't make sense (indexes that do not exist).
	 * 
	 * @param row
	 * @param column
	 */
	private void printThisPrime(int row, int column) {
		/*
		 * Since only one row can be printed a time, the next value to be
		 * printed must be predicted However, it must be verified this index
		 * actually exists. This does both.
		 */
		int indexToCheck = row + column * numberOfRows;
		// only print if the index to print has actually been calculated.
		if (indexToCheck <= numberOfPrimes) {
			int indexToPrint = row + column * numberOfRows;
			int numberToPrint = listOfPrimes[indexToPrint];
			// %10 formats the output into neat columns which spaces
			// cannot do
			System.out.format("%10d", numberToPrint);
		}
	}
}
