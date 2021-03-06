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
import java.util.Set;
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
	
	public void setWorkingDirectoryFromString(String s) {
		Path p = Paths.get(s);
		setWorkingDirectory(p);
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
		if (!valid()) {
			return null;
		}

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

		LOGGER.finest("ProcessBuilder created: " + toString());

		return pb;
	}
	
	public boolean valid() {
		//Check there is a name
		if (name.isEmpty()) {
			return false;
		}
		
		//Check we are actually running commands
		if (items.size() == 0) {
			return false;
		}
		
		//Check that none of the commands are blank
		for (int i=0; i<items.size(); i++) {
			String s = items.get(i);
			if (s.isEmpty()) {
				return false;
			}
		}
		
		//Check that none of the environment variables are empty
		Set<String> keySet = env.keySet();
		for (String s : keySet) {
			if (s.isEmpty()
				|| env.get(s).isEmpty()) {
				return false;
			}
		}
		
		return true;
	}

	@Override
	public String toString() {
		return name + ": " + items + env + " in " + workingDirectory;
	}

	/*
	 * Serialisation methods
	 */
	private void writeObject(ObjectOutputStream out) throws IOException {
		out.writeObject(name);
		out.writeObject(items);
		out.writeObject(env);
		String path = workingDirectory.toString();
		if (path == null) {
			path = "";
		}
		out.writeObject(path);
	}
	
	@SuppressWarnings("unchecked")
	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		name = (String)in.readObject();
		items = (List<String>)in.readObject();
		env = (Map<String, String>)in.readObject();
		String pathString = (String)in.readObject();
		if (pathString.equals("")) {
			workingDirectory = null;
		}
		else {
			workingDirectory = Paths.get(pathString);
		}
	}
}
