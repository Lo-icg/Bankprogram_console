package program.command;

import program.bank.BankEntity;

public enum Selection {
	
	/* command to manage user bank account */
	CREATE, SELECT, EXIT;
	
	//asdddddddddddddddddddddddd
	/* this variable is for input of user as command to interact with this program.
	 * Its class is Enum which have specific constant value designed as a command
	 * and already contains a return type method to pass the specific value with exception handler*/
	
	/* return a specified command e.g. ADD, SELECT, EXIT */
	public static Selection selectCommand(String label) {
		Selection c = null;
		do {
			try {
				c = Selection.valueOf(BankEntity.input(label).toUpperCase());
				BankEntity.inputValid();
			} catch (IllegalArgumentException e) {
				System.out.println("Invalid command.\n");
			}
		} while (!BankEntity.valid);
		BankEntity.resetFlag();
		return c;
	}
	
}
