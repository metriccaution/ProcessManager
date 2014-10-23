package procBuilder.engine;

public class StringProcessItem extends AbstractProcessItem {

	private String text = null;
	
	public StringProcessItem(String text) {
		this.text = text;
	}
	
	@Override
	public String toString() {
		return text;
	}
}