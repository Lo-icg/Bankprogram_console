package program.bank.login;

import java.util.List;

import program.bank.BankEntity;
import program.command.AccountAction;
import program.command.Confirmation;
import program.user.UserAccount;

public class Acccount {

	private Acccount() {
		super();
	}


	public static void login(List<UserAccount> userLogin, int indexNum) {

		UserAccount user = userLogin.get(indexNum);
		var passwordMatched = false;

		do {
			System.out.println("Username: " + user.getUsername());
			String password = BankEntity.input("Password: ");

			if (user.getPassword().equals(password)) {
				passwordMatched = true;
				System.out.println();
				
				user.setAccountListRef(userLogin);
				bankAccountLogin(user);

			} else {
				System.out.println("\nPassword not matched\n");
				Confirmation option = Confirmation.selectCommand("CONTINUE | CANCEL >> ", Confirmation.CONFIRM);
				if (option == Confirmation.CANCEL) break;
				else System.out.println();
			}

		} while (!passwordMatched);

	}

	public static void bankAccountLogin(UserAccount user) {

		var terminated = false;

		while (!terminated) {

			printDetails(user);

			AccountAction command = AccountAction.selectCommand("DEPOSIT | WITHDRAW | TRANSFER | HISTORY | DELETE | QUIT >> ");

			switch (command) {
			case AccountAction.DEPOSIT  -> user.balance.deposit();
			case AccountAction.WITHDRAW -> user.balance.withdraw();
			case AccountAction.TRANSFER -> user.balance.transfer(user.getAccountListRef(), user);
			case AccountAction.HISTORY  -> user.balance.getAccountHistory();
			case AccountAction.DELETE   -> user.deleteAccount();
			case AccountAction.QUIT     -> terminated = true;
			}
			
			if (user.accountHasBeenDeleted) break;

		}
		user.revokeAccountListRef();
		System.out.println();
	}

	public static StringBuilder generateBorder(String borderLength, char leftmost, char center, char rightmost) {

		StringBuilder str = new StringBuilder();
		for (int i = 0; i < borderLength.length(); i++) {
			if (i == 0) str.append(leftmost);
			else if (i == borderLength.length() - 1) str.append(rightmost);
			else str.append(center);
		}
		return str;
	}

	public static void printDetails(UserAccount user) {

		StringBuilder formatInfo = new StringBuilder();

		String userNameInfo = String.format("username: %s | ", user.getUsername());
		String userBalanceInfo = String.format("Balance %s%s | ", (user.balance.getCurrentBalance() == 0) ? "" : "P", user.balance.getCurrentBalance());
		StringBuilder border = new StringBuilder(generateBorder(userNameInfo, '+', '-', '+').append(generateBorder(userBalanceInfo, '-', '-', '+')));

		formatInfo.append("Account\n" + border + "\n|" + userNameInfo + userBalanceInfo + "\n" + border);
		System.out.println(formatInfo.toString() + "\n");

		//		accountInfo.append("Account\n");
		//		accountInfo.append(border);
		//		accountInfo.append("\n|" + userNameInfo + userBalanceInfo + "\n");
		//		accountInfo.append(border);
	}




}
