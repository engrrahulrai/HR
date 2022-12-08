import com.rahul.rai.hr.bl.interfaces.managers.*;
import com.rahul.rai.hr.bl.managers.*;
import com.rahul.rai.hr.bl.exceptions.*;
import java.util.*;
public class DesignationManagerGetDesignationCountTestCase
{
public static void main(String gg[])
{
try
{
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getDesignationManager();
System.out.println(designationManager.getDesignationCount());
}catch(BLException blException)
{
if(blException.hasGenericException())
{
System.out.println("Generic Exception : "+blException.getGenericException());
}
List<String> properties=blException.getProperties();
for(String property:properties)
{
System.out.println("Property : "+property+" "+blException.getException(property));
}
}
}
}