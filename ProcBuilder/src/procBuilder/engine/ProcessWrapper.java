package procBuilder.engine;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class ProcessWrapper implements Serializable{
	private final static Logger LOGGER = Logger.getLogger(ProcessWrapper.class.getName());

	private String name;
	private List<String> items;
	private Map<String, String> env;
	private Path workingDirectory;

	public ProcessWrapper() {
		items = new ArrayList<String>();
		env = new HashMap<String, String>();
	}

	/*
	 * Methods to handle the name
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/*
	 * Methods to handle commands
	 */
	public void addItem(String item) {
		items.add(item);
	}

	public List<String> getItems() {
		return items;
	}

	public void setItems(List<String> items) {
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
	 * Methods to handle the environment variables
	 */
	public Map<String, String> getEnv() {
		return env;
	}

	public void setEnv(Map<String, String> env) {
		this.env = env;
	}

	/*
	 * Method to create a processbuilder set up correctly
	 */
	public ProcessBuilder toProcessBuilder() {
		//TODO - Validation - Path etc

		//Convert the AbstractProcessItem objects to Strings to use as the command strings
		List<String> commands = new ArrayList<String>();
		for (String i : items) {
			commands.add(i);
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

		LOGGER.finest("ProcessBuilder created from " + name + " with commands " + items);

		return pb;
	}

	@Override
	public String toString() {
		return name + ": " + items + env;
	}

	/*
	 * Serialisation methods
	 */
	private void writeObject(ObjectOutputStream out) throws IOException {
		out.writeObject(name);
		out.writeObject(items);
		out.writeObject(env);
		out.writeObject(workingDirectory.toString());
	}
	
	@SuppressWarnings("unchecked")
	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		name = (String)in.readObject();
		items = (List<String>)in.readObject();
		env = (Map<String, String>)in.readObject();
		workingDirectory = Paths.get((String)in.readObject());
	}
}