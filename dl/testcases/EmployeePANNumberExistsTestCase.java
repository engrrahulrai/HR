import com.rahul.rai.hr.dl.exceptions.*;
import com.rahul.rai.hr.dl.interfaces.dto.*;
import com.rahul.rai.hr.dl.interfaces.dao.*;
import com.rahul.rai.hr.dl.dto.*;
import com.rahul.rai.hr.dl.dao.*;
public class EmployeePANNumberExistsTestCase
{
public static void main(String gg[])
{
String panNumber=gg[0];
try
{
System.out.println("PAN number : "+panNumber+" exists : "+new EmployeeDAO().panNumberExists(panNumber));
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}