package Tests;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import components.Account;
import components.Client;
import components.Credit;
import components.CurrentAccount;
import components.Debit;
import components.Flow;
import components.SavingsAccount;
import components.Transfert;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


public class Main {
	
	
	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
		//1.1.2 Creation of Main class for tests
		int HowManyClients = 5;
		ArrayList<Client> ClientsList = CreateClientsList(HowManyClients);
		DisplayClients(ClientsList);
		
		//1.2.3 Creation of the tablea account
		ArrayList<Account> AccountsList = generateAccounts(ClientsList);
		displayAccounts(AccountsList);
		
		
		//1.3.1 Creation of Hashtable
		HashMap<Integer, Account> hashAccount = generateHashAccount(AccountsList);
		displaySortedHash(hashAccount);
		//The method sorted(Comparator<? super Map.Entry<Integer,Account>>) in the type Stream<Map.Entry<Integer,Account>> is not applicable for the arguments (Comparator<Map.Entry<Object,Comparable<? super Comparable<? super V>>>>)
		
		//1.3.4 Creation of the flow array
		ArrayList<Flow> FlowsList = new ArrayList<Flow>();
		FlowsList = LoadFlowsList(AccountsList); 
		
		//1.3.5 Update accounts
		updateAccounts(hashAccount,FlowsList);
		displaySortedHash(hashAccount);

		
		
		
		//Bugged from now on
		//2.1 JSON file of flows and accounts from an xml file
		
		//Bugged because of imports issues that I failed to fix
		//final String JSONpath = "src/Tests/tests.json";
		//ArrayList<Flow> FlowsList2 = createFlowsFromJSON(JSONpath);
		
