package de.hbrs.todolistmanager.controller;

import de.hbrs.todolistmanager.model.view.TodoItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Wird genutzt, wenn die View-Größe verändert wird.
 */
public class ResizingListener implements ChangeListener<Bounds> {

	public static final int MINIMUM_SIZE_OF_ISSUE_COLUMN = 200;
	public static final int SPACE_BETWEEN_TWO_COLUMNS = 22;
	public static final int PREFERRED_SIZE_OF_LATEST_FINISH_DATE_COLUMN = 180;
	
	private TableView<TodoItem> tableView;
	private TableColumn<TodoItem, String> issueTableColumn;
	private TableColumn<TodoItem, String> latestFinishDateTableColumn;
	
	public ResizingListener(TableView<TodoItem> tableView, 
			TableColumn<TodoItem, String> issueTableColumn, TableColumn<TodoItem, String> latestFinishDateTableColumn) {
		this.tableView = tableView;
		this.issueTableColumn = issueTableColumn;
		this.latestFinishDateTableColumn = latestFinishDateTableColumn;
	}
	

	/**
	 * Aufgerufene Methode bei Größenänderung
	 */
	@Override
	public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {

		tableView.setPrefSize(newValue.getWidth(), newValue.getHeight());

		if (newValue.getWidth() - PREFERRED_SIZE_OF_LATEST_FINISH_DATE_COLUMN - SPACE_BETWEEN_TWO_COLUMNS < MINIMUM_SIZE_OF_ISSUE_COLUMN) {
			// Kleines Fenster
			issueTableColumn.setPrefWidth(MINIMUM_SIZE_OF_ISSUE_COLUMN);
			latestFinishDateTableColumn.setPrefWidth(newValue.getWidth() - issueTableColumn.getWidth() - SPACE_BETWEEN_TWO_COLUMNS);
		} else {
			// Großes Fenster
			latestFinishDateTableColumn.setPrefWidth(PREFERRED_SIZE_OF_LATEST_FINISH_DATE_COLUMN);
			issueTableColumn.setPrefWidth(newValue.getWidth() - latestFinishDateTableColumn.getWidth() - SPACE_BETWEEN_TWO_COLUMNS);
		}

	}

}
