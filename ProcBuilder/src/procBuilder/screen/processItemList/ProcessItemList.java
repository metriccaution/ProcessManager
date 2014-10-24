package procBuilder.screen.processItemList;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import procBuilder.screen.ProcBuilderScreenConstants;

public class ProcessItemList extends JPanel implements ProcBuilderScreenConstants {
	private final static Logger LOGGER = Logger.getLogger(ProcessItemList.class.getName());
	//LATER - Maybe make this a table with buttons instead
	
	/*
	 * Screen components
	 */
	private JPanel jPanelListArea;
	private JPanel jPanelButtons;
	private JButton jButtonNewItem;
	private JScrollPane jScrollPane;
	
	/*
	 * Non-screen components
	 */
	private List<ProcessItemPanel> panels;
	
	/**
	 * Create the panel.
	 */
	public ProcessItemList() {
		panels = new ArrayList<ProcessItemPanel>();
		
		setLayout(new BorderLayout());
		add(getjPanelButtons(), BorderLayout.PAGE_START);
		add(getJScrollPane(getJPanelListArea()), BorderLayout.CENTER);
		
		resetListPanel();
	}
	
	/**
	 * Set if the panel can be editable or not
	 * @param editable
	 */
	public void setEditable(boolean editable) {
		getjPanelButtons().setVisible(editable);
		for (ProcessItemPanel p : panels) {
			p.setEditableMode(editable);
		}
	}
	
	/*
	 * List methods
	 */
	/**
	 * Get the list of panels in this list
	 */
	public List<ProcessItemPanel> getList() {
		return panels;
	}
	
	/**
	 * Set the list of panels this screen displays to a new list
	 * @param panels
	 */
	public void setList(List<ProcessItemPanel> panels) {
		this.panels = panels;
		resetListPanel();
	}
	
	/**
	 * Move a panel up one in the list
	 * @param panel
	 */
	public void moveUp(ProcessItemPanel panel) {
		int index = getPanelIndex (panel);
		if (index > 0) {
			LOGGER.finest("Swapping pannels " + (index - 1) + " and " + index);
			Collections.swap(panels, index - 1, index);
			resetListPanel();
		}
	}
	
	/**
	 * Move a panel down one in the list
	 * @param panel
	 */
	public void moveDown(ProcessItemPanel panel) {
		int index = getPanelIndex (panel);
		if (index > -1
				&& index < panels.size() - 1) {
			LOGGER.finest("Swapping pannels " + index + " and " + (index + 1));
			Collections.swap(panels, index, index + 1);
			resetListPanel();
		}
	}
	
	/**
	 * Delete a panel from the list
	 * @param panel
	 */
	public void deletePanel(ProcessItemPanel panel) {
		int index = getPanelIndex(panel);
		if (index > -1) {
			LOGGER.finest("Deleting panel at index " + index);
			panels.remove(index);
			resetListPanel();
		}
	}
	
	public void addPanel() {
		LOGGER.finest("Adding new panel");
		panels.add(new ProcessItemPanel(this));
		resetListPanel();
	}
	
	public List<String> panelsToProcessItems() {
		ArrayList<String> items = new ArrayList<String>(panels.size());
		
		for (ProcessItemPanel p : panels) {
			items.add(p.getValue());
		}
		
		return items;
	}
	
	public void processItemsToPanels(List<String> items) {
		for (String item : items) {
			ProcessItemPanel panel = new ProcessItemPanel(this);
			panel.setValue(item);
			panels.add(panel);
		}
		
		resetListPanel();
	}
	
	/**
	 * Get the index of a panel in the list of panels stored on this component
	 * @param panel
	 * @return The index of the panel
	 */
	private int getPanelIndex(ProcessItemPanel panel) {
		if (!panels.contains(panel)) {
			LOGGER.severe("Trying to move panel, but it isn't contained in the list");
			return -1;
		}
		
		return panels.indexOf(panel);
	}
	
	/**
	 * Remove all the components from the list panel and add them again from the source list.
	 */
	public void resetListPanel() {
		JPanel listArea = getJPanelListArea();
		listArea.removeAll();
		
		Color displayColor = Color.WHITE;
		for (ProcessItemPanel p : panels) {
			listArea.add(p);
			p.setBackground(displayColor);
			displayColor = getNextDisplayColor(displayColor);
		}

		validate();
		repaint();
	}
	
	private Color getNextDisplayColor(Color currentColor) {
		if (currentColor == Color.WHITE) {
			return Color.GRAY;
		}
		else {
			return Color.WHITE;
		}
	}
	
	/*
	 * Screen gets\sets
	 */
	private JScrollPane getJScrollPane(JPanel scrollPanel) {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane(getJPanelListArea());
			//LATER - Scroll speed
		}
		
		return jScrollPane;
	}
	
	private JPanel getJPanelListArea() {
		if (jPanelListArea == null) {
			jPanelListArea = new JPanel();
			jPanelListArea.setLayout(new BoxLayout(jPanelListArea, BoxLayout.Y_AXIS));
		}
		
		return jPanelListArea;
	}
	
	private JPanel getjPanelButtons() {
		if (jPanelButtons == null) {
			jPanelButtons = new JPanel();
			jPanelButtons.setLayout(new BoxLayout(jPanelButtons, BoxLayout.X_AXIS));
			jPanelButtons.add(getJButtonNewItem());
		}
		
		return jPanelButtons;
	}
	
	private JButton getJButtonNewItem() {
		if (jButtonNewItem == null) {
			jButtonNewItem = new JButton("New panel");
			jButtonNewItem.setMaximumSize(BUTTON_MAX_SIZE);
			jButtonNewItem.setPreferredSize(BUTTON_PREFERRED_SIZE);
			jButtonNewItem.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					addPanel();
				}
			});
		}
		
		return jButtonNewItem;
	}
}