package program.bank;

import java.util.List;
import java.util.Scanner;

import program.bank.login.Account;
import program.command.Confirmation;
import program.thread.Mythread;
import program.user.UserAccount;

public class BankEntity  {

	//flag variable
	/*----------------------------------*/
	public static boolean valid = false;
	public static void inputValid() {
		valid = true;
	}
	public static void resetFlag() {
		valid = false;
	}
	/*----------------------------------*/

	/* I made this function to custom input like userInput = read.nextLine(); 
	 * this method has a parameter designed as a label and returns a String value as an inputStreamLine
	 */
	public static String input(String label) {
		String userInput;
		System.out.print( (label == null) ? "" : label );

		/* if user press enter without any value, this loop will continue; */
		do {
			userInput = read.nextLine();
			if (!userInput.equals("")) inputValid();
		} while(!valid);

		resetFlag();
		return userInput;
	}
    
	
	// display user account in a table
	private void displayInTable(List<UserAccount> userList) {

		var longestUsernameSize = userList.get(0).getUsername().length();

		for (int i = 0; i < userList.size() - 1; i++) {

			if (userList.get(i + 1).getUsername().length() > longestUsernameSize) {
				longestUsernameSize = userList.get(i + 1).getUsername().length();
			}
		}

		StringBuilder format = new StringBuilder();

		String field1 = align("Id no", 5, false);
		String field2 = align("username", (longestUsernameSize > 8) ? longestUsernameSize : 8, true);

		String border = Account.generateBorder(field1, '+', '-', '-').toString() + 
				Account.generateBorder(field2, '+', '-', '+').toString() + "\n";

		format.append(border);
		format.append(field1).append(field2).append("\n");
		format.append(border);

		int i = 0;

		for (UserAccount usr : userList) {
			format.append(align(String.valueOf(i), 5, false));
			format.append(align(usr.getUsername(), (longestUsernameSize > 8) ? longestUsernameSize : 8, true));
			format.append("\n");
			i++;
		}

		format.append(border);
		System.out.print(format.toString());
	}

    // return a new format of String with a fixed size for the border alignment
	private String align(String text, int sizeBase, boolean addBorderAtTheEnd) {

		StringBuilder format = new StringBuilder();

		format.append("| ");

		for (int j = 0; j < sizeBase; j++) {

			try {
				format.append(text.charAt(j));
			} catch (Exception e) {
				format.append(" ");
			}
		}

		if (addBorderAtTheEnd) format.append(" |");
		else format.append(" ");

		return format.toString();
	}

	/* show account list, this method invoked in menu(List<UserAccount> users) method */
	private void showUserAccountList(List<UserAccount> usersAcc) {
		if (usersAcc.size() != 0) {
			displayInTable(usersAcc);
			System.out.println();
		} else System.out.println("No User Bank Account created\n");

	}

	private static Scanner read = new Scanner(System.in);

	/* display menu selection with list of user account */
	protected void menu(List<UserAccount> users) {
		System.out.println("Account list:");
		showUserAccountList(users);
	}

	/* method to add account that stores in reference parameter */
	protected void add(List<UserAccount> users) {

		String username = null, password = null;

		do {
			username = input("\nCreate username: ");
			if (!Character.isAlphabetic(username.charAt(0))) {
				System.out.println("Username must begin with alphabetical letter");
			} else inputValid();
		} while (!valid);

		resetFlag();

		password = input("Create password: ");
		System.out.println();

		/*The first parameter is a label that prompts a user input,
		 * the second is a Constant Value of Enum which it exclude to the selection*/
		Confirmation confirmation = Confirmation.selectCommand("CONFIRM | CANCEL>> ", Confirmation.CONTINUE);

		if (confirmation == Confirmation.CONFIRM) {
			System.out.print("Account creating ");
			Mythread.call(); //thread ....just design
			System.out.println("created\n");
			users.add(UserAccount.createNew(username, password)); // add account in List
		} else System.out.println();

	}

	// this method select specific account and direct to account log in if account exist
	// if bank account list is empty nothing will execute in this method
	protected void select(List<UserAccount> user) {

		int id; // select account is through id number i.e, index number to simplified
		System.out.println();

		int attempt = 2; // number of user input attempt in case of invalid
		var userIdFound = false;

		if (!user.isEmpty()) {
			do {
				try {
					id = Integer.valueOf(input("Select Id Number>> "));

					if (id >= 0 && id < user.size()) {

						Account.login(user, id); // direct to log in user bank account method
						userIdFound = true;

					} else throw new IndexOutOfBoundsException();

				} catch (NumberFormatException e) {
					System.out.println("Invalid Input. Try Again\n");
					attempt--;
				} catch (IndexOutOfBoundsException e) {
					System.out.println("Account Not Exist\n");
					attempt--;
				}
				
				/* this will bypass if attempt not reaches to 0,
				 * otherwise it will execute and prompt user to continue or cancel*/
				if (attempt == 0) {
					Confirmation c = Confirmation.selectCommand("CONTINUE | CANCEL>> ", Confirmation.CONFIRM);

					if (c == Confirmation.CONTINUE) {
						System.out.println();
						attempt += 2;
					} else {
						System.out.println();
						break;
					}
				}

			} while (!userIdFound);
		}
	}

	//terminate program
	protected boolean terminate() {
		return true;
	}


}
