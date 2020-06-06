
public class SavingsAccount extends BankAccount {

	public SavingsAccount(long timeClosed, String name, int pin, double balance, int type) {
		super(timeClosed, name, pin, balance, 2);
		// TODO Auto-generated constructor stub
	}

	protected double calculateNewBalance(long timeClosed, double balance) {
		long timeOpen = System.currentTimeMillis();
		long timePassed = timeOpen - timeClosed;
		
		//converting milliseconds into hours
		long timePassedHours = timePassed / 3600000;
		double newBalance = balance * Math.pow(1.01, timePassedHours);
		return newBalance;
	}

}
