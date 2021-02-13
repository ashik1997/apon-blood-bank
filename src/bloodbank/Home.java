package bloodbank;

import static bloodbank.Login.loginby;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author Aashiq
 */
public class Home extends javax.swing.JFrame {
Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    /**
     * Creates new form NewJFrame
     */
    public Home() {
        initComponents();
         conn= DatabaseConnection.db_connect();
         Update_BloodDonor_table();
         AutoDonorNo();
         CurrentDateTime();
         PieChart();
         txt_user.setText(loginby);
        
    }
    ///---Date and time show-----
    public void CurrentDateTime(){
     
        Thread clock=new Thread(){
        public void run(){
        for(;;){
         Calendar cal=new GregorianCalendar();
        int month=cal.get(Calendar.MONTH);
        int year=cal.get(Calendar.YEAR);
        int day=cal.get(Calendar.DAY_OF_MONTH);
        date.setText((year+"-"+(month+1)+"-"+day));
        
        int second=cal.get(Calendar.SECOND);
        int minute=cal.get(Calendar.MINUTE);
        int hour=cal.get(Calendar.HOUR);
        time.setText((hour+":"+minute+":"+second));        
        try {
            sleep(1000);
        }catch (InterruptedException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
        } 
        }
        };
        clock.start();       
    }
    
    
    
     ////------show table-----
    public void Update_BloodDonor_table(){
        try{
        String sl="select * from BloodDonor";
        pst=conn.prepareStatement(sl);
        rs=pst.executeQuery();
        tbl_blood_donor_list.setModel(DbUtils.resultSetToTableModel(rs));
        }catch(SQLException e){
//        JOptionPane.showMessageDialog(null, e);
        }finally{
            try{
            rs.close();
            pst.close();
            }catch(SQLException e){}
        }
    }
    
    //---------automatic blood donor no increse--------
    public void AutoDonorNo(){
        try{
            String sl="select max(Donor_No) from BloodDonor";
            pst=conn.prepareStatement(sl);
            rs=pst.executeQuery();
            String s=rs.getString("max(Donor_No)");
            if(s==null){
            txt_donor_no.setText("1"); 
            }else{
            int a = Integer.parseInt(s);
            int b=a+1;
            txt_donor_no.setText(String.valueOf(b));
            }  
        }catch (NumberFormatException | SQLException e) {
//            JOptionPane.showMessageDialog(null, e);
        }
        finally{
        try{
            rs.close();
            pst.close();
        }catch(SQLException e){}
        }
    }
    //------all field clear--------
     public void ClearField(){
        txt_name.setText(null);
        txt_age.setText(null);
        txt_phone.setText(null);
        txt_email.setText(null);
        txt_address.setText(null);
        txt_search.setText("Enter BloodGroup or Phone or Name or DonarNo...............");
        cmb_blood_group.setSelectedIndex(0);
        cmb_sex.setSelectedIndex(0);
        Update_BloodDonor_table();
        AutoDonorNo();
    }
     ///-----blood group of pie chart-----
     public void PieChart(){
        String abp = null,abm = null,ap = null,am = null,bp = null,bm = null,op = null,om = null; 
        try{
            //---count all ab+ blood group--            
            String ab1="select count(Blood_Group) from BloodDonor where Blood_Group='AB+'";            
            pst=conn.prepareStatement(ab1);
            rs=pst.executeQuery();
            if(rs.next()){
                abp=rs.getString("count(Blood_Group)");
            }                                   
            //---count all ab- blood group--        
            String ab2="select count(Blood_Group) from BloodDonor where Blood_Group='AB-'";            
            pst=conn.prepareStatement(ab2);
            rs=pst.executeQuery();
            if(rs.next()){
                abm=rs.getString("count(Blood_Group)");
            }                                   
            //---count all a+ blood group--        
            String a1="select count(Blood_Group) from BloodDonor where Blood_Group='A+'";            
            pst=conn.prepareStatement(a1);
            rs=pst.executeQuery();
            if(rs.next()){
                ap=rs.getString("count(Blood_Group)");
            }                                   
            //---count all a- blood group--        
            String a2="select count(Blood_Group) from BloodDonor where Blood_Group='A-'";            
            pst=conn.prepareStatement(a2);
            rs=pst.executeQuery();
            if(rs.next()){
                am=rs.getString("count(Blood_Group)");
            }                                   
            //---count all b+ blood group--        
            String b1="select count(Blood_Group) from BloodDonor where Blood_Group='B+'";            
            pst=conn.prepareStatement(b1);
            rs=pst.executeQuery();
            if(rs.next()){
                bp=rs.getString("count(Blood_Group)");
            }                                   
            //---count all b- blood group--         
            String b2="select count(Blood_Group) from BloodDonor where Blood_Group='B-'";            
            pst=conn.prepareStatement(b2);
            rs=pst.executeQuery();
            if(rs.next()){
                bm=rs.getString("count(Blood_Group)");
            }                                   
            //---count all o+ blood group--         
            String o1="select count(Blood_Group) from BloodDonor where Blood_Group='O+'";            
            pst=conn.prepareStatement(o1);
            rs=pst.executeQuery();
            if(rs.next()){
                op=rs.getString("count(Blood_Group)");
            }                                   
            //---count all o- blood group--         
            String o2="select count(Blood_Group) from BloodDonor where Blood_Group='O-'";            
            pst=conn.prepareStatement(o2);
            rs=pst.executeQuery();
            if(rs.next()){
                om=rs.getString("count(Blood_Group)");
            }                                   
        }catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e);
        }
        finally{
        try{
            rs.close();
            pst.close();
        }catch(SQLException e){}
        }
        ///Show pie chart on the home page
        try{
        DefaultPieDataset dcd=new DefaultPieDataset(); 
        dcd.setValue("AB+",new Integer(abp));
        dcd.setValue("AB-",new Integer(abm));
        dcd.setValue("A+",new Integer(ap));
        dcd.setValue("A-",new Integer(am));
        dcd.setValue("B+",new Integer(bp));
        dcd.setValue("B-",new Integer(bm));
        dcd.setValue("O+",new Integer(op));
        dcd.setValue("O-",new Integer(om));
        JFreeChart jchart=ChartFactory.createPieChart3D("Blood Group Record",  dcd, true, true, true);
        PiePlot3D plot=(PiePlot3D) jchart.getPlot();
//        plot.setForegroundAlpha(TOP_ALIGNMENT);
        ChartFrame chartframe=new ChartFrame("Blood Group Record", jchart);
//        chartframe.setVisible(true);
        chartframe.setSize(500,400);
        ChartPanel chartpanel=new ChartPanel(jchart);
        pnaelReport.removeAll();
        pnaelReport.add(chartpanel);
        pnaelReport.updateUI(); 
       }catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e);
        }
        finally{
        try{
            rs.close();
            pst.close();
        }catch(SQLException e){}
        }
     }
