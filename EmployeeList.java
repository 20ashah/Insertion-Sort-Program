
/*
 This class contains the list of all the employees entered.
 It contains methods to remove, add, and modify employees. It 
 also contains methods for sorting the list of employees by 
 salary and alphabetically using insertion sort
*/

import java.util.*;

public class EmployeeList {

	// lists for the unsorted and sorted lists of employees
	private DoubleLinkedList<Employee> originalEmployeeList;
	private DoubleLinkedList<Employee> sortedEmployeeList;

	// creating new lists
	public EmployeeList() {
		originalEmployeeList = new DoubleLinkedList<Employee>();
		sortedEmployeeList = new DoubleLinkedList<Employee>();
	}

	// adding a new employee to the lists
	public void addEmployee(Employee e) {
		originalEmployeeList.add(e);
		sortedEmployeeList.add(e);
	}

	// getting the original list of employees
	public DoubleLinkedList<Employee> getOriginal() {
		return originalEmployeeList;
	}

	// clearing both lists of employees
	public void reset() {
		originalEmployeeList = new DoubleLinkedList<Employee>();
		sortedEmployeeList = new DoubleLinkedList<Employee>();
	}

	// getting the number of employees
	public int getSize() {
		return originalEmployeeList.size();
	}

	// removing an employee from the list
	public void remove(Employee i) {
		DoubleLinkedList<Employee>.DoublyLinkedListIterator iterator = originalEmployeeList.iterator();
		while (iterator.hasNext()) {
			// removes the matching employee
			Employee temp = iterator.next();
			if (temp.getName().equals(i.getName()) && temp.getSalary() == i.getSalary()
					&& temp.getDepartment().equals(i.getDepartment())) {
				iterator.remove();
				break;
			}
		}
	}

	// modifying the information of an old employee
	public void setEmployee(Employee oldE, Employee newE) {
		// creating a new iterator
		DoubleLinkedList<Employee>.DoublyLinkedListIterator iterator = originalEmployeeList.iterator();
		while (iterator.hasNext()) { // looping through list
			Employee temp = iterator.next();
			// if the employee is a match
			if (temp.getName().equals(oldE.getName()) && temp.getDepartment().equals(oldE.getDepartment())
					&& temp.getSalary() == oldE.getSalary()) {
				// replace information with new information
				temp.setName(newE.getName());
				temp.setDepartment(newE.getDepartment());
				temp.setSalary(newE.getSalary());
				break;
			}
		}
	}

	// getting an employee from the list
	public Employee getEmp(int i) {
		DoubleLinkedList<Employee>.DoublyLinkedListIterator iterator = originalEmployeeList.iterator();
		return iterator.get(i);
	}

	// sorting the list alphabetically using insertion sort
	public DoubleLinkedList<Employee> insertionSortAlphabetical() {
		DoubleLinkedList<Employee>.DoublyLinkedListIterator iterator = sortedEmployeeList.iterator();
		// sort the list using insertion sort
		for (int i = 1; i < originalEmployeeList.size(); i++) {
			Employee key = iterator.get(i);
			int j = i - 1;
			// loop backwards until it finds its place
			while (j >= 0 && iterator.get(j).getName().compareTo(key.getName()) > 0) {
				// shifts everything up
				iterator.get(j + 1);
				iterator.next();
				iterator.set(iterator.get(j));
				j--;
			}
			// sets it in correct position
			iterator.get(j + 1);
			iterator.next();
			iterator.set(key);
		}
		return sortedEmployeeList;
	}

	// sorting the list by salary using insertion sort
	public DoubleLinkedList<Employee> insertionSortSalary() {
		DoubleLinkedList<Employee>.DoublyLinkedListIterator iterator = sortedEmployeeList.iterator();
		// sort the list using insertion sort
		for (int i = 1; i < originalEmployeeList.size(); i++) {
			Employee key = iterator.get(i);
			int j = i - 1;
			// loop backwards until it finds its place
			while (j >= 0 && iterator.get(j).getSalary() > key.getSalary()) {
				// shifts everything up
				iterator.get(i + 1);
				iterator.next();
				iterator.set(iterator.get(j));
				j--;
			}
			// sets it in correct position
			iterator.get(j + 1);
			iterator.next();
			iterator.set(key);
		}
		return sortedEmployeeList;

	}

}
