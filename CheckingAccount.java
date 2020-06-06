
public class CheckingAccount extends BankAccount {

	public CheckingAccount(long timeClosed, String name, int pin, double balance, int type) {
		super(timeClosed, name, pin, balance, 1);
		// TODO Auto-generated constructor stub
	}

	protected double calculateNewBalance(long timeClosed, double balance) {
		long timeOpen = System.currentTimeMillis();
		long timePassed = timeOpen - timeClosed;
		long timePassedHours = timePassed / 3600000;
		double newBalance = balance * Math.pow(1.01, timePassedHours);
		return newBalance;
	}

}
