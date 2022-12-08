import com.rahul.rai.hr.bl.exceptions.*;
import com.rahul.rai.hr.bl.interfaces.pojo.*;
import com.rahul.rai.hr.bl.interfaces.managers.*;
import com.rahul.rai.hr.bl.pojo.*;
import com.rahul.rai.hr.bl.managers.*;
import java.util.*;
public class DesignationManagerAddTestCase
{
public static void main(String gg[])
{
DesignationInterface designation;
designation=new Designation();
designation.setCode(0);
designation.setTitle(gg[0]);
try
{
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getDesignationManager();
designationManager.addDesignation(designation);
System.out.println("designation : "+designation.getTitle()+" added with code : "+designation.getCode());
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