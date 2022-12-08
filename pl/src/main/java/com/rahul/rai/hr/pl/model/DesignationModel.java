package com.rahul.rai.hr.pl.model;
import com.rahul.rai.hr.bl.interfaces.managers.*;
import com.rahul.rai.hr.bl.interfaces.pojo.*;
import com.rahul.rai.hr.bl.managers.*;
import com.rahul.rai.hr.bl.pojo.*;
import com.rahul.rai.hr.bl.exceptions.*;
import java.util.*;
import javax.swing.table.*;
import java.io.*;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.io.image.*;
import com.itextpdf.kernel.font.*;
import com.itextpdf.io.font.constants.*;
import com.itextpdf.layout.*;
import com.itextpdf.layout.element.*;
public class DesignationModel extends AbstractTableModel
{
private java.util.List<DesignationInterface> designations;
private DesignationManagerInterface designationManager;
private String[] columnTitle;
public DesignationModel()
{
this.populateDataStructures();
}
private void populateDataStructures()
{
this.columnTitle=new String[2];
this.columnTitle[0]="S.No.";
this.columnTitle[1]="Designation";
try
{
designationManager=DesignationManager.getDesignationManager();
}catch(BLException blException)
{
// ??? what to do
}
Set<DesignationInterface> blDesignations=designationManager.getDesignations();
this.designations=new LinkedList<>();
for(DesignationInterface designation:blDesignations)
{
this.designations.add(designation);
}
Collections.sort(this.designations,new Comparator<DesignationInterface>(){
public int compare(DesignationInterface left, DesignationInterface right)
{
return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
}
});
}
public int getRowCount()
{
return this.designations.size();
}
public int getColumnCount()
{
return this.columnTitle.length;
}
public String getColumnName(int columnIndex)
{
return columnTitle[columnIndex];
}
public Object getValueAt(int rowIndex, int columnIndex)
{
if(columnIndex==0) return rowIndex+1;
return this.designations.get(rowIndex).getTitle();
}
public Class getColumnClass(int columnIndex)
{
if(columnIndex==0) return Integer.class; 
// special treatment it will convert into Class.forName(java.lang.Integer);
return String.class;
}
public boolean isCellEditable(int rowIndex,int columnIndex)
{
return false;
}
// Application specific methods
public void add(DesignationInterface designation) throws BLException
{
designationManager.addDesignation(designation);
this.designations.add(designation);
Collections.sort(this.designations,new Comparator<DesignationInterface>(){
public int compare(DesignationInterface left, DesignationInterface right)
{
return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
}
});
fireTableDataChanged();
}
public int indexOfDesignation(DesignationInterface designation) throws BLException
{
Iterator<DesignationInterface> iterator=this.designations.iterator();
DesignationInterface d;
int index=0;
while(iterator.hasNext())
{
d=iterator.next();
if(d.equals(designation))
{
return index;
}
index++;
}
BLException blException=new BLException();
blException.setGenericException("Invalid Designation : "+designation);
throw blException;
}
public int indexOfTitle(String title,boolean partialLeftSearch) throws BLException
{
Iterator<DesignationInterface> iterator=this.designations.iterator();
int index=0;
DesignationInterface d;
while(iterator.hasNext())
{
d=iterator.next();
if(partialLeftSearch)
{
if(d.getTitle().toUpperCase().startsWith(title.toUpperCase())) return index;
}
else
{
if(d.getTitle().equalsIgnoreCase(title)) return index;
}
index++;
}
BLException blException=new BLException();
blException.setGenericException("Invalid Title : "+title);
throw blException;
}
public void update(DesignationInterface designation) throws BLException
{
this.designationManager.updateDesignation(designation);
this.designations.remove(indexOfDesignation(designation));
this.designations.add(designation);
Collections.sort(this.designations,new Comparator<DesignationInterface>(){
public int compare(DesignationInterface left, DesignationInterface right)
{
return left.getTitle().toUpperCase().compareTo(right.getTitle().toUpperCase());
}
});
fireTableDataChanged();
}
public void remove(int code) throws BLException
{
designationManager.removeDesignation(code);
Iterator<DesignationInterface> iterator=this.designations.iterator();
int index=0;
while(iterator.hasNext())
{
if(iterator.next().getCode()==code) break;
index++;
}
if(index==designations.size())
{
BLException blException=new BLException();
blException.setGenericException("Invalid designation code : "+code);
throw blException;
}
this.designations.remove(index);
fireTableDataChanged();
}
public DesignationInterface getDesignationAt(int index) throws BLException
{
if(index<0 || index>=this.designations.size())
{
BLException blException=new BLException();
blException.setGenericException("Invalid index : "+index);
throw blException;
}
return this.designations.get(index);
}
public void exportToPDF(File file) throws BLException
{
try
{
if(file.exists()) file.delete(); // now we have to ask the user if file is already exists by yes or no option to save or not
PdfWriter pdfWriter=new PdfWriter(file);
PdfDocument pdfDocument=new PdfDocument(pdfWriter);
Document doc=new Document(pdfDocument);
doc.add(new Paragraph("great"));
// done done 
// we have to write the code of creating pdf file

doc.close();
}catch(Exception exception)
{
BLException blException;
blException=new BLException();
blException.setGenericException(exception.getMessage());
throw blException;
}
}
}