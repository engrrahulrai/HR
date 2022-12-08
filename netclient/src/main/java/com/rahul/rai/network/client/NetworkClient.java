package com.rahul.rai.network.client;
import com.rahul.rai.network.common.*;
import com.rahul.rai.network.common.exceptions.*;
import java.io.*;
import java.net.*;
public class NetworkClient
{
public Response send(Request request) throws NetworkException
{
try
{
ByteArrayOutputStream baos=new ByteArrayOutputStream();
ObjectOutputStream oos=new ObjectOutputStream(baos);
oos.writeObject(request);
oos.flush();
byte objectBytes[];
objectBytes=baos.toByteArray();

int requestLength=objectBytes.length;
int x=requestLength;
byte header[]=new byte[1024];
int i=1023;
while(x>0)
{
header[i]=(byte)(x%10);
x=x/10;
i--;
}
Socket socket=new Socket(Configuration.getHost(),Configuration.getPort());
OutputStream os=socket.getOutputStream();
InputStream is=socket.getInputStream();
os.write(header,0,1024);
os.flush();

byte ack[]=new byte[1];
int bytesReadCount;
while(true)
{
bytesReadCount=is.read(ack);
if(bytesReadCount==-1) continue;
break;
}

int bytesToSend=requestLength;
int j=0;
int chunkSize=1024;
while(j<bytesToSend)
{
if((bytesToSend-j)<chunkSize) chunkSize=bytesToSend-j;
os.write(objectBytes,j,chunkSize);
os.flush();
j=j+chunkSize;
}

int bytesToReceive=1024;
byte tmp[]=new byte[1024];
j=0;
int k;
i=0;
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
ack[0]=1;
os.write(ack,0,1);
os.flush();

int responseLength=0;
j=1023;
i=1;
while(j>=0)
{
responseLength=responseLength+(header[j]*i);
i=i*10;
j--;
}
bytesToReceive=responseLength;
i=0;
j=0;
byte responseObject[]=new byte[responseLength];
while(j<bytesToReceive)
{
bytesReadCount=is.read(tmp);
if(bytesReadCount==-1) continue;
for(k=0;k<bytesReadCount;k++)
{
responseObject[i]=tmp[k];
i++;
}
j=j+bytesReadCount;
}
ack[0]=1;
os.write(ack,0,1);
os.flush();
socket.close();
ByteArrayInputStream bais=new ByteArrayInputStream(responseObject);
ObjectInputStream ois=new ObjectInputStream(bais);
Response response=(Response)ois.readObject();
socket.close();
return response;
}catch(Exception exception)
{
throw new NetworkException(exception.getMessage());
}
}
}