package procBuilder.screen.scrollTable.processWrapper;

import procBuilder.engine.ProcessWrapper;
import procBuilder.screen.scrollTable.ScrollTable;

public class ProcessWrapperTable extends ScrollTable<ProcessWrapperRowWrapper> {
	
	public final static String[] DEFAULT_COLUMN_NAMES = {"Name"};
	
	@Override
	public String[] getColumnNames() {
		return DEFAULT_COLUMN_NAMES;
	}
	
	public void addProcessWrapper(ProcessWrapper newWrapper) {
		ProcessWrapperRowWrapper wrapper = new ProcessWrapperRowWrapper(newWrapper);
		addRow(wrapper);
	}

}