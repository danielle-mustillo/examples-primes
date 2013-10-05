public class PrintPrimes {
  int numberOfPrimes;
  int numberOfRows;
  int numberOfColumns;
  int WW;
  int ORDMAX;
  int listOfPrimes[];

  public PrintPrimes(int numberOfPrimes, int numberOfRows, int numberOfColumns, int WW, int ORDMAX) {
    this.numberOfPrimes   = numberOfPrimes;
    this.numberOfRows  = numberOfRows;
    this.numberOfColumns  = numberOfColumns;
    this.WW  = WW;
    this.ORDMAX = ORDMAX;
    this.listOfPrimes = new int[numberOfPrimes + 1];
  }


  public static void main(String[] args) {
      PrintPrimes printPrimes = new PrintPrimes(300, 50, 4, 10, 30);
      printPrimes.calculatePrimes();
      printPrimes.printPrimes();
  }

  public void calculatePrimes() {
      /* Two is the only even prime. All other prime numbers are odd.
       * To simplify the code, we simply add 2 as a prime number, and
       * delegate the task of finding all odd prime numbers to a helper
       * function.
       */
      listOfPrimes[1] = 2;
      calculateOddPrimes();
  }

  private void calculateOddPrimes() {
      boolean isNotPrime;
      int N;
      int MULT[] = new int[ORDMAX + 1];

      int numToCheck = 1; // the number were checking
      int indexOfNum = 2; // the index of the number were checking.
      int numToCheckSquared = 9; // first square possible is 3, since 2 is already a prime number

      for(int primesFoundSoFar = 1; primesFoundSoFar <= numberOfPrimes; primesFoundSoFar++) {
        do {
          numToCheck = numToCheck + 2;
          /* If the number were checking equals the square of the last non-prime number, 
           * Then the number were checking is definitely not prime.
           * Go to the next number to check.
           */
          if (numToCheck == numToCheckSquared) { 
            indexOfNum = indexOfNum + 1; //go to next number
            numToCheckSquared = listOfPrimes[indexOfNum] * listOfPrimes[indexOfNum];
            MULT[indexOfNum - 1] = numToCheck;
          }
          
          N = 2;
          isNotPrime = true;
          while (N < indexOfNum && isNotPrime) {
            while (MULT[N] < numToCheck)
              MULT[N] = MULT[N] + listOfPrimes[N] + listOfPrimes[N];
            if (MULT[N] == numToCheck)
              isNotPrime = false;
            N = N + 1;
          }
        } while (!isNotPrime);
        listOfPrimes[primesFoundSoFar] = numToCheck;
      }
    }

  
    public void printPrimes() {
        int pageNumber = 1; // the page number currently at
        int primeIndex = 1; // the index to commence printing this page at
        
        while (primeIndex <= numberOfPrimes) {
        	//update user
          System.out.println("The First " + numberOfPrimes + " Prime Numbers --- Page " + pageNumber);
          System.out.println("");
          
          //print each prime number column by column.
          for (int row = primeIndex; row <= primeIndex + numberOfRows; row++) { // starting at row 1, print every row in page.
        	  for (int column = 0; column < numberOfColumns; column++) { //print every column in page
        		  
        		  /* Since only one row can be printed a time, the next value to be printed must be predicted
    			   * However, it must be verified this index actually exists
    			   */
        		  if (row + column * numberOfRows <= numberOfPrimes) { // check if index exists, if so print it
        			  int indexToPrint = row + column * numberOfRows;
        			  int primeToPrint = listOfPrimes[indexToPrint];
        			  //%10 formats the output into neat columns which spaces cannot do
        			  System.out.format("%10d", primeToPrint); 
        		  }
        	  }
        	  System.out.println(""); // skip a line, to be pretty.
          }
          System.out.println(""); // skip a line, to be pretty.
          primeIndex = primeIndex + numberOfRows * numberOfColumns; 
          pageNumber = pageNumber + 1; // next page
                 
        }
    }
}

					 
