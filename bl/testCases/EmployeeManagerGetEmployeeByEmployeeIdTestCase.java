import com.rahul.rai.hr.bl.interfaces.managers.*;
import com.rahul.rai.hr.bl.managers.*;
import com.rahul.rai.hr.bl.interfaces.pojo.*;
import com.rahul.rai.hr.bl.pojo.*;
import com.rahul.rai.hr.bl.exceptions.*;
import com.rahul.rai.enums.*;
import java.math.*;
import java.util.*;
import java.text.*;
class EmployeeManagerGetEmployeeByEmployeeIdTestCase
{
public static void main(String gg[])
{
EmployeeInterface employee;
String employeeId=gg[0];
try
{
SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
EmployeeManagerInterface employeeManager;
employeeManager=EmployeeManager.getEmployeeManager();
employee=employeeManager.getEmployeeByEmployeeId(employeeId);
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