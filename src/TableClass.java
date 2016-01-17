
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

	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public int getActive1() {
		return Active1;
	}
	public void setActive1(int active1) {
		Active1 = active1;
	}
	public int getActive2() {
		return Active2;
	}
	public void setActive2(int active2) {
		Active2 = active2;
	}
	public int getActive3() {
		return Active3;
	}
	public void setActive3(int active3) {
		Active3 = active3;
	}
	public int getActive4() {
		return Active4;
	}
	public void setActive4(int active4) {
		Active4 = active4;
	}
	public int getPassive1() {
		return Passive1;
	}
	public void setPassive1(int passive1) {
		Passive1 = passive1;
	}
	public int getPassive2() {
		return Passive2;
	}
	public void setPassive2(int passive2) {
		Passive2 = passive2;
	}
	public int getPassive3() {
		return Passive3;
	}
	public void setPassive3(int passive3) {
		Passive3 = passive3;
	}
	public int getPassive4() {
		return Passive4;
	}
	public void setPassive4(int passive4) {
		Passive4 = passive4;
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
