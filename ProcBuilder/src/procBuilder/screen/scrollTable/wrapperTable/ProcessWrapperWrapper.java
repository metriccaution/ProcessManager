package procBuilder.screen.scrollTable.wrapperTable;

import procBuilder.engine.ProcessWrapper;
import procBuilder.screen.scrollTable.ScrollTableRowWrapper;

public class ProcessWrapperWrapper extends ScrollTableRowWrapper {

	private ProcessWrapper wrapper;
	
	public ProcessWrapperWrapper(ProcessWrapper wrapper) {
		this.wrapper = wrapper;
	}
	
	@Override
	public Object getData(String collumnName) {
		switch (collumnName) {
		case("Name"):
			return wrapper.getName();
		default:
			return null;
		}
	}
	
	public ProcessWrapper getWrapper() {
		return wrapper;
	}
	
	public void setWrapper(ProcessWrapper wrapper) {
		this.wrapper = wrapper;
	}
}