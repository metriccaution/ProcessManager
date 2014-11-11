package procBuilder.screen.processPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import procBuilder.screen.ScreenStatics;

/**
 * A panel for configuring a list of String values
 * @author David
 *
 */
public class ProcessCommandList extends JPanel implements ScreenStatics{
	/*
	 * Screen variables
	 */
	private JPanel jPanelButtons, jPanelList;
	private JButton jButtonAdd, jButtonDelete, jButtonUp, jButtonDown;
	private JScrollPane jScrollPaneList;

	/*
	 * List of text values
	 */
	private List<JTextField> values;
	/*
	 * Currently selected text field
	 */
	private JTextField currentField;

	public ProcessCommandList() {
		values = new ArrayList<JTextField>();
		values.add(makeTextField(""));
		currentField = values.get(0);

		setLayout(new BorderLayout());
		add(getJScrollPaneList(), BorderLayout.CENTER);
		add(getJPanelButtons(), BorderLayout.PAGE_START);
		updateList();
	}
	
	/*
	 * Interface methods - Strings in and out
	 */
	/**
	 * Set the list of {@link JTextField}s from a list of strings
	 * @param strings String values
	 */
	public void setFromStrings(List<String> strings) {
		currentField = null;
		values.clear();
		for (int i=0; i<strings.size(); i++) {
			values.add(makeTextField(strings.get(i)));
		}
		
		if (values.size() > 0) {
			currentField = values.get(0);
		}
		
		updateList();
	}
	
	/**
	 * Retrieve the list of String values
	 * @return
	 */
	public List<String> getStringValues() {
		List<String> retVal = new ArrayList<String>();
		for (int i=0; i<values.size(); i++) {
			JTextField field = values.get(i);
			String value = field.getText();
			retVal.add(value);
		}
		
		return retVal;
	}
	
	public void setReadOnly(boolean readOnly) {
		getJPanelButtons().setVisible(!readOnly);
		for (JTextField value : values) {
			value.setEditable(!readOnly);
		}
	}

	/*
	 * Helper methods
	 */
	/**
	 * Get the correct colour for a {@link JTextField} in the list
	 * @param field
	 * @return
	 */
	private Color getColor(JTextField field) {
		if (currentField != null
			&& currentField.equals(field)) {
			return FOCUS_COLOUR;
		}
		
		int index = values.indexOf(field);
		if (index % 2 == 0) {
			return PRIMARY_COLOUR;
		}
		else {
			return SECONDARY_COLOUR;
		}
	}
	
	private void recolourList() {
		for (JTextField value : values) {
			Color textFieldColour = getColor(value);
			value.setBackground(textFieldColour);
		}
	}
	
	/**
	 * Rebuild the {@link JPanel} of {@link JTextField}s - Remove all of them from the form and then add them again
	 * Colours them correctly
	 */
	private void updateList() {
		getJPanelList().removeAll();

		for (JTextField value : values) {
			getJPanelList().add(value);
			Color textFieldColour = getColor(value);
			value.setBackground(textFieldColour);
		}

		getJPanelList().revalidate();
		getJPanelList().repaint();
	}

	/**
	 * Create a {@link JTextField} for a value, set up with the correct formatting
	 * @param value The initial value for the text field
	 * @return The new {@link JTextField}
	 */
	private JTextField makeTextField(String value) {
		final JTextField retVal = new JTextField(value);
		retVal.setMaximumSize(TEXT_MAX);
		retVal.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				//Do nothing
			}

			@Override
			public void focusGained(FocusEvent e) {
				setCurrentTextField(retVal);
			}
		});
		return retVal;
	}

	private void setCurrentTextField(JTextField field) {
		currentField = field;
		recolourList();
	}

	private void delete(JTextField field) {
		values.remove(field);
		updateList();
	}
	
	/*
	 * Screen gets/sets
	 */
	private JPanel getJPanelButtons() {
		if (jPanelButtons == null) {
			jPanelButtons = new JPanel();

			jPanelButtons.setBackground(SECONDARY_COLOUR);
			jPanelButtons.setLayout(new BoxLayout(jPanelButtons, BoxLayout.X_AXIS));
			jPanelButtons.add(getJButtonAdd());
			jPanelButtons.add(getJButtonDelete());
			jPanelButtons.add(getJButtonUp());
			jPanelButtons.add(getJButtonDown());
		}

		return jPanelButtons;
	}

	private JScrollPane getJScrollPaneList() {
		if (jScrollPaneList == null) {
			jScrollPaneList = new JScrollPane(getJPanelList());
			//LATER - Set the scroll properties to be sensible
		}

		return jScrollPaneList;
	}

	private JPanel getJPanelList() {
		if (jPanelList == null) {
			jPanelList = new JPanel();
			jPanelList.setBackground(COMPONENT_BACKGROUND_COLOUR);
			jPanelList.setLayout(new BoxLayout(jPanelList, BoxLayout.Y_AXIS));
		}

		return jPanelList;
	}

	private JButton getJButtonAdd() {
		if (jButtonAdd == null) {
			jButtonAdd = new JButton("Add");
			jButtonAdd.setMaximumSize(BUTTON_MAX);
			jButtonAdd.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					JTextField newTextField = makeTextField("");
					values.add(newTextField);
					updateList();
					if (currentField == null) {
						setCurrentTextField(newTextField);
					}
				}
			});
		}

		return jButtonAdd;
	}

	private JButton getJButtonDelete() {
		if (jButtonDelete == null) {
			jButtonDelete = new JButton("Delete");
			jButtonDelete.setMaximumSize(BUTTON_MAX);
			jButtonDelete.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					//Make a noise if trying to delete when they can't
					if (currentField == null
						|| values.size() < 2) {
						Toolkit.getDefaultToolkit().beep();
						return;
					}
					
					int index = values.indexOf(currentField);
					delete(currentField);
					//Delete one that isn't last
					if (index < values.size()) {
						setCurrentTextField(values.get(index));
					}
					else {
						setCurrentTextField(values.get(index - 1));
					}
				}
			});
		}

		return jButtonDelete;
	}

	private JButton getJButtonUp() {
		if (jButtonUp == null) {
			jButtonUp = new JButton("Up");
			jButtonUp.setMaximumSize(BUTTON_MAX);
			jButtonUp.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if (currentField == null) {
						Toolkit.getDefaultToolkit().beep();
						return;
					}
					
					int index = values.indexOf(currentField);
					if (index == 0) {
						Toolkit.getDefaultToolkit().beep();
						return;
					}
					
					Collections.swap(values, index, index - 1);
					updateList();
					currentField.requestFocus();
				}
			});
		}

		return jButtonUp;
	}

	private JButton getJButtonDown() {
		if (jButtonDown == null) {
			jButtonDown = new JButton("Down");
			jButtonDown.setMaximumSize(BUTTON_MAX);
			jButtonDown.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if (currentField == null) {
						Toolkit.getDefaultToolkit().beep();
						return;
					}
					
					int index = values.indexOf(currentField);
					if (index == values.size() - 1) {
						Toolkit.getDefaultToolkit().beep();
						return;
					}
					
					Collections.swap(values, index, index + 1);
					updateList();
					currentField.requestFocus();
				}
			});
		}

		return jButtonDown;
	}
}