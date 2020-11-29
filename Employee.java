
/*
  This class models an Employee object. It contains instance
  variables for its name, department, and salary. It contains
  methods to get and set these attributes and to prints its
  information
*/

import java.text.DecimalFormat;

public class Employee {

	// for rounding salary
	DecimalFormat decform = new DecimalFormat("$0.00");

	// declare employee instance variables
	private String name;
	private String department;
	private double salary;

	// creating an employee with values
	public Employee(String n, String d, double s) {
		name = n;
		department = d;
		salary = s;
	}

	// override toString method
	public String toString() {
		return name;
	}

	// getting employee's name
	public String getName() {
		return name;
	}

	// getting employee's salary
	public double getSalary() {
		return salary;
	}

	// getting employee's department
	public String getDepartment() {
		return department;
	}

	// setting employee's name
	public void setName(String n) {
		name = n;
	}

	// setting employee's salary
	public void setSalary(double s) {
		salary = s;
	}

	// setting employee's department
	public void setDepartment(String d) {
		department = d;
	}

	// printing the employee's information
	public String print() {
		String message = "Name: " + name + "\nDepartment: " + department + "\nSalary: " + decform.format(salary)
				+ "\n\n";
		return message;
	}
}
