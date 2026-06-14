package pack_studentDB; 
import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*; 
import java.sql.*; 
public class StudentGui implements ActionListener { 
JFrame jFrame; 
JLabel jLabelrno, jLabelnm, jLabelcourse, jLabelfee; 
JTextField jTextFieldrno, jTextFieldname, jTextFieldcourse, 
jTextFieldfee; 
JButton jButtonfirst, jButtonnext, jButtonprev, jButtonlast, 
jButtonnew, jButtoninsert, jButtonupdte, jButtondelete, jButtonclear, 
jButtonexit; 
Font font; 
Connection con; 
PreparedStatement pstmt; 
ResultSet rs; 
public StudentGui() { 
font = new Font("Arial", Font.BOLD, 16); 
jFrame = new JFrame("Student Database"); 
jFrame.setBounds(100, 100, 500, 450); 
jFrame.setLayout(null); 
jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
// Labels 
jLabelrno = new JLabel("Roll No:"); 
jLabelrno.setBounds(50, 40, 100, 30); 
jLabelrno.setFont(font); 
jFrame.add(jLabelrno); 
jLabelnm = new JLabel("Name:"); 
jLabelnm.setBounds(50, 80, 100, 30); 
jLabelnm.setFont(font); 
jFrame.add(jLabelnm); 
jLabelcourse = new JLabel("Course:"); 
jLabelcourse.setBounds(50, 120, 100, 30); 
jLabelcourse.setFont(font); 
jFrame.add(jLabelcourse); 
jLabelfee = new JLabel("Fees:"); 
jLabelfee.setBounds(50, 160, 100, 30); 
jLabelfee.setFont(font); 
jFrame.add(jLabelfee); 
// TextFields 
jTextFieldrno = new JTextField(); 
jTextFieldrno.setBounds(160, 40, 200, 30); 
jTextFieldrno.setFont(font); 
jFrame.add(jTextFieldrno); 
jTextFieldname = new JTextField(); 
jTextFieldname.setBounds(160, 80, 200, 30); 
jTextFieldname.setFont(font); 
jFrame.add(jTextFieldname); 
jTextFieldcourse = new JTextField(); 
jTextFieldcourse.setBounds(160, 120, 200, 30); 
jTextFieldcourse.setFont(font); 
jFrame.add(jTextFieldcourse); 
jTextFieldfee = new JTextField(); 
jTextFieldfee.setBounds(160, 160, 200, 30); 
jTextFieldfee.setFont(font); 
jFrame.add(jTextFieldfee); 
// Buttons 
jButtonfirst = new JButton("First"); 
jButtonnext = new JButton("Next"); 
jButtonprev = new JButton("Prev"); 
jButtonlast = new JButton("Last"); 
jButtonnew = new JButton("New"); 
jButtoninsert = new JButton("Insert"); 
jButtonupdte = new JButton("Update"); 
jButtondelete = new JButton("Delete"); 
jButtonclear = new JButton("Clear"); 
jButtonexit = new JButton("Exit"); 
JButton[] btns = {jButtonfirst, jButtonnext, jButtonprev, 
jButtonlast, jButtonnew, 
jButtoninsert, jButtonupdte, jButtondelete, jButtonclear, 
jButtonexit}; 
int x = 20, y = 220; 
for (int i = 0; i < btns.length; i++) { 
btns[i].setBounds(x, y, 100, 35); 
btns[i].setFont(font); 
btns[i].addActionListener(this); 
jFrame.add(btns[i]); 
x += 110; 
if (x > 400) { 
x = 20; 
y += 50; 
} 
} 
jFrame.setVisible(true); 
 
        // Connect to Database 
        connectToDB(); 
    } 
 
    public void connectToDB() { 
        try { 
            Class.forName("com.mysql.cj.jdbc.Driver"); 
            con = DriverManager.getConnection( 
                    
"jdbc:mysql://localhost:3306/studentdb?useSSL=false&serverTimezo
ne=UTC", 
                    "root", 
                    "sa123" // change to your password 
            ); 
            getDataFromDB(); 
        } catch (Exception ex) { 
            JOptionPane.showMessageDialog(jFrame, "Database 
Connection Failed: " + ex.getMessage()); 
        } 
    } 
 
    public void getDataFromDB() { 
        try { 
            pstmt = con.prepareStatement("SELECT * FROM student", 
                    ResultSet.TYPE_SCROLL_INSENSITIVE, 
                    ResultSet.CONCUR_READ_ONLY); 
            rs = pstmt.executeQuery(); 
            if (rs.next()) { 
                displayData(); 
            } 
        } catch (Exception ex) { 
            ex.printStackTrace(); 
        } 
    } 
 
    public void displayData() { 
        try { 
            jTextFieldrno.setText(rs.getString("rno")); 
            jTextFieldname.setText(rs.getString("name")); 
            jTextFieldcourse.setText(rs.getString("course")); 
            jTextFieldfee.setText(rs.getString("fees")); 
        } catch (Exception ex) { 
            ex.printStackTrace(); 
        } 
    } 
 
    public void insertDataIntoDB() { 
        try { 
            int rno = Integer.parseInt(jTextFieldrno.getText()); 
            String name = jTextFieldname.getText(); 
            String course = jTextFieldcourse.getText(); 
            float fee = Float.parseFloat(jTextFieldfee.getText()); 
 
            String sql = "INSERT INTO student VALUES(?, ?, ?, ?)"; 
            pstmt = con.prepareStatement(sql); 
            pstmt.setInt(1, rno); 
            pstmt.setString(2, name); 
            pstmt.setString(3, course); 
            pstmt.setFloat(4, fee); 
 
            int n = pstmt.executeUpdate(); 
            if (n > 0) { 
                JOptionPane.showMessageDialog(jFrame, "Record Inserted 
Successfully!"); 
                getDataFromDB(); 
} 
} catch (Exception e) { 
JOptionPane.showMessageDialog(jFrame, "Error: " + 
e.getMessage()); 
} 
} 
public void clearALL() { 
jTextFieldrno.setText(""); 
jTextFieldname.setText(""); 
jTextFieldcourse.setText(""); 
jTextFieldfee.setText(""); 
} 
public void actionPerformed(ActionEvent e) { 
try { 
Object src = e.getSource(); 
if (src == jButtonfirst) { 
if (rs.first()) displayData(); 
} else if (src == jButtonnext) { 
if (rs.next()) displayData(); 
else JOptionPane.showMessageDialog(jFrame, "Last 
Record"); 
} else if (src == jButtonprev) { 
if (rs.previous()) displayData(); 
else JOptionPane.showMessageDialog(jFrame, "First 
Record"); 
} else if (src == jButtonlast) { 
if (rs.last()) displayData(); 
} else if (src == jButtonnew || src == jButtonclear) { 
clearALL(); 
} else if (src == jButtoninsert) { 
insertDataIntoDB(); 
} else if (src == jButtonexit) { 
System.exit(0); 
} 
} catch (Exception ex) { 
ex.printStackTrace(); 
} 
} 
public static void main(String[] args) { 
new StudentGui(); 
} 
} 
package pack_studentDB; 
public class MainClass 
{ 
public static void main(String[] args) { 
StudentGui gui = new StudentGui(); 
} 
} 
