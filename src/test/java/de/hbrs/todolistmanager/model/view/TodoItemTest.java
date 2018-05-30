package de.hbrs.todolistmanager.model.view;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.*;

import java.time.LocalDate;

import org.junit.Test;

import de.hbrs.todolistmanager.model.view.TodoItem;

public class TodoItemTest {

	private static final String TESTISSUE = "testissue";

	@Test
	public void testSettingAndGetting() {
		TodoItem todoItem = new TodoItem();

		todoItem.setIssue(TESTISSUE);
		todoItem.setLatestFinishDate(LocalDate.now());

		assertThat(todoItem.getIssue(), is(TESTISSUE));
		assertThat(todoItem.getLatestFinishDate(), is(LocalDate.now()));
	}

	@Test
	public void testConstructorWithParameters() {
		TodoItem todoItem = new TodoItem(TESTISSUE, LocalDate.now());

		assertThat(todoItem.getIssue(), is(TESTISSUE));
		assertThat(todoItem.getLatestFinishDate(), is(LocalDate.now()));
	}

	@Test
	public void testNotEqual() {
		assertFalse(testEquals(new TodoItem("test", null), new TodoItem("test", LocalDate.now())));
		assertFalse(testEquals(new TodoItem("test", LocalDate.now()), new TodoItem("test", null)));
	}

	@Test
	public void testEqualsNullOrOtherObject() {
		boolean nullTest = testEquals(new TodoItem("test", LocalDate.now()), null);
		assertFalse(nullTest);
		assertFalse((new TodoItem("test", LocalDate.now()).equals("test")));
	}

	@Test
	public void testNotEqualOtherDate() {
		assertFalse(testEquals(new TodoItem("test", LocalDate.now()), new TodoItem("test", LocalDate.of(2018, 5, 20))));
	}

	/**
	 * Hilfemethode
	 */
	private boolean testEquals(TodoItem todoItem, TodoItem todoItem2) {
		return todoItem.equals(todoItem2);
	}
}
