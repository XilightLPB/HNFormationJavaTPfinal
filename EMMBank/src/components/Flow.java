//1.3.2 creation of the flow class
package components;

import java.time.LocalDate;

public abstract class Flow {

	private String _comment;
	private int _identifier;
	private double _amount;
	private int _targetAccountNumber;
	private boolean _effect = false;
	private LocalDate _date;
	
	public Flow(String comment, double d, int targetAccountNumber) {
		_comment = comment;
		_amount = d;
		_targetAccountNumber = targetAccountNumber;
		_date = LocalDate.now().plusDays(2);
	}
	
	
	public String getComment() {
		return _comment;
	}

	public double getAmount() {
		return _amount;
	}
	
	public int getIdentifier() {
		return _identifier;
	}
	
	public int getTargetAccountNumber() {
		return _targetAccountNumber;
	}
	
	public boolean getEffect() {
		return _effect;
	}
	
	public LocalDate getDate() {
		return _date;
	}
	
	public void setComment(String comment) {
		_comment = comment;
	}


	public void setAmount(double amount) {
		_amount = amount;
	}
	
	public void setIdentifier(int id) {
		_identifier = id;
	}

	public void setTargetAccountNumber(int targetAccountNumber) {
		_targetAccountNumber = targetAccountNumber;
	}


	public void setEffect(boolean effect) {
		_effect = effect;
	}
	
	



	
}
