import com.rahul.rai.hr.dl.exceptions.*;
import com.rahul.rai.hr.dl.interfaces.dto.*;
import com.rahul.rai.hr.dl.interfaces.dao.*;
import com.rahul.rai.hr.dl.dto.*;
import com.rahul.rai.hr.dl.dao.*;
public class EmployeeEmployeeIdExistsTestCase
{
public static void main(String gg[])
{
String employeeId=gg[0];
try
{
System.out.println("Employee Id.: "+employeeId+" exists : "+new EmployeeDAO().employeeIdExists(employeeId));
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}