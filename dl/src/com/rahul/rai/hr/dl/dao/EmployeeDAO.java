package com.rahul.rai.hr.dl.dao;
import com.rahul.rai.enums.*;
import com.rahul.rai.hr.dl.interfaces.dao.*;
import com.rahul.rai.hr.dl.interfaces.dto.*;
import com.rahul.rai.hr.dl.dto.*;
import com.rahul.rai.hr.dl.exceptions.*;
import java.util.*;
import java.io.*;
import java.math.*;
import java.text.*;
public class EmployeeDAO implements EmployeeDAOInterface
{
private static final String FILE_NAME="employee.data";
public void add(EmployeeDTOInterface employeeDTO) throws DAOException
{
if(employeeDTO==null) throw new DAOException("Employee is null");
String employeeId;
String name=employeeDTO.getName();
if(name==null) throw new DAOException("Name is null");
name=name.trim();
if(name.length()==0) throw new DAOException("Length of name is zero");
int designationCode=employeeDTO.getDesignationCode();
if(designationCode<=0) throw new DAOException("Invalid desgination code : "+designationCode);
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
boolean designationCodeExists=designationDAO.codeExists(designationCode);
if(!designationCodeExists) throw new DAOException("Invalid designation code : "+designationCode);
Date dateOfBirth=employeeDTO.getDateOfBirth();
if(dateOfBirth==null) throw new DAOException("Date of Birth is null");
char gender=employeeDTO.getGender();
if(gender==' ') throw new DAOException("Gender is not set Male/Female");
boolean isIndian=employeeDTO.getIsIndian();
BigDecimal basicSalary=employeeDTO.getBasicSalary();
if(basicSalary==null) throw new DAOException("Basic salary is null");
if(basicSalary.signum()==-1) throw new DAOException("Basic salary is negative");
String panNumber=employeeDTO.getPANNumber();
if(panNumber==null) throw new DAOException("PAN Number is null");
panNumber=panNumber.trim();
if(panNumber.length()==0) throw new DAOException("Length of PAN number is zero");
String aadharCardNumber=employeeDTO.getAadharCardNumber();
if(aadharCardNumber==null) throw new DAOException("Aadhar card number is null");
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) throw new DAOException("Length of Aadhar card number is zero");
try
{
int lastGeneratedEmployeeId=10000000;
String lastGeneratedEmployeeIdString="";
int recordCount=0;
String recordCountString="";
File file=new File(FILE_NAME);
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
lastGeneratedEmployeeIdString=String.format("%-10s","10000000");
recordCountString=String.format("%-10s","0");
randomAccessFile.writeBytes(lastGeneratedEmployeeIdString+"\n");
randomAccessFile.writeBytes(recordCountString+"\n");
}
else
{
lastGeneratedEmployeeId=Integer.parseInt(randomAccessFile.readLine().trim());
recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
}
String fPANNumber;
String fAadharCardNumber;
int x;
boolean panNumberExists=false;
boolean aadharCardNumberExists=false;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
for(x=1;x<=7;x++) randomAccessFile.readLine();
fPANNumber=randomAccessFile.readLine();
fAadharCardNumber=randomAccessFile.readLine();
if(panNumberExists==false && fPANNumber.equalsIgnoreCase(panNumber))
{
panNumberExists=true;
}
if(aadharCardNumberExists==false && fAadharCardNumber.equalsIgnoreCase(aadharCardNumber))
{
aadharCardNumberExists=true;
}
if(aadharCardNumberExists && panNumberExists) break;
}
if(aadharCardNumberExists && panNumberExists)
{
randomAccessFile.close();
throw new DAOException("PAN card numbber ("+panNumber+") and Aadhar card number ("+aadharCardNumber+") exists.");
}
if(panNumberExists)
{
randomAccessFile.close();
throw new DAOException("PAN number ("+panNumber+") exists.");
}
if(aadharCardNumberExists)
{
randomAccessFile.close();
throw new DAOException("Aadhar card number ("+aadharCardNumber+") exists.");
}
lastGeneratedEmployeeId++;
employeeId="A"+lastGeneratedEmployeeId;
recordCount++;
randomAccessFile.writeBytes(employeeId+"\n");
randomAccessFile.writeBytes(name+"\n");
randomAccessFile.writeBytes(designationCode+"\n");
SimpleDateFormat simpleDateFormat;
simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
randomAccessFile.writeBytes(simpleDateFormat.format(dateOfBirth)+"\n");
randomAccessFile.writeBytes(gender+"\n");
randomAccessFile.writeBytes(isIndian+"\n");
randomAccessFile.writeBytes(basicSalary.toPlainString()+"\n");
randomAccessFile.writeBytes(panNumber+"\n");
randomAccessFile.writeBytes(aadharCardNumber+"\n");
randomAccessFile.seek(0);
lastGeneratedEmployeeIdString=String.format("%-10d",lastGeneratedEmployeeId);
recordCountString=String.format("%-10d",recordCount);
randomAccessFile.writeBytes(lastGeneratedEmployeeIdString+"\n");
randomAccessFile.writeBytes(recordCountString+"\n");
randomAccessFile.close();
employeeDTO.setEmployeeId(employeeId);
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public void update(EmployeeDTOInterface employeeDTO) throws DAOException
{
if(employeeDTO==null) throw new DAOException("Employee is null");
String employeeId=employeeDTO.getEmployeeId();
if(employeeId==null) throw new DAOException("Employee Id. is null");
if(employeeId.length()==0) throw new DAOException("Length of employee id is zero");
String name=employeeDTO.getName();
if(name==null) throw new DAOException("Name is null");
name=name.trim();
if(name.length()==0) throw new DAOException("Length of name is zero");
int designationCode=employeeDTO.getDesignationCode();
if(designationCode<=0) throw new DAOException("Invalid designation code : "+designationCode);
if(!(new DesignationDAO().codeExists(designationCode))) throw new DAOException("Invalid designation code : "+designationCode);
Date dateOfBirth=employeeDTO.getDateOfBirth();
if(dateOfBirth==null) throw new DAOException("Date of birth is null");
char gender=employeeDTO.getGender();
if(gender==' ') throw new DAOException("Gender is not set Male/Female");
boolean isIndian=employeeDTO.getIsIndian();
BigDecimal basicSalary=employeeDTO.getBasicSalary();
if(basicSalary==null) throw new DAOException("Basic salary is null");
if(basicSalary.signum()==-1) throw new DAOException("Basic salary is negative");
String panNumber=employeeDTO.getPANNumber();
if(panNumber==null) throw new DAOException("PAN number is null");
panNumber=panNumber.trim();
if(panNumber.length()==0) throw new DAOException("Length of PAN number is zero");
String aadharCardNumber=employeeDTO.getAadharCardNumber();
if(aadharCardNumber==null) throw new DAOException("Aadhar card number is null");
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) throw new DAOException("Length of Aadhar card number is zero");
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("Invalid employee id. : "+employeeId);
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0) throw new DAOException("Invalid employee id. : "+employeeId);
randomAccessFile.readLine();
randomAccessFile.readLine();
String fEmployeeId;
String fPANNumber;
String fAadharCardNumber;
int x;
long foundAt=0;
boolean employeeIdFound=false;
boolean panNumberFound=false;
boolean aadharCardNumberFound=false;
String panNumberFoundAgainstEmployeeId="";
String aadharCardNumberFoundAgainstEmployeeId="";
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
if(employeeIdFound==false) foundAt=randomAccessFile.getFilePointer();
fEmployeeId=randomAccessFile.readLine();
for(x=1;x<=6;x++) randomAccessFile.readLine();
fPANNumber=randomAccessFile.readLine();
fAadharCardNumber=randomAccessFile.readLine();
if(employeeIdFound==false && employeeId.equalsIgnoreCase(fEmployeeId))
{
employeeIdFound=true;
}
if(panNumberFound==false && panNumber.equalsIgnoreCase(fPANNumber))
{
panNumberFound=true;
panNumberFoundAgainstEmployeeId=fEmployeeId;
}
if(aadharCardNumberFound==false && aadharCardNumber.equalsIgnoreCase(fAadharCardNumber))
{
aadharCardNumberFound=true;
aadharCardNumberFoundAgainstEmployeeId=fEmployeeId;
}
if(employeeIdFound && panNumberFound && aadharCardNumberFound) break;
}
if(employeeIdFound==false)
{
randomAccessFile.close();
throw new DAOException("Invalid employee id. : "+employeeId);
}
boolean panNumberExists=false;
boolean aadharCardNumberExists=false;
if(panNumberFound==true && panNumberFoundAgainstEmployeeId.equalsIgnoreCase(employeeId)==false)
{
panNumberExists=true;
}
if(aadharCardNumberFound==true && aadharCardNumberFoundAgainstEmployeeId.equalsIgnoreCase(employeeId)==false)
{
aadharCardNumberExists=true;
}
if(panNumberExists && aadharCardNumberExists)
{
randomAccessFile.close();
throw new DAOException("PAN number ("+panNumber+") and Aadhar card number ("+aadharCardNumber+") both exists.");
}
if(panNumberExists)
{
randomAccessFile.close();
throw new DAOException("PAN number ("+panNumber+") exists.");
}
if(aadharCardNumberExists)
{
randomAccessFile.close();
throw new DAOException("Aadhar card number ("+aadharCardNumber+") exists.");
}
randomAccessFile.seek(foundAt);
for(x=1;x<=9;x++) randomAccessFile.readLine();
File tmpFile=new File("tmp.tmp");
if(tmpFile.exists()==true) tmpFile.delete();
RandomAccessFile tmpRandomAccessFile;
tmpRandomAccessFile=new RandomAccessFile(tmpFile,"rw");
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
}
randomAccessFile.seek(foundAt);
randomAccessFile.writeBytes(employeeId+"\n");
randomAccessFile.writeBytes(name+"\n");
randomAccessFile.writeBytes(designationCode+"\n");
SimpleDateFormat simpleDateFormat;
simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
randomAccessFile.writeBytes(simpleDateFormat.format(dateOfBirth)+"\n");
randomAccessFile.writeBytes(gender+"\n");
randomAccessFile.writeBytes(isIndian+"\n");
randomAccessFile.writeBytes(basicSalary.toPlainString()+"\n");
randomAccessFile.writeBytes(panNumber+"\n");
randomAccessFile.writeBytes(aadharCardNumber+"\n");
tmpRandomAccessFile.seek(0);
while(tmpRandomAccessFile.getFilePointer()<tmpRandomAccessFile.length())
{
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
}
randomAccessFile.setLength(randomAccessFile.getFilePointer());
tmpRandomAccessFile.setLength(0);
randomAccessFile.close();
tmpRandomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public void delete(String employeeId) throws DAOException
{
if(employeeId==null) throw new DAOException("Employee id is null");
employeeId=employeeId.trim();
if(employeeId.length()==0) throw new DAOException("Length of employee id is zero");
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("Invalid employee id. : "+employeeId);
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0) throw new DAOException("Invalid employee id. : "+employeeId);
randomAccessFile.readLine();
int recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
String fEmployeeId;
int x;
long foundAt=0;
boolean employeeIdFound=false;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
foundAt=randomAccessFile.getFilePointer();
fEmployeeId=randomAccessFile.readLine();
for(x=1;x<=8;x++) randomAccessFile.readLine();
if(employeeId.equalsIgnoreCase(fEmployeeId))
{
employeeIdFound=true;
break;
}
}
if(employeeIdFound==false)
{
randomAccessFile.close();
throw new DAOException("Invalid employee id. : "+employeeId);
}
File tmpFile=new File("tmp.tmp");
if(tmpFile.exists()==true) tmpFile.delete();
RandomAccessFile tmpRandomAccessFile;
tmpRandomAccessFile=new RandomAccessFile(tmpFile,"rw");
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
}
randomAccessFile.seek(foundAt);
tmpRandomAccessFile.seek(0);
while(tmpRandomAccessFile.getFilePointer()<tmpRandomAccessFile.length())
{
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
}
randomAccessFile.setLength(randomAccessFile.getFilePointer());
recordCount--;
String recordCountString=String.format("%-10d",recordCount);
randomAccessFile.seek(0);
randomAccessFile.readLine();
randomAccessFile.writeBytes(recordCountString+"\n");
randomAccessFile.close();
tmpRandomAccessFile.setLength(0);
tmpRandomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public Set<EmployeeDTOInterface> getAll() throws DAOException
{
Set<EmployeeDTOInterface> employees=new TreeSet<>();
try
{
File  file=new File(FILE_NAME);
if(file.exists()==false) return employees;
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return employees;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
EmployeeDTOInterface employeeDTO;
SimpleDateFormat simpleDateFormat;
simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
char fGender;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(randomAccessFile.readLine());
employeeDTO.setName(randomAccessFile.readLine());
employeeDTO.setDesignationCode(Integer.parseInt(randomAccessFile.readLine()));
try
{
employeeDTO.setDateOfBirth(simpleDateFormat.parse(randomAccessFile.readLine()));
}catch(ParseException pe)
{
// do nothing
}
fGender=randomAccessFile.readLine().charAt(0);
if(fGender=='M')
{
employeeDTO.setGender(GENDER.MALE);
}
if(fGender=='F')
{
employeeDTO.setGender(GENDER.FEMALE);
}
employeeDTO.setIsIndian(Boolean.parseBoolean(randomAccessFile.readLine()));
employeeDTO.setBasicSalary(new BigDecimal(randomAccessFile.readLine()));
employeeDTO.setPANNumber(randomAccessFile.readLine());
employeeDTO.setAadharCardNumber(randomAccessFile.readLine());
employees.add(employeeDTO);
}
randomAccessFile.close();
return employees;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public Set<EmployeeDTOInterface> getByDesignationCode(int designationCode) throws DAOException
{
if(designationCode<=0) throw new DAOException("Invalid designation code : "+designationCode);
if(!(new DesignationDAO().codeExists(designationCode))) throw new DAOException("Invalid designation code : "+designationCode);
Set<EmployeeDTOInterface> employees=new TreeSet<>();
try
{
File  file=new File(FILE_NAME);
if(file.exists()==false) return employees;
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return employees;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
EmployeeDTOInterface employeeDTO=null;
SimpleDateFormat simpleDateFormat;
simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
String fEmployeeId="";
String fName="";
int fDesignationCode=0;
int x;
char fGender;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fEmployeeId=randomAccessFile.readLine();
fName=randomAccessFile.readLine();
fDesignationCode=Integer.parseInt(randomAccessFile.readLine());
if(fDesignationCode!=designationCode)
{
for(x=1;x<=6;x++) randomAccessFile.readLine();
continue;
}
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setName(fName);
employeeDTO.setDesignationCode(fDesignationCode);
try
{
employeeDTO.setDateOfBirth(simpleDateFormat.parse(randomAccessFile.readLine()));
}catch(ParseException pe)
{
randomAccessFile.close();
throw new DAOException(pe.getMessage());
}
fGender=randomAccessFile.readLine().charAt(0);
if(fGender=='M')
{
employeeDTO.setGender(GENDER.MALE);
}
if(fGender=='F')
{
employeeDTO.setGender(GENDER.FEMALE);
}
employeeDTO.setIsIndian(Boolean.parseBoolean(randomAccessFile.readLine()));
employeeDTO.setBasicSalary(new BigDecimal(randomAccessFile.readLine()));
employeeDTO.setPANNumber(randomAccessFile.readLine());
employeeDTO.setAadharCardNumber(randomAccessFile.readLine());
employees.add(employeeDTO);
}
randomAccessFile.close();
return employees;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public boolean isDesignationAlloted(int designationCode) throws DAOException
{
if(designationCode<=0) return false;
if(!(new DesignationDAO().codeExists(designationCode))) return false;
try
{
File  file=new File(FILE_NAME);
if(file.exists()==false) return false;
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return false;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
int fDesignationCode=0;
int x;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
randomAccessFile.readLine();
randomAccessFile.readLine();
fDesignationCode=Integer.parseInt(randomAccessFile.readLine());
if(fDesignationCode==designationCode)
{
randomAccessFile.close();
return true;
}
for(x=1;x<=6;x++) randomAccessFile.readLine();
}
randomAccessFile.close();
return false;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public EmployeeDTOInterface getByEmployeeId(String employeeId) throws DAOException
{
if(employeeId==null) throw new DAOException("Invalid employee id. : "+employeeId);
employeeId=employeeId.trim();
if(employeeId.length()==0) throw new DAOException("Employee id length is zero");
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("Invalid employee id : "+employeeId);
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid employee id : "+employeeId);
}
randomAccessFile.readLine();
randomAccessFile.readLine();
String fEmployeeId="";
int x;
EmployeeDTOInterface employeeDTO;
SimpleDateFormat simpleDateFormat;
simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
char fGender;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fEmployeeId=randomAccessFile.readLine().trim();
if(employeeId.equalsIgnoreCase(fEmployeeId))
{
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(employeeId);
employeeDTO.setName(randomAccessFile.readLine());
employeeDTO.setDesignationCode(Integer.parseInt(randomAccessFile.readLine()));
try
{
employeeDTO.setDateOfBirth(simpleDateFormat.parse(randomAccessFile.readLine()));
}catch(ParseException parseException)
{
// do nothing
}
fGender=randomAccessFile.readLine().charAt(0);
if(fGender=='M')
{
employeeDTO.setGender(GENDER.MALE);
}
if(fGender=='F')
{
employeeDTO.setGender(GENDER.FEMALE);
}
employeeDTO.setIsIndian(Boolean.parseBoolean(randomAccessFile.readLine()));
employeeDTO.setBasicSalary(new BigDecimal(randomAccessFile.readLine()));
employeeDTO.setPANNumber(randomAccessFile.readLine());
employeeDTO.setAadharCardNumber(randomAccessFile.readLine());
randomAccessFile.close();
return employeeDTO;
}
for(x=1;x<=8;x++) randomAccessFile.readLine();
}
randomAccessFile.close();
throw new DAOException("Invalid employee id : "+employeeId);
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public EmployeeDTOInterface getByPANNumber(String panNumber) throws DAOException
{
if(panNumber==null) throw new DAOException("PAN number is null");
panNumber=panNumber.trim();
if(panNumber.length()==0) throw new DAOException("PAN number length is zero");
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("Invalid PAN number : "+panNumber);
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid PAN number : "+panNumber);
}
randomAccessFile.readLine();
randomAccessFile.readLine();
String fEmployeeId;
String fName;
int fDesignationCode;
Date fDateOfBirth=null;
SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
char fGender;
boolean fIsIndian;
BigDecimal fBasicSalary;
String fPANNumber;
EmployeeDTOInterface employeeDTO;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fEmployeeId=randomAccessFile.readLine();
fName=randomAccessFile.readLine();
fDesignationCode=Integer.parseInt(randomAccessFile.readLine());
try
{
fDateOfBirth=simpleDateFormat.parse(randomAccessFile.readLine());
}catch(ParseException parseException)
{
// do nothing
}
fGender=randomAccessFile.readLine().charAt(0);
fIsIndian=Boolean.parseBoolean(randomAccessFile.readLine());
fBasicSalary=new BigDecimal(randomAccessFile.readLine());
fPANNumber=randomAccessFile.readLine();
if(panNumber.equalsIgnoreCase(fPANNumber))
{
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setName(fName);
employeeDTO.setDesignationCode(fDesignationCode);
employeeDTO.setDateOfBirth(fDateOfBirth);
if(fGender=='M')
{
employeeDTO.setGender(GENDER.MALE);
}
if(fGender=='F')
{
employeeDTO.setGender(GENDER.FEMALE);
}
employeeDTO.setIsIndian(fIsIndian);
employeeDTO.setBasicSalary(fBasicSalary);
employeeDTO.setPANNumber(fPANNumber);
employeeDTO.setAadharCardNumber(randomAccessFile.readLine());
randomAccessFile.close();
return employeeDTO;
}
randomAccessFile.readLine();
}
randomAccessFile.close();
throw new DAOException("Invalid PAN number : "+panNumber);
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public EmployeeDTOInterface getByAadharCardNumber(String aadharCardNumber) throws DAOException
{
if(aadharCardNumber==null) throw new DAOException("Aadhar card number is null");
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) throw new DAOException("Aadhar card number length is zero");
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("Invalid Aadhar card number : "+aadharCardNumber);
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid Aadhar card number : "+aadharCardNumber);
}
randomAccessFile.readLine();
randomAccessFile.readLine();
String fEmployeeId;
String fName;
int fDesignationCode;
Date fDateOfBirth=null;
SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
char fGender;
boolean fIsIndian;
BigDecimal fBasicSalary;
String fPANNumber;
String fAadharCardNumber;
EmployeeDTOInterface employeeDTO;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fEmployeeId=randomAccessFile.readLine();
fName=randomAccessFile.readLine();
fDesignationCode=Integer.parseInt(randomAccessFile.readLine());
try
{
fDateOfBirth=simpleDateFormat.parse(randomAccessFile.readLine());
}catch(ParseException parseException)
{
// do nothing
}
fGender=randomAccessFile.readLine().charAt(0);
fIsIndian=Boolean.parseBoolean(randomAccessFile.readLine());
fBasicSalary=new BigDecimal(randomAccessFile.readLine());
fPANNumber=randomAccessFile.readLine();
fAadharCardNumber=randomAccessFile.readLine();
if(aadharCardNumber.equalsIgnoreCase(fAadharCardNumber))
{
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setName(fName);
employeeDTO.setDesignationCode(fDesignationCode);
employeeDTO.setDateOfBirth(fDateOfBirth);
if(fGender=='M')
{
employeeDTO.setGender(GENDER.MALE);
}
if(fGender=='F')
{
employeeDTO.setGender(GENDER.FEMALE);
}
employeeDTO.setIsIndian(fIsIndian);
employeeDTO.setBasicSalary(fBasicSalary);
employeeDTO.setPANNumber(fPANNumber);
employeeDTO.setAadharCardNumber(fAadharCardNumber);
randomAccessFile.close();
return employeeDTO;
}
}
randomAccessFile.close();
throw new DAOException("Invalid Aadhar card number : "+aadharCardNumber);
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public boolean employeeIdExists(String employeeId) throws DAOException
{
if(employeeId==null) return false;
employeeId=employeeId.trim();
if(employeeId.length()==0) return false;
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) return false;
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return false;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
String fEmployeeId;
int x;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fEmployeeId=randomAccessFile.readLine().trim();
if(employeeId.equalsIgnoreCase(fEmployeeId))
{
randomAccessFile.close();
return true;
}
for(x=1;x<=8;x++) randomAccessFile.readLine();
}
randomAccessFile.close();
return false;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public boolean panNumberExists(String panNumber) throws DAOException
{
if(panNumber==null) return false;
panNumber=panNumber.trim();
if(panNumber.length()==0) return false;
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) return false;
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return false;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
String fPANNumber;
int x;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
for(x=1;x<=7;x++) randomAccessFile.readLine();
fPANNumber=randomAccessFile.readLine();
if(panNumber.equalsIgnoreCase(fPANNumber))
{
randomAccessFile.close();
return true;
}
randomAccessFile.readLine();
}
randomAccessFile.close();
return false;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public boolean aadharCardNumberExists(String aadharCardNumber) throws DAOException
{
if(aadharCardNumber==null) return false;
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) return false;
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) return false;
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return false;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
String fAadharCardNumber;
int x;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
for(x=1;x<=8;x++) randomAccessFile.readLine();
fAadharCardNumber=randomAccessFile.readLine();
if(aadharCardNumber.equalsIgnoreCase(fAadharCardNumber))
{
randomAccessFile.close();
return true;
}
}
randomAccessFile.close();
return false;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public int getCount() throws DAOException
{
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) return 0;
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return 0;
}
randomAccessFile.readLine();
int recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
randomAccessFile.close();
return recordCount;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public int getCountByDesignationCode(int designationCode) throws DAOException
{
if(designationCode<=0) throw new DAOException("Invalid designation code : "+designationCode);
if(!(new DesignationDAO().codeExists(designationCode))) throw new DAOException("Invalid designation code : "+designationCode);
try
{
File  file=new File(FILE_NAME);
if(file.exists()==false) return 0;
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return 0;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
EmployeeDTOInterface employeeDTO=null;
int fDesignationCode=0;
int x;
int recordCount=0;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
randomAccessFile.readLine();
randomAccessFile.readLine();
fDesignationCode=Integer.parseInt(randomAccessFile.readLine());
if(fDesignationCode==designationCode)
{
recordCount++;
}
for(x=1;x<=6;x++) randomAccessFile.readLine();
}
randomAccessFile.close();
return recordCount;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}

}