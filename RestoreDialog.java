
/*
 This class is for restoring deleted employees. The user can click on one or
 multiple employees and they will be restored to the main list.
*/

import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import BreezySwing.*;

public class RestoreDialog extends GBDialog implements ListSelectionListener {

	// GUI components
	private JList<Employee> deleteList;
	private JButton restore;
	private LinkedList<Employee> delete;
	private EmployeeList employeeList; // existing list

	// constructor for setting up GUI components
	public RestoreDialog(JFrame parent, LinkedList<Employee> del, EmployeeList el) {
		// setting up window
		super(parent);
		setTitle("Manage Deletions");
		setSize(200, 250);

		// transferring data from main gui
		employeeList = el;
		delete = del;

		// positioning GUI components to windows
		deleteList = addList(1, 1, 5, 1);
		restore = addButton("Restore", 2, 1, 1, 1);

		// starting values
		deleteList.getSelectionModel().addListSelectionListener((ListSelectionListener) this);

		// create the deletion list
		DefaultListModel model = (DefaultListModel) deleteList.getModel();
		for (int i = 0; i < del.size(); i++) {
			model.addElement(del.get(i));
		}

	}

	// performing actions when buttons are clicked
	public void buttonClicked(JButton button) {
		if (button == restore) { // if user clicks the restore button
			// get employees that the user selected and their indices
			List<Employee> selected = deleteList.getSelectedValuesList();
			int[] indices = deleteList.getSelectedIndices();
			// add these employees to the main list
			for (int i = 0; i < selected.size(); i++) {
				employeeList.addEmployee(selected.get(i));
			}

			// delete these employees from the deleted list
			for (int i = 0; i < indices.length; i++) {
				delete.remove(indices[i]);
			}

			dispose(); // close dialog box
		}
	}

	// performs changes when action listener is triggered
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting() == false) {
			boolean enabled = deleteList.isSelectionEmpty();
			// only let restore enabled if employees are selected
			restore.setEnabled(!enabled);

		}

	}

}
