package procBuilder.screen.scrollTable.wrapperTable;

import java.util.ArrayList;
import java.util.List;

import procBuilder.engine.ProcessWrapper;
import procBuilder.screen.scrollTable.ScrollTable;

public class WrapperTable extends ScrollTable<ProcessWrapperWrapper>{

	public final static String[] COLLUMN_NAMES = {"Name"};
	
	@Override
	public String[] getColumnNames() {
		return COLLUMN_NAMES;
	}
	
	public void addProcess(ProcessWrapper wrapper) {
		ProcessWrapperWrapper rowWrapper = new ProcessWrapperWrapper(wrapper);
		addRow(rowWrapper);
	}
	
	public List<ProcessWrapper> getProcessList() {
		List<ProcessWrapper> retVal = new ArrayList<ProcessWrapper>();
		
		List<ProcessWrapperWrapper> listData = getData();
		for (ProcessWrapperWrapper rowWrapper : listData) {
			retVal.add(rowWrapper.getWrapper());
		}
		
		return retVal;
	}
	
	public ProcessWrapperWrapper getSelectedRow() {
		int index = getTable().getSelectedRow();
		if (index == -1
			|| index >= getData().size()) {
			return null;
		}
		return getRowWrapper(index);
	}
}