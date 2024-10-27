package program.user;

import java.util.List;

import program.command.Confirmation;

public class UserAccount {
	
	private String username;
	private String password;
	private int idNumber;
	
    public Balance balance = Balance.createNew();
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(int idNumber) {
		this.idNumber = idNumber;
	}
	//default constructor like void
	private UserAccount(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	//static constructor, has return type
	public static UserAccount createNew(String username, String password) {
		return new UserAccount(username, password);
	}
	
	private List<UserAccount> accountListRef;

	public List<UserAccount> getAccountListRef() {
		return accountListRef;
	}
	
	public void setAccountListRef(List<UserAccount> accountListRef) {
		this.accountListRef = accountListRef;
	}
	
	public void revokeAccountListRef() {
		setAccountListRef(null);
	}
	
	
	private void removeThisAccountFromList() {
		getAccountListRef().remove(this);
	}
	
	public void deleteAccount() {
		
		System.out.println("\nYour account will delete...");
		Confirmation conf = Confirmation.selectCommand("CONFIRM | CANCEL >> ", Confirmation.CONTINUE);
		//removeThisAccountFromList();
		if (conf.equals(Confirmation.CONFIRM)) {
			System.out.printf("\nAccount username: %s has been deleted\n", this.getUsername());
			removeThisAccountFromList();
			accountHasBeenDeleted = true;
		} else System.out.println();
		
	}
	
	public boolean accountHasBeenDeleted = false;
	
	
}
