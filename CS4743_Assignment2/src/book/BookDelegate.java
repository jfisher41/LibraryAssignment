package book;

import java.util.Calendar;

public class BookDelegate {
	
	public BookDelegate() {}
	
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
