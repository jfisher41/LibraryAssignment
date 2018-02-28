package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

import book.Book;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import db.AuthorTableGateway;
import db.BookTableGateway;
import db.PublisherTableGateway;
import model.Author;

public class MenuController implements Initializable {
	@FXML private MenuBar menuBar;
	@FXML private MenuItem menuItemAddAuthors;
	@FXML private MenuItem menuItemAuthors;
	@FXML private MenuItem menuItemBooks;
	@FXML private MenuItem menuItemAddBook;
	@FXML private MenuItem menuItemQuit;
	@FXML private BorderPane rootPane;
	
	AuthorTableGateway gateway;
	BookTableGateway bookGateway;
	//PublisherTableGateway pupGateway;
	
	private ObservableList<Author> authors;
	private ObservableList<Book> books;
	private ControllerSingleton controller;
	private Connection conn;
	
	public MenuController() {
		controller = ControllerSingleton.getInstance();
		conn = controller.getConnection();
		//pupGateway = new PublisherTableGateway(conn);
		bookGateway = new BookTableGateway(conn);
		gateway = new AuthorTableGateway(conn);
		
	}
	
	@FXML private void handleMenuAction(ActionEvent event) throws IOException {

		if(event.getSource() == menuItemAuthors) {
			try {
				authors = gateway.getAuthors();
			} catch (Exception e) {
				e.printStackTrace();
			}
			controller.changeView("/view/AuthorListView.fxml", new AuthorListViewController(authors, rootPane), rootPane);
		}
		else if(event.getSource() == menuItemAddAuthors) {
			controller.changeView("/view/AuthorDetailView.fxml", new AuthorDetailController(new Author()), rootPane);
		}
		else if(event.getSource() == menuItemBooks) {
			try {
				books = bookGateway.getBooks();
			} catch (Exception e) {
				e.printStackTrace();
			}
			controller.changeView("/view/BookListView.fxml", new BookListViewController(books, rootPane), rootPane);
		}
		else if(event.getSource() == menuItemAddBook){
			controller.changeView("/view/BookDetailView.fxml", new BookDetailController(new Book()), rootPane);
		}
		else if(event.getSource() == menuItemQuit) {
			gateway.closeConnection();
			System.exit(0);
		}
	}
	
	public void initialize(URL location, ResourceBundle resources) {
		menuBar.setFocusTraversable(true);
	}
}
