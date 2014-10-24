package procBuilder.screen.scrollTable.mapValues;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.TableColumn;

import procBuilder.screen.scrollTable.ScrollTable;


public class MapValuesTable extends ScrollTable<MapValueRowWrapper> {

	//TODO - Make this editable
	
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
	
	/**
	 * Get the contents of this table as a map
	 * @return The contents.
	 */
	public Map<String, String> getMap() {
		List<MapValueRowWrapper> data = getData();
		Map<String, String> retVal = new HashMap<String, String>();
		
		for (MapValueRowWrapper row : data) {
			retVal.put(row.getKey(), row.getValue());
		}
		
		return retVal;
	}
	
	public void setMap(Map<String, String> values) {
		for (String k : values.keySet()) {
			MapValueRowWrapper row = new MapValueRowWrapper();
			row.setKey(k);
			row.setValue(values.get(k));
			addRow(row);
		}
	}
}