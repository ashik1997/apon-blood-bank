/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bloodbank;

import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author Aashiq
 */
public class BarChart extends javax.swing.JInternalFrame {
Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    String abp = null,abm = null,ap = null,am = null,bp = null,bm = null,op = null,om = null; 
    /**
     * Creates new form BarChart
     */
    public BarChart() {
        initComponents();
        conn= DatabaseConnection.db_connect();
        
        BarChart();
        PieChart();
        TotalDonor();
    }


    ///----Show bar chart--
    void BarChart(){
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
            JOptionPane.showMessageDialog(null, e);
        }
        finally{
        try{
            rs.close();
            pst.close();
        }catch(SQLException e){}
        }
        
        DefaultCategoryDataset dcd=new DefaultCategoryDataset();
        dcd.setValue(Integer.parseInt(abp), "Blood", "AB+");
        dcd.setValue(Integer.parseInt(abm), "Blood", "AB-");
        dcd.setValue(Integer.parseInt(ap), "Blood", "A+");
        dcd.setValue(Integer.parseInt(am), "Blood", "A-");
        dcd.setValue(Integer.parseInt(bp), "Blood", "B+");
        dcd.setValue(Integer.parseInt(bm), "Blood", "B-");
        dcd.setValue(Integer.parseInt(op), "Blood", "O+");
        dcd.setValue(Integer.parseInt(om), "Blood", "O-");
        JFreeChart jchart=ChartFactory.createBarChart("Blood Group Record", "Blood Group", "Number of Donor", dcd, PlotOrientation.VERTICAL, true, true, false);
        CategoryPlot plot=jchart.getCategoryPlot();
        plot.setRangeGridlinePaint(Color.black);
        ChartFrame chartframe=new ChartFrame("Blood Group Record", jchart, true);
//        chartframe.setVisible(true);
        chartframe.setSize(500,400);
        ChartPanel chartpanel=new ChartPanel(jchart);
        barchart_report_panel.removeAll();
        barchart_report_panel.add(chartpanel);
        barchart_report_panel.updateUI();
    }
    ///----show Pie chart-------
    void PieChart(){
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
        piechart_report_panel.removeAll();
        piechart_report_panel.add(chartpanel);
        piechart_report_panel.updateUI();
    }
    void TotalDonor(){
        try{
            //---total donor count query anb set jlebel
            String ab1="select count(Blood_Group) from BloodDonor";            
            pst=conn.prepareStatement(ab1);
            rs=pst.executeQuery();
            if(rs.next()){
            lbl_total.setText(rs.getString("count(Blood_Group)"));
            }
            //---end total donor count query
            lbl_abp.setText(abp);
            lbl_abm.setText(abm);
            lbl_ap.setText(ap);
            lbl_am.setText(am);
            lbl_bp.setText(bp);
            lbl_bm.setText(bm);
            lbl_op.setText(op);
            lbl_om.setText(om);
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        finally{
        try{
            rs.close();
            pst.close();
        }catch(SQLException e){}
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

        jDesktopPane1 = new javax.swing.JDesktopPane();
        chartPnael = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        barchart_report_panel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        piechart_report_panel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lbl_abp = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lbl_total = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lbl_bp = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel12 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lbl_ap = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        lbl_am = new javax.swing.JLabel();
        lbl_abm = new javax.swing.JLabel();
        lbl_op = new javax.swing.JLabel();
        lbl_om = new javax.swing.JLabel();
        lbl_bm = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();

        setClosable(true);
        setResizable(true);
        setPreferredSize(new java.awt.Dimension(958, 635));

        chartPnael.setBackground(new java.awt.Color(0, 51, 51));

        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2ActionPerformedjButton2ActionPerformedjButton2ActionPerformed(evt);
            }
        });

        barchart_report_panel.setBorder(javax.swing.BorderFactory.createTitledBorder("Report"));
        barchart_report_panel.setLayout(new java.awt.BorderLayout());
        jTabbedPane1.addTab("Bar Chart", barchart_report_panel);

        piechart_report_panel.setBorder(javax.swing.BorderFactory.createTitledBorder("Report"));
        piechart_report_panel.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(piechart_report_panel, javax.swing.GroupLayout.DEFAULT_SIZE, 897, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(piechart_report_panel, javax.swing.GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Pie Chart", jPanel1);

        jPanel2.setBackground(new java.awt.Color(204, 204, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        lbl_abp.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        lbl_abp.setText("jLabel2");

        jLabel10.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel10.setText("AB-");

        lbl_total.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        lbl_total.setText("jLabel2");

        jLabel11.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel11.setText("A+");

        jLabel15.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel15.setText("O+");

        jLabel13.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel13.setText("B+");

        lbl_bp.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        lbl_bp.setText("jLabel2");

        jSeparator1.setBackground(new java.awt.Color(51, 51, 51));

        jLabel12.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel12.setText("A-");

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel1.setText("AB+");

        lbl_ap.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        lbl_ap.setText("jLabel2");

        jLabel16.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel16.setText("O-");

        jLabel14.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel14.setText("B-");

        lbl_am.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        lbl_am.setText("jLabel2");

        lbl_abm.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        lbl_abm.setText("jLabel2");

        lbl_op.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        lbl_op.setText("jLabel2");

        lbl_om.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        lbl_om.setText("jLabel2");

        lbl_bm.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        lbl_bm.setText("jLabel2");

        jLabel17.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        jLabel17.setText("Total");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jSeparator1)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbl_abp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbl_abm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbl_ap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbl_am, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbl_bp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbl_bm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbl_op, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbl_om, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_total, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1)
                        .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_abp)
                    .addComponent(lbl_abm)
                    .addComponent(lbl_ap)
                    .addComponent(lbl_am)
                    .addComponent(lbl_bp)
                    .addComponent(lbl_bm)
                    .addComponent(lbl_op)
                    .addComponent(lbl_om)
                    .addComponent(lbl_total))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout chartPnaelLayout = new javax.swing.GroupLayout(chartPnael);
        chartPnael.setLayout(chartPnaelLayout);
        chartPnaelLayout.setHorizontalGroup(
            chartPnaelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(chartPnaelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(chartPnaelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addGroup(chartPnaelLayout.createSequentialGroup()
                        .addGap(815, 815, 815)
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, chartPnaelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 713, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        chartPnaelLayout.setVerticalGroup(
            chartPnaelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, chartPnaelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 474, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jDesktopPane1.setLayer(chartPnael, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chartPnael, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chartPnael, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformedjButton2ActionPerformedjButton2ActionPerformed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2ActionPerformedjButton2ActionPerformedjButton2ActionPerformed
        BarChart();
    }//GEN-LAST:event_jButton2ActionPerformedjButton2ActionPerformedjButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel barchart_report_panel;
    private javax.swing.JPanel chartPnael;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lbl_abm;
    private javax.swing.JLabel lbl_abp;
    private javax.swing.JLabel lbl_am;
    private javax.swing.JLabel lbl_ap;
    private javax.swing.JLabel lbl_bm;
    private javax.swing.JLabel lbl_bp;
    private javax.swing.JLabel lbl_om;
    private javax.swing.JLabel lbl_op;
    private javax.swing.JLabel lbl_total;
    private javax.swing.JPanel piechart_report_panel;
    // End of variables declaration//GEN-END:variables
}
