package model;

import java.sql.Connection;
import java.time.LocalDate;
import controller.ControllerSingleton;
import db.AuthorTableGateway;

public class AuthorDelegate {
	
	public AuthorDelegate() {}
	
	public void saveAuthor(Author author) {
		AuthorTableGateway gateway = author.getGateway();
		try {
			gateway.updateAuthor(author);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void insertAuthor(Author author) {
		ControllerSingleton controller = ControllerSingleton.getInstance();
		Connection conn = controller.getConnection();
		AuthorTableGateway gateway = new AuthorTableGateway(conn);
		 
		try {
			gateway.insertAuthor(author);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteAuthor(Author author) {
		AuthorTableGateway gateway = author.getGateway();
		try {
			gateway.deleteAuthor(author);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Validators
	public boolean isValidID(int id) {
		return id >=0;
	}
	
	public boolean isValidFirstName(String fName) {
		if (fName.length() > 0 && fName.length() <= 100)
			return true;
		return false;
	}

	public boolean isValidLastName(String lName) {
		if (lName.length() > 0 && lName.length() <= 100)
			return true;
		return false;
	}
	
	public boolean isValidDOB(LocalDate date) {
		LocalDate current = LocalDate.now();
		return date.isBefore(current);
	}
	
	public boolean isValidGender(String gender) {
		gender = gender.toLowerCase();
		if(gender.equals("male") || gender.equals("female") || gender.equals("unknown"))
			return true;
		return false;
	}
	
	public boolean isValidLastWebsite(String web) {
		if (web.length() <= 100)
			return true;
		return false;
	}
}