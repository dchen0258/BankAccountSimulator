import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import acm.util.ErrorException;
import acm.program.ConsoleProgram;

//Help received from Ethan Chen

public class BankAccountRunner extends ConsoleProgram {
	ArrayList<BankAccount> bankAccounts = new ArrayList<BankAccount>();

	public void run() {
		readFile();
		int action = -1;
		println("Welcome to David Bank.");
		// repeats until someone wants to end the session
		while (action != 0) {
			println();
			action = readInt(
					"If you would like to open a new bank account enter 1, withdraw, press 2, deposit, 3, and check balance, 4. "
							+ "To remove a account, type 5, If you are done with your session enter 0: ");
			if (action == 1) {
				createNewAccount();
			} else if (action == 2) {
				withdraw();
			} else if (action == 3) {
				deposit();
			} else if (action == 4) {
				checkBalance();
			} else if (action == 5) {
				removeAccount();
			}
		}
		writeFile();
		println("Have a good day. Goodbye. ");
	}

	public void createNewAccount() {
		int type = readInt("If you a checking account enter 1, or savings, enter 2?: ");
		String name = readLine("What do you want to name your account: ");
		int pin = readInt("What do you want your pin to be? Please enter an integer: ");
		BankAccount currAccount = null;

		// checks if the type + name + pin combination is already taken
		for (BankAccount act : bankAccounts) {
			if (act.getName().equals(name) && act.getPin() == pin && act.getType() == type) {
				currAccount = act;
			}
		}
		while (currAccount != null) {
			println("Sorry. This type + name + pin combination is already taken. Try another. ");
			type = readInt("If you want a checking account enter 1, or savings, enter 2?: ");
			name = readLine("What do you want to name your account: ");
			pin = readInt("What do you want your pin to be? Please enter an integer: ");
			currAccount = null;
			for (BankAccount act : bankAccounts) {
				if (act.getName().equals(name) && act.getPin() == pin && act.getType() == type) {
					currAccount = act;
				}
			}
		}
		double balance = readDouble("What do you want to deposit: ");
		long timeClosed = System.currentTimeMillis();
		if (type == 1) {
			CheckingAccount DAChecking = new CheckingAccount(timeClosed, name, pin, balance, 1);
			bankAccounts.add(DAChecking);
		} else {
			SavingsAccount DASavings = new SavingsAccount(timeClosed, name, pin, balance, 2);
			bankAccounts.add(DASavings);
		}

	}

	public void withdraw() {
		String name = readLine("what is the name of your account: ");
		int type = readInt("If it is a checking account enter 1, if savings, enter 2: ");
		int pin = readInt("Please type in your pin: ");
		int withdrawalAmount = readInt("How much would do want to withdraw?: ");

		// finds account
		BankAccount currAccount = null;
		for (BankAccount act : bankAccounts) {
			if (act.getName().equals(name) && act.getPin() == pin && act.getType() == type) {
				currAccount = act;
			}
		}
		if (currAccount == null) {
			println("Sorry this account doesn't exist");
		} else {
			long timeClosed = currAccount.getTimeClosed();
			double balance = currAccount.getBalance();
			currAccount.setBalance(currAccount.withdraw(withdrawalAmount, timeClosed, balance));
			println("Your account's balance is " + currAccount.getBalance());
			currAccount.setTimeClosed(System.currentTimeMillis());
		}
	}

	public void deposit() {
		String name = readLine("what is the name of your account: ");
		int type = readInt("If you want to access a checking account enter 1, if savings, enter 2: ");
		int pin = readInt("Please type in your pin: ");
		int depositAmount = readInt("How much would do want to deposit?: ");

		BankAccount currAccount = null;
		for (BankAccount act : bankAccounts) {
			if (act.getName().equals(name) && act.getPin() == pin && act.getType() == type) {
				currAccount = act;
			}
		}
		if (currAccount == null) {
			println("Sorry this account doesn't exist");
		} else {
			long timeClosed = currAccount.getTimeClosed();
			double balance = currAccount.getBalance();
			currAccount.setBalance(currAccount.deposit(depositAmount, timeClosed, balance));
			println("Your account's balance is " + currAccount.getBalance());
			//resets time closed
			currAccount.setTimeClosed(System.currentTimeMillis());
		}
	}

	public void checkBalance() {
		String name = readLine("what is the name of your account: ");
		int type = readInt("If you want to access a checking account enter 1, if savings, enter 2: ");
		int pin = readInt("Please type in your pin: ");

		BankAccount currAccount = null;
		for (BankAccount act : bankAccounts) {
			if (act.getName().equals(name) && act.getPin() == pin && act.getType() == type) {
				currAccount = act;
			}
		}
		if (currAccount == null) {
			println("Sorry this account doesn't exist");
		} else {
			long timeClosed = currAccount.getTimeClosed();
			double balance = currAccount.getBalance();
			currAccount.setBalance(currAccount.calculateNewBalance(timeClosed, balance));
			println("Your account's balance is " + currAccount.getBalance());
			currAccount.setTimeClosed(System.currentTimeMillis());
		}
	}

	public void removeAccount() {
		String name = readLine("what is the name of your account: ");
		int type = readInt("If you want to access a checking account enter 1, if savings, enter 2: ");
		int pin = readInt("Please type in your pin: ");

		BankAccount currAccount = null;
		for (BankAccount act : bankAccounts) {
			if (act.getName().equals(name) && act.getPin() == pin && act.getType() == type) {
				currAccount = act;
			}
		}
		if (currAccount == null) {
			println("Sorry this account doesn't exist");
		} else {
			bankAccounts.remove(currAccount);
		}
	}

	public void writeFile() {

		PrintWriter writer;

		try {
			writer = new PrintWriter(new FileWriter("BankAccounts"));
		} catch (IOException ex) {
			throw new ErrorException(ex);
		}

		for (int bankAccountsIndex = 0; bankAccountsIndex < bankAccounts.size(); bankAccountsIndex++) {
			writer.println(bankAccounts.get(bankAccountsIndex).getType());
			writer.println(bankAccounts.get(bankAccountsIndex).getName());
			writer.println(bankAccounts.get(bankAccountsIndex).getPin());
			writer.println(bankAccounts.get(bankAccountsIndex).getTimeClosed());
			writer.println(bankAccounts.get(bankAccountsIndex).getBalance());
		}
		writer.close();
	}

	public void readFile() {
		try {
			BufferedReader rd = new BufferedReader(new FileReader("BankAccounts"));
			String line = rd.readLine();
			while (line != null) {
				CheckingAccount DAChecking;
				SavingsAccount DASavings;
				int type = Integer.valueOf(line);
				String name = rd.readLine();
				int pin = Integer.valueOf(rd.readLine());
				long timeClosed = Long.valueOf(rd.readLine());
				double balance = Double.valueOf(rd.readLine());
				if (type == 1) {
					DAChecking = new CheckingAccount(timeClosed, name, pin, balance, type);
					bankAccounts.add(DAChecking);
				} else {
					DASavings = new SavingsAccount(timeClosed, name, pin, balance, type);
					bankAccounts.add(DASavings);
				}

				line = rd.readLine();
			}

			rd.close();

		} catch (FileNotFoundException e) {

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
