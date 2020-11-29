
/*
 Arjun Shah
 B Block
 10/17/19
 Program Description:
 This program rewrites the insertion sort program using the doubly linked
 list class made from scratch instead of the built in Java one. This program
 takes in a number of employees with names, departments, and salaries. It 
 should also edit and remove these employees. It displays the list of 
 employees sorted and unsorted either alphabetically or by salary.
*/

import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import BreezySwing.*;

public class MainGUI extends GBFrame implements ListSelectionListener {

	// declaring the GUI components
	private JLabel nameL, departmentL, salaryL;
	private JTextField nameF, departmentF;
	private JTextArea moreInfo;
	private DoubleField salaryF;
	private JList<Employee> list;
	private JButton add, remove, modify;
	private JMenuItem reset, quit, sortAlpha, sortSalary, unsorted, restore;

	// list of all deleted employees
	private LinkedList<Employee> deleted = new LinkedList<Employee>();

	// list of all employees
	private EmployeeList employeeList = new EmployeeList();

	// constructor for setting components to screen
	public MainGUI() {

		// positioning GUI components on the window
		reset = addMenuItem("File", "Reset");
		quit = addMenuItem("File", "Quit");
		restore = addMenuItem("File", "Restore");
		sortAlpha = addMenuItem("Sort", "By Alphabet");
		sortSalary = addMenuItem("Sort", "By Salary");
		unsorted = addMenuItem("Sort", "Original");
		nameL = addLabel("Name", 1, 1, 1, 1);
		nameF = addTextField("", 1, 2, 1, 1);
		departmentL = addLabel("Department", 2, 1, 1, 1);
		departmentF = addTextField("", 2, 2, 1, 1);
		salaryL = addLabel("Salary", 3, 1, 1, 1);
		salaryF = addDoubleField(0, 3, 2, 1, 1);
		salaryF.setText("");
		add = addButton("Add Employee", 4, 1, 1, 1);
		remove = addButton("Remove Employee(s)", 4, 2, 1, 1);
		modify = addButton("Modify Employee", 5, 3, 1, 1);
		list = addList(5, 1, 1, 1);
		moreInfo = addTextArea("Employee Info:\n\n", 5, 2, 1, 1);

		// adding restrictions to the list and the buttons
		remove.setEnabled(false);
		modify.setEnabled(false);
		list.getSelectionModel().addListSelectionListener((ListSelectionListener) this);
		list.getSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}

	// performs actions when top menu options are selected
	public void menuItemSelected(JMenuItem item) {
		if (item == quit) { // if user clicks the quit option
			System.exit(0);
		} else if (item == reset) { // if user clicks the reset option
			// reset button and list
			moreInfo.setText("");
			DefaultListModel model = (DefaultListModel) list.getModel();
			model.removeAllElements();
			employeeList.reset();
			modify.setEnabled(false);
		} else if (item == sortAlpha) { // if user clicks the sort alphabetical option
			// sort list
			DoubleLinkedList<Employee> sorted = employeeList.insertionSortAlphabetical();
			DoubleLinkedList<Employee>.DoublyLinkedListIterator iterator = sorted.iterator();
			String output = "";
			// print out each employee
			for (int i = 0; i < sorted.size(); i++) {
				output += iterator.get(i).print() + "\n\n";
			}
			messageBox(output, 350, 500);
		} else if (item == sortSalary) { // if user clicks the sort salary option
			// sort list
			DoubleLinkedList<Employee> sorted = employeeList.insertionSortSalary();
			DoubleLinkedList<Employee>.DoublyLinkedListIterator iterator = sorted.iterator();
			String output = "";
			// print out each employee
			for (int i = 0; i < sorted.size(); i++) {
				output += iterator.get(i).print() + "\n\n";
			}
			messageBox(output, 350, 500);
		} else if (item == unsorted) { // if user clicks the unsorted option
			DoubleLinkedList<Employee> original = employeeList.getOriginal();
			String output = "";
			// print out each employee in original list
			DoubleLinkedList<Employee>.DoublyLinkedListIterator iterator = original.iterator();
			while (iterator.hasNext()) {
				output += iterator.next().print() + "\n\n";
			}
			messageBox(output, 350, 500);
		} else if (item == restore) { // if user clicks the restore option
			// open restore dialog
			RestoreDialog dialog = new RestoreDialog(this, deleted, employeeList);
			dialog.setVisible(true);
			moreInfo.setText("");
			addItemsToList();
		}
	}

	// performs actions when buttons are clicked
	public void buttonClicked(JButton button) {
		DefaultListModel model = (DefaultListModel) list.getModel();
		if (button == add) { // if user clicks the add button
			// error check user inputs
			if (nameF.getText().trim().equals("") || departmentF.getText().trim().equals("") || !salaryF.isValidNumber()
					|| salaryF.getNumber() < 0) {
				messageBox("Invalid inputs!");
			} else {
				// create new employee and add him to the list
				Employee emp = new Employee(nameF.getText(), departmentF.getText(), salaryF.getNumber());
				employeeList.addEmployee(emp);
				model.addElement(emp);
				salaryF.setText("");
				nameF.setText("");
				departmentF.setText("");
				nameF.requestFocus();
			}
		} else if (button == remove) { // if user clicks the remove button
			List<Employee> removeList = list.getSelectedValuesList(); // list of all selected things
			// remove each of them
			for (int i = 0; i < removeList.size(); i++) {
				employeeList.remove(removeList.get(i));
				deleted.add(removeList.get(i));
			}
			// reset list and side box
			moreInfo.setText("");
			addItemsToList();
			modify.setEnabled(false);
		} else if (button == modify) { // if user clicks the modify button
			// open modify dialog box
			ModifyDialog dialog = new ModifyDialog(this, employeeList, list.getSelectedValue());
			dialog.setVisible(true);
			moreInfo.setText("");
			addItemsToList();
		}
	}

	// performs things when changes are made to the list
	public void listItemSelected(JList l) {
		moreInfo.setText("Employee Info:\n\n");
		// when things are selected add them to side box
		List<Employee> addList = list.getSelectedValuesList();
		for (int i = 0; i < addList.size(); i++) {
			moreInfo.append(addList.get(i).print());
		}

	}

	// performs changes when action listener is triggered
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting() == false) {
			boolean enabled = list.isSelectionEmpty();
			// only let remove enabled if employees are selected
			remove.setEnabled(!enabled);
			// only enable modify if one employee is selected
			if (list.getSelectedValuesList().size() == 1) {
				modify.setEnabled(!enabled);
			} else {
				modify.setEnabled(enabled);
			}
		}
	}

	// method for adding items to the list
	public void addItemsToList() {
		// remove all existing elements first
		DefaultListModel model = (DefaultListModel) list.getModel();
		model.removeAllElements();
		// add all employees in the list
		for (int i = 0; i < employeeList.getSize(); i++) {
			model.addElement(employeeList.getEmp(i));
		}
	}

	// main method
	public static void main(String[] args) {
		MainGUI theGUI = new MainGUI(); // GUI window
		theGUI.setSize(650, 650); // size of window
		theGUI.setVisible(true); // visibility of window
		theGUI.setTitle("Insertion Sort Program"); // title
	}

}
