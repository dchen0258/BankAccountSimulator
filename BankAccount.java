import java.util.ArrayList;

public abstract class BankAccount {

	private long timeClosed;
	private String name;
	private int pin;
	private double balance;
	private int type;

	public BankAccount(long timeClosed, String name, int pin, double balance, int type) {
		this.timeClosed = timeClosed;
		this.name = name;
		this.pin = pin;
		this.balance = balance;
		this.type = type;
	}

	public long getTimeClosed() {
		return timeClosed;
	}

	public void setTimeClosed(long timeClosed) {
		this.timeClosed = timeClosed;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	protected abstract double calculateNewBalance(long timeClosed, double balance);

	// finds new balance after withdrawing/depositing, including interest
	public double withdraw(int withdrawalAmount, long timeClosed, double balance) {
		balance = calculateNewBalance(timeClosed, balance);
		balance -= withdrawalAmount;
		return balance;
	}

	public double deposit(int depositAmount, long timeClosed, double balance) {
		balance = calculateNewBalance(timeClosed, balance);
		balance += depositAmount;
		return balance;
	}
}
