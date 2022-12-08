import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import com.rahul.rai.hr.pl.model.*;
import com.rahul.rai.hr.bl.exceptions.*;
class DesignationModelTestCase extends JFrame
{
private JTable table;
private Container container;
private JScrollPane jsp;
private DesignationModel designationModel;
DesignationModelTestCase()
{
designationModel=new DesignationModel();
table=new JTable(designationModel);
jsp=new JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
container=getContentPane();
container.setLayout(new BorderLayout());
container.add(jsp);
setLocation(100,200);
setSize(500,400);
setVisible(true);
setDefaultCloseOperation(EXIT_ON_CLOSE);
}
public static void main(String gg[])
{
DesignationModelTestCase dmtc=new DesignationModelTestCase();
}
}