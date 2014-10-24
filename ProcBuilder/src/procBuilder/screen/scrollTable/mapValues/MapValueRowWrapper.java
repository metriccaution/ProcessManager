package procBuilder.screen.scrollTable.mapValues;

import java.util.logging.Logger;

import procBuilder.screen.scrollTable.ScrollTableRowWrapper;

class MapValueRowWrapper extends ScrollTableRowWrapper {

	private final static Logger LOGGER = Logger.getLogger(MapValueRowWrapper.class.getName());

	private String key;
	private String value;
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public Object getData(String collumnName) {
		switch (collumnName){
		case "Key":
			return key;
		case "Value":
			return value;
		default:
			LOGGER.severe("Unrecognised column name " + collumnName);
			return null;
		}
	}

}