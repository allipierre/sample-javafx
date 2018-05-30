package de.hbrs.todolistmanager.view;

import de.hbrs.todolistmanager.controller.TodoListViewController;
import de.hbrs.todolistmanager.model.view.TodoItem;
import javafx.beans.NamedArg;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Diese Klasse wird benutzt, um Datumszellen in der Tabelle anzeigen zu können.
 *
 */
public class DatePropertyValueFactory extends PropertyValueFactory<TodoItem,String> {

    public DatePropertyValueFactory(@NamedArg("property") String property) {
        super(property);
    }

    /**
     * Aufruf erfolgt, wenn Zelleinhalt zu String konvertiert wird.
     */
    @Override
    public ObservableValue<String> call(TableColumn.CellDataFeatures<TodoItem, String> todoItem) {
        String localDateAsString = TodoListViewController.DATE_TIME_FORMATTER.format((todoItem.getValue()).getLatestFinishDate());
        SimpleStringProperty simpleStringProperty = new SimpleStringProperty();
        simpleStringProperty.setValue(localDateAsString);
        return simpleStringProperty;
    }
}