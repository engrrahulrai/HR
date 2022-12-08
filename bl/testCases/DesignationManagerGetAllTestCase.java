import com.rahul.rai.hr.bl.exceptions.*;
import com.rahul.rai.hr.bl.interfaces.managers.*;
import com.rahul.rai.hr.bl.managers.*;
import com.rahul.rai.hr.bl.interfaces.pojo.*;
import com.rahul.rai.hr.bl.pojo.*;
import java.util.*;
public class DesignationManagerGetAllTestCase
{
public static void main(String gg[])
{
try
{
Set<DesignationInterface> designations;
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getDesignationManager();
designations=designationManager.getDesignations();
designations.forEach((d)->{
System.out.printf("Code : %d, Title : %s\n",d.getCode(),d.getTitle());
});
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