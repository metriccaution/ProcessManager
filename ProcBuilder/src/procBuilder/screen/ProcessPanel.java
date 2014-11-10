package procBuilder.screen;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * A panel for creating or viewing a ProcessWrapper
 * @author David
 *
 */
public class ProcessPanel extends JPanel implements ScreenStatics{

	//TODO - Write ProcessPanel
	/*
	 * Add tables - Editable - For map and for values
	 */
	
	private JLabel jLabelName, jLabelPath;
	private JTextField jTextFieldName, jTextFieldPath;
	private JPanel jPanelTextFields;
	
	/**
	 * Create the panel.
	 */
	public ProcessPanel() {
		setLayout(new GridLayout(0, 2));
		setLayout(new BorderLayout());
		setBackground(BACKGROUND_COLOUR);
		add(getJPanelTextFields(), BorderLayout.PAGE_START);
		
		add(new ProcessCommandList(), BorderLayout.CENTER);
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
}