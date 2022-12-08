import com.rahul.rai.hr.dl.dao.*;
import com.rahul.rai.hr.dl.interfaces.dao.*;
import com.rahul.rai.hr.dl.exceptions.*;
public class DesignationGetCountTestCase
{
public static void main(String gg[])
{
try
{
DesignationDAO designationDAO;
designationDAO=new DesignationDAO();
System.out.println("Number of Records : "+designationDAO.getCount());
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}