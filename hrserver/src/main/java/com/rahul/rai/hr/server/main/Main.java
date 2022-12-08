package com.rahul.rai.hr.server.main;
import com.rahul.rai.hr.server.*;
import com.rahul.rai.network.common.exceptions.*;
import com.rahul.rai.network.server.*;
public class Main
{
public static void main(String gg[])
{
try
{
RequestHandler requestHandler=new RequestHandler();
NetworkServer networkServer;
networkServer=new NetworkServer(requestHandler);
networkServer.start();
}catch(NetworkException networkException)
{
System.out.println(networkException);
}
}
}