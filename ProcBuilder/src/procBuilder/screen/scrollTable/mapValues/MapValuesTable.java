package procBuilder.screen.scrollTable.mapValues;

import javax.swing.table.TableColumn;

import procBuilder.screen.scrollTable.ScrollTable;

public class MapValuesTable extends ScrollTable<MapValueRowWrapper> {

	//TODO - Make this editable / get key-value pairs out
	
	public final static String[] COLUMN_NAMES = {"Key", "Value"};
	
	public MapValuesTable() {
		int columnWidth = getWidth() / getColumnNames().length;
		TableColumn column = null;
		for (int i = 0; i < getColumnNames().length; i++) {
		    column = getTable().getColumnModel().getColumn(i);
		    column.setPreferredWidth(columnWidth);
		}
	}
	
	@Override
	public String[] getColumnNames() {
		return COLUMN_NAMES;
	}
}