import com.rahul.rai.hr.dl.interfaces.dto.*;
import com.rahul.rai.hr.dl.dto.*;
import com.rahul.rai.hr.dl.interfaces.dao.*;
import com.rahul.rai.hr.dl.dao.*;
import com.rahul.rai.hr.dl.exceptions.*;
public class DesignationUpdateTestCase
{
public static void main(String gg[])
{
int code=Integer.parseInt(gg[0]);
String title=gg[1];
DesignationDTO designationDTO=new DesignationDTO();
designationDTO.setCode(code);
designationDTO.setTitle(title);
try
{
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
designationDAO.update(designationDTO);
System.out.println("Successfully updated");
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}