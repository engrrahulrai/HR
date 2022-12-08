import com.rahul.rai.hr.dl.interfaces.dao.*;
import com.rahul.rai.hr.dl.dao.*;
import com.rahul.rai.hr.dl.exceptions.*;
public class DesignationDeleteTestCase
{
public static void main(String gg[])
{
int code=Integer.parseInt(gg[0]);
try
{
DesignationDAO designationDAO;
designationDAO=new DesignationDAO();
designationDAO.delete(code);
System.out.println("Successfully Deleted");
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}