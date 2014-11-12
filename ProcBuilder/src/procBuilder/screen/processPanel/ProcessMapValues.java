package procBuilder.screen.processPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import procBuilder.screen.ScreenStatics;

public class ProcessMapValues extends JPanel implements ScreenStatics{
	private JPanel jPanelButtons, jPanelList;
	private JScrollPane jScrollPaneList;
	private JButton jButtonAdd, jButtonDelete;

	private List<KeyValuePair> keyValues;
	private KeyValuePair currentKvp;

	/**
	 * Create the panel.
	 */
	public ProcessMapValues(){
		keyValues = new ArrayList<KeyValuePair>();

		setLayout(new BorderLayout());
		add(getJPanelButtons(), BorderLayout.PAGE_START);
		add(getJScrollPaneList(), BorderLayout.CENTER);
	}

	/*
	 * Interface methods
	 */
	public Map<String, String> getMap(){
		Map<String, String> retVal = new HashMap<String, String>();

		for (KeyValuePair keyValue : keyValues) {
			retVal.put(keyValue.getKey(), keyValue.getValue());
		}

		return retVal;
	}

	public void setMap(Map<String, String> map) {
		keyValues.clear();

		Set<String> keys = map.keySet();
		for (String key : keys) {
			String value = map.get(key);

			KeyValuePair kvp = new KeyValuePair();
			kvp.setKey(key);
			kvp.setValue(value);

			keyValues.add(kvp);
		}

		rebuildList();
	}

	public void setReadOnly(boolean readOnly) {
		for (KeyValuePair keyValue : keyValues) {
			keyValue.setReadOnly(readOnly);
		}
	}

	/*
	 * Helper methods
	 */
	private Color getColour(KeyValuePair kvp) {
		if (kvp.equals(currentKvp)) {
			return FOCUS_COLOUR;
		}

		int index = keyValues.indexOf(kvp);
		if (index % 2 == 0) {
			return PRIMARY_COLOUR;
		}
		else {
			return SECONDARY_COLOUR;
		}
	}

	private void recolour() {
		for (KeyValuePair kvp : keyValues) {
			Color colour = getColour(kvp);
			kvp.setBackground(colour);
		}
	}

	private void rebuildList() {
		getJPanelList().removeAll();

		for (KeyValuePair kvp : keyValues) {
			getJPanelList().add(kvp);
		}

		recolour();

		getJPanelList().revalidate();
		getJPanelList().repaint();
	}
	
	private void setCurrentKvp(KeyValuePair kvp) {
		currentKvp = kvp;
		recolour();
	}

	private void addKvp() {
		KeyValuePair kvp = new KeyValuePair();
		keyValues.add(kvp);
		rebuildList();
		
		if (currentKvp == null) {
			setCurrentKvp(keyValues.get(0));
		}
	}

	private void deleteCurrentKvp() {
		if (currentKvp != null) {
			int index = keyValues.indexOf(currentKvp);
			deleteKvp(index);
			
			KeyValuePair kvp = null;
			if (keyValues.size() >= index + 1) {
				kvp = keyValues.get(index);
			}
			else if (keyValues.size() > 0) {
				kvp = keyValues.get(keyValues.size() - 1);
			}
			
			setCurrentKvp(kvp);
		}
	}

	private void deleteKvp(int index) {
		keyValues.remove(index);
		rebuildList();
	}

	/*
	 * Screen gets/sets
	 */
	private JPanel getJPanelButtons() {
		if (jPanelButtons == null) {
			jPanelButtons = new JPanel();
			jPanelButtons.setLayout(new BoxLayout(jPanelButtons, BoxLayout.X_AXIS));
			jPanelButtons.setBackground(PRIMARY_COLOUR);

			jPanelButtons.add(getJButtonAdd());
			jPanelButtons.add(getJButtonDelete());
		}

		return jPanelButtons;
	}

	private JScrollPane getJScrollPaneList() {
		if (jScrollPaneList == null) {
			jScrollPaneList = new JScrollPane(getJPanelList());
			jScrollPaneList.getVerticalScrollBar().setUnitIncrement((int)BUTTON_MAX.getHeight());
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
					addKvp();
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
					deleteCurrentKvp();
				}
			});
		}

		return jButtonDelete;
	}

	/**
	 * Wrapper class for a pair of {@link JTextField}s
	 * @author David
	 *
	 */
	private class KeyValuePair extends JPanel {
		private JTextField jTextFieldKey, jTextFieldValue;
		private KeyValuePair() {
			setLayout(new GridLayout(1, 0));
			setMaximumSize(TEXT_MAX);
			add(getJTextFieldKey());
			add(getJTextFieldValue());
		}

		@Override
		public void setBackground(Color color) {
			super.setBackground(color);
			getJTextFieldKey().setBackground(color);
			getJTextFieldValue().setBackground(color);
		}

		private JTextField getJTextFieldKey() {
			if (jTextFieldKey == null) {
				jTextFieldKey = new JTextField();
				jTextFieldKey.setMaximumSize(TEXT_MAX);

				jTextFieldKey.addFocusListener(new FocusListener() {

					@Override
					public void focusLost(FocusEvent e) {
						//Do nothing
					}

					@Override
					public void focusGained(FocusEvent e) {
						currentKvp = KeyValuePair.this;
						recolour();
					}
				});
			}

			return jTextFieldKey;
		}

		private JTextField getJTextFieldValue() {
			if (jTextFieldValue == null) {
				jTextFieldValue = new JTextField();
				jTextFieldValue.setMaximumSize(TEXT_MAX);

				jTextFieldValue.addFocusListener(new FocusListener() {

					@Override
					public void focusLost(FocusEvent e) {
						//Do nothing
					}

					@Override
					public void focusGained(FocusEvent e) {
						currentKvp = KeyValuePair.this;
						recolour();
					}
				});
			}

			return jTextFieldValue;
		}

		private void setReadOnly(boolean readOnly) {
			jTextFieldKey.setEnabled(!readOnly);
			jTextFieldValue.setEnabled(!readOnly);
		}

		private String getKey() {
			return jTextFieldKey.getText();
		}

		private void setKey(String key) {
			jTextFieldKey.setText(key);
		}

		private String getValue() {
			return jTextFieldValue.getText();
		}

		private void setValue(String value) {
			jTextFieldValue.setText(value);
		}
	}
}