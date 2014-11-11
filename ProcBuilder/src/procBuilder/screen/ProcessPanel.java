package procBuilder.screen;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import procBuilder.engine.ProcessWrapper;

/**
 * A panel for creating or viewing a ProcessWrapper
 * @author David
 *
 */
public class ProcessPanel extends JPanel implements ScreenStatics{
	private JLabel jLabelName, jLabelPath;
	private JTextField jTextFieldName, jTextFieldPath;
	private JPanel jPanelTextFields, jPanelListAndMap;
	private ProcessCommandList commandList;
	private ProcessMapValues mapValues;
	
	/**
	 * Create the panel.
	 */
	public ProcessPanel() {
		setLayout(new BorderLayout());
		setBackground(BACKGROUND_COLOUR);
		add(getJPanelTextFields(), BorderLayout.PAGE_START);
		add(getJPanelListAndMap(), BorderLayout.CENTER);
	}
	
	/*
	 * Interface methods
	 */
	public ProcessWrapper getProcessWrapper() {
		ProcessWrapper wrapper = new ProcessWrapper();
		
		String name = getJTextFieldName().getText();
		wrapper.setName(name);
		
		String workingDir = getJTextFieldPath().getText();
		wrapper.setWorkingDirectoryFromString(workingDir);
		
		List<String> commands = getCommandList().getStringValues();
		wrapper.setItems(commands);
		
		Map<String, String> env = getMapValues().getMap();
		wrapper.setEnv(env);
		
		return wrapper;
	}
	
	public void setFromProcessWrapper(ProcessWrapper wrapper) {
		String name = wrapper.getName();
		getJTextFieldName().setText(name);
		
		Path workingDir = wrapper.getWorkingDirectory();
		String workingDirString = workingDir.toString();
		getJTextFieldPath().setText(workingDirString);
		
		List<String> commands = wrapper.getItems();
		getCommandList().setFromStrings(commands);
		
		Map<String, String> env = wrapper.getEnv();
		getMapValues().setMap(env);
	}
	
	public void setReadOnly(boolean readOnly) {
		getJTextFieldName().setEnabled(!readOnly);
		getJTextFieldPath().setEnabled(!readOnly);
		getCommandList().setReadOnly(readOnly);
		getMapValues().setReadOnly(readOnly);
	}

	/*
	 * Screen gets/sets
	 */
	private JPanel getJPanelTextFields() {
		if (jPanelTextFields == null) {
			jPanelTextFields = new JPanel();
			jPanelTextFields.setBackground(COMPONENT_BACKGROUND_COLOUR);
			jPanelTextFields.setLayout(new GridLayout(0, 2));
			jPanelTextFields.add(getJLabelName());
			jPanelTextFields.add(getJTextFieldName());
			jPanelTextFields.add(getJLabelPath());
			jPanelTextFields.add(getJTextFieldPath());
		}
		
		return jPanelTextFields;
	}
	
	private JPanel getJPanelListAndMap() {
		if (jPanelListAndMap == null) {
			jPanelListAndMap = new JPanel();
			jPanelListAndMap.setLayout(new GridLayout(1, 0));
			
			jPanelListAndMap.add(getCommandList());
			jPanelListAndMap.add(getMapValues());
		}
		
		return jPanelListAndMap;
	}
	
	private JLabel getJLabelName() {
		if (jLabelName == null) {
			jLabelName = new JLabel("Name");
		}
		
		return jLabelName;
	}
	
	private JLabel getJLabelPath() {
		if (jLabelPath == null) {
			jLabelPath = new JLabel("Path");
		}
		
		return jLabelPath;
	}
	
	private JTextField getJTextFieldName() {
		if (jTextFieldName == null) {
			jTextFieldName = new JTextField();
		}
		
		return jTextFieldName;
	}
	
	private JTextField getJTextFieldPath() {
		if (jTextFieldPath == null) {
			jTextFieldPath = new JTextField();
		}
		
		return jTextFieldPath;
	}
	
	private ProcessCommandList getCommandList() {
		if (commandList == null) {
			commandList = new ProcessCommandList();
		}
		
		return commandList;
	}
	
	private ProcessMapValues getMapValues() {
		if (mapValues == null) {
			mapValues = new ProcessMapValues();
		}
		
		return mapValues;
	}
}