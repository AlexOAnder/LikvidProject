package dbPackage;

import javax.swing.table.AbstractTableModel;

public class MyTableModel extends AbstractTableModel{
    @Override
    public int getRowCount() {
        return 10;
    }
    @Override
    public int getColumnCount() {
        return 10;
    }
	@Override
	public Object getValueAt(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
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