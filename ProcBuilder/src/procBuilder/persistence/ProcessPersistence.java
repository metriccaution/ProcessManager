package procBuilder.persistence;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import procBuilder.engine.ProcessWrapper;

/**
 * Class for saving and retrieving a list of processWrappers from a file
 * @author David
 *
 */
public class ProcessPersistence {
	private final static Logger LOGGER = Logger.getLogger(ProcessPersistence.class.getName());
	public final static String FILE_NAME = "Prefs.ser";
	
	private String saveLocation;

	public ProcessPersistence(String saveLocation) {
		this.saveLocation = saveLocation + FILE_NAME;
	}

	public String getSaveLocation() {
		return saveLocation;
	}

	public void persistProcessWrapperList(List<ProcessWrapper> wrappers) throws IOException {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(saveLocation.toString()))){
			oos.writeObject(wrappers);
		}
	}

	@SuppressWarnings("unchecked")
	public List<ProcessWrapper> retrieveProcessWrapperList() {
		//LATER - Validate file names on retrieve?
		List<ProcessWrapper> persistedWrappers = new ArrayList<ProcessWrapper>();
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(saveLocation.toString()))) {
        	persistedWrappers = (List<ProcessWrapper>)in.readObject();
        } catch (IOException | ClassNotFoundException e) {
			LOGGER.log(Level.WARNING, "Error deserialising object", e);
			return null;
		}
        return persistedWrappers;
	}
}