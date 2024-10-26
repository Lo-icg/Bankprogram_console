package program.command;

import program.bank.BankEntity;

public enum Confirmation {
	
	/* command as confirmation */
	CONFIRM, CONTINUE, CANCEL;
	
	/* return a specified command e.g. CONFIRM, CANCEL */
	public static Confirmation selectCommand(String label, Confirmation ex) {
		Confirmation c = null;
		do {
			try {
				c = Confirmation.valueOf(BankEntity.input(label).toUpperCase());
				
				if (c == ex) throw new IllegalArgumentException();
				else BankEntity.inputValid();
				
			} catch (IllegalArgumentException e) {
				System.out.println("Invalid command.\n");
			}
		} while (!BankEntity.valid);
		BankEntity.resetFlag();
		return c;
	}
	
	/* return a specified command e.g. CONFIRM, CANCEL */
	public static Confirmation selectCommand(String label) {
		return selectCommand(label, null);
	}


}
