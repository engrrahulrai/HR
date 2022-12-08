package com.rahul.rai.hr.bl.managers;
import com.rahul.rai.hr.bl.interfaces.pojo.*;
import com.rahul.rai.hr.bl.pojo.*;
import com.rahul.rai.hr.bl.interfaces.managers.*;
import com.rahul.rai.hr.bl.exceptions.*;
import java.util.*;
import com.rahul.rai.hr.dl.interfaces.dto.*;
import com.rahul.rai.hr.dl.interfaces.dao.*;
import com.rahul.rai.hr.dl.dto.*;
import com.rahul.rai.hr.dl.dao.*;
import com.rahul.rai.hr.dl.exceptions.*;
public class DesignationManager implements DesignationManagerInterface
{
private Map<Integer,DesignationInterface> codeWiseDesignationsMap;
private Map<String,DesignationInterface> titleWiseDesignationsMap;
private Set<DesignationInterface> designationsSet;
private static DesignationManagerInterface designationManager=null;
private DesignationManager() throws BLException
{
populateDataStructure();
}
private void populateDataStructure() throws BLException
{
this.codeWiseDesignationsMap=new HashMap<>();
this.titleWiseDesignationsMap=new HashMap<>();
this.designationsSet=new TreeSet<>();
try
{
Set<DesignationDTOInterface> dlDesignations;
dlDesignations=new DesignationDAO().getAll();
DesignationInterface designation;
for(DesignationDTOInterface dlDesignation: dlDesignations)
{
designation=new Designation();
designation.setCode(dlDesignation.getCode());
designation.setTitle(dlDesignation.getTitle());
this.codeWiseDesignationsMap.put(designation.getCode(),designation);
this.titleWiseDesignationsMap.put(designation.getTitle().toUpperCase(),designation);
this.designationsSet.add(designation);
}
}catch(DAOException daoException)
{
BLException blException=new BLException();
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public static DesignationManagerInterface getDesignationManager() throws BLException
{
if(designationManager==null) designationManager=new DesignationManager();
return designationManager;
}
public void addDesignation(DesignationInterface designation) throws BLException
{
BLException blException=new BLException();
if(designation==null)
{
blException.setGenericException("designation is null");
throw blException;
}
int code=designation.getCode();
if(code!=0)
{
blException.addException("code","Code should be zero");
}
String title=designation.getTitle();
if(title==null)
{
blException.addException("title","Title required");
title="";
}
else
{
title=title.trim();
if(title.length()==0)
{
blException.addException("title","Title required");
}
}
if(title.length()>0)
{
if(this.titleWiseDesignationsMap.containsKey(title.toUpperCase()))
{
blException.addException("title","Designation : "+title+" exists.");
}
}
if(blException.hasException())
{
throw blException;
}
try
{
DesignationDTOInterface designationDTO;
designationDTO=new DesignationDTO();
designationDTO.setTitle(title);
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
designationDAO.add(designationDTO);
code=designationDTO.getCode();
designation.setCode(code);
DesignationInterface dsDesignation;
dsDesignation=new Designation();
dsDesignation.setCode(code);
dsDesignation.setTitle(title);
this.codeWiseDesignationsMap.put(new Integer(code),dsDesignation);
this.titleWiseDesignationsMap.put(title.toUpperCase(),dsDesignation);
this.designationsSet.add(dsDesignation);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public void updateDesignation(DesignationInterface designation) throws BLException
{
BLException blException=new BLException();
if(designation==null)
{
blException.setGenericException("designation is null");
throw blException;
}
int code=designation.getCode();
String title=designation.getTitle();
if(code<=0)
{
blException.addException("code","Code should be greater than zero to update");
}
if(code>0)
{
if(!codeWiseDesignationsMap.containsKey(code))
{
blException.addException("code","Invalid code : "+code);
throw blException;
}
}
if(title==null)
{
blException.addException("title","Title required");
title="";
}
else
{
title=title.trim();
if(title.length()==0)
{
blException.addException("title","Title required");
}
}
if(title.length()>0)
{
DesignationInterface d;
d=titleWiseDesignationsMap.get(title.toUpperCase());
if(d!=null && d.getCode()!=code)
{
blException.addException("title","Title : "+title+" exists against another code : "+d.getCode());
}
}
if(blException.hasException())
{
throw blException;
}
try
{
DesignationInterface dsDesignation;
dsDesignation=codeWiseDesignationsMap.get(code);
DesignationDTOInterface designationDTO;
designationDTO=new DesignationDTO();
designationDTO.setCode(code);
designationDTO.setTitle(title);
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
designationDAO.update(designationDTO);
// code to remove from DS
codeWiseDesignationsMap.remove(code);
titleWiseDesignationsMap.remove(dsDesignation.getTitle().toUpperCase());
designationsSet.remove(dsDesignation);
// update the DS object
dsDesignation.setTitle(title);
// update the DS
codeWiseDesignationsMap.put(code,dsDesignation);
titleWiseDesignationsMap.put(title.toUpperCase(),dsDesignation);
designationsSet.add(dsDesignation);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public void removeDesignation(int code) throws BLException
{
BLException blException=new BLException();
if(code<=0)
{
blException.setGenericException("Code should be greater than zero to update");
throw blException;
}
if(!codeWiseDesignationsMap.containsKey(code))
{
blException.addException("code","Invalid code : "+code);
throw blException;
}
try
{
DesignationInterface dsDesignation;
dsDesignation=codeWiseDesignationsMap.get(code);
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
designationDAO.delete(code);
// code to remove
codeWiseDesignationsMap.remove(code);
titleWiseDesignationsMap.remove(dsDesignation.getTitle());
designationsSet.remove(dsDesignation);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public Set<DesignationInterface> getDesignations()
{
Set<DesignationInterface> designations=new TreeSet<>();
designationsSet.forEach((d)->{
DesignationInterface designation=new Designation();
designation.setCode(d.getCode());
designation.setTitle(d.getTitle());
designations.add(designation);
});
return designations;
}
public DesignationInterface getDesignationByCode(int code) throws BLException
{
DesignationInterface designation=codeWiseDesignationsMap.get(code);
if(designation==null)
{
BLException blException=new BLException();
blException.addException("code","Invalid code : "+code);
throw blException;
}
DesignationInterface d=new Designation();
d.setCode(designation.getCode());
d.setTitle(designation.getTitle());
return d;
}
public DesignationInterface getDesignationByTitle(String title) throws BLException
{
if(title==null || title.trim().length()==0)
{
BLException blException=new BLException();
blException.addException("title","Title required");
throw blException;
}
DesignationInterface designation=this.titleWiseDesignationsMap.get(title.toUpperCase());
if(designation==null)
{
BLException blException=new BLException();
blException.addException("title","Invalid title : "+title);
throw blException;
}
DesignationInterface d=new Designation();
d.setCode(designation.getCode());
d.setTitle(designation.getTitle());
return d;
}
DesignationInterface getDSDesignationByCode(int designationCode)
{
DesignationInterface designation;
designation=codeWiseDesignationsMap.get(designationCode);
return designation;
}
public int getDesignationCount()
{
return this.designationsSet.size();
}
public boolean designationCodeExists(int code)
{
return this.codeWiseDesignationsMap.containsKey(code);
}
public boolean designationTitleExists(String title)
{
if(title==null)
{
return false;
}
return this.titleWiseDesignationsMap.containsKey(title.toUpperCase());
}
}