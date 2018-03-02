package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import book.Book;

public class BookTableGateway {
	private Connection conn;
	private static Logger logger;
	private PublisherTableGateway pubGateway;
	
	public BookTableGateway(Connection conn) {
		this.conn = conn;
		logger = LogManager.getLogger();
		pubGateway = new PublisherTableGateway(conn);
	}
	
	public void closeConnection() {
		logger.info("Closing connection...");
		
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ObservableList<Book> getBooks() throws Exception{
		PreparedStatement st = conn.prepareStatement("SELECT * FROM `Book` ORDER BY `Book`.`title` ASC");
		return searchBooks(st);
	}
	
	//override the previous methods to accept a searchable string
	public ObservableList<Book> getBooks(String stringToFind) throws Exception{
		PreparedStatement st = conn.prepareStatement("SELECT * FROM Book WHERE title LIKE '%" + stringToFind + "%'");
		return searchBooks(st);
	}
	
	public ObservableList<Book> searchBooks(PreparedStatement st) throws Exception{
		ObservableList<Book> books = FXCollections.observableArrayList();
				
		try {
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				Book book = new Book();
				book.setId(rs.getInt("id"));
				book.setTitle(rs.getString("title"));
				book.setSummary(rs.getString("summary"));
				book.setYearPublished((rs.getInt("year_published")));
				book.setIsbn(rs.getString("isbn"));
				book.setDateAdded(rs.getDate("date_added"));
				book.setPublisher(pubGateway.getPublisherById(rs.getInt("publisher_id")));
				
				book.setBookGateway(this);
				books.add(book);	
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			try {
				if(st != null)
					st.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new Exception(e);
			}
		}
		
		return books;
	}
	
	
	public void updateBook(Book book) throws Exception {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("update Book set title = ?, "
					+ "summary = ?, "
					+ "year_published = ?, "
					+ "publisher_id = ?, "
					+ "isbn = ?  "
					+ "where id = ?");
			st.setString(1, book.getTitle());
			st.setString(2, book.getSummary());
			st.setInt(3, book.getYearPublished());
			st.setInt(4, book.getPublisher().getId());
			st.setString(5, book.getIsbn());
			st.setInt(6, book.getId());
			st.executeUpdate();
			
			logger.info(book.getTitle() + " SAVED");
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			try {
				if(st != null)
					st.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new Exception(e);
			}
		}
	}
	
	
	public void insertBook(Book book) throws Exception {
		PreparedStatement st = null;
		PreparedStatement stFindLast = null;
		
		try {
			st = conn.prepareStatement("INSERT INTO Book(title,summary,year_published,publisher_id,isbn) VALUES(?,?,?,?,?)");
			st.setString(1, book.getTitle());
			st.setString(2, book.getSummary());
			st.setInt(3, book.getYearPublished());
			st.setInt(4, book.getPublisher().getId());
			st.setString(5, book.getIsbn());
			
			st.executeUpdate();
			
			stFindLast = conn.prepareStatement("SELECT * FROM `Book` WHERE 1 ORDER BY id DESC LIMIT 1 ");
			ResultSet rs = stFindLast.executeQuery();
			rs.next();
			book.setId(rs.getInt("id"));
			book.setBookGateway(this);

			logger.info(book.getTitle() + " INSERTED");
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			try {
				if(st != null)
					st.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new Exception(e);
			}
		}
	}
	
	public void deleteBook(Book book) throws Exception {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM Book WHERE id = ?");
			st.setInt(1, book.getId());
			st.executeUpdate();
			
			logger.info(book.title + " DELETED");
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			try {
				if(st != null)
					st.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new Exception(e);
			}
		}
	}
	
}
