package com.rahul.rai.network.server;
import org.xml.sax.*;
import javax.xml.xpath.*;
import java.io.*;
import com.rahul.rai.network.common.exceptions.*;
public class Configuration
{
private static int port=-1;
private static boolean malformed=false;;
private static boolean fileMissing=false;
static
{
try
{
File file=new File("server.xml");
if(file.exists())
{
InputSource inputSource=new InputSource("server.xml");
XPath xpath=XPathFactory.newInstance().newXPath();
String port=xpath.evaluate("//server/@port",inputSource);
Configuration.port=Integer.parseInt(port);
}
else
{
fileMissing=true;
}
}catch(Exception exception)
{
System.out.println(exception.getMessage());
malformed=true;
}
}
public static int getPort() throws NetworkException
{
if(fileMissing) throw new NetworkException("server.xml is missing, read documentation");
if(malformed) throw new NetworkException("server.xml is not configured according to documentation");
if(port<0 || port>49151) throw new NetworkException("server.xml contains invalid port number, read documentation");
return port;
}
}