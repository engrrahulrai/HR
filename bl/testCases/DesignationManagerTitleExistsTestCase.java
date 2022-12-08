import com.rahul.rai.hr.bl.exceptions.*;
import com.rahul.rai.hr.bl.interfaces.managers.*;
import com.rahul.rai.hr.bl.managers.*;
import com.rahul.rai.hr.bl.interfaces.pojo.*;
import com.rahul.rai.hr.bl.pojo.*;
import java.util.*;
public class DesignationManagerTitleExistsTestCase
{
public static void main(String gg[])
{
String title=gg[0];
try
{
DesignationManagerInterface designationManager;
designationManager=DesignationManager.getDesignationManager();
System.out.println("Title "+title+" exists : "+designationManager.designationTitleExists(title));
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