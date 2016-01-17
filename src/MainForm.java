import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dbPackage.DataBaseProvider;
import dbPackage.MyTableModel;
import dbPackage.TableClass;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.FlowLayout;
import javax.swing.border.BevelBorder;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import java.awt.Insets;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

/*Разработать GUI-приложение с организацией взаимодействия с БД, реализующий методику 
расчета показателей для оценки ликвидности и платежеспо-собности предприятия.*/
public class MainForm {

	private JFrame frame;

	private JTable table;
	
	private List<TableClass> tableDataList;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DataBaseProvider.GetInstance();
					MainForm window = new MainForm();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainForm() {
		
		initialize();
		ShowTableFromDb();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(){
		frame = new JFrame();
		frame.setTitle("likvid Project");
		frame.setBounds(100, 100, 716, 485);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);
		
		
		table = new JTable();
		table.setBounds(10, 11, 665, 313);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(653, 11, 22, 313);
		frame.getContentPane().add(scrollPane);
		frame.getContentPane().add(table);

	}

	public void ShowTableFromDb() {
		
		try {
			Statement s = DataBaseProvider.GetNewStatement();
			String sql = "Select * from likvid.organization as org "
					+ "INNER JOIN likvid.actives as act ON act.OrganizationId = org.Id "
					+ "INNER JOIN likvid.passives as pas ON pas.OrganizationId = org.Id ";
			ResultSet rs = s.executeQuery(sql);

			ResultSetMetaData md = rs.getMetaData();
			// get counts of the columns
			int columns = md.getColumnCount();
			
			tableDataList.clear();

            // read every row with .next
            while (rs.next()) {
            	TableClass p = new TableClass(rs);
                tableDataList.add(p);
            }
            rs.close();
            s.close();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
}
