package program.user;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import program.command.Confirmation;
import program.customException.NegativeNumberException;

public class Balance {

	private Balance() {
	}
	
	private double currentBalance;

	private Scanner read = new Scanner(System.in);

	public double input(String label) {

		double numericValue = -1;

		System.out.print(label);
		try {
			numericValue = read.nextDouble();
			read.nextLine();

			if (numericValue < 0) throw new NegativeNumberException();
			else if (numericValue == 0) System.err.println("\n0 value Not allowed\n");
			else return numericValue;

		} catch (InputMismatchException e) {
			System.err.println("\nInvalid numeric value\n");
			read.nextLine();
		} catch (NegativeNumberException e) {
			//read.nextLine();
			System.err.println("\nNegative value not allowed\n");
		}

		return numericValue;
	}

	public double getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(double currentBalance) {
		this.currentBalance = currentBalance;
	}

	public static Balance createNew() {
		return new Balance();
	}

	public void withdraw() {

		double withdrawalAmount = input("Amount to withdraw>> ");

		if (withdrawalAmount > 0 && withdrawalAmount <= this.getCurrentBalance()) {

			Confirmation confirmation = Confirmation.selectCommand("CONFIRM | CANCEL>> ", Confirmation.CONTINUE);

			if (confirmation == Confirmation.CONFIRM) {
				setCurrentBalance(getCurrentBalance() - withdrawalAmount);
				addAccountHistory("You withdraw P" + withdrawalAmount);
				System.out.printf("\nYou withdraw P%s\n\n", withdrawalAmount);
			} else System.out.println();

		} else if (withdrawalAmount > this.getCurrentBalance() && withdrawalAmount != -1.0 && withdrawalAmount != 0) {
			System.out.println("\nNot enough balance.\n");
		}


	}

	public void deposit() {

		var depositAmount = input("Amount to deposit>> ");
		
		if (depositAmount > 0) {
			Confirmation conf = Confirmation.selectCommand("CONFIRM | CANCEL >> ", Confirmation.CONTINUE);
			if (conf == Confirmation.CONFIRM) {
				this.setCurrentBalance(this.getCurrentBalance() + depositAmount);
				addAccountHistory("You deposit P" + depositAmount);
				System.out.println("\nBalance updated\n");
			} else System.out.println();
		}
	}


	// Transfer amount of balance to other user account balance
	public void transfer(List<UserAccount> receiverAcc, UserAccount sender) {

		int senderId = sender.getAccountListRef().indexOf(sender);

		int receiverId = 0;
		var receiverAccountFound = false;
		var inputMismatchException = false;

		try {
			System.out.print("Select ID Number of Receiver: ");
			receiverId = read.nextInt();
			read.nextLine();

			for (int i = 0; i < receiverAcc.size(); i++) {
				if (receiverId == i && receiverId != senderId) {
					receiverAccountFound = true;
					break;
				}
			}

		} catch (InputMismatchException e) {
			read.nextLine();
			inputMismatchException = true;
		} 


		if (receiverAccountFound) {

			UserAccount receiverAccount = receiverAcc.get(receiverId);
			String receiverUsername = receiverAccount.getUsername();

			double transferAmount = input("\nEnter amount to Transfer: ");

			if (transferAmount > 0 && transferAmount <= this.getCurrentBalance()) {
				System.out.printf("\nTransfer P%s to Account: | Id no.%s | username: %s | ?\n", transferAmount, receiverId, receiverUsername);
				Confirmation confirmation = Confirmation.selectCommand("CONFIRM | CANCEL >> ", Confirmation.CONTINUE);

				if (confirmation == Confirmation.CONFIRM) {

					double receiverCurrentBalance = receiverAccount.balance.getCurrentBalance();
					this.setCurrentBalance(getCurrentBalance() - transferAmount);
					receiverAccount.balance.setCurrentBalance(receiverCurrentBalance + transferAmount);

					addAccountHistory(String.format("You transfer P%s to account username: %s", transferAmount, receiverUsername));
					receiverAccount.balance.addAccountHistory(String.format("You receive P%s from account username: %s", transferAmount, sender.getUsername()));

					System.out.println("\nTransfer sucessed\n");

				} else System.out.println("\nTransfer canceled\n");
			} else if (transferAmount > this.getCurrentBalance() && transferAmount != -1.0 && transferAmount != 0) {
				System.out.println("\nNot enough balance.\n");
			}
		} 

		else if (inputMismatchException) System.err.println("Id Number must be numeric whole number\n");
		else if (receiverId == senderId) System.err.println("Cannot transfer to your account\n");
		else if (receiverId < 0) System.err.println("Id Number must be positive number\n");
		else System.err.println("Account not found\n");



		//beta

		//		if (tranferAmount > getCurrentBalance()) System.out.println("Not enough balance\n");
		//		if (this.getCurrentBalance() >= tranferAmount) {
		//			user.balance.setCurrentBalance(user.balance.getCurrentBalance() + tranferAmount);
		//			this.setCurrentBalance(getCurrentBalance() - tranferAmount);
		//		}


	}

	private StringBuilder accountHistory = new StringBuilder();

	public void addAccountHistory(String data) {
		accountHistory.append(data + ".\n");
	}

	public void getAccountHistory() {
		System.out.println();
		System.out.println(accountHistory.toString());
	}

}
