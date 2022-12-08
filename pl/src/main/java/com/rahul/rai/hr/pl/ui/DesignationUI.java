package com.rahul.rai.hr.pl.ui;
import com.rahul.rai.hr.pl.model.*;
import com.rahul.rai.hr.bl.exceptions.*;
import com.rahul.rai.hr.bl.interfaces.pojo.*;
import com.rahul.rai.hr.bl.pojo.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.io.*;
public class DesignationUI extends JFrame implements DocumentListener,ListSelectionListener
{
private JLabel titleLabel;
private JLabel searchLabel;
private JTextField searchTextField;
private JButton clearSearchTextFieldButton;
private JLabel searchErrorLabel;
private JTable designationTable;
private Container container;
private JScrollPane scrollPane;
private DesignationModel designationModel;
private DesignationPanel designationPanel;
private enum MODE{VIEW,ADD,EDIT,DELETE,EXPORT_TO_PDF};
private MODE mode;
private ImageIcon logoIcon;
private ImageIcon addIcon;
private ImageIcon editIcon;
private ImageIcon deleteIcon;
private ImageIcon cancelIcon;
private ImageIcon pdfIcon;
private ImageIcon saveIcon;
private ImageIcon clearSearchIcon;
public DesignationUI()
{
initComponents(); // all intialization will done in this method
setAppearance();
addListeners();
setViewMode();
designationPanel.setViewMode();
setDefaultCloseOperation(EXIT_ON_CLOSE);
}
private void initComponents()
{
logoIcon=new ImageIcon("c:"+File.separator+"java"+File.separator+"javaprojects"+File.separator+"hr"+File.separator+"icons"+File.separator+"logo.png");
addIcon=new ImageIcon(new ImageIcon("c:"+File.separator+"java"+File.separator+"javaprojects"+File.separator+"hr"+File.separator+"icons"+File.separator+"add_icon.jpg").getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH));
editIcon=new ImageIcon(new ImageIcon("c:"+File.separator+"java"+File.separator+"javaprojects"+File.separator+"hr"+File.separator+"icons"+File.separator+"edit_icon.png").getImage().getScaledInstance(50,50,java.awt.Image.SCALE_SMOOTH));
cancelIcon=new ImageIcon(new ImageIcon("c:"+File.separator+"java"+File.separator+"javaprojects"+File.separator+"hr"+File.separator+"icons"+File.separator+"cancel_icon.png").getImage().getScaledInstance(50,50,java.awt.Image.SCALE_SMOOTH));
deleteIcon=new ImageIcon(new ImageIcon("c:"+File.separator+"java"+File.separator+"javaprojects"+File.separator+"hr"+File.separator+"icons"+File.separator+"delete_icon.png").getImage().getScaledInstance(50,40,java.awt.Image.SCALE_SMOOTH));
pdfIcon=new ImageIcon(new ImageIcon("c:"+File.separator+"java"+File.separator+"javaprojects"+File.separator+"hr"+File.separator+"icons"+File.separator+"pdf_icon.jpg").getImage().getScaledInstance(50,50,java.awt.Image.SCALE_SMOOTH));
saveIcon=new ImageIcon(new ImageIcon("c:"+File.separator+"java"+File.separator+"javaprojects"+File.separator+"hr"+File.separator+"icons"+File.separator+"save_icon.png").getImage().getScaledInstance(50,50,java.awt.Image.SCALE_SMOOTH));
clearSearchIcon=new ImageIcon(new ImageIcon("c:"+File.separator+"java"+File.separator+"javaprojects"+File.separator+"hr"+File.separator+"icons"+File.separator+"clearSearch_icon.jpg").getImage().getScaledInstance(50,50,java.awt.Image.SCALE_SMOOTH));
setIconImage(logoIcon.getImage());

designationModel=new DesignationModel();
titleLabel=new JLabel("Designations");
searchLabel=new JLabel("Search");
searchTextField=new JTextField();
clearSearchTextFieldButton=new JButton("x");
searchErrorLabel=new JLabel("");
designationTable=new JTable(designationModel);
designationPanel=new DesignationPanel();
scrollPane=new JScrollPane(designationTable,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
container=getContentPane();
}
public void setAppearance()
{
Font titleFont=new Font("Verdana",Font.BOLD,18);
Font captionFont=new Font("Verdana",Font.BOLD,16);
Font dataFont=new Font("Verdana",Font.PLAIN,16);
Font searchErrorFont=new Font("Verdana",Font.BOLD,12);
Font columnHeaderFont=new Font("Verdana",Font.BOLD,16);
titleLabel.setFont(titleFont);
searchLabel.setFont(captionFont);
searchTextField.setFont(dataFont);
searchErrorLabel.setFont(searchErrorFont);
searchErrorLabel.setForeground(Color.red);
designationTable.setFont(dataFont);
designationTable.setRowHeight(35);
designationTable.getColumnModel().getColumn(0).setPreferredWidth(10); // we will get an address of object which is refering to the column
designationTable.getColumnModel().getColumn(1).setPreferredWidth(400); 
JTableHeader header=designationTable.getTableHeader();
header.setFont(columnHeaderFont);
header.setReorderingAllowed(false);
header.setResizingAllowed(false);
designationTable.setRowSelectionAllowed(true);
designationTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
container.setLayout(null); // By default BorderLayout if we didnot set null then we are not able to show on screen anything in which style we want
int leftMargin,topMargin;
leftMargin=0;
topMargin=0;
titleLabel.setBounds(leftMargin+10,topMargin+10,200,40); // 3rd-width and 4th Argument is Height and we will plus this height in next Element
searchErrorLabel.setBounds(leftMargin+10+100+400+10-75, topMargin+10+20+10,100,20);
searchLabel.setBounds(leftMargin+10,topMargin+10+40+10,100,30);
searchTextField.setBounds(leftMargin+10+100+5,topMargin+10+40+10,400,30);
clearSearchTextFieldButton.setBounds(leftMargin+10+100+5+400+10,topMargin+10+40+10,30,30);
scrollPane.setBounds(leftMargin+10,topMargin+10+40+10+30+10,565,230);
designationPanel.setBounds(leftMargin+10,topMargin+10+40+10+30+10+230+10,565,200);
container.add(titleLabel);
container.add(searchErrorLabel);
container.add(searchLabel);
container.add(searchTextField);
container.add(clearSearchTextFieldButton);
container.add(scrollPane);
container.add(designationPanel);
int w,h;
w=600;
h=600;
setSize(w,h);
Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
setLocation((d.width/2)-(w/2), (d.height/2)-(h/2));
}
private void addListeners()
{
searchTextField.getDocument().addDocumentListener(this);
clearSearchTextFieldButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
searchTextField.setText("");
searchTextField.requestFocus();
}
});
designationTable.getSelectionModel().addListSelectionListener(this);
}
// teeno se kaam to ek hi kerna h search to private method aur fir teeno se call ker lenge
private void searchDesignation()
{
searchErrorLabel.setText("");
String title=searchTextField.getText().trim();
if(title.length()==0) return;
int rowIndex;
try
{
rowIndex=designationModel.indexOfTitle(title,true);
this.designationTable.setRowSelectionInterval(rowIndex,rowIndex); // from where to , where
Rectangle rectangle=designationTable.getCellRect(rowIndex,0,true);
designationTable.scrollRectToVisible(rectangle);
}catch(BLException blException)
{
searchErrorLabel.setText("Not Found");
return;
}

}
public void removeUpdate(DocumentEvent de)
{
// ye jab kuch hataya to chalegi
searchDesignation();
}
public void insertUpdate(DocumentEvent de)
{
// ye jab kuch badaya to chalegi
searchDesignation();
}
public void changedUpdate(DocumentEvent de)
{
// ye kuch alag scenerio me chalegi
searchDesignation();
}
public void valueChanged(ListSelectionEvent ev)
{
int selectedRowIndex=designationTable.getSelectedRow();
try
{
DesignationInterface designation=designationModel.getDesignationAt(selectedRowIndex);
designationPanel.setDesignation(designation);
}catch(BLException blException)
{
designationPanel.clearDesignation();
}
}
private void setViewMode()
{
this.mode=MODE.VIEW;
if(designationModel.getRowCount()==0)
{
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
designationTable.setEnabled(false);
}
else
{
searchTextField.setEnabled(true);
clearSearchTextFieldButton.setEnabled(true);
designationTable.setEnabled(true);
}
}
private void setAddMode()
{
this.mode=MODE.ADD;
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
designationTable.setEnabled(false);
}
private void setEditMode()
{
this.mode=MODE.EDIT;
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
designationTable.setEnabled(false);
}
private void setDeleteMode()
{
this.mode=MODE.DELETE;
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
designationTable.setEnabled(false);
}
private void setExportToPDFMode()
{
this.mode=MODE.EXPORT_TO_PDF;
searchTextField.setEnabled(false);
clearSearchTextFieldButton.setEnabled(false);
designationTable.setEnabled(false);
}
// inner class starts
class DesignationPanel extends JPanel
{
private JLabel titleCaptionLabel;
private JLabel titleLabel;
private JTextField titleTextField;
private JButton clearTitleTextFieldButton;
private JButton addButton;
private JButton editButton;
private JButton cancelButton;
private JButton deleteButton;
private JButton exportToPDFButton;
private JPanel buttonsPanel;
private DesignationInterface designation;
DesignationPanel()
{
setBorder(BorderFactory.createLineBorder(new Color(130,130,130))); // 165
initComponents();
setAppearance();
addListeners();
}
public void setDesignation(DesignationInterface designation)
{
this.designation=designation;
titleLabel.setText(designation.getTitle());
}
public void clearDesignation()
{
this.designation=null;
titleLabel.setText("");
}
private void initComponents()
{
designation=null;
titleCaptionLabel=new JLabel("Designation");
titleLabel=new JLabel("");
titleTextField=new JTextField();
clearTitleTextFieldButton=new JButton("x");
buttonsPanel=new JPanel();
addButton=new JButton(addIcon);
editButton=new JButton(editIcon);
cancelButton=new JButton(cancelIcon);
deleteButton=new JButton(deleteIcon);
exportToPDFButton=new JButton(pdfIcon);
}
private void setAppearance()
{
Font captionFont=new Font("Verdana",Font.BOLD,16);
Font dataFont=new Font("Verdana",Font.PLAIN,16);
titleCaptionLabel.setFont(captionFont);
titleLabel.setFont(dataFont);
titleTextField.setFont(dataFont);
setLayout(null);
int lm,tm;
lm=0;
tm=0;
titleCaptionLabel.setBounds(lm+10,tm+20,110,30);
titleLabel.setBounds(lm+110+5,tm+20,400,30);
titleTextField.setBounds(lm+10+110+5,tm+20,350,30);
clearTitleTextFieldButton.setBounds(lm+10+110+5+350+5,tm+20,30,30);

buttonsPanel.setBounds(50,tm+20+30+30,465,75);
buttonsPanel.setBorder(BorderFactory.createLineBorder(new Color(165,165,165)));
addButton.setBounds(70,12,50,50);
editButton.setBounds(70+50+20,12,50,50);
cancelButton.setBounds(70+50+20+50+20,12,50,50);
deleteButton.setBounds(70+50+20+50+20+50+20,12,50,50);
exportToPDFButton.setBounds(70+50+20+50+20+50+20+50+20,12,50,50);
buttonsPanel.setLayout(null);
buttonsPanel.add(addButton);
buttonsPanel.add(editButton);
buttonsPanel.add(cancelButton);
buttonsPanel.add(deleteButton);
buttonsPanel.add(exportToPDFButton);
add(titleCaptionLabel);
add(titleTextField);
add(titleLabel);
add(clearTitleTextFieldButton);
add(buttonsPanel);
}
private boolean addDesignation()
{
String title=titleTextField.getText().trim();
if(title.length()==0)
{
JOptionPane.showMessageDialog(this,"Designation required");
titleTextField.requestFocus();
return false;
}
DesignationInterface d=new Designation();
d.setTitle(title);
try
{
designationModel.add(d);
int rowIndex=0;
try
{
rowIndex=designationModel.indexOfDesignation(d);
}catch(BLException blException)
{
// do nothing
}
designationTable.setRowSelectionInterval(rowIndex,rowIndex);
Rectangle rectangle=designationTable.getCellRect(rowIndex,0,true);
designationTable.scrollRectToVisible(rectangle);
return true;
}catch(BLException blException)
{
if(blException.hasGenericException())
{
JOptionPane.showMessageDialog(this,blException.getGenericException());
}
else
{
if(blException.hasException("title"))
{
JOptionPane.showMessageDialog(this,blException.getException("title"));
}
}
titleTextField.requestFocus();
return false;
}
}
private boolean updateDesignation()
{
String title=titleTextField.getText().trim();
if(title.length()==0)
{
JOptionPane.showMessageDialog(this,"Designation required");
titleTextField.requestFocus();
return false;
}
DesignationInterface d=new Designation();
d.setCode(this.designation.getCode());
d.setTitle(title);
try
{
designationModel.update(d);
int rowIndex=0;
try
{
rowIndex=designationModel.indexOfDesignation(d);
}catch(BLException blException)
{
// do nothing
}
designationTable.setRowSelectionInterval(rowIndex,rowIndex);
Rectangle rectangle=designationTable.getCellRect(rowIndex,0,true);
designationTable.scrollRectToVisible(rectangle);
return true;
}catch(BLException blException)
{
if(blException.hasGenericException())
{
JOptionPane.showMessageDialog(this,blException.getGenericException());
}
else
{
if(blException.hasException("title"))
{
JOptionPane.showMessageDialog(this,blException.getException("title"));
}
}
titleTextField.requestFocus();
return false;
}

}
private void removeDesignation()
{
try
{
String title=this.designation.getTitle();
int selectedOption=JOptionPane.showConfirmDialog(this,"Delete "+title+" ?","Confirmation",JOptionPane.YES_NO_OPTION);
if(selectedOption==JOptionPane.NO_OPTION) return;
designationModel.remove(this.designation.getCode());
JOptionPane.showMessageDialog(this,title+" deleted.");
this.clearDesignation();
}catch(BLException blException)
{
if(blException.hasGenericException())
{
JOptionPane.showMessageDialog(this,blException.getGenericException());
}
else
{
if(blException.hasException("title"))
{
JOptionPane.showMessageDialog(this,blException.getException("title"));
}
}
}
}
private void addListeners()
{
this.exportToPDFButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
JFileChooser jfc=new JFileChooser();
jfc.setCurrentDirectory(new File("."));
int selectedOption=jfc.showSaveDialog(DesignationUI.this);
if(selectedOption==JFileChooser.APPROVE_OPTION)
{
try
{
File selectedFile=jfc.getSelectedFile();
// System.out.println(selectedFile.getAbsolutePath());
String pdfFile=selectedFile.getAbsolutePath();
if(pdfFile.endsWith(".")) pdfFile+="pdf";
else if(pdfFile.endsWith(".pdf")==false) pdfFile+=".pdf";
File file=new File(pdfFile);
// System.out.println(file.getParent());
File parent=new File(file.getParent());
if(parent.exists()==false || parent.isDirectory()==false)
{
JOptionPane.showMessageDialog(DesignationUI.this,"Incorrect path : "+file.getAbsolutePath());
return;
}
designationModel.exportToPDF(file);
JOptionPane.showMessageDialog(DesignationUI.this,"Data exported to "+file.getAbsolutePath());
}catch(BLException blException)
{
if(blException.hasGenericException())
{
JOptionPane.showMessageDialog(DesignationUI.this,blException.getGenericException());
}
}
catch(Exception e)
{
System.out.println(e);
}
}
}
});
this.addButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
if(mode==MODE.VIEW)
{
setAddMode();
}
else
{
if(addDesignation())
{
setViewMode();
}
}
}
});
this.editButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
if(mode==MODE.VIEW)
{
setEditMode();
}
else
{
if(updateDesignation()) setViewMode();
}
}
});
this.deleteButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
setDeleteMode();
}
});

