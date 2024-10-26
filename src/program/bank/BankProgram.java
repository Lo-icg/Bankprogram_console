package program.bank;

import java.util.LinkedList;
import java.util.List;

import program.command.Selection;
import program.user.UserAccount;

public class BankProgram extends BankEntity {

	// stores users bank account
	private List<UserAccount> userBankAccount = new LinkedList<>();

	// stores specific command for specified selection 
	Selection command;

	private BankProgram() {
		var terminated = false;
		
		while (!terminated) {
			
			menu(userBankAccount); // display all current user bank account
			command = Selection.selectCommand("CREATE | SELECT | EXIT >> "); // returns the specified command

			switch (command) { // the following argument pass the userBankAccount as a reference to modify changes
			case Selection.CREATE -> add(userBankAccount);     // direct to add() method 
			case Selection.SELECT -> select(userBankAccount);  // direct to select()
			case Selection.EXIT   -> terminated = terminate(); // exit bank program
			}
		}
	}

	// run a Bank Program
	public static void run() {	
		new BankProgram();	
	}
}
