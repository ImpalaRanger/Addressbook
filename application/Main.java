package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.control.ComboBox;
import java.io.Serializable;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

public class Main extends Application implements Serializable{

	public static ListView<String> contactListView = new ListView<String>();
	public static ObservableList<Contact> contacts;
	public static ObservableList<String> convertContactToName;
	public static int currentlySelectedIndex;
	
	public static TextField firstNameBox = new TextField();
	public static TextField lastNameBox = new TextField();
	public static TextField streetBox = new TextField();
	public static TextField cityBox = new TextField();
	public static ComboBox<String> stateBox = new ComboBox<String>();
	public static TextField zipBox = new TextField();
	public static TextField emailBox = new TextField();
	public static TextArea notesBox = new TextArea();
	
	public static File file = new File("Addressbook");
	
	@Override
	public void start(Stage primaryStage)  {
		try {
			GridPane root = new GridPane();
			root.setAlignment(Pos.CENTER);
			root.setHgap(50);
			root.setVgap(50);
			Font font = Font.font("Verdana", FontWeight.EXTRA_BOLD, 25);
			Text addLbl = new Text("Addressbook");
			addLbl.setFont(font);
			root.add(addLbl, 0, 0); // top labeling
			
			// checks if file exists, if not, initializes new contact
			File importFile = new File("Addressbook");
			if (importFile.exists()) {
				contacts = FXCollections.observableArrayList(load());
			}
			else {
				contacts = FXCollections.observableArrayList(new Contact("", "", "", "", "", "", "", ""));
			}
			
			// represents list of clickable names on left
			convertContactToName = FXCollections.observableArrayList();
			
			for (Contact con:  contacts) {
				convertContactToName.add(con.getLastName() + ", " + con.getFirstName());
			}
			
			contactListView = new ListView<String>(convertContactToName);
			contactListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
			
			
			contactListView.getSelectionModel().selectedItemProperty().addListener(
					new InvalidationListener() {
						@Override
						public void invalidated(Observable ov) {
							int indexToShow = (contactListView.getSelectionModel().getSelectedIndex());
							if (indexToShow < 0) {
								indexToShow = 0;
							}
							currentlySelectedIndex = indexToShow;
							
							System.out.println("DEBUG: Currently Selected: " + currentlySelectedIndex);
							firstNameBox.setText((contacts.get(indexToShow)).getFirstName());	
							lastNameBox.setText((contacts.get(indexToShow)).getLastName());
							streetBox.setText((contacts.get(indexToShow)).getStreet());
							cityBox.setText((contacts.get(indexToShow)).getCity());
							stateBox.setValue((contacts.get(indexToShow)).getState());
							zipBox.setText((contacts.get(indexToShow)).getZip());
							emailBox.setText((contacts.get(indexToShow)).getEmail());
							notesBox.setText((contacts.get(indexToShow)).getNotes());
						}	
					});
			
			contactListView.setPadding(new Insets(5));
			root.add(contactListView, 0, 1);
			
			//-------------------------------------------------------------------    all formatted info 
			
			GridPane formattedBoxes = new GridPane();
			formattedBoxes.setHgap(5);
			formattedBoxes.setVgap(5);
			
			GridPane firstNameGrid = new GridPane();
			firstNameGrid.getColumnConstraints().add(new ColumnConstraints(40));
			firstNameGrid.add(new Text("First"), 0, 0);
			firstNameGrid.add(firstNameBox, 1, 0);
			
			GridPane lastNameGrid = new GridPane();
			lastNameGrid.getColumnConstraints().add(new ColumnConstraints(40));
			lastNameGrid.add(new Text("Last"), 0, 0);
			lastNameGrid.add(lastNameBox, 1, 0);
			
			GridPane streetGrid = new GridPane();
			streetGrid.getColumnConstraints().add(new ColumnConstraints(40));
			streetGrid.add(new Text("Street"), 0, 0);
			streetGrid.add(streetBox, 1, 0);	
			
			
			GridPane cityGrid = new GridPane();
			cityGrid.getColumnConstraints().add(new ColumnConstraints(40));
			cityGrid.add(new Text("City"), 0, 0);
			cityGrid.add(cityBox, 1, 0);
			
			GridPane stateGrid = new GridPane();
			stateGrid.getColumnConstraints().add(new ColumnConstraints(40));
			stateGrid.add(new Text("State"), 0, 0);
			stateBox.getItems().addAll("State", "AL", "AK", "AZ", "AR","CA","CO","CT","DE","FL","GA","HI","ID","IL","IN","IA","KS","KY","LA","ME","MD","MA",
					"MI","MN","MS","MO","MT","NE","NV","NH","NJ","NM","NY","NC","ND","OH","OK","OR","PA","RI","SC","SD","TN","TX","UT","VT","VA","WA","WV","WI","WY");
			stateBox.setValue("State");
			stateGrid.add(stateBox, 1, 0);
			
			GridPane zipGrid = new GridPane();
			zipGrid.getColumnConstraints().add(new ColumnConstraints(40));
			zipGrid.add(new Text("ZIP"), 0, 0);
			zipGrid.add(zipBox, 1, 0);
			
			GridPane emailGrid = new GridPane();
			emailGrid.getColumnConstraints().add(new ColumnConstraints(40));
			emailGrid.add(new Text("Email"), 0, 0);
			emailGrid.add(emailBox, 1, 0);		
			
			GridPane notesGrid = new GridPane();
			notesGrid.getColumnConstraints().add(new ColumnConstraints(40));
			notesGrid.add(new Text("Notes"), 0, 0);
			notesGrid.add(notesBox, 1, 0);			
			
			formattedBoxes.add(firstNameGrid, 0, 0);
			formattedBoxes.add(lastNameGrid, 1, 0);
			formattedBoxes.add(streetGrid, 0, 1, 2, 1);
			formattedBoxes.add(cityGrid, 0, 2);
			formattedBoxes.add(stateGrid, 1, 2);
			formattedBoxes.add(zipGrid, 0, 3);
			formattedBoxes.add(emailGrid, 0, 4);	
			formattedBoxes.add(notesGrid, 0, 5, 2, 1);
			formattedBoxes.setPadding(new Insets(5));
			root.add(formattedBoxes, 1, 1);
			
			
			//-------------------------------------------------------------------    buttons at bottom (new, delete, update)
			
			FlowPane buttonPane = new FlowPane();
			
			Button BtNew = new Button("New");
			Button BtDelete = new Button("Delete");
			Button BtUpdate = new Button("Update");
			
			BtNewHandler newHandler = new BtNewHandler();
			BtNew.setOnAction(newHandler);
			
			BtUpdateHandler updateHandler = new BtUpdateHandler();
			BtUpdate.setOnAction(updateHandler);
			
			BtDeleteHandler deleteHandler = new BtDeleteHandler();
			BtDelete.setOnAction(deleteHandler);
			
			buttonPane.getChildren().addAll(BtNew, BtUpdate, BtDelete);
			buttonPane.setPadding(new Insets(5));
			root.add(buttonPane, 1, 2);
			
			//-------------------------------------------------------------------
			
			
			
			
			Scene scene = new Scene(root,550,400);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

				@Override
				public void handle(WindowEvent arg0) {
					System.out.println("DEBUG: Stage is closing");
					save(contacts);
					
				}
			});
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	// saves contacts to file
	public void save(ObservableList<Contact> savingList) {
		try {
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
			
			ArrayList<Contact> contactArr = new ArrayList<>();
			for (Contact con : savingList) {
				contactArr.add(con);
			}
			
			
			
			output.writeObject(contactArr);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// loads contacts from 
	public ArrayList<Contact> load() throws IOException {
		FileInputStream fileIn = new FileInputStream("Addressbook");
		ObjectInputStream input = new ObjectInputStream(fileIn);
		ArrayList<Contact> loadedContacts = new ArrayList<Contact>();
		
		try {
			
			loadedContacts = (ArrayList<Contact>)(input.readObject());
			System.out.println("Load successful");
			input.close();
			return loadedContacts;
		}
		catch (IOException e) {
			input.close();
			throw e;
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		input.close();
		
		return loadedContacts;
	}
}


// handles new button
class BtNewHandler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent e) {
		Contact newContact = new Contact("", "", "", "", "", "", "", "");
		Main.contacts.add(newContact);
		Main.contactListView.getItems().addAll("");
		
	}
}

// handles update button
class BtUpdateHandler implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent e) {
		String updatedFirstName = Main.firstNameBox.getText();
		String updatedLastName = Main.lastNameBox.getText();
		String updatedStreetName = Main.streetBox.getText();
		String updatedCityName = Main.cityBox.getText();
		String updatedState = Main.stateBox.getValue();
		String updatedZip = Main.zipBox.getText();
		String updatedEmail = Main.emailBox.getText();
		String updatedNotes = Main.notesBox.getText();
		Main.contacts.get(Main.currentlySelectedIndex).setFirstName(updatedFirstName);
		Main.contacts.get(Main.currentlySelectedIndex).setLastName(updatedLastName);
		Main.contacts.get(Main.currentlySelectedIndex).setStreet(updatedStreetName);
		Main.contacts.get(Main.currentlySelectedIndex).setCity(updatedCityName);
		Main.contacts.get(Main.currentlySelectedIndex).setState(updatedState);
		Main.contacts.get(Main.currentlySelectedIndex).setZip(updatedZip);
		Main.contacts.get(Main.currentlySelectedIndex).setEmail(updatedEmail);	
		Main.contacts.get(Main.currentlySelectedIndex).setNotes(updatedNotes);			
		Main.convertContactToName.set(Main.currentlySelectedIndex, updatedLastName + ", " + updatedFirstName);
	}
}

// handles delete button
class BtDeleteHandler implements EventHandler<ActionEvent> {
	@Override
	public void handle(ActionEvent e) {
		// removes selected contact 
		if (Main.contacts.size() > 1) {
			System.out.println("DEBUG: size > 1");
			Main.contacts.remove(Main.currentlySelectedIndex);
			Main.convertContactToName.remove(Main.currentlySelectedIndex);
		}
		// if only 1 contact is left, is it set to a new contact
		else {
			Main.contacts.get(Main.currentlySelectedIndex).setFirstName("");
			Main.contacts.get(Main.currentlySelectedIndex).setLastName("");
			Main.contacts.get(Main.currentlySelectedIndex).setStreet("");
			Main.contacts.get(Main.currentlySelectedIndex).setCity("");
			Main.contacts.get(Main.currentlySelectedIndex).setState("");
			Main.contacts.get(Main.currentlySelectedIndex).setZip("");
			Main.contacts.get(Main.currentlySelectedIndex).setEmail("");	
			Main.contacts.get(Main.currentlySelectedIndex).setNotes("");
			Main.convertContactToName.set(Main.currentlySelectedIndex, "");
		}

	}
}








