import com.rahul.rai.hr.dl.interfaces.dao.*;
import com.rahul.rai.hr.dl.interfaces.dto.*;
import com.rahul.rai.hr.dl.dao.*;
import com.rahul.rai.hr.dl.dto.*;
import com.rahul.rai.hr.dl.exceptions.*;
import java.io.*;
import java.util.*;
public class DesignationAddTestCase
{
public static void main(String gg[])
{
String title=gg[0];
DesignationDTOInterface designationDTO;
designationDTO=new DesignationDTO();
designationDTO.setTitle(title);
try
{
DesignationDAO designationDAO;
designationDAO=new DesignationDAO();
designationDAO.add(designationDTO);
System.out.println("title : "+title+" added with code : "+designationDTO.getCode());
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}