import com.rahul.rai.hr.dl.interfaces.dao.*;
import com.rahul.rai.hr.dl.interfaces.dto.*;
import com.rahul.rai.hr.dl.dao.*;
import com.rahul.rai.hr.dl.dto.*;
import com.rahul.rai.enums.*;
import com.rahul.rai.hr.dl.exceptions.*;
import java.util.*;
import java.math.*;
import java.text.*;
public class EmployeeUpdateTestCase
{
public static void main(String gg[])
{
EmployeeDTOInterface employeeDTO;
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(gg[0]);
employeeDTO.setName(gg[1]);
employeeDTO.setDesignationCode(Integer.parseInt(gg[2]));
SimpleDateFormat simpleDateFormat;
simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
try
{
employeeDTO.setDateOfBirth(simpleDateFormat.parse(gg[3]));
}catch(ParseException parseException)
{
System.out.println(parseException.getMessage());
}
char gender=gg[4].charAt(0);
if(gender=='M')
{
employeeDTO.setGender(GENDER.MALE);
}
if(gender=='F')
{
employeeDTO.setGender(GENDER.FEMALE);
}
employeeDTO.setIsIndian(Boolean.parseBoolean(gg[5]));
employeeDTO.setBasicSalary(new BigDecimal(gg[6]));
employeeDTO.setPANNumber(gg[7]);
employeeDTO.setAadharCardNumber(gg[8]);
try
{
EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();
employeeDAO.update(employeeDTO);
System.out.println("Employee details updated");
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}
