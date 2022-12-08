import com.rahul.rai.hr.dl.interfaces.dao.*;
import com.rahul.rai.hr.dl.interfaces.dto.*;
import com.rahul.rai.hr.dl.dao.*;
import com.rahul.rai.hr.dl.dto.*;
import com.rahul.rai.hr.dl.exceptions.*;
import java.util.*;
import java.math.*;
import java.text.*;
public class EmployeeGetByDesignationTestCase
{
public static void main(String gg[])
{
int designationCode=Integer.parseInt(gg[0]);
try
{
Set<EmployeeDTOInterface> employeeSet;
EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();
employeeSet=employeeDAO.getByDesignationCode(designationCode);
employeeSet.forEach((employeeDTO)->{
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
System.out.println("Employee id : "+employeeDTO.getEmployeeId());
System.out.println("Name : "+employeeDTO.getName());
System.out.println("Designation code : "+employeeDTO.getDesignationCode());
System.out.println("Date of Birth : "+sdf.format(employeeDTO.getDateOfBirth()));
System.out.println("Gender : "+employeeDTO.getGender());
System.out.println("Indian : "+employeeDTO.getIsIndian());
System.out.println("Basic Salary : "+employeeDTO.getBasicSalary().toPlainString());
System.out.println("PAN Number : "+employeeDTO.getPANNumber());
System.out.println("Aadhar card number : "+employeeDTO.getAadharCardNumber());
});

}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}