///------Logout-----
     public void Logout(){
     try{
        //--audite table update.... 
        String sql = "update Audite set Logout_Date='"+date.getText()+"',"         
        + "Logout_Time='"+time.getText()+"'"
        + "where User='"+txt_user.getText()+"'";
        pst = conn.prepareStatement(sql);                   
        pst.execute();
            //        JOptionPane.showMessageDialog(null,"Upadate");
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null,e);
        }
        finally {
        try {
            rs.close();
            pst.close();
        }catch (SQLException e) {}
        }
     }
     void DeletedIfo(){
        try{
        String sql = "insert into DeleteInfo (Delete_By,Donor_No,Donor_Name,Date,Time) values (?,?,?,?,?)";
        pst = conn.prepareStatement(sql);
        pst.setString(1, txt_user.getText());        
        pst.setString(2, txt_donor_no.getText());
        pst.setString(3, txt_name.getText());        
        pst.setString(4, date.getText());
        pst.setString(5, time.getText());        
        pst.execute();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,e);
        }
        finally {
        try {
            rs.close();
            pst.close();
        }catch (SQLException e) {}
        }        
     }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dynamicPnaelHome = new javax.swing.JDesktopPane();
        jPanel4 = new javax.swing.JPanel();
        chartPnael = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_blood_donor_list = new javax.swing.JTable();
        txt_search = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        pnaelReport = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        btn_clear = new javax.swing.JButton();
        btn_save = new javax.swing.JButton();
        btn_update = new javax.swing.JButton();
        btn_delete = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        txt_email = new javax.swing.JTextField();
        txt_donor_no = new javax.swing.JTextField();
        txt_name = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txt_phone = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        cmb_sex = new javax.swing.JComboBox<>();
        txt_age = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        cmb_blood_group = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        txt_address = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        mnu_new_user = new javax.swing.JMenuItem();
        mnu_staff = new javax.swing.JMenuItem();
        mnu_logout = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        date = new javax.swing.JMenu();
        jMenu5 = new javax.swing.JMenu();
        time = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        txt_user = new javax.swing.JMenu();
        btn_copyright = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jPanel4.setBackground(new java.awt.Color(102, 102, 102));

        chartPnael.setBackground(new java.awt.Color(204, 204, 204));

        tbl_blood_donor_list.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        tbl_blood_donor_list.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tbl_blood_donor_list.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_blood_donor_listMouseClicked(evt);
            }
        });
        tbl_blood_donor_list.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbl_blood_donor_listKeyReleased(evt);
            }
        });
        jScrollPane3.setViewportView(tbl_blood_donor_list);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1311, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );

        jScrollPane4.setViewportView(jPanel5);

        txt_search.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txt_search.setText("Enter BloodGroup or Phone or Name or DonarNo...............");
        txt_search.setToolTipText("Enter Blood Group or Phone or Name or DonarNo");
        txt_search.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_searchMouseClicked(evt);
            }
        });
        txt_search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_searchKeyPressed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel9.setText("Search");

        pnaelReport.setBorder(javax.swing.BorderFactory.createTitledBorder("Report"));
        pnaelReport.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout chartPnaelLayout = new javax.swing.GroupLayout(chartPnael);
        chartPnael.setLayout(chartPnaelLayout);
        chartPnaelLayout.setHorizontalGroup(
            chartPnaelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(chartPnaelLayout.createSequentialGroup()
                .addGroup(chartPnaelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(chartPnaelLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(chartPnaelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(chartPnaelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pnaelReport, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
                .addContainerGap())
        );
        chartPnaelLayout.setVerticalGroup(
            chartPnaelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(chartPnaelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(chartPnaelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnaelReport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));

        btn_clear.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        btn_clear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bloodbank/Clear-icon.png"))); // NOI18N
        btn_clear.setText("Clear");
        btn_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clearActionPerformed(evt);
            }
        });

        btn_save.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        btn_save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bloodbank/Actions-document-save-icon.png"))); // NOI18N
        btn_save.setText("Save");
        btn_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saveActionPerformed(evt);
            }
        });

        btn_update.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        btn_update.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bloodbank/Actions-edit-redo-icon.png"))); // NOI18N
        btn_update.setText("Update");
        btn_update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateActionPerformed(evt);
            }
        });

        btn_delete.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        btn_delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bloodbank/Actions-window-close-icon.png"))); // NOI18N
        btn_delete.setText("Delete");
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_delete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_update, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_clear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_save, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_delete)
                    .addComponent(btn_update)
                    .addComponent(btn_clear)
                    .addComponent(btn_save))
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        txt_email.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        txt_donor_no.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txt_donor_no.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_donor_noActionPerformed(evt);
            }
        });

        txt_name.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel6.setText("Phone");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel2.setText("Name");

        jLabel8.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel8.setText("Address");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel4.setText("Age");

        txt_phone.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txt_phone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_phoneActionPerformed(evt);
            }
        });
        txt_phone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_phoneKeyTyped(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel5.setText("Sex");

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel1.setText("Blood Group");

        cmb_sex.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        cmb_sex.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "Male", "Female" }));

        txt_age.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel7.setText("Email");

        cmb_blood_group.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        cmb_blood_group.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "O+", "O-", "A+", "A-", "B+", "B-", "AB+", "AB-" }));
        cmb_blood_group.setPreferredSize(new java.awt.Dimension(6, 23));
        cmb_blood_group.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_blood_groupActionPerformed(evt);
            }
        });

        txt_address.setColumns(20);
        txt_address.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        txt_address.setRows(5);
        jScrollPane1.setViewportView(txt_address);

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jLabel3.setText("Donor No");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmb_blood_group, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_donor_no))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_phone))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_email))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_name))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cmb_sex, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txt_age)))))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_donor_no, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmb_blood_group, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_name, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_age, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmb_sex, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_phone, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bloodbank/as.jpg"))); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(chartPnael, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(chartPnael, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        dynamicPnaelHome.setLayer(jPanel4, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout dynamicPnaelHomeLayout = new javax.swing.GroupLayout(dynamicPnaelHome);
        dynamicPnaelHome.setLayout(dynamicPnaelHomeLayout);
        dynamicPnaelHomeLayout.setHorizontalGroup(
            dynamicPnaelHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dynamicPnaelHomeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        dynamicPnaelHomeLayout.setVerticalGroup(
            dynamicPnaelHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dynamicPnaelHomeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jMenu1.setText("File");

        mnu_new_user.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        mnu_new_user.setText("New User");
        mnu_new_user.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnu_new_userActionPerformed(evt);
            }
        });
        jMenu1.add(mnu_new_user);

        mnu_staff.setText("Staff");
        mnu_staff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnu_staffActionPerformed(evt);
            }
        });
        jMenu1.add(mnu_staff);

        mnu_logout.setText("Log Out");
        mnu_logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnu_logoutActionPerformed(evt);
            }
        });
        jMenu1.add(mnu_logout);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");

        jMenuItem1.setText("BarChart&PieChart");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);

        jMenuBar1.add(jMenu2);

        jMenu4.setText("Date :");
        jMenuBar1.add(jMenu4);

        date.setText("date");
        jMenuBar1.add(date);

        jMenu5.setText("Time :");
        jMenuBar1.add(jMenu5);

        time.setText("time");
        jMenuBar1.add(time);

        jMenu3.setText("Login by :");
        jMenuBar1.add(jMenu3);
        jMenuBar1.add(txt_user);

        btn_copyright.setText("copyright");
        btn_copyright.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_copyrightMouseClicked(evt);
            }
        });
        btn_copyright.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_copyrightActionPerformed(evt);
            }
        });
        jMenuBar1.add(btn_copyright);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dynamicPnaelHome)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dynamicPnaelHome)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txt_donor_noActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_donor_noActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_donor_noActionPerformed

    private void txt_phoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_phoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_phoneActionPerformed

    private void txt_phoneKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_phoneKeyTyped
        char c=evt.getKeyChar();
        if(!(Character.isDigit(c)|| (c==KeyEvent.VK_BACK_SLASH)|| c==KeyEvent.VK_DELETE)){
        evt.consume();
        }      
    }//GEN-LAST:event_txt_phoneKeyTyped

    private void cmb_blood_groupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_blood_groupActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmb_blood_groupActionPerformed

    private void tbl_blood_donor_listMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_blood_donor_listMouseClicked
        //--mouse click table then set value text field
        try{
            int row=tbl_blood_donor_list.getSelectedRow();
            String tableClick=(tbl_blood_donor_list.getModel().getValueAt(row, 0).toString());
            String sql="select * from BloodDonor where Donor_No='"+tableClick+"' ";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            if(rs.next()){
                txt_donor_no.setText(rs.getString("Donor_No"));
                txt_name.setText(rs.getString("Name"));
                txt_age.setText(rs.getString("Age"));
                txt_phone.setText(rs.getString("Phone"));
                txt_email.setText(rs.getString("Email"));
                txt_address.setText(rs.getString("Address"));
                cmb_blood_group.setSelectedItem(rs.getString("Blood_Group"));
                cmb_sex.setSelectedItem(rs.getString("Sex"));
            }
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null,e);

        }finally{
            try{
                rs.close();
                pst.close();
            }catch(SQLException e){}
        }
    }//GEN-LAST:event_tbl_blood_donor_listMouseClicked

    private void tbl_blood_donor_listKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_blood_donor_listKeyReleased
        //----table up down key and set value text field
        try{
            int row=tbl_blood_donor_list.getSelectedRow();
            String tableClick=(tbl_blood_donor_list.getModel().getValueAt(row, 0).toString());
            String sql="select * from BloodDonor where Donor_No='"+tableClick+"' ";
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            if(rs.next()){
                txt_donor_no.setText(rs.getString("Donor_No"));
                txt_name.setText(rs.getString("Name"));
                txt_age.setText(rs.getString("Age"));
                txt_phone.setText(rs.getString("Phone"));
                txt_email.setText(rs.getString("Email"));
                txt_address.setText(rs.getString("Address"));
                cmb_blood_group.setSelectedItem(rs.getString("Blood_Group"));
                cmb_sex.setSelectedItem(rs.getString("Sex"));
            }
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null,e);
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(SQLException e){}
        }
    }//GEN-LAST:event_tbl_blood_donor_listKeyReleased

    private void txt_searchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_searchKeyPressed
        //----search press any key then result set jtable
        try {
            String sql1 = "select * from BloodDonor  where Blood_Group || Name || Donor_No || Phone || Address like  '%" + txt_search.getText() + "%' order by Donor_No asc";
            pst=conn.prepareStatement(sql1);
            rs=pst.executeQuery();
            tbl_blood_donor_list.setModel(DbUtils.resultSetToTableModel(rs));
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e);
        }finally {
            try {
                rs.close();
                pst.close();
            }catch (SQLException e) {}
        }
    }//GEN-LAST:event_txt_searchKeyPressed

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        //--delete blood donor info from database
        if("".equals(txt_name.getText())||"".equals(txt_donor_no.getText())){
        JOptionPane.showMessageDialog(null, "Please Enter Donor No And Name");
        }else{
        int p = JOptionPane.showConfirmDialog(null, "Do You Want To Delete", "Delete", JOptionPane.YES_NO_OPTION);
        if (p == 0) {
            try {
                String sql = "delete from BloodDonor where Donor_No=? and Name=?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, txt_donor_no.getText());
                pst.setString(2, txt_name.getText());
                pst.execute();
                DeletedIfo();
                AutoDonorNo();
                ClearField();
                Update_BloodDonor_table();
                PieChart();
//                JOptionPane.showMessageDialog(null, "Data Deleted");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {}
            }
        }        
        }
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void btn_updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateActionPerformed
        //------blood donor info edit and save databse
        if("".equals(cmb_blood_group.getSelectedItem().toString())){
            JOptionPane.showMessageDialog(null,"Plese fill up all field");
            cmb_blood_group.requestFocus();
        }else if("".equals(txt_name.getText())){
            JOptionPane.showMessageDialog(null,"Plese fill up all field");
            txt_name.requestFocus();
        }else if("".equals(txt_age.getText())){
            JOptionPane.showMessageDialog(null,"Plese fill up all field");
            txt_age.requestFocus();
        }else if("".equals(cmb_sex.getSelectedItem().toString())){
            JOptionPane.showMessageDialog(null,"Plese fill up all field");
            cmb_sex.requestFocus();
        }else if("".equals(txt_phone.getText())){
            JOptionPane.showMessageDialog(null,"Plese fill up all field");
            txt_phone.requestFocus();
        }else{
            int p=JOptionPane.showConfirmDialog(null,"Do You Want To Update","Update",JOptionPane.YES_NO_OPTION);
            if(p==0){
                try{
                    String sql = "update BloodDonor set Name='"+txt_name.getText()+"',"
                    + "Age='"+txt_age.getText()+"',"
                    + "Sex='"+cmb_sex.getSelectedItem().toString()+"',"
                    + "Phone='"+txt_phone.getText()+"',"
                    + "Email='"+txt_email.getText()+"',"
                    + "Address='"+txt_address.getText()+"',"
                    + "Blood_Group='"+cmb_blood_group.getSelectedItem().toString()+"'," 
                    + "Update_By='"+txt_user.getText()+"'"
                    + "where Donor_No='"+txt_donor_no.getText()+"'";
                    pst = conn.prepareStatement(sql);                   
                    pst.execute();
                    Update_BloodDonor_table();
                    ClearField();
                    AutoDonorNo();
                    PieChart();
                    //        JOptionPane.showMessageDialog(null,"Upadate");
                }catch(SQLException e){
                    JOptionPane.showMessageDialog(null,e);
                }
                finally {
                try {
                    rs.close();
                    pst.close();
                }catch (SQLException e) {}
                }                
            }
        }
    }//GEN-LAST:event_btn_updateActionPerformed

    private void btn_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clearActionPerformed
        ClearField();
    }//GEN-LAST:event_btn_clearActionPerformed

    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed
        //---new blood donor info save in the database
        try{            
            String sql1 = "select * from BloodDonor where Donor_No=?";
            pst=conn.prepareStatement(sql1);
            pst.setString(1, txt_donor_no.getText());
            rs=pst.executeQuery();
            if(rs.next()){
            JOptionPane.showMessageDialog(null, "Already Exist");
            }else{
        if("".equals(cmb_blood_group.getSelectedItem().toString())){
            JOptionPane.showMessageDialog(null,"Plese fill up all field");
            cmb_blood_group.requestFocus();
        }else if("".equals(txt_name.getText())){
            JOptionPane.showMessageDialog(null,"Plese fill up all field");
            txt_name.requestFocus();
        }else if("".equals(txt_age.getText())){
            JOptionPane.showMessageDialog(null,"Plese fill up all field");
            txt_age.requestFocus();
        }else if("".equals(cmb_sex.getSelectedItem().toString())){
            JOptionPane.showMessageDialog(null,"Plese fill up all field");
            cmb_sex.requestFocus();
        }else if("".equals(txt_phone.getText())){
            JOptionPane.showMessageDialog(null,"Plese fill up all field");
            txt_phone.requestFocus();
        }else{
                    int p=JOptionPane.showConfirmDialog(null,"Do You Want To Save","Saved",JOptionPane.YES_NO_OPTION);
                    if(p==0){
                        try {
                            String sql = "insert into BloodDonor (Donor_No,Blood_Group,Name,Age,Sex,Phone,Email,Address,Saved_By) values (?,?,?,?,?,?,?,?,?)";
                            pst = conn.prepareStatement(sql);
                            pst.setString(1, txt_donor_no.getText());
                            pst.setString(2, (cmb_blood_group.getSelectedItem().toString()));
                            pst.setString(3, txt_name.getText());
                            pst.setString(4, txt_age.getText());
                            pst.setString(5, (cmb_sex.getSelectedItem().toString()));
                            pst.setString(6, txt_phone.getText());
                            pst.setString(7, txt_email.getText());
                            pst.setString(8, txt_address.getText());
                            pst.setString(9, txt_user.getText());
                            pst.execute();
                            Update_BloodDonor_table();
                            ClearField();
                            AutoDonorNo();
                            PieChart();
                            //            JOptionPane.showMessageDialog(null, "Data Saved");
                        }catch (SQLException e) {
                            JOptionPane.showMessageDialog(null, e);
                        }
                        finally{
                            try{
                                rs.close();
                                pst.close();
                            }catch(SQLException e){}
                        }
                    }                   
                }
            }
        }catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }finally{
            try{
                rs.close();
                pst.close();
            }catch(SQLException e){}
        }
    }//GEN-LAST:event_btn_saveActionPerformed

    private void mnu_new_userActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnu_new_userActionPerformed
        NewUser nu= new NewUser();
       dynamicPnaelHome.add(nu);
       nu.show();
        
    }//GEN-LAST:event_mnu_new_userActionPerformed

    private void txt_searchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_searchMouseClicked
        txt_search.setText("");
    }//GEN-LAST:event_txt_searchMouseClicked

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        BarChart nu= new BarChart();
       dynamicPnaelHome.add(nu);
       nu.show();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void mnu_logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnu_logoutActionPerformed
        Logout();
        super.dispose();
        Login hm= new Login();
        hm.show();
    }//GEN-LAST:event_mnu_logoutActionPerformed

    private void btn_copyrightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_copyrightActionPerformed
        
    }//GEN-LAST:event_btn_copyrightActionPerformed

    private void btn_copyrightMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_copyrightMouseClicked
        JOptionPane.showMessageDialog(null,"'Ashik'\nSoftCare IT");
    }//GEN-LAST:event_btn_copyrightMouseClicked

    private void mnu_staffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnu_staffActionPerformed
        StaffRegistation s=new StaffRegistation();
        dynamicPnaelHome.add(s);
        s.show();
    }//GEN-LAST:event_mnu_staffActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Home().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_clear;
    private javax.swing.JMenu btn_copyright;
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_save;
    private javax.swing.JButton btn_update;
    private javax.swing.JPanel chartPnael;
    private javax.swing.JComboBox<String> cmb_blood_group;
    private javax.swing.JComboBox<String> cmb_sex;
    private javax.swing.JMenu date;
    private javax.swing.JDesktopPane dynamicPnaelHome;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JMenuItem mnu_logout;
    private javax.swing.JMenuItem mnu_new_user;
    private javax.swing.JMenuItem mnu_staff;
    private javax.swing.JPanel pnaelReport;
    private javax.swing.JTable tbl_blood_donor_list;
    private javax.swing.JMenu time;
    private javax.swing.JTextArea txt_address;
    private javax.swing.JTextField txt_age;
    private javax.swing.JTextField txt_donor_no;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_name;
    private javax.swing.JTextField txt_phone;
    private javax.swing.JTextField txt_search;
    private javax.swing.JMenu txt_user;
    // End of variables declaration//GEN-END:variables

}
