package procBuilder.screen.scrollTable.mapValues;

import java.util.logging.Logger;

import procBuilder.screen.scrollTable.ScrollTableRowWrapper;

class MapValueRowWrapper extends ScrollTableRowWrapper {

	private final static Logger LOGGER = Logger.getLogger(MapValueRowWrapper.class.getName());

	private String key;
	private String value;

	@Override
	public Object getData(String collumnName) {
		// TODO Auto-generated method stub
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