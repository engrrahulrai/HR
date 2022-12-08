import com.rahul.rai.hr.dl.interfaces.dao.*;
import com.rahul.rai.hr.dl.interfaces.dto.*;
import com.rahul.rai.hr.dl.dao.*;
import com.rahul.rai.hr.dl.dto.*;
import com.rahul.rai.hr.dl.exceptions.*;
import java.io.*;
import java.util.*;
public class DesignationGetAllTestCase
{
public static void main(String gg[])
{
Set<DesignationDTOInterface> set;
try
{
DesignationDAO designationDAO;
designationDAO=new DesignationDAO();
set=designationDAO.getAll();
set.forEach((designationDTO)->
{
System.out.println("Code : "+designationDTO.getCode()+", Title : "+designationDTO.getTitle());
});
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}