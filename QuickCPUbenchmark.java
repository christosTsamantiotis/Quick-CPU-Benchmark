class QuickCPUbenchmark{
		
		public static void main (String[] args){
			
			System.out.println("This is a quick program which measures the time needed to accrue interest on a set number of pseudorandomly generated account values for a set amount of periods(years). It scales greatly among physical Performance and Efficiency Cores and uses SMT or HT well. The first argument sets the number of accounts(Recommended 10000000), the second the interest rate, the third the amount of threads to use and the fourth the number of periods(years) to calculate for. The more threads the faster, however speed bumps are determined by your CPUs physical cores. Setting more can hurt performance and may starve other background apps on your PC of resources.");
			
		
			int numberOfAccounts = Integer.parseInt(args[0]);  // Recommended 10000000
			double interestRate = Double.parseDouble(args[1]); // Does not matter
			int numberOfThreads = Integer.parseInt(args[2]);   // The number of threads you want to test
			int numberOfPeriods = Integer.parseInt(args[3]);   // Reccomended 100000
			double[] accounts = new double[numberOfAccounts];
			
			for(int i = 0; i < numberOfAccounts;i++){
				accounts[i] = 1 + (Math.random() * 1000000000);
			}
			
			int block = numberOfAccounts / numberOfThreads;
			int from = 0;
			int to = 0;
			SplitterThread[] workers = new SplitterThread[numberOfThreads]; //splitting the work among the number of threads the user provided
			long startTime = System.currentTimeMillis();
			
			for(int j = 0; j < numberOfThreads; j++){
				from = j * block;
				to = (j * block) + block;
				workers[j] = new SplitterThread(accounts, from, to, interestRate,numberOfPeriods);
				workers[j].start();
				
			}
			
			for(int i = 0; i<numberOfThreads; i++){
				
				try{
					workers[i].join();
					
				}
				catch(InterruptedException e){
					System.out.println("Threads could not be synchronized");
				}
			}
			
			long time = System.currentTimeMillis() - startTime;
			System.out.println("Time needed: " + time + " ms");
			
		}}
		

class SplitterThread extends Thread{
	
	private double[] accounts;
	private int from;
	private int to;
	private double interest;
	private int period;
	
	public SplitterThread(double[] accs, int apo, int ews, double interes, int xronia){
		
		accounts = accs;
		from = apo; 			//Greek translation
		to = ews;				//Greek translation
		interest = interes;
		period = xronia; 		//Greek translation
		
	}
	
	public void run(){
		
		for(int i = from; i < to; i++){
			
			accounts[i] = accounts[i] * interest;
			for(int j = 0; j < period; j++){
				accounts[i] *= interest;
			}
			
		}
	}
}		