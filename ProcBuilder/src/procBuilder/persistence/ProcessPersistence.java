package procBuilder.persistence;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
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
	public final static String PROCESSES_FILE_NAME = "Procs.ser";
	
	private String saveLocation;

	/**
	 * Create a class for persisting ProcessWrappers.
	 * @param saveLocation The folder that the preferences are saved to.
	 */
	public ProcessPersistence(String saveLocation) {
		this.saveLocation = saveLocation + PROCESSES_FILE_NAME;
	}

	/**
	 * Get the location that this w
	 * @return
	 */
	public String getSaveLocation() {
		return saveLocation;
	}

	/**
	 * Save a List of ProcessWrappers to file at the location
	 * @param wrappers List of ProcessWrappers to persist.
	 * @throws IOException When an IOException is thrown during writing the ProcessWrappers to an ObjectOutputStream
	 */
	public void persistProcessWrapperList(List<ProcessWrapper> wrappers) throws IOException {
		LOGGER.finest("Persisting process wrappers to file.");
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(saveLocation.toString()))){
			oos.writeObject(wrappers);
		}
		LOGGER.finest(wrappers.size() + " wrappers persisted.");
	}

	/**
	 * Retrieve the List of ProcessWrappers saved to file at the specified location
	 * @return The List of ProcessWrappers at the location
	 */
	@SuppressWarnings("unchecked")
	public List<ProcessWrapper> retrieveProcessWrapperList() {
		//LATER - Validate file names on retrieve?
		LOGGER.finest("Retrieving process wrappers from file.");
		Path filePath = Paths.get(saveLocation);
		if (Files.exists(filePath, LinkOption.NOFOLLOW_LINKS)) {
			List<ProcessWrapper> persistedWrappers = new ArrayList<ProcessWrapper>();
	        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(saveLocation.toString()))) {
	        	persistedWrappers = (List<ProcessWrapper>)in.readObject();
	        } catch (IOException | ClassNotFoundException e) {
				LOGGER.log(Level.WARNING, "Error deserialising object", e);
				return null;
			}
	        LOGGER.finest(persistedWrappers.size() + " wrappers retrieved.");
	        return persistedWrappers;	
		}
		else {
			LOGGER.fine("No process list found at " + saveLocation);
			return new ArrayList<ProcessWrapper>();
		}
	}
}