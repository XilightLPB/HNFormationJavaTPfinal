//1.1.1 Creation of the client class
package components;

public class Client {
	private int _clientCount = 0;
	private String _name;
	private String _firstName;
	private int _clientNumber;
	
	public Client(String name, String firstName) {
		this._firstName = firstName;
		this._name = name;
		this._clientNumber = _clientCount+1;
		_clientCount +=1;
	}
	
	public String getName() {
		return this._name;
	}
	
	public String getFirstName() {
		return this._firstName;
	}
	
	public int getClientNumber() {
		return this._clientNumber;
	}
	
	public void setName(String name) {
		this._name = name;
	}
	
	public void setFirstName(String firstName) {
		this._firstName = firstName;
	}


	public void setClientNumber(int clientNumber) {
		this._clientNumber = clientNumber;
	}
	
	@Override
	public String toString() {
		String res = "Client [name=" + this._name + ", firstName=" + this._firstName + ", clientNumber=" + this._clientNumber + "]";
		return res;
	}

	
}
