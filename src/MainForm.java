import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import dbPackage.DataBaseProvider;
import javax.swing.ListSelectionModel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/*Created by alexoander - GUI + JDBC java learning*/
public class MainForm {

	private JFrame frame;

	private JTable table;
	
	private List<TableClass> tableDataList = new ArrayList<TableClass>();
	private final Action action = new SwingAction();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DataBaseProvider.GetInstance();
					MainForm window = new MainForm();
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
		ShowTableFromDb();
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(){
		frame = new JFrame();
		frame.setTitle("likvid Project");
		frame.setPreferredSize(new Dimension(710,480));
		frame.setBounds(100, 100, 710, 485);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
				
		TableModel model = new MyTableModel(tableDataList);

		table = new JTable(model);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFillsViewportHeight(true);

		table.setBounds(10, 11, 680, 196);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 11, 680, 196);
		
		frame.getContentPane().add(scrollPane);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(10, 218, 494, 209);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel label = new JLabel("\u041B\u0438\u043A\u0432\u0438\u0434\u043D\u0430 \u043B\u0438 \u043E\u0440\u0433\u0430\u043D\u0438\u0437\u0430\u0446\u0438\u044F:");
		label.setFont(new Font("Arial", Font.BOLD, 18));
		label.setBounds(10, 11, 330, 30);
		panel.add(label);
		
		JLabel PayableValueLabel = new JLabel("");
		PayableValueLabel.setFont(new Font("Arial", Font.BOLD, 22));
		PayableValueLabel.setBounds(232, 131, 108, 52);
		panel.add(PayableValueLabel);
		
		JLabel label_1 = new JLabel("\u041E\u0431\u0449\u0438\u0439 \u043F\u043E\u043A\u0430\u0437\u0430\u0442\u0435\u043B\u044C \u043F\u043B\u0430\u0442\u0435\u0436\u0435\u0441\u043F\u043E\u0441\u043E\u0431\u043D\u043E\u0441\u0442\u0438");
		label_1.setFont(new Font("Arial", Font.BOLD, 18));
		label_1.setBounds(10, 93, 410, 36);
		panel.add(label_1);
		
		JLabel IsLikvidLabel = new JLabel("");
		IsLikvidLabel.setFont(new Font("Arial", Font.BOLD, 22));
		IsLikvidLabel.setBounds(206, 41, 55, 52);
		panel.add(IsLikvidLabel);
		
		JLabel label_3 = new JLabel("L1 = ");
		label_3.setFont(new Font("Arial", Font.BOLD, 22));
		label_3.setBounds(142, 131, 55, 52);
		panel.add(label_3);
		
