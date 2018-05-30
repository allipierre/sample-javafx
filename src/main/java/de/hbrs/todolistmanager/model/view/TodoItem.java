package de.hbrs.todolistmanager.model.view;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

/**
 * Todo-Item, welches sich innerhalb der Tabelle im View befindet.
 */
public class TodoItem {

	private SimpleStringProperty issue = new SimpleStringProperty("");

	private ObjectProperty<LocalDate> latestFinishDate = new SimpleObjectProperty<>();

	public TodoItem() {
		new TodoItem("", null);
	}

	public TodoItem(String issue, LocalDate latestFinishDate) {
		this.issue.setValue(issue);
		this.latestFinishDate.setValue(latestFinishDate);
	}

	public String getIssue() {
		return issue.getValue();
	}

	public void setIssue(String issue) {
		this.issue.setValue(issue);
	}

	public LocalDate getLatestFinishDate() {
		return latestFinishDate.getValue();
	}

	public void setLatestFinishDate(LocalDate latestFinishDate) {
		this.latestFinishDate.setValue(latestFinishDate);
	}

	@Override
	public boolean equals(Object obj) {
		
		if (obj == null || ! (obj instanceof TodoItem))
			return false;
		
		if ((issue.getValue().equals(((TodoItem) obj).issue.getValue()))) {

			if (latestFinishDate.getValue() == null) {
				if (((TodoItem) obj).latestFinishDate.getValue() == null) {
					return true;
				} else {
					return false;
				}
			}

			if (latestFinishDate.getValue().equals(((TodoItem) obj).latestFinishDate.getValue())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		} 
	}
}