package procBuilder.main;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import procBuilder.screen.ProcManFrame;

public class ProcBuilder {
	private final static Logger LOGGER = Logger.getLogger("procBuilder");

	public static void main(String[] args) {
		initLogger();
		new ProcManFrame();
		/*ProcessEngine engine = new ProcessEngine();
		
		PathProcessItem item = new PathProcessItem(Paths.get("C:\\Games\\jilloff\\jilloff.exe"));
		ProcessWrapper wrapper = new ProcessWrapper();
		wrapper.addItem(item);
		
		try {
			engine.runProcessWrapper(wrapper);
		}
		catch (IOException ioe) {
			LOGGER.log(Level.SEVERE, "IOException thrown while running wrapper", ioe);
		}*/
	}

	private static void initLogger() {
		Level logLevel = Level.ALL;
		LOGGER.setUseParentHandlers(false);
		LOGGER.setLevel(logLevel);
		ConsoleHandler handler = new ConsoleHandler();
		handler.setLevel(logLevel);
		LOGGER.addHandler(handler);
		LOGGER.info("Logger initialised");
	}
}