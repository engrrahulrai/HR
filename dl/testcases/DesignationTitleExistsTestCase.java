import com.rahul.rai.hr.dl.interfaces.dao.*;
import com.rahul.rai.hr.dl.dao.*;
import com.rahul.rai.hr.dl.exceptions.*;
public class DesignationTitleExistsTestCase
{
public static void main(String gg[])
{
String title=gg[0];
try
{
DesignationDAO designationDAO;
designationDAO=new DesignationDAO();
System.out.println(designationDAO.titleExists(title));
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}