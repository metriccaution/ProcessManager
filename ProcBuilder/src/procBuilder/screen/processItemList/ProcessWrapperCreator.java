package procBuilder.screen.processItemList;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;

import procBuilder.screen.scrollTable.mapValues.MapValuesTable;

/**
 * A screen containing a ProcessWrapper for creating one from scratch or editing one
 * @author David
 *
 */
public class ProcessWrapperCreator extends JPanel {
	//TODO - Write this screen 
	
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
	
	private JTextField jTextFieldName;
	private JTextField jTextFieldPath;
	private ProcessItemList list;
	private MapValuesTable scrollTableMapValues;

	public ProcessWrapperCreator() {
		setLayout(new BorderLayout());
		add(getJPanelTextPanels(), BorderLayout.PAGE_START);
		add(getJPanelCommandsAndMap(), BorderLayout.CENTER);
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
			
			jPanelTextPanels.setLayout(new GridLayout(0, 1));
			
			jPanelTextPanels.add(getJTextFieldName());
			jPanelTextPanels.add(getJTextFieldPath());
		}
		
		return jPanelTextPanels;
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