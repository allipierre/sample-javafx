package de.hbrs.todolistmanager.model;

import static org.junit.Assert.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.Test;

import de.hbrs.todolistmanager.model.view.TodoItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsCollectionContaining.*;

public class DataIntegrationTest {
	
	
	private static final String TODOLIST_INTEGRATIONTEST_EMPTY_XML = "todolist-integrationtest-empty.xml";
	private static final String TODOLIST_INTEGRATIONTEST_XML = "todolist-integrationtest.xml";
	private static final String TODOLIST_INTEGRATIONTEST_MISSING_XML = "todolist-integrationtest-missing.xml";
	
	private static final String ISSUE1 = "demoissue";
	private static final String ISSUE2 = "demoissue2";
	

	@Test
	public void writeAndReadEmptyList() {

		ObservableList<TodoItem> data = FXCollections.observableArrayList(new ArrayList<TodoItem>());
		
		DataIntegration dataIntegration = new DataIntegration();
		dataIntegration.fileName = TODOLIST_INTEGRATIONTEST_EMPTY_XML;
		data.addListener(dataIntegration);
		
		data.add(new TodoItem(ISSUE1, LocalDate.now()));
		data.remove(0);
								
		DataIntegration readDataIntegration = new DataIntegration();
		readDataIntegration.fileName = TODOLIST_INTEGRATIONTEST_EMPTY_XML;
		ObservableList<TodoItem> data2 = FXCollections.observableArrayList(new ArrayList<TodoItem>());
		readDataIntegration.readData(data2);
				
		assertThat(data.size(), is(data2.size()));
	}

	@Test
	public void writeAndReadList() {

		ObservableList<TodoItem> data = FXCollections.observableArrayList(new ArrayList<TodoItem>());
		
		DataIntegration dataIntegration = new DataIntegration();
		dataIntegration.fileName = TODOLIST_INTEGRATIONTEST_XML;
		data.addListener(dataIntegration);
		
		data.add(new TodoItem(ISSUE1, LocalDate.now()));
		data.add(new TodoItem(ISSUE2, LocalDate.now()));
						
		DataIntegration readDataIntegration = new DataIntegration();
		readDataIntegration.fileName = TODOLIST_INTEGRATIONTEST_XML;
		ObservableList<TodoItem> data2 = FXCollections.observableArrayList(new ArrayList<TodoItem>());
		readDataIntegration.readData(data2);
				
		assertThat(data.size(), is(data2.size()));
		
		for (TodoItem item : data) {
			assertThat(data2, hasItem(item));
		}

	}
	
	@Test 
	public void missingFile() {
		DataIntegration readDataIntegration = new DataIntegration();
		readDataIntegration.fileName = TODOLIST_INTEGRATIONTEST_MISSING_XML;
		ObservableList<TodoItem> data2 = FXCollections.observableArrayList(new ArrayList<TodoItem>());
		readDataIntegration.readData(data2);
	}
	
	
	@Test
	public void addAndDeleteItem() {

		// Hinzufügen
		
		ObservableList<TodoItem> data = FXCollections.observableArrayList(new ArrayList<TodoItem>());
		
		DataIntegration dataIntegration = new DataIntegration();
		dataIntegration.fileName = TODOLIST_INTEGRATIONTEST_XML;
		data.addListener(dataIntegration);
		
		data.add(new TodoItem(ISSUE1, LocalDate.now()));
		
		DataIntegration readDataIntegration = new DataIntegration();
		readDataIntegration.fileName = TODOLIST_INTEGRATIONTEST_XML;
		ObservableList<TodoItem> data2 = FXCollections.observableArrayList(new ArrayList<TodoItem>());
		readDataIntegration.readData(data2);
				
		assertThat(data2.size(), is(1));
		
		assertThat(data2.get(0), is(data.get(0)));
		
		// Löschen
		
		data.remove(0);
		
		DataIntegration readDataIntegrationDelete = new DataIntegration();
		readDataIntegrationDelete.fileName = TODOLIST_INTEGRATIONTEST_XML;
		ObservableList<TodoItem> dataDelete = FXCollections.observableArrayList(new ArrayList<TodoItem>());
		readDataIntegrationDelete.readData(dataDelete);
				
		assertThat(dataDelete.size(), is(0));
		
	}
	
	@Test
	public void changeItem() {
		
		ObservableList<TodoItem> data = FXCollections.observableArrayList(new ArrayList<TodoItem>());
		
		DataIntegration dataIntegration = new DataIntegration();
		dataIntegration.fileName = TODOLIST_INTEGRATIONTEST_XML;
		data.addListener(dataIntegration);
		
		TodoItem todoItem = new TodoItem(ISSUE1, LocalDate.now());
		
		data.add(todoItem);
		
		data.remove(todoItem);
		
		todoItem.setIssue(ISSUE2);
		
		data.add(todoItem);
			
		
		DataIntegration readDataIntegration = new DataIntegration();
		readDataIntegration.fileName = TODOLIST_INTEGRATIONTEST_XML;
		ObservableList<TodoItem> data2 = FXCollections.observableArrayList(new ArrayList<TodoItem>());
		readDataIntegration.readData(data2);
				
		assertThat(data2.size(), is(1));
		
		assertThat(todoItem, is(data2.get(0)));
		
		data.remove(0);
	}
}
