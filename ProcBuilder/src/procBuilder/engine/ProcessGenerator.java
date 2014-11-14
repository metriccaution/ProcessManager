package procBuilder.engine;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProcessGenerator {

	public static List<ProcessWrapper> generateProcesses(Path dir, List<String> commands, Map<String, String> env, String extension) throws IOException {
		ProcessWalker walker = new ProcessWalker(commands, env, extension);
		Files.walkFileTree(dir, walker);
		return walker.getWrappers();
	}

	private static class ProcessWalker extends SimpleFileVisitor<Path>{
		List<ProcessWrapper> processes;
		String extension;
		List<String> commands;
		Map<String, String> env;

		public ProcessWalker(List<String> commands, Map<String, String> env, String extension) {
			this.commands = commands;
			this.env = env;
			this.extension = extension;
			processes = new ArrayList<ProcessWrapper>();
		}

		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
			if (valid(file)) {
				List<String> commandsCopy = new ArrayList<String>(commands);
				commandsCopy.add(file.getFileName().toString());
				
				System.out.println(commandsCopy);
				
				ProcessWrapper wrapper = new ProcessWrapper();
				wrapper.setName(file.getFileName().toString());
				wrapper.setWorkingDirectory(file.getParent());
				wrapper.setItems(commandsCopy);
				wrapper.setEnv(env);
				processes.add(wrapper);
			}

			return FileVisitResult.CONTINUE;
		}

		private boolean valid(Path p) {
			if (p.toString().toLowerCase().endsWith(extension)) {
				return true;
			}

			return false;
		}

		public List<ProcessWrapper> getWrappers() {
			return processes;
		}
	}
}