		JButton exitButton = new JButton("\u0412\u044B\u0445\u043E\u0434");
		exitButton.setFont(new Font("Tahoma", Font.BOLD, 18));
		exitButton.setBounds(514, 218, 180, 209);       
        exitButton.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                  frame.setVisible(false);
                  System.exit(0);
             }
        });
		
        ListSelectionModel selModel = table.getSelectionModel();
        
        selModel.addListSelectionListener(new ListSelectionListener() { 
        	@Override
            public void valueChanged(ListSelectionEvent e) {
                 String result = "";
                 int[] selectedRows = table.getSelectedRows();
                 for(int i = 0; i < selectedRows.length; i++) {
                      int selIndex = selectedRows[i];
                      TableModel model = table.getModel();
                      Object value = model.getValueAt(selIndex, 0);
                      if ( CheckLikvid(model,selIndex))
                      {
                    	  IsLikvidLabel.setForeground(Color.GREEN);
                    	  IsLikvidLabel.setVisible(true);
                    	  IsLikvidLabel.setText("\u0414\u0430");
                      }
                      else{
                    	  IsLikvidLabel.setForeground(Color.RED);
                    	  IsLikvidLabel.setVisible(true);
                    	  IsLikvidLabel.setText("\u041D\u0435\u0442");
                      }
                      double res = CalculatePayability(model,selIndex);
                      
                      PayableValueLabel.setText(String.format("%.2f",res));
                 }                   
            }             
       });
        
		frame.getContentPane().add(exitButton);
		frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);

	}

	public double CalculatePayability(TableModel model,int selIndex)
	{
		int a1 = (int) model.getValueAt(selIndex, 2);
        int a2 = (int) model.getValueAt(selIndex, 3);
        int a3 = (int) model.getValueAt(selIndex, 4);
        
        int p1 = (int) model.getValueAt(selIndex, 6);
        int p2 = (int) model.getValueAt(selIndex, 7);
        int p3 = (int) model.getValueAt(selIndex, 8);
        double tmp = a1+0.5*a2+0.3*a3;
        double tmp2 = p1+0.5*p2+0.3*p3;
        return tmp/tmp2;
	}
	
	public boolean CheckLikvid(TableModel model,int selIndex)
	{
		int a1 = (int) model.getValueAt(selIndex, 2);
        int a2 = (int) model.getValueAt(selIndex, 3);
        int a3 = (int) model.getValueAt(selIndex, 4);
        int a4 = (int) model.getValueAt(selIndex, 5);
        
        int p1 = (int) model.getValueAt(selIndex, 6);
        int p2 = (int) model.getValueAt(selIndex, 7);
        int p3 = (int) model.getValueAt(selIndex, 8);
        int p4 = (int) model.getValueAt(selIndex, 9);
        if (a1>=p1 && a2>=p2 && a3>=p3 && a4<=p4)
        {
        	return true;
        }
		return false;
	}
	
	public void ShowTableFromDb() {
		
		try {
			Statement s = DataBaseProvider.GetNewStatement();
			String sql = "Select org.*,act.a1,act.a2,act.a3,act.a4,"
					+ "pas.p1,pas.p2,pas.p3,pas.p4 "
					+ "from likvid.organization as org "
					+ "INNER JOIN likvid.actives as act ON act.OrganizationId = org.Id "
					+ "INNER JOIN likvid.passives as pas ON pas.OrganizationId = org.Id ";
			ResultSet rs = s.executeQuery(sql);

			ResultSetMetaData md = rs.getMetaData();
			// get counts of the columns
			int columns = md.getColumnCount();
			if (tableDataList!=null)
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
	
	public class MyTableModel extends AbstractTableModel{
		
		private List<TableClass> rows;
	    @Override
	    public int getRowCount() {
	    	 return rows.size();
	    }
	    @Override
	    public int getColumnCount() {
	        return 10;
	    }
	    
	    public Class<?> getColumnClass(int columnIndex) {
            return String.class;
        }
	    
	    public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }
	    
		@Override
		public Object getValueAt(int rowIndex, int colIndex) {
			TableClass row = rows.get(rowIndex);
            switch (colIndex) {
            case 0:
	            return row.getId();
	        case 1:
	            return row.getName();
	        case 2:
	            return row.getActive1();
	        case 3:
	            return row.getActive1();
	        case 4:
	            return row.getActive1();
	        case 5:
	            return row.getActive1();
	        case 6:
	            return row.getActive1();
	        case 7:
	            return row.getActive2();
	        case 8:
	            return row.getActive3();
	        case 9:
	            return row.getActive4();
	        }
            return "";
		}

		public MyTableModel(List<TableClass> data) {
	            this.rows = data;
	    }

		public String getColumnName(int columnIndex) {
	        switch (columnIndex) {
	        case 0:
	            return "Id";
	        case 1:
	            return "Name";
	        case 2:
	            return "Active 1";
	        case 3:
	            return "Active 2";
	        case 4:
	            return "Active 3";
	        case 5:
	            return "Active 4";
	        case 6:
	            return "Passive 1";
	        case 7:
	            return "Passive 2";
	        case 8:
	            return "Passive 3";
	        case 9:
	            return "Passive 4";
	        }
	        
	        return "";
	    }
	}
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
