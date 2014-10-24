package procBuilder.screen.scrollTable.processWrapper;

import java.util.logging.Logger;

import procBuilder.engine.ProcessWrapper;
import procBuilder.screen.scrollTable.ScrollTableRowWrapper;

public class ProcessWrapperRowWrapper extends ScrollTableRowWrapper {
	private final static Logger LOGGER = Logger.getLogger(ProcessWrapperRowWrapper.class.getName());
	
	private ProcessWrapper source;
	
	public ProcessWrapperRowWrapper(ProcessWrapper source) {
		this.source = source;
	}
	
	public ProcessWrapper getSource() {
		return source;
	}
	
	@Override
	public Object getData(String collumnName) {
		switch (collumnName) {
		case "Name" :
			return source.getName();
		case "Items":
			return source.getItems();
		case "Working Directory":
			return source.getWorkingDirectory();
		default:
			LOGGER.severe("Unrecognised column name " + collumnName);
			return null;
		}
	}

}