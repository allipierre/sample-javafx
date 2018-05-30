package de.hbrs.todolistmanager.model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import de.hbrs.todolistmanager.model.view.TodoItem;

/**
 * Todo-Item, welches persistiert wird
 */
public class PersistentTodoItem {

	private String issue;

	private Date latestFinishDate;

	/**
	 * Konvertierung von View-Todo-Item in persistentes Todo-Item
	 * @param item das Todo-Item aus dem View
	 */
	public PersistentTodoItem(TodoItem item) {
		this.issue = item.getIssue();
		if (item.getLatestFinishDate() != null) {
			this.latestFinishDate = Date.from(item.getLatestFinishDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
		} else {
			this.latestFinishDate = null;
		}
	}

	/**
	 * Konvertierung von persistentem Todo-Item in View-Todo-Item
	 * @return das Todo-Item für den View
	 */
	public TodoItem createTodoItem() {
		LocalDate localDate = this.latestFinishDate != null
				? this.latestFinishDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
		return new TodoItem(this.issue, localDate);
	}

}
