import com.rahul.rai.hr.dl.interfaces.dao.*;
import com.rahul.rai.hr.dl.interfaces.dto.*;
import com.rahul.rai.hr.dl.dao.*;
import com.rahul.rai.hr.dl.dto.*;
import com.rahul.rai.hr.dl.exceptions.*;
public class DesignationGetByTitleTestCase
{
public static void main(String gg[])
{
String title=gg[0];
DesignationDTOInterface designationDTO;
try
{
DesignationDAO designationDAO;
designationDAO=new DesignationDAO();
designationDTO=designationDAO.getByTitle(title);
System.out.printf("Code : %d, Title : %s\n",designationDTO.getCode(),designationDTO.getTitle());
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}