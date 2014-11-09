package procBuilder.screen.scrollTable.editable;

import javax.swing.JTable;

import procBuilder.screen.scrollTable.ScrollTable;

public abstract class EditableScrollTable<E extends EditableScrollTableRowWrapper> extends ScrollTable<E> {
	public EditableScrollTable() {
		super();
		JTable table = getTable();
		table.setRowSelectionAllowed(true);
	}
}