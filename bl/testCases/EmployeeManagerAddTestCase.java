import com.rahul.rai.hr.bl.interfaces.managers.*;
import com.rahul.rai.hr.bl.managers.*;
import com.rahul.rai.hr.bl.interfaces.pojo.*;
import com.rahul.rai.hr.bl.pojo.*;
import com.rahul.rai.hr.bl.exceptions.*;
import com.rahul.rai.enums.*;
import java.math.*;
import java.util.*;
import java.text.*;
class EmployeeManagerAddTestCase
{
public static void main(String gg[])
{
EmployeeInterface employee=new Employee();
employee.setName(gg[0]);
DesignationInterface designation=new Designation();
designation.setCode(8);
designation.setTitle("clerk");
employee.setDesignation(designation);
SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
Date dateOfBirth=null;
try
{
employee.setDateOfBirth(simpleDateFormat.parse(gg[1]));
}catch(ParseException parseException)
{
System.out.println(parseException.getMessage());
}
char gender=gg[2].charAt(0);
employee.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE);
employee.setIsIndian(Boolean.parseBoolean(gg[3]));
employee.setBasicSalary(new BigDecimal(gg[4]));
employee.setPANNumber(gg[5]);
employee.setAadharCardNumber(gg[6]);
try
{
EmployeeManagerInterface employeeManager;
employeeManager=EmployeeManager.getEmployeeManager();
employeeManager.addEmployee(employee);
System.out.println("Employee added with employee id : "+employee.getEmployeeId());
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