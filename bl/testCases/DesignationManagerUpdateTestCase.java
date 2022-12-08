import com.rahul.rai.hr.bl.interfaces.pojo.*;
import com.rahul.rai.hr.bl.pojo.*;
import com.rahul.rai.hr.bl.interfaces.managers.*;
import com.rahul.rai.hr.bl.managers.*;
import com.rahul.rai.hr.bl.exceptions.*;
import java.util.*;
public class DesignationManagerUpdateTestCase
{
public static void main(String gg[])
{
DesignationInterface designation;
designation=new Designation();
designation.setCode(Integer.parseInt(gg[0]));
designation.setTitle(null);
try
{
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getDesignationManager();
designationManager.updateDesignation(designation);
System.out.println("Successfully updated");
}catch(BLException blException)
{
if(blException.hasGenericException())
{
System.out.println(blException.getGenericException());
}
List<String> properties=blException.getProperties();
for(String property:properties)
{
System.out.println(blException.getException(property));
}
}
}
}