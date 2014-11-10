package procBuilder.main;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProcBuilder {
	private final static Logger LOGGER = Logger.getLogger("procBuilder");

	public static void main(String[] args) {
		initLogger();
	}

	private static void initLogger() {
		//LATER - Config - Log to file instead
		Level logLevel = Level.ALL;
		LOGGER.setUseParentHandlers(false);
		LOGGER.setLevel(logLevel);
		ConsoleHandler handler = new ConsoleHandler();
		handler.setLevel(logLevel);
		LOGGER.addHandler(handler);
		LOGGER.info("Logger initialised");
	}
}