package controller;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import book.Book;
import book.Publisher;
import db.GatewayDistributer;
import db.PublisherTableGateway;
import javafx.fxml.Initializable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.util.converter.NumberStringConverter;
import misc.AlertHelper;

public class BookDetailController implements Initializable{
	private GatewayDistributer distributer;
	
	private static Logger logger;
	private Book book;
	private PublisherTableGateway pubGateway;
	private ObservableList<Publisher> publishers;
	private DateFormat format;
	
	@FXML private TextField title;
	@FXML private TextField publisher;
	@FXML private TextField year_published;
	@FXML private TextField isbn;
	@FXML private DatePicker date_added;
	@FXML private TextArea summary;
	@FXML private Button saveButton;
	@FXML private Label dateLabel;
    @FXML private ComboBox<Publisher> publishersCombo;
    

	public BookDetailController(Book book) throws Exception {
		distributer = GatewayDistributer.getInstance();
		
		pubGateway = distributer.getPublisherGateway();
		publishers = pubGateway.getPublishers();
		logger = LogManager.getLogger();
		
		format = new SimpleDateFormat("MMMMM dd, yyyy");
		
		this.book = book;
	}
	 
	@FXML
	void saveButtonClicked(MouseEvent event){
		logger.info("SAVE button clicked");
		
		//Validate each field
		//Prompt if one fails and return to original value
		if(!book.isValidTitle(book.getTitle())) {
    		logger.error("Invalid Title:\t\"" + book.getTitle() + "\"");
    		AlertHelper.showWarningMessage("SAVE ERROR", "Title is invalid", "Titles must be 1-255 characters.");
    		//firstName.textProperty().set(origFirstName);
		}
		else if(!book.isValidSummary(book.getSummary())) {
    		logger.error("Invalid Summary:\t\"" + book.getSummary() + "\"");
    		AlertHelper.showWarningMessage("SAVE ERROR", "Summary is invalid", "Book summaries must be less than 65,536 characters.");
    		//lastName.textProperty().set(origLastName);
		}
		else if(!book.isValidPublished(book.getYearPublished())) {
    		logger.error("Invalid Gender:\t\"" + book.getYearPublished() + "\"");
    		AlertHelper.showWarningMessage("SAVE ERROR", "Year published is invalid", "The year published cannot be after the current year.");
    		//gender.textProperty().set(origGender);
		}
		else if(!book.isValidIsbn(book.getIsbn())) {
    		logger.error("Invalid ISBN:\t\"" + book.getIsbn() + "\"");
    		AlertHelper.showWarningMessage("SAVE ERROR", "ISBN is invalid", "ISBN cannot be less than 13 characters.");
    		//web.textProperty().set(origWebsite);
		}
		else {
			book.setPublisher(publishersCombo.getSelectionModel().getSelectedItem());
			book.save();
		}
	}

	public void initialize(URL arg0, ResourceBundle arg1) {
		publishersCombo.setItems(publishers);
		publishersCombo.setValue(book.getPublisher());
		
		title.textProperty().bindBidirectional(book.getTitleProperty());
		//publisher.textProperty().bindBidirectional(book.getPublisher().getPublisherNameProperty());
		year_published.textProperty().bindBidirectional(book.getYearPublishedProperty(), new NumberStringConverter("####"));
		isbn.textProperty().bindBidirectional(book.getIsbnProperty());
		summary.textProperty().bindBidirectional(book.getSummaryProperty());
		
		if(book.getId() == 0)
			dateLabel.setText("N/A");
		else
			dateLabel.setText(format.format(book.getDateAdded()));
	}
}
