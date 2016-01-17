import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dbPackage.DataBaseProvider;

/*Разработать GUI-приложение с организацией взаимодействия с БД, реализующий методику 
расчета показателей для оценки ликвидности и платежеспо-собности предприятия.*/
public class MainForm {

	private JFrame frame;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
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
		try {
			initialize();	
			ShowTableFromDb();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("likvid Project");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		table = new JTable();

		JTable table = new JTable(data, columnNames) {
			public Class getColumnClass(int column) {
				for (int row = 0; row < getRowCount(); row++) {
					Object o = getValueAt(row, column);
					if (o != null) {
						return o.getClass();
					}
				}
				return Object.class;
			}
		};

		table.setModel(new DefaultTableModel(
				new Object[][] { { null, null, null, null }, { null, null, null, null }, { null, null, null, null },
						{ null, null, null, null }, },
				new String[] { "New column", "New column", "New column", "New column" }));
		table.setBounds(52, 35, 330, 166);
		frame.getContentPane().add(table);
	}

	public void ShowTableFromDb() throws SQLException {
		Vector columnNames = new Vector();
		Vector data = new Vector();
		try {
			Statement s = DataBaseProvider.GetNewStatement();
			String sql = "Select * from likvid.organization as org " + "INNER JOIN "
					+ "actives as act ON act.OrganisationId = org.Id " + "INNER JOIN"
					+ "passives as pas ON pas.OrganisationId = org.Id";
			ResultSet rs = s.executeQuery(sql);

			ResultSetMetaData md = rs.getMetaData();
			// get counts of the columns
			int columns = md.getColumnCount();
			
			 //  Get the names of the columns
            for (int i = 1; i <= columns; i++) {
                columnNames.addElement(md.getColumnName(i));
            }

            // read every row with .next
            while (rs.next()) {
                Vector row = new Vector(columns);
                for (int i = 1; i <= columns; i++) {
                    row.addElement(rs.getObject(i));
                }
                data.addElement(row);
            }
            rs.close();
            s.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
