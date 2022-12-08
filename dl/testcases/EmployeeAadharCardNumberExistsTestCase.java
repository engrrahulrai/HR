import com.rahul.rai.hr.dl.exceptions.*;
import com.rahul.rai.hr.dl.interfaces.dto.*;
import com.rahul.rai.hr.dl.interfaces.dao.*;
import com.rahul.rai.hr.dl.dto.*;
import com.rahul.rai.hr.dl.dao.*;
public class EmployeeAadharCardNumberExistsTestCase
{
public static void main(String gg[])
{
String aadharCardNumber=gg[0];
try
{
System.out.println("Aadhar card number : "+aadharCardNumber+" exists : "+new EmployeeDAO().aadharCardNumberExists(aadharCardNumber));
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}