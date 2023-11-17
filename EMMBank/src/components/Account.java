//1.2.1 Creation of the account class
package components;

//2.2 XML to file
@XmlRootElement(name = "Account")
@XmlType(propOrder = { "label", "client" })
//end of XML to file


public class Account {
	protected String label;
	protected double balance = 0;
	protected int accountNumber;
	protected static int count = 0;
	protected Client client;
	
	protected Account(String label, Client client) {
		this.label = label;
		this.client = client;
		this.accountNumber = count++;
	}
	
	public String getLabel() {
		return label;
	}
	
	public double getBalance() {
		return balance;
	}

	public int getAccountNumber() {
		return this.accountNumber;
	}

	public Client getClient() {
		return client;
	}

	@XmlAttribute(name = "label")
	public void setLabel(String label) {
		this.label = label;
	}
	
	//here I will make modification later
	public void setBalance(Flow flow) {
		double Amount = flow.getAmount();
		
		if (flow instanceof Credit) 
		{
		this.balance += Amount;
		}
		
		else if (flow instanceof Debit) {
			this.balance -= Amount;
		}
		else if (flow instanceof Transfert) 
		{
			
			if (flow.getTargetAccountNumber() == this.getAccountNumber())
			{
				this.balance += Amount;
			}
			
			else if (((Transfert) flow).getSourceAccount() == this.getAccountNumber()) 
			{
				this.balance -= Amount;
			}
		}
			
		
	}
	
	public void setAccountNumber(int NewNumber) {
		this.accountNumber = NewNumber;
	}
	
	@XmlAttribute(name = "client")
	public void setClient(Client client) {
		this.client = client;
	}
	
	@Override
	public String toString() {
		
		return "Compte N:" + this.accountNumber + "Solde: " + this.balance + " label: " + this.label;
	}

	
}
