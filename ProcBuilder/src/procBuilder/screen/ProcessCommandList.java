package procBuilder.screen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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

public class ProcessCommandList extends JPanel implements ScreenStatics{

	private JPanel jPanelButtons, jPanelList;
	private JButton jButtonAdd, jButtonDelete, jButtonUp, jButtonDown;
	private JScrollPane jScrollPaneList;

	private List<JTextField> values;
	private JTextField currentField;

	public ProcessCommandList() {
		values = new ArrayList<JTextField>();
		for (int i=0; i<1; i++) {
			values.add(makeTextField("Hello world"));
		}
		currentField = values.get(0);

		setLayout(new BorderLayout());
		add(getJPanelButtons(), BorderLayout.PAGE_START);
		add(getJScrollPaneList(), BorderLayout.CENTER);
		updateList();
	}

	/**
	 * Rebuild the {@link JPanel} of {@link JTextField}s - Remove all of them from the form and then add them again
	 * Colours them correctly
	 */
	private void updateList() {
		getJPanelList().removeAll();

		Color currentColor = PRIMARY_COLOUR;
		for (JTextField value : values) {
			getJPanelList().add(value);

			if (value.equals(currentField)) {
				value.setBackground(FOCUS_COLOUR);
			}
			else {
				value.setBackground(currentColor);
			}

			if (currentColor.equals(PRIMARY_COLOUR)) {
				currentColor = SECONDARY_COLOUR;
			}
			else {
				currentColor = PRIMARY_COLOUR;
			}
		}

		//TODO - Stop focus dancing

		if (!currentField.hasFocus()) {
			currentField.requestFocus();
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
		retVal.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
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
		updateList();
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
		}

		return jScrollPaneList;
	}

	private JPanel getJPanelList() {
		if (jPanelList == null) {
			jPanelList = new JPanel();

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
					values.add(makeTextField(""));
					updateList();
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
					if (values.size() < 2) {
						Toolkit.getDefaultToolkit().beep();
						return;
					}
					
					int index = values.indexOf(currentField);
					delete(currentField);
					System.out.println(index);
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
					int index = values.indexOf(currentField);
					if (index == 0) {
						Toolkit.getDefaultToolkit().beep();
						return;
					}
					
					Collections.swap(values, index, index - 1);
					updateList();
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
					int index = values.indexOf(currentField);
					if (index == values.size() - 1) {
						Toolkit.getDefaultToolkit().beep();
						return;
					}
					
					Collections.swap(values, index, index + 1);
					updateList();
				}
			});
		}

		return jButtonDown;
	}
}