package com.rahul.rai.network.server;
import java.io.*;
import java.net.*;
import com.rahul.rai.network.common.*;
public class RequestProcessor extends Thread
{
private Socket socket;
private RequestHandlerInterface requestHandler;
RequestProcessor(Socket socket, RequestHandlerInterface requestHandler)
{
this.socket=socket;
this.requestHandler=requestHandler;
start();
}
public void run()
{
try
{
InputStream is=socket.getInputStream();
OutputStream os=socket.getOutputStream();
byte header[]=new byte[1024];
byte tmp[]=new byte[1024];
int bytesReadCount;
int  j=0;
int k;
int i=0;
int bytesToReceive=1024;
while(j<bytesToReceive)
{
bytesReadCount=is.read(tmp);
if(bytesReadCount==-1) continue;
for(k=0;k<bytesReadCount;k++)
{
header[i]=tmp[k];
i++;
}
j=j+bytesReadCount;
}
byte ack[]=new byte[1];
ack[0]=1;
os.write(ack,0,1);
os.flush();

int requestLength=0;
j=1023;
i=1;
while(j>=0)
{
requestLength=requestLength+(header[j]*i);
i=i*10;
j--;
}

bytesToReceive=requestLength;
byte objectBytes[]=new byte[requestLength];
j=0;
i=0;
while(j<bytesToReceive)
{
bytesReadCount=is.read(tmp);
if(bytesReadCount==-1) continue;
for(k=0;k<bytesReadCount;k++)
{
objectBytes[i]=tmp[k];
i++;
}
j=j+bytesReadCount;
}
ByteArrayInputStream bais=new ByteArrayInputStream(objectBytes);
ObjectInputStream ois=new ObjectInputStream(bais);
Request request=(Request)ois.readObject();
Response response=requestHandler.process(request);
ByteArrayOutputStream baos=new ByteArrayOutputStream();
ObjectOutputStream oos=new ObjectOutputStream(baos);
oos.writeObject(response);
oos.flush();
objectBytes=baos.toByteArray();
int responseLength=objectBytes.length;
header=new byte[1024];
int x=responseLength;
j=1023;
while(x>0)
{
header[j]=(byte)(x%10);
x=x/10;
j--;
}
os.write(header,0,1024);
os.flush();
while(true)
{
bytesReadCount=is.read(ack);
if(bytesReadCount==-1) continue;
break;
}

int bytesToSend=responseLength;
j=0;
int chunkSize=1024;
while(j<bytesToSend)
{
if((bytesToSend-j)<chunkSize) chunkSize=bytesToSend-j;
os.write(objectBytes,j,chunkSize);
os.flush();
j=j+chunkSize;
}

while(true)
{
bytesReadCount=is.read(ack);
if(bytesReadCount==-1) continue;
break;
}
socket.close();
}catch(Exception e)
{
System.out.println(e);
}
}
}