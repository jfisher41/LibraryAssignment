package controller;

import java.net.URL;
import java.util.ResourceBundle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import db.AuthorTableGateway;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
import model.Author;

public class AuthorListViewController implements Initializable{
	
	@FXML private ListView<Author> authorList;
	@FXML private Button deleteButton;
   
	private static Logger logger;
	private ObservableList<Author> authors;
	private BorderPane rootPane;
	private ControllerSingleton controller = ControllerSingleton.getInstance();

	public AuthorListViewController(ObservableList<Author> authors, BorderPane rootPane) {
		logger = LogManager.getLogger();
		this.authors = authors;
		this.rootPane = rootPane;
	}
	
	@FXML void deleteButtonClicked(MouseEvent event) { 
		
		Author selectedAuthor = authorList.getSelectionModel().getSelectedItem();
		AuthorTableGateway gateway = selectedAuthor.getGateway();
	
		selectedAuthor.delete();
		
		//update the list view
		try {
			authors = gateway.getAuthors();
			authorList.setItems(authors);

		} catch (Exception e) {	e.printStackTrace(); }
	}
	
	@FXML
	void onAuthorListClicked(MouseEvent click) {
		//Go to detail view on double click
		if (click.getClickCount() == 2) {
			Author author = authorList.getSelectionModel().getSelectedItem();
			logger.info(author.getFirstName() + " " + author.getLastName() + " double clicked");
			controller.changeView("/view/AuthorDetailView.fxml", new AuthorDetailController(author), rootPane);
	    }
	}

	public void initialize(URL location, ResourceBundle resources) {
		authorList.setItems(authors);
	}
}
