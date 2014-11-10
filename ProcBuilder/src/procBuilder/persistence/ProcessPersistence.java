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

	private final static Path DEFAULT_FILE_LOCATION = Paths.get(System.getProperty("user.home") + "\\ProcManager\\");
	public final static String PROCESSES_FILE_NAME = "Procs.ser";
	
	/*
	 * Paths
	 */
	private Path persistenceDir, processWrappersPath;

	/**
	 * Create a class for persisting ProcessWrappers. Called from factory methods
	 * @param saveLocation The directory that the preferences are saved to.
	 * @throws IOException When there is an error creating the directories
	 */
	private ProcessPersistence(Path persistenceDir) throws IOException {
		this.persistenceDir = persistenceDir;
		if (!Files.exists(persistenceDir, LinkOption.NOFOLLOW_LINKS)) {
			Files.createDirectory(persistenceDir);
		}
		
		//Initialise the persisted wrappers location
		processWrappersPath = persistenceDir.resolve(PROCESSES_FILE_NAME);
		//Ensure there is a file for persisted wrappers
		if (!Files.exists(processWrappersPath, LinkOption.NOFOLLOW_LINKS)) {
			Files.createFile(processWrappersPath);
			persistProcessWrapperList(new ArrayList<ProcessWrapper>());
		}
	}
	
	/**
	 * Factory a persistence object from default values
	 * @return The persistence object, null if there is an error
	 */
	public static ProcessPersistence factoryDefaultProcessPersistence() {
		ProcessPersistence retVal;
		try {
			retVal = new ProcessPersistence(DEFAULT_FILE_LOCATION);
		}
		catch (IOException ioe) {
			LOGGER.log(Level.WARNING, "Error creating process persistence", ioe);
			retVal = null;
		}
		
		return retVal;
	}

	/**
	 * Save a List of ProcessWrappers to file at the location
	 * @param wrappers List of ProcessWrappers to persist.
	 * @throws IOException When an IOException is thrown during writing the ProcessWrappers to an ObjectOutputStream
	 */
	public void persistProcessWrapperList(List<ProcessWrapper> wrappers) throws IOException {
		LOGGER.finest("Persisting process wrappers to file.");
		
		if (Files.exists(processWrappersPath, LinkOption.NOFOLLOW_LINKS)) {
			try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(processWrappersPath.toString()))){
				oos.writeObject(wrappers);
			}
			LOGGER.finest(wrappers.size() + " wrappers persisted.");
		}
		else {
			LOGGER.warning("No persistence file found in " + persistenceDir + ", no wrappers persisted");
		}
	}

	/**
	 * Retrieve the List of ProcessWrappers saved to file at the specified location
	 * @return The List of ProcessWrappers at the location
	 */
	@SuppressWarnings("unchecked")
	public List<ProcessWrapper> retrieveProcessWrapperList() {
		LOGGER.finest("Retrieving process wrappers from file.");
		if (Files.exists(processWrappersPath, LinkOption.NOFOLLOW_LINKS)) {
			List<ProcessWrapper> persistedWrappers = new ArrayList<ProcessWrapper>();
	        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(processWrappersPath.toString()))) {
	        	persistedWrappers = (List<ProcessWrapper>)in.readObject();
	        } catch (IOException | ClassNotFoundException e) {
				LOGGER.log(Level.WARNING, "Error deserialising object", e);
				return null;
			}
	        LOGGER.finest(persistedWrappers.size() + " wrappers retrieved.");
	        return persistedWrappers;	
		}
		else {
			LOGGER.fine("No process list found at " + processWrappersPath);
			return new ArrayList<ProcessWrapper>();
		}
	}
	
	public Path getPersistenceDir() {
		return persistenceDir;
	}
}