public class PrintPrimes {
	int numberOfPrimes; // the total number of primes to be found
	int numberOfRows; // the number of rows to be printed per page
	int numberOfColumns; // the number of columns to print.
	int maxMultipleOfPrimesIndex;
	int listOfPrimes[];

	// constructor
	public PrintPrimes(int numberOfPrimes, int numberOfRows,
			int numberOfColumns, int ORDMAX) {
		this.numberOfPrimes = numberOfPrimes;
		this.numberOfRows = numberOfRows;
		this.numberOfColumns = numberOfColumns;
		this.maxMultipleOfPrimesIndex = ORDMAX;
		this.listOfPrimes = new int[numberOfPrimes + 1];
	}

	public static void main(String[] args) {
		PrintPrimes printPrimes = new PrintPrimes(300, 50, 4, 30);
		printPrimes.calculatePrimes();
		printPrimes.printPrimes();
	}

	public void calculatePrimes() {
		/*
		 * Two is the only even prime. All other prime numbers are odd. To
		 * simplify the code, we simply add 2 as a prime number, and delegate
		 * the task of finding all odd prime numbers to a helper function.
		 */
		listOfPrimes[1] = 2;
		calculateOddPrimes();
	}

	private void calculateOddPrimes() {
		boolean isPrime;
		int tempIndex;
		int multipleOfPrimes[] = new int[maxMultipleOfPrimesIndex + 1];

		int numToCheck = 1; // the number were checking
		int indexToFill = 2; // the index of the place were trying to fill
		int lastPrimeSquared = 9; // first square possible is 3, since 2 is
									// already a prime number

		for (int primesFoundSoFar = 2; primesFoundSoFar <= numberOfPrimes; primesFoundSoFar++) {
			do {
				// prime numbers are only ever odd, so add 2 to the next check
				numToCheck = numToCheck + 2;
				/*
				 * If the number were checking equals the square of the last
				 * non-prime number, Then the number were checking is definitely
				 * not prime. Go to the next number to check.
				 */
				if (numToCheck == lastPrimeSquared) {
					indexToFill = indexToFill + 1; // go to next number
					lastPrimeSquared = listOfPrimes[indexToFill]
							* listOfPrimes[indexToFill];
					multipleOfPrimes[indexToFill - 1] = numToCheck;
				}

				tempIndex = 2;
				isPrime = true;
				/*
				 * Checks if the number were looking at is prime or not
				 */
				while (tempIndex < indexToFill && isPrime) {
					/*
					 * This will take lastPrimeSquared and add to it twice the
					 * multiple of the current Prime This produces a non-prime
					 * number If the number were looking at is
					 */
					while (multipleOfPrimes[tempIndex] < numToCheck) {
						multipleOfPrimes[tempIndex] = multipleOfPrimes[tempIndex]
								+ listOfPrimes[tempIndex]
								+ listOfPrimes[tempIndex];
					}
					if (multipleOfPrimes[tempIndex] == numToCheck)
						isPrime = false;
					tempIndex = tempIndex + 1;
				}
			} while (!isPrime);
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
		/* Since only one row can be printed a time, the next value to be
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
