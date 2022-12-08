package com.rahul.rai.hr.bl.exceptions;
import java.util.*;
public class BLException extends Exception
{
private Map<String,String> exceptions;
private String genericException;
public BLException()
{
this.exceptions=new HashMap<>();
this.genericException=null;
}
public void setGenericException(String genericException)
{
this.genericException=genericException;
}
public String getGenericException()
{
if(this.genericException==null) return "";
return this.genericException;
}
public void addException(String property, String message)
{
exceptions.put(property,message);
}
public String getException(String property)
{
return exceptions.get(property);
}
public void removeException(String property)
{
this.exceptions.remove(property);
}
public int getExceptionsCount()
{
if(this.genericException!=null) return this.exceptions.size()+1;
return this.exceptions.size();
}
public boolean hasException(String property)
{
return this.exceptions.containsKey(property);
}
public boolean hasGenericException()
{
return this.genericException!=null;
}
public boolean hasException()
{
return this.getExceptionsCount()>0;
}
public List<String> getProperties()
{
List<String> properties=new ArrayList<>();
this.exceptions.forEach((k,v)->
{
properties.add(k);
});
return properties;
}
public String getMessage()
{
if(this.genericException==null) return "";
return this.genericException;
}
}