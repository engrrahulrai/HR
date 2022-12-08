package com.rahul.rai.network.server;
import java.io.*;
import java.net.*;
import com.rahul.rai.network.common.exceptions.*;
public class NetworkServer
{
private RequestHandlerInterface requestHandler;
public NetworkServer(RequestHandlerInterface requestHandler) throws NetworkException
{
if(requestHandler==null)
{
throw new NetworkException("Request Handler is missing");
}
this.requestHandler=requestHandler;
}
public void start() throws NetworkException
{
ServerSocket serverSocket=null;
int port=Configuration.getPort();
try
{
serverSocket=new ServerSocket(port);
}catch(Exception exception)
{
throw new NetworkException(exception.getMessage());
}
try
{
RequestProcessor requestProcessor;
Socket socket;
while(true)
{
System.out.println("Server is ready");
socket=serverSocket.accept();
requestProcessor=new RequestProcessor(socket,requestHandler);
}
}catch(Exception e)
{
System.out.println(e);
}
}
}