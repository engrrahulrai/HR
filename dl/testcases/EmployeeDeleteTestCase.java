import com.rahul.rai.hr.dl.interfaces.dao.*;
import com.rahul.rai.hr.dl.dao.*;
import com.rahul.rai.hr.dl.exceptions.*;
public class EmployeeDeleteTestCase
{
public static void main(String gg[])
{
String employeeId=gg[0];
try
{
EmployeeDAOInterface employeeDAO;
employeeDAO=new EmployeeDAO();
employeeDAO.delete(employeeId);
System.out.println("Employee details are deleted");
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}
