import com.rahul.rai.hr.dl.interfaces.dao.*;
import com.rahul.rai.hr.dl.dao.*;
import com.rahul.rai.hr.dl.exceptions.*;
public class DesignationCodeExistsTestCase
{
public static void main(String gg[])
{
int code=Integer.parseInt(gg[0]);
try
{
DesignationDAO designationDAO;
designationDAO=new DesignationDAO();
System.out.println(designationDAO.codeExists(code));
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}