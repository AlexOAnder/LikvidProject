package dbPackage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TableClass {

	public TableClass(ResultSet rs) throws SQLException {
		Id = rs.getInt(1);
		Name = rs.getString(2);
		Active1 = rs.getInt(3);
		Active2 = rs.getInt(4);
		Active3 = rs.getInt(5);
		Active4 = rs.getInt(6);
		Passive1 = rs.getInt(7);
		Passive2 = rs.getInt(8);
		Passive3 = rs.getInt(9);
		Passive4 = rs.getInt(10);
	}
	private int Id;
	private String Name;
	private int Active1;
	private int Active2;
	private int Active3;
	private int Active4;
	
	private int Passive1;
	private int Passive2;
	private int Passive3;
	private int Passive4;
}
