package com.rahul.rai.hr.bl.interfaces.managers;
import com.rahul.rai.hr.bl.exceptions.*;
import com.rahul.rai.hr.bl.interfaces.pojo.*;
import java.util.*;
public interface EmployeeManagerInterface
{
public void addEmployee(EmployeeInterface employee) throws BLException;
public void updateEmployee(EmployeeInterface employee) throws BLException;
public void removeEmployee(String employeeId) throws BLException;
public EmployeeInterface getEmployeeByEmployeeId(String employeeId) throws BLException;
public EmployeeInterface getEmployeeByPANNumber(String panNumber) throws BLException;
public EmployeeInterface getEmployeeByAadharCardNumber(String aadharCardNumber) throws BLException;
public Set<EmployeeInterface> getEmployees();
public Set<EmployeeInterface> getEmployeesByDesignationCode(int designationCode) throws BLException;
public int getEmployeeCount();
public int getEmployeeCountByDesignationCode(int designationCode) throws BLException;
public boolean isDesignationAlloted(int designationCode) throws BLException;
public boolean employeeIdExists(String employeeId);
public boolean employeePANNumberExists(String panNumber);
public boolean employeeAadharCardNumberExists(String aadharCardNumber);
}