package de.hbrs.todolistmanager.model;

import static org.junit.Assert.assertThat;

import java.time.LocalDate;

import org.junit.Test;

import de.hbrs.todolistmanager.model.view.TodoItem;
import static org.hamcrest.core.Is.*;

public class PersistentTodoItemTest {
	
	private static final String TESTISSUE = "testissue";
	
	@Test
	public void testSettingAndGetting () {
		
		TodoItem todoItem = new TodoItem(TESTISSUE,LocalDate.now());
		
		PersistentTodoItem persistentTodoItem = new PersistentTodoItem(todoItem);
		
		TodoItem todoItem2 = persistentTodoItem.createTodoItem();
		
		assertThat(todoItem2, is(todoItem));
		
	}
	
	@Test
	public void testSettingAndGettingWithNullTime () {
		
		TodoItem todoItem = new TodoItem(TESTISSUE,null);
		
		PersistentTodoItem persistentTodoItem = new PersistentTodoItem(todoItem);
		
		TodoItem todoItem2 = persistentTodoItem.createTodoItem();
		
		assertThat(todoItem2, is(todoItem));
		
	}


}
