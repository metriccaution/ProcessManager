package procBuilder.screen.processItemList;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import procBuilder.engine.ProcessWrapper;
import procBuilder.screen.scrollTable.mapValues.MapValuesTable;

/**
 * A screen containing a ProcessWrapper for creating one from scratch or editing one
 * @author David
 *
 */
public class ProcessWrapperCreator extends JPanel {
	
	/*
	 * Screen variables
	 */
	/**
	 * Holds the everything that's not the commands and map - ProcessWrapper name and working directory
	 */
	private JPanel jPanelTextPanels;
	/**
	 * Holds the list of commands and the map
	 */
	private JPanel jPanelCommandsAndMap;
	private JLabel jLabelName;
	private JLabel jLabelWorkingDir;
	private JTextField jTextFieldName;
	private JTextField jTextFieldWorkingDir;
	private ProcessItemList list;
	private MapValuesTable scrollTableMapValues;

	public ProcessWrapperCreator() {
		setLayout(new BorderLayout());
		add(getJPanelTextPanels(), BorderLayout.PAGE_START);
		add(getJPanelCommandsAndMap(), BorderLayout.CENTER);
	}
	
	/**
	 * Create a ProcessWrapper from the values on this panel
	 * @return A ProcessWrapper from the values on the form, null if invalid
	 */
	public ProcessWrapper getWrapperFromValues() {
		if (valid()) {
			String name = getJTextFieldName().getText();
			
			String workingDirString = getJTextFieldWorkingDir().getText();
			Path workingDir = Paths.get(workingDirString);
			
			List<String> items = getList().panelsToProcessItems();
			
			Map<String, String> env = getScrollTableMapValues().getMap();
			
			ProcessWrapper wrapper = new ProcessWrapper();
			wrapper.setName(name);
			wrapper.setWorkingDirectory(workingDir);
			wrapper.setItems(items);
			wrapper.setEnv(env);
			
			return wrapper;
		}
		
		return null;
	}
	
	private boolean valid() {
		if (getJTextFieldName().getText() == null
				|| getJTextFieldName().getText().isEmpty()) {
			return false;
		}
		
		return true;
	}
	
	public void setFromProcessWrapper(ProcessWrapper wrapper) {
		String name = wrapper.getName();
		getJTextFieldName().setText(name);
		
		Path workingDir = wrapper.getWorkingDirectory();
		if (workingDir != null) {
			String workingDirString = workingDir.toString();
			getJTextFieldWorkingDir().setText(workingDirString);
		}
		List<String> list = wrapper.getItems();
		getList().processItemsToPanels(list);
		
		Map<String, String> env = wrapper.getEnv();
		getScrollTableMapValues().setMap(env);
	}
	
	private JPanel getJPanelCommandsAndMap() {
		if (jPanelCommandsAndMap == null) {
			jPanelCommandsAndMap = new JPanel();
			
			jPanelCommandsAndMap.setLayout(new GridLayout(1, 0));
			
			jPanelCommandsAndMap.add(getList());
			jPanelCommandsAndMap.add(getScrollTableMapValues());
		}
		
		return jPanelCommandsAndMap;
	}
	
	private JPanel getJPanelTextPanels() {
		if (jPanelTextPanels == null) {
			jPanelTextPanels = new JPanel();
			jPanelTextPanels.setLayout(new GridLayout(0, 2));
			
			jPanelTextPanels.add(getJLabelName());
			jPanelTextPanels.add(getJTextFieldName());
			jPanelTextPanels.add(getJLabelWorkingDir());
			jPanelTextPanels.add(getJTextFieldWorkingDir());
		}
		
		return jPanelTextPanels;
	}
	
	private JLabel getJLabelName() {
		if (jLabelName == null) {
			jLabelName = new JLabel("Name");
		}
		
		return jLabelName;
	}
	
	private JLabel getJLabelWorkingDir() {
		if (jLabelWorkingDir == null) {
			jLabelWorkingDir = new JLabel("Working Directory");
		}
		
		return jLabelWorkingDir;
	}
	
	private JTextField getJTextFieldName() {
		if (jTextFieldName == null) {
			jTextFieldName = new JTextField();
		}
		
		return jTextFieldName;
	}
	
	private JTextField getJTextFieldWorkingDir() {
		if (jTextFieldWorkingDir == null) {
			jTextFieldWorkingDir = new JTextField();
		}
		
		return jTextFieldWorkingDir;
	}

	private ProcessItemList getList() {
		if (list == null) {
			list = new ProcessItemList();
		}

		return list;
	}
	
	private MapValuesTable getScrollTableMapValues() {
		if (scrollTableMapValues == null) {
			scrollTableMapValues = new MapValuesTable();
		}
		
		return scrollTableMapValues;
	}
}