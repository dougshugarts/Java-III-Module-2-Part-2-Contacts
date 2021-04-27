package GuestBook;

import java.util.ArrayList;

public class Contact implements Comparable <Contact>, AutoCloseable {

	private String firstName, lastName, addressOne, phoneNumber;
	
	
	public Contact () {
		
		this("undefined","undefined","undefined","undefined");
	}
	
	public Contact (String first, String last, String address1, String phone) {
		
		try {
			
			this.validateNameInput(first, last);
			this.setAddressOne(address1);
			this.validatePhoneInput(phone);
		}
		
		catch (ContactBookException e) {
			
			
		}
	}
	
	
	public void validateNameInput (String firstname, String lastname) throws ContactBookException {
		
		if (firstname.equals("")) {
			
			throw new ContactBookException ("You must enter a first name");
		}
		
		else if ((firstname.length() < 2) || (lastname.length() < 2))  {
			
			throw new ContactBookException ("Name entries must be at least two letters long.");
		}
		
		else if ((firstname.matches(".*\\d+.*") || (lastname.matches(".*\\d+.*")))) {
			
			throw new ContactBookException ("Name entries cannot contain numbers.");
		}
		
		else if ((firstname.matches(".*\\s+.*") || (lastname.matches(".*\\s+.*")))) {
			
			throw new ContactBookException ("Name entries cannot contain spaces.");
		}
		
		else if ((firstname.matches(".*\\W+.*") || (lastname.matches(".*\\W+.*")))) {
			
			throw new ContactBookException ("Name entries cannot contain punctuation or non-alpha characters.");
		}

		
		else {
			
			this.firstName = firstname;
			this.lastName = lastname;
		}
		
	}
	
	
	public void validatePhoneInput (String phone) throws ContactBookException {
		
		String patterns =
	       "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$" 
	      + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$" 
	      + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$";
		
		if (!phone.matches(patterns)) {
			
			throw new ContactBookException ("Phone number entry cannot contain punctuation or other non-alpha characters.");
		}
		
		else {
			
			this.phoneNumber = phone;
		}
	}
	

	
	public String getFirstName () {
		
		return this.firstName;
	}
	

	public String getLastName () {
		
		return this.lastName;
	}
	
	
	public void setAddressOne(String address1) throws ContactBookException{
			
		this.addressOne = address1;
	}
	
	public String getAddressOne() {
		
		return this.addressOne;
	}
	
	
	public String getPhoneNumber () {
				
			return this.phoneNumber;
	}
	
	// Overridden Comparable method for sorting
	@Override
	public int compareTo(Contact c) {
		
		int nameCheck = this.getFirstName().compareTo(c.getFirstName());
		return nameCheck == 0 ? this.getFirstName().compareTo(c.getFirstName()) : nameCheck;
	}

	// from AutoCloseable
	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public boolean equals (Contact c) {
		
		if (this.getFirstName().equals(c.getFirstName()) &&
				this.getLastName().equals(c.getLastName()) &&
				this.getAddressOne().equals(c.getAddressOne()) &&
				this.getPhoneNumber().equals(c.getPhoneNumber())
				) {return true;}
		
		else {return false;}
			
	}
	
	public void checkForEquals (ArrayList <Contact> list) throws ContactBookException {
		
		boolean greenLight = true;
		
		for (Contact contact : list) {
			
			if (this.equals(contact)) {
				
				greenLight = false;
			}
		}
		
		if (greenLight == true) {
			
			list.add(this);
		}
		
		else {
			
			throw new ContactBookException ("A duplicate contact exists.");
		}
	}

}
