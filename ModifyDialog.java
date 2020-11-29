
/*
 This class is for modifying an employee chosen by the user. The
 user can either save or close out of this window. The changes
 made in this window will transfer over to the main window.
*/

import javax.swing.*;
import BreezySwing.*;

public class ModifyDialog extends GBDialog {

	// GUI components
	private JLabel nameL, departmentL, salaryL;
	private JTextField nameF, departmentF;
	private DoubleField salaryF;
	private JButton save, cancel;

	private EmployeeList employeeList; // existing list
	private Employee employeeOld; // chosen employee

	// constructor for setting up GUI components
	public ModifyDialog(JFrame parent, EmployeeList el, Employee e) {
		// setting up window
		super(parent);
		setTitle("Modifying " + e.getName());
		setSize(500, 200);

		// transferring data from main gui
		employeeList = el;
		employeeOld = e;

		// positioning GUI components to windows
		nameL = addLabel("Name", 1, 1, 1, 1);
		nameF = addTextField("", 1, 2, 1, 1);
		departmentL = addLabel("Department", 2, 1, 1, 1);
		departmentF = addTextField("", 2, 2, 1, 1);
		salaryL = addLabel("Salary", 3, 1, 1, 1);
		salaryF = addDoubleField(0, 3, 2, 1, 1);
		save = addButton("Save Changes", 4, 1, 1, 1);
		cancel = addButton("Cancel", 4, 2, 1, 1);

		// starting values
		nameF.setText(e.getName());
		departmentF.setText(e.getDepartment());
		salaryF.setNumber(e.getSalary());

		nameF.requestFocus();
		nameF.selectAll();

	}

	// performing actions when buttons are clicked
	public void buttonClicked(JButton button) {
		if (button == save) { // if user clicks the save button
			// error checking the user inputs
			if (!nameF.getText().trim().equals("") && !departmentF.getText().trim().equals("")
					&& salaryF.isValidNumber() && salaryF.getNumber() >= 0) {
				// change existing values for new ones
				employeeList.setEmployee(employeeOld,
						new Employee(nameF.getText(), departmentF.getText(), salaryF.getNumber()));
				dispose(); // close dialog box
			} else {
				messageBox("Invalid inputs!"); // error message
			}
		}
	}

}
