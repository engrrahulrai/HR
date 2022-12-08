package com.rahul.rai.hr.server;
import com.rahul.rai.network.server.*;
import com.rahul.rai.network.common.*;
import com.rahul.rai.hr.bl.interfaces.managers.*;
import com.rahul.rai.hr.bl.interfaces.pojo.*;
import com.rahul.rai.hr.bl.exceptions.*;
import com.rahul.rai.hr.bl.managers.*;
public class RequestHandler implements RequestHandlerInterface
{
private DesignationManagerInterface designationManager;
public RequestHandler()
{
try
{
designationManager=DesignationManager.getDesignationManager();
}catch(BLException blException)
{
// do nothing
}
}
public Response process(Request request)
{
Response response=new Response();
String manager=request.getManager();
String action=request.getAction();
Object[] arguments=request.getArguments();
if(manager.equals("DesignationManager"))
{
if(designationManager==null)
{
// will implement later on
}
if(action.equals("addDesignation"))
{
try
{
designationManager.addDesignation((DesignationInterface)(arguments[0]));
response.setSuccess(true);
response.setResult(arguments[0]);
response.setException(null);
}catch(BLException blException)
{
response.setSuccess(false);
response.setResult(null);
response.setException(blException);
}
}
if(action.equals("updateDesignation"))
{
try
{
designationManager.updateDesignation((DesignationInterface)(arguments[0]));
response.setSuccess(true);
response.setResult(null);
response.setException(null);
}catch(BLException blException)
{
response.setSuccess(false);
response.setResult(null);
response.setException((BLException)blException);
}
}
if(action.equals("removeDesignation"))
{
try
{
designationManager.removeDesignation((Integer)(arguments[0]));
response.setSuccess(true);
response.setResult(null);
response.setException(null);
}catch(BLException  blException)
{
response.setSuccess(false);
response.setResult(false);
response.setException(blException);
}
}
if(action.equals("getDesignations"))
{
Object result=designationManager.getDesignations();
response.setSuccess(true);
response.setResult(result);
response.setException(null);
}
if(action.equals("getDesignationByCode"))
{
try
{
Object result=designationManager.getDesignationByCode((Integer)(arguments[0]));
response.setSuccess(true);
response.setResult(result);
response.setException(null);
}catch(BLException blException)
{
response.setSuccess(false);
response.setResult(null);
response.setException(blException);
}
}
if(action.equals("getDesignationByTitle"))
{
try
{
Object result=designationManager.getDesignationByTitle((String)(arguments[0]));
response.setSuccess(true);
response.setResult(result);
response.setException(null);
}catch(BLException blException)
{
response.setSuccess(false);
response.setResult(null);
response.setException(blException);
}
}
if(action.equals("getDesignationCount"))
{
Object result=designationManager.getDesignationCount();
response.setSuccess(true);
response.setResult(result);
response.setException(null);
}
if(action.equals("designationCodeExists"))
{
Object result=designationManager.designationCodeExists((Integer)(arguments[0]));
response.setSuccess(true);
response.setResult(result);
response.setException(null);
}
if(action.equals("designationTitleExists"))
{
Object result=designationManager.designationTitleExists((String)(arguments[0]));
response.setSuccess(true);
response.setResult(result);
response.setException(null);
}
}// DesignationManager part ends here
return response;
}
}