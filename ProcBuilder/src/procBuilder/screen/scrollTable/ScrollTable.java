package procBuilder.screen.scrollTable;

import java.awt.BorderLayout;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

public abstract class ScrollTable<E extends ScrollTableRowWrapper> extends JPanel {
	private final static Logger LOGGER = Logger.getLogger(ScrollTable.class.getName());

	/*
	 * Screen components
	 */
	private JScrollPane scrollPane = null;
	private JTable table = null;

	/**
	 * Get the column names for this table, in order of appearance
	 * @return The column names
	 */
	public abstract String[] getColumnNames();
	
	/**
	 * Create the panel.
	 */
	public ScrollTable() {
		LOGGER.finest("Creating table with column names " + getColumnNames());
		setLayout(new BorderLayout());
		add(getScrollPane(getTable()), BorderLayout.CENTER);
	}

	private JScrollPane getScrollPane(JTable fileTable) {
		if (scrollPane == null) {
			scrollPane = new JScrollPane(fileTable);
			fileTable.setFillsViewportHeight(true);
		}

		return scrollPane;
	}

	public JTable getTable() {
		if (table == null) {
			table = new JTable(new ScrollTableModel<E>());
		}

		return table;
	}

	@SuppressWarnings("unchecked")
	private ScrollTableModel<E> getTableModel() {
		return (ScrollTableModel<E>)table.getModel();
	}

	public void addRow(E newRow) {
		ScrollTableModel<E> model = getTableModel();
		model.addRow(newRow);
	}

	public void setData(ArrayList<E> newData) {
		ScrollTableModel<E> model = getTableModel();
		model.setData(newData);
	}

	public E getRowWrapper(int rowIndex) {
		ScrollTableModel<E> model = getTableModel();
		return model.getRowWrapper(rowIndex);
	}

	public void addTableMouseListener(MouseListener ml) {
		table.addMouseListener(ml);
	}
	
	public List<E> getData() {
		return getTableModel().getData();
	}
	
	/**
	 * The TableModel for a ScrollTable Holds an ArrayList of RowWrappers, basic implementation of TableModel stuff
	 *
	 * @param <K> The type of ScrollTableRowWrapper this TableModel uses
	 */
	public class ScrollTableModel<K extends ScrollTableRowWrapper> extends AbstractTableModel {
		
		/**
		 * The ArrayList of ScrollTableRowWrappers
		 */
		private ArrayList<K> data = null;

		/**
		 * Create a table model
		 * @param columnNames The column names for this table, in order of appearance
		 */
		public ScrollTableModel() {
			data = new ArrayList<K>();
		}

		/**
		 * Add a new row wrapper to the table
		 * @param newRow The row to add
		 */
		public void addRow(K newRow) {
			data.add(newRow);
		}

		/**
		 * Replace the current table data with a new ArrayList
		 * @param data The new data
		 */
		public void setData(ArrayList<K> data) {
			LOGGER.finest("Setting table data - " + data.size() + " items.");
			this.data = data;
		}

		@Override
		public int getRowCount() {
			return data.size();
		}

		@Override
		public int getColumnCount() {
			return getColumnNames().length;
		}

		@Override
		public String getColumnName(int col) {
			return getColumnNames()[col];
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			ScrollTableRowWrapper rowData = data.get(rowIndex);
			return rowData.getData(getColumnNames()[columnIndex]);
		}

		/**
		 * Get the row wrapper at rowIndex
		 * @param rowIndex The row to get
		 * @return The row wrapper
		 */
		public K getRowWrapper(int rowIndex) {
			return data.get(rowIndex);
		}
		
		/**
		 * Get the data contained in the table
		 * @return
		 */
		public List<K> getData() {
			return data;
		}
	}
}