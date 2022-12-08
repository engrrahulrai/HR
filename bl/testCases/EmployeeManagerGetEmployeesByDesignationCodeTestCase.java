import com.rahul.rai.hr.bl.interfaces.managers.*;
import com.rahul.rai.hr.bl.managers.*;
import com.rahul.rai.hr.bl.interfaces.pojo.*;
import com.rahul.rai.hr.bl.pojo.*;
import com.rahul.rai.hr.bl.exceptions.*;
import com.rahul.rai.enums.*;
import java.math.*;
import java.util.*;
import java.text.*;
class EmployeeManagerGetEmployeesByDesignationCodeTestCase
{
public static void main(String gg[])
{
int designationCode=Integer.parseInt(gg[0]);
try
{
SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
EmployeeManagerInterface employeeManager;
employeeManager=EmployeeManager.getEmployeeManager();
Set<EmployeeInterface> employees;
employees=employeeManager.getEmployeesByDesignationCode(designationCode);
for(EmployeeInterface employee:employees)
{
System.out.println("Employee Id. : "+employee.getEmployeeId());
System.out.println("Name : "+employee.getName());
System.out.println("Designation code : "+employee.getDesignation().getCode());
System.out.println("Designation title : "+employee.getDesignation().getTitle());
System.out.println("Date of birth : "+simpleDateFormat.format(employee.getDateOfBirth()));
System.out.println("Gender : "+employee.getGender());
System.out.println("Is Indian : "+employee.getIsIndian());
System.out.println("Basic Salary : "+employee.getBasicSalary());
System.out.println("PAN number : "+employee.getPANNumber());
System.out.println("Aadhar card number : "+employee.getAadharCardNumber());
System.out.println("**************************************");
}
}catch(BLException blException)
{
if(blException.hasGenericException())
{
System.out.println("Generic : "+blException.getGenericException());
}
List<String> properties=blException.getProperties();
for(String property:properties)
{
System.out.println(blException.getException(property));
}
}
}
}