this.cancelButton.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent ev)
{
setViewMode();
}
});
}
void setViewMode()
{
DesignationUI.this.setViewMode();
this.addButton.setIcon(addIcon);
this.editButton.setIcon(editIcon);
this.titleTextField.setVisible(false);
this.clearTitleTextFieldButton.setVisible(false);
this.titleLabel.setVisible(true);
this.addButton.setEnabled(true);
this.cancelButton.setEnabled(false);
if(designationModel.getRowCount()>0)
{
this.editButton.setEnabled(true);
this.deleteButton.setEnabled(true);
this.exportToPDFButton.setEnabled(true);
}
else
{
this.editButton.setEnabled(false);
this.deleteButton.setEnabled(false);
this.exportToPDFButton.setEnabled(false);
}
}
void setAddMode()
{
DesignationUI.this.setAddMode();
this.titleTextField.setText("");
this.titleLabel.setVisible(false);
this.clearTitleTextFieldButton.setVisible(true);
this.titleTextField.setVisible(true);
addButton.setIcon(saveIcon);
editButton.setEnabled(false);
cancelButton.setEnabled(true);
deleteButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
}
void setEditMode()
{
if(designationTable.getSelectedRow()<0 || designationTable.getSelectedRow()>=designationModel.getRowCount())
{
JOptionPane.showMessageDialog(this,"Select designation to edit");
return;
}
DesignationUI.this.setEditMode();
this.titleTextField.setText(this.designation.getTitle());
this.titleLabel.setVisible(false);
this.clearTitleTextFieldButton.setVisible(true);
this.titleTextField.setVisible(true);
addButton.setEnabled(false);
editButton.setEnabled(true);
cancelButton.setEnabled(true);
deleteButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
editButton.setIcon(saveIcon);
}
void setDeleteMode()
{
if(designationTable.getSelectedRow()<0 || designationTable.getSelectedRow()>=designationModel.getRowCount())
{
JOptionPane.showMessageDialog(this,"Select designation to delete");
return;
}
DesignationUI.this.setDeleteMode();
addButton.setEnabled(false);
editButton.setEnabled(false);
cancelButton.setEnabled(false);
deleteButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
removeDesignation();
setViewMode();
}
void setExportToPDFMode()
{
DesignationUI.this.setExportToPDFMode();
addButton.setEnabled(false);
editButton.setEnabled(false);
cancelButton.setEnabled(false);
deleteButton.setEnabled(false);
exportToPDFButton.setEnabled(false);
}

} // inner class ends
}