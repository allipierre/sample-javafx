package de.hbrs.todolistmanager.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;

import com.thoughtworks.xstream.XStream;

import de.hbrs.todolistmanager.model.view.TodoItem;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 * Schreiben und Lesen von Daten in das Dateisystem
 */
public class DataIntegration implements ListChangeListener<TodoItem> {
	
	/**
	 * Dateiname, änderbar für Tests
	 */
	String fileName = "todolist.xml";
	
	/**
	 * Lesen der Daten aus dem Dateisystem, falls Datei vorhanden, in die Liste data
	 * @param data Die Liste, in welcher die Daten abgelegt werden sollen.
	 */
	public void readData(ObservableList<TodoItem> data) {
		ObjectInputStream in = null;
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));

			XStream xstream = new XStream();
			xstream.alias("todoitem", PersistentTodoItem.class);

			in = xstream.createObjectInputStream(bufferedReader);

			// Daten befinden sich im Speicher. Nun Übertrag auf die lokale Struktur.
			data.clear();
			while (true) {
				data.add(((PersistentTodoItem) in.readObject()).createTodoItem());
			}

		} catch (EOFException e) {
			// Datei ist bis zum Ende gelesen worden
		} catch (IOException | ClassNotFoundException e) {
			System.err.println(e.getLocalizedMessage());
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				// Exception beim Schließen der Datei
			}
		}
	}

	/**
	 * Der Listener sorgt dafür, dass Änderungen direkt in das Dateisystem geschrieben werden.
	 */
	@Override
	public void onChanged(Change<? extends TodoItem> c) {

		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName) , "UTF-8"));
						
			XStream xstream = new XStream();
			xstream.alias("todoitem", PersistentTodoItem.class);
			ObjectOutputStream out = xstream.createObjectOutputStream(bufferedWriter);

			ObservableList<? extends TodoItem> newList = c.getList();
			for (TodoItem todoItem : newList) {
				out.writeObject(new PersistentTodoItem(todoItem));
			}

			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}