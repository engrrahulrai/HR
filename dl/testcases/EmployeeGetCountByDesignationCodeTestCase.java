import com.rahul.rai.hr.dl.interfaces.dao.*;
import com.rahul.rai.hr.dl.interfaces.dto.*;
import com.rahul.rai.hr.dl.dao.*;
import com.rahul.rai.hr.dl.dto.*;
import com.rahul.rai.hr.dl.exceptions.*;
public class EmployeeGetCountByDesignationCodeTestCase
{
public static void main(String gg[])
{
int designationCode=Integer.parseInt(gg[0]);
try
{
System.out.println("designation code : "+designationCode+" count is : "+new EmployeeDAO().getCountByDesignationCode(designationCode));
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}