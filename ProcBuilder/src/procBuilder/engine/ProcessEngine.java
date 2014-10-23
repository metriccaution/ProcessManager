package procBuilder.engine;

import java.io.IOException;
import java.util.logging.Logger;

public class ProcessEngine {
	
	private final static Logger LOGGER = Logger.getLogger(ProcessEngine.class.getName());
	
	/**
	 * Run the selected wrapper
	 * @param wrapper The wrapper to run
	 * @throws IOException If there is a problem running the process
	 */
	public void runProcessWrapper(ProcessWrapper wrapper) throws IOException{
		LOGGER.finest("Running wrapper");
		ProcessBuilder pb = wrapper.toProcessBuilder();
		pb.start();
		//Do something with the input and output
	}
	
}