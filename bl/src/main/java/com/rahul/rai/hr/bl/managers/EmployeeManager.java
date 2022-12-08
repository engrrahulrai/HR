package com.rahul.rai.hr.bl.managers;
import com.rahul.rai.hr.bl.interfaces.managers.*;
import com.rahul.rai.enums.*;
import com.rahul.rai.hr.bl.interfaces.pojo.*;
import com.rahul.rai.hr.bl.pojo.*;
import com.rahul.rai.hr.bl.exceptions.*;
import java.util.*;
import java.text.*;
import java.math.*;
import com.rahul.rai.hr.dl.interfaces.dto.*;
import com.rahul.rai.hr.dl.interfaces.dao.*;
import com.rahul.rai.hr.dl.dto.*;
import com.rahul.rai.hr.dl.dao.*;
import com.rahul.rai.hr.dl.exceptions.*;
public class EmployeeManager implements EmployeeManagerInterface
{
private Map<String,EmployeeInterface> employeeIdWiseEmployeesMap;
private Map<String,EmployeeInterface> panNumberWiseEmployeesMap;
private Map<String,EmployeeInterface> aadharCardNumberWiseEmployeesMap;
private Set<EmployeeInterface> employeesSet;
private Map<Integer,Set<EmployeeInterface>> designationCodeWiseEmployeesMap;
private static EmployeeManagerInterface employeeManager=null;
private EmployeeManager() throws BLException
{
populateDataStructure();
}
private void populateDataStructure() throws BLException
{
this.employeeIdWiseEmployeesMap=new HashMap<>();
this.panNumberWiseEmployeesMap=new HashMap<>();
this.aadharCardNumberWiseEmployeesMap=new HashMap<>();
this.employeesSet=new TreeSet<>();
this.designationCodeWiseEmployeesMap=new HashMap<>();
try
{
Set<EmployeeDTOInterface> dlEmployeesSet;
dlEmployeesSet=new EmployeeDAO().getAll();
EmployeeInterface employee;
char dlGender;
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getDesignationManager();
DesignationInterface designation;
Set<EmployeeInterface> ets;
for(EmployeeDTOInterface employeeDTO:dlEmployeesSet)
{
employee=new Employee();
employee.setEmployeeId(employeeDTO.getEmployeeId());
employee.setName(employeeDTO.getName());
designation=designationManager.getDesignationByCode(employeeDTO.getDesignationCode());
employee.setDesignation(designation);
employee.setDateOfBirth((Date)employeeDTO.getDateOfBirth().clone());
employee.setGender((employeeDTO.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
employee.setIsIndian(employeeDTO.getIsIndian());
employee.setBasicSalary(employeeDTO.getBasicSalary());
employee.setPANNumber(employeeDTO.getPANNumber());
employee.setAadharCardNumber(employeeDTO.getAadharCardNumber());
this.employeeIdWiseEmployeesMap.put(employee.getEmployeeId().toUpperCase(),employee);
this.panNumberWiseEmployeesMap.put(employee.getPANNumber().toUpperCase(),employee);
this.aadharCardNumberWiseEmployeesMap.put(employee.getAadharCardNumber().toUpperCase(),employee);
this.employeesSet.add(employee);
ets=this.designationCodeWiseEmployeesMap.get(designation.getCode());
if(ets==null)
{
ets=new TreeSet<>();
ets.add(employee);
this.designationCodeWiseEmployeesMap.put(designation.getCode(),ets);
}
else
{
ets.add(employee);
}
}
}catch(DAOException daoException)
{
BLException blException=new BLException();
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public static EmployeeManagerInterface getEmployeeManager() throws BLException
{
if(employeeManager==null) employeeManager=new EmployeeManager();
return employeeManager;
}
public void addEmployee(EmployeeInterface employee) throws BLException
{
BLException blException=new BLException();
if(employee==null)
{
blException.setGenericException("employee details is null");
throw blException;
}
String employeeId=employee.getEmployeeId();
if(employeeId!=null)
{
employeeId=employeeId.trim();
if(employeeId.length()>0)
{
blException.addException("employeeId","Employee Id. should be nil/empty");
}
}
String name=employee.getName();
if(name==null)
{
blException.addException("name","Name required");
}
else
{
name=name.trim();
if(name.length()==0)
{
blException.addException("name","Name required");
}
}
DesignationInterface designation=employee.getDesignation();
int designationCode=0;
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getDesignationManager();
if(designation==null)
{
blException.addException("designation","designation required");
}
else
{
designationCode=designation.getCode();
String title=designation.getTitle();
DesignationInterface checkDesignation=designationManager.getDesignationByCode(designationCode);
if(checkDesignation==null)
{
blException.addException("desigantion","Invalid designation");
}
if(checkDesignation!=null && title.equalsIgnoreCase(checkDesignation.getTitle())==false)
{
blException.addException("designation","Designation code : "+designationCode+" and designation title : "+title+" does not match.");
}
} 
Date dateOfBirth=employee.getDateOfBirth();
if(dateOfBirth==null)
{
blException.addException("dateOfBirth","Date of birth required");
}
char gender=employee.getGender();
if(gender==' ')
{
blException.addException("gender","Gender required");
}
boolean isIndian=employee.getIsIndian();
BigDecimal basicSalary=employee.getBasicSalary();
if(basicSalary==null)
{
blException.addException("basicSalary","Basic salary required");
}
else
{
if(basicSalary.signum()==-1)
{
blException.addException("basicSalary","Basic salary can not be negative "+basicSalary.toPlainString());
}
}
String panNumber=employee.getPANNumber();
if(panNumber==null)
{
blException.addException("panNumber","PAN number required");
}
else
{
panNumber=panNumber.trim();
if(panNumber.length()==0)
{
blException.addException("panNumber","PAN number required");
}
}
String aadharCardNumber=employee.getAadharCardNumber();
if(aadharCardNumber==null)
{
blException.addException("aadharCardNumber","Aadhar card number required");
}
else
{
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0)
{
blException.addException("aadharCardNumber","Aadhar card number required");
}
}
if(panNumber!=null && panNumber.length()>0)
{
if(panNumberWiseEmployeesMap.containsKey(panNumber.toUpperCase()))
{
blException.addException("panNumber","PAN number "+panNumber+" exists.");
}
}
if(aadharCardNumber!=null && aadharCardNumber.length()>0)
{
if(aadharCardNumberWiseEmployeesMap.containsKey(aadharCardNumber.toUpperCase()))
{
blException.addException("aadharCardNumber","Aadhar card number "+aadharCardNumber+" exists.");
}
}
if(blException.hasException())
{
throw blException;
}
try
{
EmployeeDTOInterface employeeDTO;
employeeDTO=new EmployeeDTO();
employeeDTO.setName(name);
employeeDTO.setDesignationCode(designation.getCode());
employeeDTO.setDateOfBirth(dateOfBirth);
employeeDTO.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE);
employeeDTO.setIsIndian(isIndian);
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setPANNumber(panNumber);
employeeDTO.setAadharCardNumber(aadharCardNumber);
new EmployeeDAO().add(employeeDTO);
employeeId=employeeDTO.getEmployeeId();
employee.setEmployeeId(employeeId);
// Creating object for Data Structure
EmployeeInterface dsEmployee;
dsEmployee=new Employee();
dsEmployee.setEmployeeId(employeeId);
dsEmployee.setName(name);
dsEmployee.setDesignation(((DesignationManager)designationManager).getDSDesignationByCode(designationCode));
dsEmployee.setDateOfBirth((Date)dateOfBirth.clone());
dsEmployee.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE);
dsEmployee.setIsIndian(isIndian); 
dsEmployee.setBasicSalary(basicSalary);
dsEmployee.setPANNumber(panNumber);
dsEmployee.setAadharCardNumber(aadharCardNumber);
// adding in Data Structure
this.employeeIdWiseEmployeesMap.put(employeeId.toUpperCase(),dsEmployee);
this.panNumberWiseEmployeesMap.put(panNumber.toUpperCase(),dsEmployee);
this.aadharCardNumberWiseEmployeesMap.put(aadharCardNumber.toUpperCase(),dsEmployee);
this.employeesSet.add(dsEmployee);
Set<EmployeeInterface> ets=designationCodeWiseEmployeesMap.get(dsEmployee.getDesignation().getCode());
if(ets==null)
{
ets=new TreeSet<>();
ets.add(dsEmployee);
designationCodeWiseEmployeesMap.put(dsEmployee.getDesignation().getCode(),ets);
}
else
{
ets.add(dsEmployee);
}
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public void updateEmployee(EmployeeInterface employee) throws BLException
{
BLException blException=new BLException();
if(employee==null)
{
blException.setGenericException("employee details is null");
throw blException;
}
String employeeId=employee.getEmployeeId();
if(employeeId==null)
{
blException.addException("employeeId","Employee id required to update");
}
else
{
employeeId=employeeId.trim();
if(employeeId.length()==0) 
{
blException.addException("employeeId","Employee id required to update");
}
else
{
if(employeeIdWiseEmployeesMap.containsKey(employeeId.toUpperCase())==false)
{
blException.addException("employeeId","Invalid employee Id. : "+employeeId);
throw blException;
}
}
}
String name=employee.getName();
if(name==null)
{
blException.addException("name","Name required");
}
else
{
name=name.trim();
if(name.length()==0) blException.addException("name","Name required");
}
DesignationInterface designation=employee.getDesignation();
int designationCode=0;
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getDesignationManager();
if(designation==null)
{
blException.addException("designation","designation required");
}
else
{
designationCode=designation.getCode();
String title=designation.getTitle();
DesignationInterface checkDesignation=designationManager.getDesignationByCode(designationCode);
if(checkDesignation==null)
{
blException.addException("desigantion","Invalid designation");
}
if(checkDesignation!=null && title.equalsIgnoreCase(checkDesignation.getTitle())==false)
{
blException.addException("designation","Designation code : "+designationCode+" and designation title : "+title+" does not match.");
}
} 
Date dateOfBirth=employee.getDateOfBirth();
if(dateOfBirth==null)
{
blException.addException("dateOfBirth","Date of birth required");
}
char gender=employee.getGender();
if(gender==' ')
{
blException.addException("gender","Gender required");
}
boolean isIndian=employee.getIsIndian();
BigDecimal basicSalary=employee.getBasicSalary();
if(basicSalary==null)
{
blException.addException("basicSalary","Basic salary required");
}
else
{
if(basicSalary.signum()==-1)
{
blException.addException("basicSalary","Basic salary can not be negative "+basicSalary.toPlainString());
}
}
String panNumber=employee.getPANNumber();
if(panNumber==null)
{
blException.addException("panNumber","PAN number required");
}
else
{
panNumber=panNumber.trim();
if(panNumber.length()==0)
{
blException.addException("panNumber","PAN number required");
}
}
String aadharCardNumber=employee.getAadharCardNumber();
if(aadharCardNumber==null)
{
blException.addException("aadharCardNumber","Aadhar card number required");
}
else
{
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0)
{
blException.addException("aadharCardNumber","Aadhar card number required");
}
}
if(panNumber!=null && panNumber.length()>0)
{
EmployeeInterface ee=panNumberWiseEmployeesMap.get(panNumber.toUpperCase());
if(ee!=null && ee.getEmployeeId().equalsIgnoreCase(employeeId)==false)
{
blException.addException("panNumber","PAN number "+panNumber+" exists.");
}
}
if(aadharCardNumber!=null && aadharCardNumber.length()>0)
{
EmployeeInterface ee=aadharCardNumberWiseEmployeesMap.get(aadharCardNumber.toUpperCase());
if(ee!=null && ee.getEmployeeId().equalsIgnoreCase(employeeId)==false)
{
blException.addException("aadharCardNumber","Aadhar card number "+aadharCardNumber+" exists.");
}
}
if(blException.hasException())
{
throw blException;
}
try
{
EmployeeInterface dsEmployee;
dsEmployee=employeeIdWiseEmployeesMap.get(employeeId.toUpperCase());
String oldPANNumber=dsEmployee.getPANNumber();
String oldAadharCardNumber=dsEmployee.getAadharCardNumber();
int oldDesignationCode=dsEmployee.getDesignation().getCode();
EmployeeDTOInterface employeeDTO;
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(dsEmployee.getEmployeeId());
employeeDTO.setName(name);
employeeDTO.setDesignationCode(designation.getCode());
employeeDTO.setDateOfBirth(dateOfBirth);
employeeDTO.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE);
employeeDTO.setIsIndian(isIndian);
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setPANNumber(panNumber);
employeeDTO.setAadharCardNumber(aadharCardNumber);
new EmployeeDAO().update(employeeDTO);
// Creating object for Data Structure

dsEmployee.setName(name);
dsEmployee.setDesignation(((DesignationManager)designationManager).getDSDesignationByCode(designation.getCode()));
dsEmployee.setDateOfBirth((Date)dateOfBirth.clone());
dsEmployee.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE);
dsEmployee.setIsIndian(isIndian);
dsEmployee.setBasicSalary(basicSalary);
dsEmployee.setPANNumber(panNumber);
dsEmployee.setAadharCardNumber(aadharCardNumber);
// code to remove 
this.employeeIdWiseEmployeesMap.remove(employeeId.toUpperCase());
this.panNumberWiseEmployeesMap.remove(oldPANNumber.toUpperCase());
this.aadharCardNumberWiseEmployeesMap.remove(oldAadharCardNumber.toUpperCase());
this.employeesSet.remove(dsEmployee);
// adding in Data Structure
this.employeeIdWiseEmployeesMap.put(employeeId.toUpperCase(),dsEmployee);
this.panNumberWiseEmployeesMap.put(panNumber.toUpperCase(),dsEmployee);
this.aadharCardNumberWiseEmployeesMap.put(aadharCardNumber.toUpperCase(),dsEmployee);
this.employeesSet.add(dsEmployee);
if(oldDesignationCode!=dsEmployee.getDesignation().getCode())
{
Set<EmployeeInterface> ets=this.designationCodeWiseEmployeesMap.get(oldDesignationCode);
ets.remove(dsEmployee);
ets=this.designationCodeWiseEmployeesMap.get(designation.getCode());
if(ets==null)
{
ets=new TreeSet<>();
ets.add(dsEmployee);
designationCodeWiseEmployeesMap.put(new Integer(designation.getCode()),ets);
}
else
{
ets.add(dsEmployee);
}
}
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public void removeEmployee(String employeeId) throws BLException
{
BLException blException=new BLException();
if(employeeId==null)
{
blException.addException("employeeId","Employee id required to update");
}
else
{
employeeId=employeeId.trim();
if(employeeId.length()==0) 
{
blException.addException("employeeId","Employee id required to update");
}
else
{
if(this.employeeIdWiseEmployeesMap.containsKey(employeeId.toUpperCase())==false)
{
blException.addException("employeeId","Invalid employee id : "+employeeId);
}
}
}
if(blException.hasException())
{
throw blException;
}
try
{
EmployeeInterface tmpEmployee;
tmpEmployee=employeeIdWiseEmployeesMap.get(employeeId.toUpperCase());
new EmployeeDAO().delete(tmpEmployee.getEmployeeId());
// code to remove 
this.employeeIdWiseEmployeesMap.remove(employeeId.toUpperCase());
this.panNumberWiseEmployeesMap.remove(tmpEmployee.getPANNumber().toUpperCase());
this.aadharCardNumberWiseEmployeesMap.remove(tmpEmployee.getAadharCardNumber().toUpperCase());
this.employeesSet.remove(tmpEmployee);
int designationCode=tmpEmployee.getDesignation().getCode();
Set<EmployeeInterface> ets=this.designationCodeWiseEmployeesMap.get(designationCode);
ets.remove(tmpEmployee);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public EmployeeInterface getEmployeeByEmployeeId(String employeeId) throws BLException
{
BLException blException=new BLException();
if(employeeId==null)
{
blException.addException("employeeId","Employee id required");
}
else
{
employeeId=employeeId.trim();
if(employeeId.length()==0) blException.addException("employeeId","Employee id required");
}
if(blException.hasException()) throw blException;
EmployeeInterface employee=employeeIdWiseEmployeesMap.get(employeeId.toUpperCase());
if(employee==null)
{
blException.addException("employeeId","Invalid employee id : "+employeeId);
throw blException;
}
EmployeeInterface e=new Employee();
e.setEmployeeId(employee.getEmployeeId());
e.setName(employee.getName());
DesignationInterface designation=employee.getDesignation();
DesignationInterface d=new Designation();
d.setCode(designation.getCode());
d.setTitle(designation.getTitle());
e.setDesignation(d);
e.setDateOfBirth((Date)employee.getDateOfBirth().clone());
e.setGender((employee.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
e.setIsIndian(employee.getIsIndian());
e.setBasicSalary(employee.getBasicSalary());
e.setPANNumber(employee.getPANNumber());
e.setAadharCardNumber(employee.getAadharCardNumber());
return e;
}
public EmployeeInterface getEmployeeByPANNumber(String panNumber) throws BLException
{
BLException blException=new BLException();
if(panNumber==null)
{
blException.addException("panNumber","PAN number required");
}
else
{
panNumber=panNumber.trim();
if(panNumber.length()==0) blException.addException("panNumber","PAN number required");
}
if(blException.hasException()) throw blException;
EmployeeInterface employee=panNumberWiseEmployeesMap.get(panNumber.toUpperCase());
if(employee==null)
{
blException.addException("employeeId","Invalid PAN number : "+panNumber);
throw blException;
}
EmployeeInterface e=new Employee();
e.setEmployeeId(employee.getEmployeeId());
e.setName(employee.getName());
DesignationInterface designation=employee.getDesignation();
DesignationInterface d=new Designation();
d.setCode(designation.getCode());
d.setTitle(designation.getTitle());
e.setDesignation(d);
e.setDateOfBirth((Date)employee.getDateOfBirth().clone());
e.setGender((employee.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
e.setIsIndian(employee.getIsIndian());
e.setBasicSalary(employee.getBasicSalary());
e.setPANNumber(employee.getPANNumber());
e.setAadharCardNumber(employee.getAadharCardNumber());
return e;
}
public EmployeeInterface getEmployeeByAadharCardNumber(String aadharCardNumber) throws BLException
{
BLException blException=new BLException();
if(aadharCardNumber==null)
{
blException.addException("aadharCardNumber","Aadhar card number required");
}
else
{
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) blException.addException("aadharCardNumber","Aadhar card number required");
}
if(blException.hasException()) throw blException;
EmployeeInterface employee=aadharCardNumberWiseEmployeesMap.get(aadharCardNumber.toUpperCase());
if(employee==null)
{
blException.addException("aadharCardNumber","Invalid Aadhar card number : "+aadharCardNumber);
throw blException;
}
EmployeeInterface e=new Employee();
e.setEmployeeId(employee.getEmployeeId());
e.setName(employee.getName());
DesignationInterface designation=employee.getDesignation();
DesignationInterface d=new Designation();
d.setCode(designation.getCode());
d.setTitle(designation.getTitle());
e.setDesignation(d);
e.setDateOfBirth((Date)employee.getDateOfBirth().clone());
e.setGender((employee.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
e.setIsIndian(employee.getIsIndian());
e.setBasicSalary(employee.getBasicSalary());
e.setPANNumber(employee.getPANNumber());
e.setAadharCardNumber(employee.getAadharCardNumber());
return e;
}
public Set<EmployeeInterface> getEmployees()
{
DesignationInterface d;
EmployeeInterface employee;
DesignationInterface designation;
Set<EmployeeInterface> employees=new TreeSet<>();
for(EmployeeInterface e:employeesSet)
{
employee=new Employee();
employee.setEmployeeId(e.getEmployeeId());
employee.setName(e.getName());
d=e.getDesignation();
designation=new Designation();
designation.setCode(d.getCode());
designation.setTitle(d.getTitle());
employee.setDesignation(designation);
employee.setDateOfBirth((Date)e.getDateOfBirth().clone());
employee.setGender((e.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
employee.setIsIndian(e.getIsIndian());
employee.setBasicSalary(e.getBasicSalary());
employee.setPANNumber(e.getPANNumber());
employee.setAadharCardNumber(e.getAadharCardNumber());
employees.add(employee);
}
return employees;
}
public Set<EmployeeInterface> getEmployeesByDesignationCode(int designationCode) throws BLException
{
if(designationCode<=0)
{
BLException blException=new BLException();
blException.addException("designationCode","Invalid designation code : "+designationCode);
throw blException;
}
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getDesignationManager();
if(designationManager.designationCodeExists(designationCode)==false)
{
BLException blException=new BLException();
blException.addException("designationCode","Invalid designation code : "+designationCode);
throw blException;
}
Set<EmployeeInterface> employees=new TreeSet<>();
EmployeeInterface employee;
DesignationInterface designation;
DesignationInterface d;
Set<EmployeeInterface> dsEmployees=designationCodeWiseEmployeesMap.get(designationCode);
if(dsEmployees==null) return employees;
for(EmployeeInterface dsEmployee:dsEmployees)
{
employee=new Employee();
employee.setEmployeeId(dsEmployee.getEmployeeId());
employee.setName(dsEmployee.getName());
d=dsEmployee.getDesignation();
designation=new Designation();
designation.setCode(d.getCode());
designation.setTitle(d.getTitle());
employee.setDesignation(designation);
employee.setDateOfBirth((Date)dsEmployee.getDateOfBirth().clone());
employee.setGender((dsEmployee.getGender()=='M')?GENDER.MALE:GENDER.FEMALE);
employee.setIsIndian(dsEmployee.getIsIndian());
employee.setBasicSalary(dsEmployee.getBasicSalary());
employee.setPANNumber(dsEmployee.getPANNumber());
employee.setAadharCardNumber(dsEmployee.getAadharCardNumber());
employees.add(employee);
}
return employees;
}
public int getEmployeeCount()
{
return employeesSet.size();
}
public int getEmployeeCountByDesignationCode(int designationCode) throws BLException
{
Set<EmployeeInterface> ets=this.designationCodeWiseEmployeesMap.get(designationCode);
if(ets==null) return 0;
return ets.size();
}
public boolean isDesignationAlloted(int designationCode) throws BLException
{
return this.designationCodeWiseEmployeesMap.containsKey(designationCode);
}
public boolean employeeIdExists(String employeeId)
{
return employeeIdWiseEmployeesMap.containsKey(employeeId.toUpperCase());
}
public boolean employeePANNumberExists(String panNumber)
{
return panNumberWiseEmployeesMap.containsKey(panNumber.toUpperCase());
}
public boolean employeeAadharCardNumberExists(String aadharCardNumber)
{
return aadharCardNumberWiseEmployeesMap.containsKey(aadharCardNumber.toUpperCase());
}
}