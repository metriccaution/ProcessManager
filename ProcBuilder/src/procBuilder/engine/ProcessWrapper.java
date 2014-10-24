package procBuilder.engine;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class ProcessWrapper {
	private final static Logger LOGGER = Logger.getLogger(ProcessWrapper.class.getName());
	
	//TODO - Use the map etc when running
	private String name;
	private List<AbstractProcessItem> items;
	private Map<String, String> env;
	private Path workingDirectory;
	
	public ProcessWrapper() {
		items = new ArrayList<AbstractProcessItem>();
	}
	
	/*
	 * Methods to handle commands
	 */
	public void addItem(AbstractProcessItem item) {
		items.add(item);
	}
	
	public List<AbstractProcessItem> getItems() {
		return items;
	}
	
	public void setItems(List<AbstractProcessItem> items) {
		this.items = items;
	}
	
	/*
	 * Methods to handle the working directory
	 */
	public Path getWorkingDirectory() {
		return workingDirectory;
	}
	
	public void setWorkingDirectory(Path workingDirectory) {
		this.workingDirectory = workingDirectory;
	}
	
	/*
	 * Method to create a processbuilder set up correctly
	 */
	public ProcessBuilder toProcessBuilder() {
		//Convert the AbstractProcessItem objects to Strings to use as the command strings
		List<String> commands = new ArrayList<String>();
		for (AbstractProcessItem i : items) {
			String command = i.toString();
			commands.add(command);
		}
		
		ProcessBuilder pb = new ProcessBuilder(commands);
		
		//Set the environment variables
		Map<String, String> pbEnv = pb.environment();
		for (String key : env.keySet()) {
			pbEnv.put(key, env.get(key));
		}
		
		//Set the working directory if there is one set on the wrapper
		if (workingDirectory != null) {
			pb.directory(workingDirectory.toFile());
		}
		
		LOGGER.finest("ProcessBuilder created with commands " + items);
		
		return pb;
	}
	
	@Override
	public String toString() {
		return name + ": " + items;
	}
}