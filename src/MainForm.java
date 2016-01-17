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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import dbPackage.DataBaseProvider;
import dbPackage.TableClass;
import javax.swing.ListSelectionModel;



/*Разработать GUI-приложение с организацией взаимодействия с БД, реализующий методику 
расчета показателей для оценки ликвидности и платежеспо-собности предприятия.*/
public class MainForm {

	private JFrame frame;

	private JTable table;
	
	private List<TableClass> tableDataList = new ArrayList<TableClass>();
	
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
		frame.setBounds(100, 100, 716, 485);
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
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

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
	            return row.Id;
	        case 1:
	            return row.Name;
	        case 2:
	            return row.Active1;
	        case 3:
	            return row.Active2;
	        case 4:
	            return row.Active3;
	        case 5:
	            return row.Active4;
	        case 6:
	            return row.Passive1;
	        case 7:
	            return row.Passive2;
	        case 8:
	            return row.Passive3;
	        case 9:
	            return row.Passive4;
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
	            return "Active1";
	        case 3:
	            return "Active2";
	        case 4:
	            return "Active3";
	        case 5:
	            return "Active4";
	        case 6:
	            return "Passive1";
	        case 7:
	            return "Passive2";
	        case 8:
	            return "Passive3";
	        case 9:
	            return "Passive4";
	        }
	        
	        return "";
	    }
	}
}
