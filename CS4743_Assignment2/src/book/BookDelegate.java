package book;

import java.sql.Connection;
import java.util.Calendar;

import controller.ControllerSingleton;
import db.BookTableGateway;

public class BookDelegate {
	
	public BookDelegate() {}
	
	public void saveBook(Book book) {
		BookTableGateway gateway = book.getBookGateway();
		try {
			gateway.updateBook(book);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void insertBook(Book book) {
		ControllerSingleton controller = ControllerSingleton.getInstance();
		Connection conn = controller.getConnection();
		BookTableGateway gateway = new BookTableGateway(conn);
		 
		try {
			gateway.insertBook(book);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteBook(Book book) {
		BookTableGateway gateway = book.getBookGateway();
		try {
			gateway.deleteBook(book);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean isValidTitle(String title) {
		return (title.length() > 0 && title.length() <=255);
	}
	
	public boolean isValidSummary(String summary) {
		return (summary.length() < 65536);
	}
	
	public boolean isValidPublished(int yearPublished) {
		return (yearPublished <= Calendar.getInstance().get(Calendar.YEAR));
	}
	
	public boolean isValidIsbn(String isbn) {
		return (isbn.length() <= 13);
	}
	
	/**
	public boolean isValidDateAdded(Date dateAdded) {
		
	}
	**/

}
