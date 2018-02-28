package controller;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import book.Book;
import book.Publisher;
import db.BookTableGateway;
import javafx.fxml.Initializable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.util.converter.NumberStringConverter;
import model.Author;
import misc.AlertHelper;

public class BookDetailController implements Initializable{
	
	private String origTitle;
	private String origYearPub;
	private LocalDate origDateAdded;
	private String origIsbn;
	private String origSummary;
	
	private static Logger logger;
	private Book book;
	private BookTableGateway gateway;
	private ObservableList<Publisher> publishers;
	
	@FXML private TextField title;
	@FXML private TextField publisher;
	@FXML private TextField year_published;
	@FXML private TextField isbn;
	@FXML private DatePicker date_added;
	@FXML private TextArea summary;
	@FXML private Button saveButton;
    @FXML private ComboBox<Publisher> publishersCombo;

	public BookDetailController(Book book) {
		logger = LogManager.getLogger();
		this.book = book;
		
		gateway = this.book.getBookGateway();
		try {
			publishers = gateway.getPublishers();
		} catch (Exception e) { e.printStackTrace(); }
	}
	 
	
	@FXML
	void saveButtonClicked(MouseEvent event){
		/**logger.info("SAVE button clicked");
		
		//Validate each field
		//Prompt if one fails and return to original value
		if(!book.isValidFirstName(book.getFirstName())) {
    		logger.error("Invalid Firstname:\t\"" + book.getFirstName() + "\"");
    		AlertHelper.showWarningMessage("SAVE ERROR", "Firstname is invalid", "Firstnames must be 1-100 characters.");
    		firstName.textProperty().set(origFirstName);
		}
		else if(!book.isValidLastName(book.getLastName())) {
    		logger.error("Invalid Lastname:\t\"" + book.getLastName() + "\"");
    		AlertHelper.showWarningMessage("SAVE ERROR", "Lastname is invalid", "Lastnames must be 1-100 characters.");
    		lastName.textProperty().set(origLastName);
		}
		else if(!book.isValidDOB(book.getDob())) {
    		logger.error("Invalid Date:\t\"" + book.getDob().toString() + "\"");
    		AlertHelper.showWarningMessage("SAVE ERROR", "Date is invalid", "Date must be before the current date.");
    		dob.valueProperty().set(origDate);
		}
		else if(!book.isValidGender(book.getGender())) {
    		logger.error("Invalid Gender:\t\"" + book.getGender() + "\"");
    		AlertHelper.showWarningMessage("SAVE ERROR", "Gender is invalid", "While we can not assume your gender, we can reject it.");
    		gender.textProperty().set(origGender);
		}
		else if(!book.isValidWebsite(book.getWebsite())) {
    		logger.error("Invalid Weabsite:\t\"" + book.getWebsite() + "\"");
    		AlertHelper.showWarningMessage("SAVE ERROR", "Website is invalid", "Website must be no more than 100 characters.");
    		web.textProperty().set(origWebsite);
		}
		else {
			book.save();
		}
	**/}

	public void initialize(URL arg0, ResourceBundle arg1) {
		publishersCombo.setItems(publishers);
		
		title.textProperty().bindBidirectional(book.getTitleProperty());
		//publisher.textProperty().bindBidirectional(book.getPublisher().getPublisherNameProperty());
		year_published.textProperty().bindBidirectional(book.getYearPublishedProperty(), new NumberStringConverter("####"));
		isbn.textProperty().bindBidirectional(book.getIsbnProperty());
		date_added.valueProperty().bindBidirectional(book.getDateAddedProperty());
		summary.textProperty().bindBidirectional(book.getSummaryProperty());
		
	}
}
