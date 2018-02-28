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
import book.Publisher;

public class BookTableGateway {
	private Connection conn;
	private static Logger logger;
	
	public BookTableGateway(Connection conn) {
		this.conn = conn;
		logger = LogManager.getLogger();
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
		ObservableList<Book> books = FXCollections.observableArrayList();
		PreparedStatement st = null;
		PreparedStatement publisherStatement = null;
		try {
			st = conn.prepareStatement("SELECT * FROM `Book` ORDER BY `Book`.`title` ASC");
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				Book book = new Book();
				book.setId(rs.getInt("id"));
				book.setTitle(rs.getString("title"));
				book.setSummary(rs.getString("summary"));
				book.setYearPublished((rs.getInt("year_published")));
				book.setIsbn(rs.getString("isbn"));
			
				int publisherId = rs.getInt("publisher_id");
				
				Publisher publisher = new Publisher();
				publisherStatement = conn.prepareStatement("SELECT * FROM `Publisher` WHERE `id` = ?");
				publisherStatement.setInt(1, book.getId());
				
				ResultSet pub_rs = publisherStatement.executeQuery();
				//publisher.setPublisherName(pub_rs.getString("publisher_name"));
				//publisher.setId(pub_rs.getInt("id"));
				
				publisher.setId(1);
				publisher.setPublisherName("Bob");
				book.setPublisher(publisher);
				
				
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
	
	public ObservableList<Publisher> getPublishers() throws Exception{
		ObservableList<Publisher> publishers = FXCollections.observableArrayList();
		PreparedStatement st = null;
	
		try {
			st = conn.prepareStatement("SELECT * FROM `Publisher` ORDER BY `Publisher`.`publisher_name` ASC");
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				Publisher publisher = new Publisher();
				publisher.setId(rs.getInt("id"));
				publisher.setPublisherName(rs.getString("publisher_name"));
				
				publishers.add(publisher);	
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
		
		return publishers;
	}
	/**
	public void updateAuthor(Author author) throws Exception {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("update Author set first_name = ?, "
					+ "last_name = ?, "
					+ "dob = ?, "
					+ "gender = ?, "
					+ "web_site = ?  "
					+ "where id = ?");
			st.setString(1, author.getFirstName());
			st.setString(2, author.getLastName());
			st.setDate(3, author.getDobDate());
			st.setString(4, author.getGender());
			st.setString(5, author.getWebsite());
			st.setInt(6, author.getId());
			st.executeUpdate();
			
			logger.info(author.getFirstName() + " " + author.getLastName() + " SAVED");
			
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
	
	public void insertAuthor(Author author) throws Exception {
		PreparedStatement st = null;
		PreparedStatement stFindLast = null;
		
		try {
			st = conn.prepareStatement("INSERT INTO Author(first_name,last_name,dob,gender,web_site) VALUES(?,?,?,?,?)");
			st.setString(1, author.getFirstName());
			st.setString(2, author.getLastName());
			st.setDate(3, author.getDobDate());
			st.setString(4, author.getGender());
			st.setString(5, author.getWebsite());
			
			st.executeUpdate();
			
			stFindLast = conn.prepareStatement("SELECT * FROM `Author` WHERE 1 ORDER BY id DESC LIMIT 1 ");
			ResultSet rs = stFindLast.executeQuery();
			rs.next();
			author.setId(rs.getInt("id"));
			author.setGateway(this);

			logger.info(author.getFirstName() + " " + author.getLastName() + " INSERTED");
			
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
	
	public void deleteAuthor(Author author) throws Exception {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM Author WHERE id = ?");
			st.setInt(1, author.getId());
			st.executeUpdate();
			
			logger.info(author.getFirstName() + " " + author.getLastName() + " DELETED");
			
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
	**/
}