		//Bugged because for some reason it returns a single account and not a list of accounts
		//final String XMLpath = "src/Tests/tests.xml";
		//ArrayList<Account> AccountsList2 = createAccountsFromXML(XMLpath);
		
	}
	
	
	//1.1.2 Creation of Main class for tests
	private static ArrayList<Client> CreateClientsList(int HowMany){
		ArrayList<Client> res = new ArrayList<Client>();
		
		for (int i = 0; i<HowMany; i++) 
		{
			res.add(new Client("name" + i, "firstname" + i));
		}
		
		return res;
	}
	
	
	private static void DisplayClients(ArrayList<Client> ClientsList) 
	{
		ClientsList.forEach(System.out::println);
	}
	
	//1.2.3 Creation of the tablea account
	private static ArrayList<Account> generateAccounts(ArrayList<Client> ClientsList) {
		ArrayList<Account> AccountsList = new ArrayList<>();
		
		for (Client client : ClientsList) 
		{
			AccountsList.add(new SavingsAccount("Saving account client" + client.getClientNumber(), client));
			AccountsList.add(new CurrentAccount("Current account client" + client.getClientNumber(), client));
		}
		
		return AccountsList;
	}
	
	private static void displayAccounts(ArrayList<Account> AccountsList) 
	{
		AccountsList.forEach(System.out::println);
	}
	
	//1.3.1 Managing of Hashtable and printing them, sorted by balance's value
	private static HashMap<Integer, Account> generateHashAccount(ArrayList<Account> AccountsList) {
		HashMap<Integer, Account> hashtable = new HashMap<>();

		for (Account account : AccountsList) 
		{
			hashtable.put(account.getAccountNumber(), account);
		}

		return hashtable;
	}

	
	private static void displaySortedHash(HashMap<Integer, Account> hashAccount) {
		
		List<Entry<Integer, Account>> sorted = hashAccount.entrySet().stream().sorted((a,b)->Double.compare(a.getValue().getBalance(), b.getValue().getBalance()))
				.toList();

		for (Entry<Integer, Account> entry : sorted) {
			System.out.println(entry.getValue());
		}

	}
	
	//1.3.4 Creation of the flows array
	private static ArrayList<Flow> LoadFlowsList(ArrayList<Account> AccountsList) {
		ArrayList<Flow> res = new ArrayList<Flow>();
		res.add(new Debit("a debit of 50€ from account n°1", 50, 1));
		
		for (int i = 0; i<(AccountsList.size()); i++ ) {
		
			if (AccountsList.get(i) instanceof CurrentAccount) 
			{
				res.add(new Credit("a credit of 100.50€ on all current accounts in the array of accounts", 100.50, i));
			}
			
			else if (AccountsList.get(i) instanceof SavingsAccount) 
			{
				res.add(new Credit("a credit of 1500€ on all current accounts in the array of accounts", 1500, i));
			}
		}
		
		res.add(new Transfert("A transfer of 50€ from account n1 to account n2", 50, 2, 1));
		
		
		return res;
		
	}
	
	//Update accounts
	private static HashMap<Integer, Account> updateAccounts(HashMap<Integer, Account> hashAccount, ArrayList FlowsList){
		for (int i = 0; i< FlowsList.size(); i++ ) {
			Flow CurrentElement = (Flow) FlowsList.get(i);
			Account account = hashAccount.get(CurrentElement.getTargetAccountNumber());
			account.setBalance(CurrentElement);
			//checking that the account doesn't have a balance below 0
			if (account.getBalance()<0) {
				System.out.println("Account number " + i + " got his balance below 0");
			}
			
			hashAccount.put(account.getAccountNumber(), account);
			
			if (CurrentElement instanceof Transfert) 
			{
				Transfert trans = (Transfert) CurrentElement;
				Account SourceAccount = hashAccount.get(trans.getSourceAccount());
				SourceAccount.setBalance(trans);
				
				//checking that the sending account isn't now with a balance below 0
				if (SourceAccount.getBalance()<0) {
					System.out.println("Account number " + SourceAccount.getAccountNumber() + " got his balance below 0");
				}
				hashAccount.put(SourceAccount.getAccountNumber(), SourceAccount);
			}
		}
		
		return hashAccount;
	}
	
	/* Bugged from here
	//2.1 JSON file to flows
	private static ArrayList<Flow> createFlowsFromJSON(String path) throws IOException {

		// Get JSON file from path
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		String json = new String(encoded, StandardCharsets.UTF_8);
		
		//Trying to fill the flow array from it
		ArrayList <Flow> res = new ArrayList<Flow>();
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		res = mapper.readerForListOf(Flow.class).readValue(json);
		
		
		
		return res;
	}
	*/
	
	//2.2 XML to account
	private static ArrayList<Account> createAccountsFromXML(String path)
			throws ParserConfigurationException, SAXException, IOException 
	{
		ArrayList<Account> accounts = new ArrayList<Account>();

		// Get the file
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = builder.parse(new File(path));
		doc.getDocumentElement().normalize();

		
		// List of the accounts to create
		NodeList nodeList = doc.getElementsByTagName("account");
		int n = nodeList.getLength();
		Node current;
		
		// iterate over the list of accounts to creates and call the account and client creation function
		for (int i = 0; i < n; i++) 
		{
			current = nodeList.item(i);
			
			if (current.getNodeType() == Node.ELEMENT_NODE && "account".equals(current.getNodeName())) 
			{
				Node type = current.getAttributes().getNamedItem("type");
				accounts.add(createAccountFromNode(current, type.getNodeValue()));
			}
		}
		return accounts;
	}
	
	private static Account createAccountFromNode(Node node, String type) 
	{	
		NodeList nodeList = node.getChildNodes();
		int n = nodeList.getLength();
		Node current;
		
		
		//Creation of the client
		String label = null;
		Client client = new Client(null, null);
		for (int i = 0; i < n; i++) 
		{
			current = nodeList.item(i);
			if (current.getNodeType() == Node.ELEMENT_NODE) 
			{
				if ("label" == (current.getNodeName())) 
				{
					label = current.getTextContent();
				}
				
				else if ("name" == (current.getNodeName())) 
				{
					client.setName(current.getTextContent());
				}
				
				else if ("firstname" == (current.getNodeName())) 
				{
					client.setFirstName(current.getTextContent());
				}
			}
		}
		Account res = null; //will be assigned latter
		//Now create the account with the created client
		if (type == "currentAccount") 
		{
			res = new CurrentAccount(label, client);
		}
		else if (type == "savingsAccount"){
			res =  new SavingsAccount(label, client);
		}
		else if (type != "savingsAccount" && type!="currentAccount")	
		{
		throw new IllegalArgumentException("Unexpected value: " + type); 
		}
		return res;
		
	}
	
	

}