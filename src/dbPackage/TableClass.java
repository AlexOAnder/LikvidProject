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
	public int Id;
	public String Name;
	public int Active1;
	public int Active2;
	public int Active3;
	public int Active4;
	
	public int Passive1;
	public int Passive2;
	public int Passive3;
	public int Passive4;
}
