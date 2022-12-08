import com.rahul.rai.hr.bl.interfaces.managers.*;
import com.rahul.rai.hr.bl.managers.*;
import com.rahul.rai.hr.bl.interfaces.pojo.*;
import com.rahul.rai.hr.bl.pojo.*;
import com.rahul.rai.hr.bl.exceptions.*;
import com.rahul.rai.enums.*;
import java.math.*;
import java.util.*;
import java.text.*;
class EmployeeManagerUpdateTestCase
{
public static void main(String gg[])
{
EmployeeInterface employee=new Employee();
employee.setEmployeeId(gg[0]);
employee.setName(gg[1]);
DesignationInterface designation=new Designation();
designation.setCode(6);
designation.setTitle("assisstant");
employee.setDesignation(designation);
SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
Date dateOfBirth=null;
try
{
employee.setDateOfBirth(simpleDateFormat.parse(gg[2]));
}catch(ParseException parseException)
{
System.out.println(parseException.getMessage());
}
char gender=gg[3].charAt(0);
employee.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE);
employee.setIsIndian(Boolean.parseBoolean(gg[4]));
employee.setBasicSalary(new BigDecimal(gg[5]));
employee.setPANNumber(gg[6]);
employee.setAadharCardNumber(gg[7]);
try
{
EmployeeManagerInterface employeeManager;
employeeManager=EmployeeManager.getEmployeeManager();
employeeManager.updateEmployee(employee);
System.out.println("Employee updated");
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