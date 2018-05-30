package de.hbrs.todolistmanager.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import de.hbrs.todolistmanager.model.DataIntegration;
import de.hbrs.todolistmanager.model.view.TodoItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;

/**
 * Kontroller für JavaFX
 */
public class TodoListViewController {

    public static final DateTimeFormatter DATE_TIME_FORMATTER =DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @FXML
    private GridPane rootGridPane;

    @FXML
    private TableView<TodoItem> tableView;

    @FXML
    private TableColumn<TodoItem, String> issueTableColumn;

    @FXML
    private TableColumn<TodoItem, String> latestFinishDateTableColumn;

    @FXML
    private TextField issueTextField;

    @FXML
    private DatePicker latestFinishDateDatePicker;


    @FXML
    public void initialize() {
        
        rootGridPane.layoutBoundsProperty().addListener(new ResizingListener (tableView, issueTableColumn, latestFinishDateTableColumn));

        tableView.setEditable(true);

        issueTableColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TodoItem, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<TodoItem, String> t) {
                                                
                        TodoItem todoItem = t.getTableView().getItems().get(t.getTablePosition().getRow());
                        
                        // Enfernen und neues Hinzufügen, damit die Listener der Liste benachrichtigt werden.
                        int position = t.getTablePosition().getRow();
                        t.getTableView().getItems().remove(todoItem);
                        todoItem.setIssue(t.getNewValue());
                        t.getTableView().getItems().add(position, todoItem); 
                    }
                }
        );


        issueTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        latestFinishDateTableColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TodoItem, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<TodoItem, String> t) {
                        LocalDate newValue = LocalDate.parse(t.getNewValue(), DATE_TIME_FORMATTER);
                        
                        TodoItem todoItem = t.getTableView().getItems().get(t.getTablePosition().getRow());
                        
                        // Enfernen und neues Hinzufügen, damit die Listener der Liste benachrichtigt werden.
                        int position = t.getTablePosition().getRow();
                        t.getTableView().getItems().remove(todoItem);
                        todoItem.setLatestFinishDate(newValue);
                        t.getTableView().getItems().add(position, todoItem); 
                    }
                }
        );

        latestFinishDateTableColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        
        ObservableList<TodoItem> data = FXCollections.observableArrayList(new ArrayList<TodoItem>());
        DataIntegration dataIntegration = new DataIntegration();
        dataIntegration.readData(data);
        data.addListener(dataIntegration);
                
       	tableView.setItems(data);
    }

    public void addTodoItem(ActionEvent actionEvent) {
        TodoItem item = new TodoItem();
        item.setIssue(issueTextField.getText());
        item.setLatestFinishDate(latestFinishDateDatePicker.getValue());

        ObservableList<TodoItem> data = tableView.getItems();
        data.add(item);

        issueTextField.clear();
        latestFinishDateDatePicker.setValue(null);
    }

    public void delete(ActionEvent actionEvent) {
        TodoItem item = tableView.getSelectionModel().getSelectedItem();
        tableView.getItems().remove(item);
    }
    
    public void undo(ActionEvent actionEvent) {
    	
    	// Ihr Code für das Command-Pattern
    	
    }
}