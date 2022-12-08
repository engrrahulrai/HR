package com.rahul.rai.hr.bl.interfaces.managers;
import java.util.*;
import com.rahul.rai.hr.bl.interfaces.pojo.*;
import com.rahul.rai.hr.bl.exceptions.*;
public interface DesignationManagerInterface
{
public void addDesignation(DesignationInterface designation) throws BLException;
public void updateDesignation(DesignationInterface designation) throws BLException;
public void removeDesignation(int code) throws BLException;
public Set<DesignationInterface> getDesignations();
public DesignationInterface getDesignationByCode(int code) throws BLException;
public DesignationInterface getDesignationByTitle(String title) throws BLException;
public int getDesignationCount();
public boolean designationCodeExists(int code);
public boolean designationTitleExists(String title);
}