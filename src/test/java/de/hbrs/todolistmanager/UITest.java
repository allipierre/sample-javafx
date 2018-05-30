package de.hbrs.todolistmanager;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.testfx.api.FxAssert.verifyThat;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Locale;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

import de.hbrs.todolistmanager.model.view.TodoItem;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class UITest extends ApplicationTest {
	
	// Dies wird benötigt, um die Tests headless im Docker-Container zu starten
	static {
		if ("true".equals(System.getProperty("headless"))) {
			System.setProperty("testfx.robot", "glass");
			System.setProperty("testfx.headless", "true");
			System.setProperty("prism.order", "sw");
			System.setProperty("prism.text", "t2k");
			System.setProperty("java.awt.headless", "true");
			System.setProperty("headless.geometry", "1600x1200-32");
		}
	}

	Stage stage;
	Scene myScene;
	Pane myPane;


	@Override
	public void start(Stage primaryStage) throws IOException {

		Locale.setDefault(Locale.GERMANY);

		this.stage = primaryStage;
		primaryStage.setTitle("Meine Aufgabenliste");
		myPane = FXMLLoader.load(getClass().getClassLoader().getResource("view/todolistview.fxml"));
		
		myScene = new Scene(myPane);
		primaryStage.setScene(myScene);
		primaryStage.show();
		
		primaryStage.setHeight(875.0);
	}

	
	
	@Test
	public void testIfGUIIsThere() {
		
		verifyThat("#rootGridPane", NodeMatchers.isNotNull());
	    verifyThat("#rootGridPane", NodeMatchers.hasChild("#tableView"));
		verifyThat("#issueTableColumn", NodeMatchers.isNotNull());
		verifyThat("#latestFinishDateTableColumn", NodeMatchers.isNotNull());
		
	}
	
	@Test
	public void testDelete() throws InterruptedException {
		
		TableView<TodoItem> tb = (TableView<TodoItem>) myScene.lookup("#tableView");
		tb.getItems().add(new TodoItem("itemToBeDeleted", LocalDate.now()));

		tb.requestFocus();
		tb.getSelectionModel().select(tb.getItems().size()-1);
		tb.getFocusModel().focus(tb.getItems().size()-1);
		
		clickOn("#deleteButton");
		
		TodoItem itemFound = null;
		for (TodoItem item : tb.getItems()) {
			if (item.getIssue().equals("itemToBeDeleted")) {
				itemFound = item;
			}
		}
		
		assertNull(itemFound);
	}
	
	@Test
	public void testAdd() {
		
		clickOn("#issueTextField");
		write("Testanliegen123");
		clickOn("#latestFinishDateDatePicker");
		write("06.06.2018").push(KeyCode.ENTER);
		clickOn("#addButton");
		
		TableView<TodoItem> tb = (TableView<TodoItem>) myScene.lookup("#tableView");
		
		
		TodoItem itemFound = null;
		for (TodoItem item : tb.getItems()) {
			if (item.getIssue().equals("Testanliegen123")) {
				itemFound = item;
			}
		}
		
		assertNotNull(itemFound);
		
		tb.getItems().remove(itemFound);
		
	}
	
	@Test 
	public void smallAndLarge() throws InterruptedException {
		
		double width = stage.getWidth();
		double height = stage.getHeight();
		
		stage.setWidth(200.0);
		Thread.sleep(500);
		
		stage.setWidth(1000.0);
		Thread.sleep(500);
		
		stage.setWidth(width);
		stage.setHeight(height);
		
	}
}
