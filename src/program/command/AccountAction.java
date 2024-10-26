package program.command;

import program.bank.BankEntity;

public enum AccountAction {
	
	DEPOSIT, WITHDRAW, TRANSFER, HISTORY, DELETE, QUIT;
	
	
	public static AccountAction selectCommand(String label) {
		
		AccountAction c = null;
		do {
			try {
				c = AccountAction.valueOf(BankEntity.input(label).toUpperCase());
				BankEntity.inputValid();
			} catch (IllegalArgumentException e) {
				System.out.println("Invalid command.\n");
			}
		} while (!BankEntity.valid);
		BankEntity.resetFlag();
		return c;
		
	}
	
	
	
	

}
