import com.rahul.rai.hr.dl.interfaces.dao.*;
import com.rahul.rai.hr.dl.interfaces.dto.*;
import com.rahul.rai.hr.dl.dao.*;
import com.rahul.rai.hr.dl.dto.*;
import com.rahul.rai.hr.dl.exceptions.*;
public class EmployeeGetCountTestCase
{
public static void main(String gg[])
{
try
{
System.out.println("Total number of workers : "+new EmployeeDAO().getCount());
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}