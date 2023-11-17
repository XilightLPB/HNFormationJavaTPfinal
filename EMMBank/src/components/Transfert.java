//1.3.3 Creation of the Transfert, Credit, Debit Classes
package components;

public class Transfert extends Flow{

	private int _SourceAccountNumber;
	public Transfert(String comment, double d, int targetAccountNumber, int SourceAccountNumber) {
		super(comment, d, targetAccountNumber);
		_SourceAccountNumber = SourceAccountNumber;
		
	}

	public int getSourceAccount() {
		return _SourceAccountNumber;
	}

	public void setSourceAccount(int SourceAccountNumber) {
		_SourceAccountNumber = SourceAccountNumber;
	}
}
