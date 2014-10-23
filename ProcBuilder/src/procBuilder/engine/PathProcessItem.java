package procBuilder.engine;

import java.nio.file.Path;

public class PathProcessItem extends AbstractProcessItem {

	private Path path = null;

	public PathProcessItem(Path path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return path.toString();
	}

}
