package procBuilder.screen.processItemList;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JPanel;

import procBuilder.engine.AbstractProcessItem;
import procBuilder.engine.ProcessEngine;
import procBuilder.engine.ProcessWrapper;
import procBuilder.screen.scrollTable.processWrapper.ProcessWrapperTable;

public class ProcessWrapperCreator extends JPanel {
	private final static Logger LOGGER = Logger.getLogger(ProcessWrapperCreator.class.getName());

	//TODO - Dummy screen - remove
	
	/*
	 * Screen variables
	 */
	private ProcessItemList list;
	private JButton jButtonRun;
	private ProcessWrapperTable wrapperTable;

	/*
	 * Non-screen variables
	 */
	private ProcessEngine engine;

	public ProcessWrapperCreator() {
		setLayout(new BorderLayout());

		add(getJButtonRun(), BorderLayout.PAGE_START);
		add(getList(), BorderLayout.LINE_END);
		add(getWrapperTable(), BorderLayout.CENTER);
	}

	public void setEngine(ProcessEngine engine) {
		this.engine = engine;
	}

	private ProcessItemList getList() {
		if (list == null) {
			list = new ProcessItemList();
			list.setPreferredSize(new Dimension(300,0));
		}

		return list;
	}

	private JButton getJButtonRun() {
		if (jButtonRun == null) {
			jButtonRun = new JButton("Run");

			jButtonRun.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					List<AbstractProcessItem> items = getList().panelsToProcessItems();
					LOGGER.finest("Made parameter set: " + items);

					ProcessWrapper wrapper = new ProcessWrapper();
					wrapper.setItems(items);
					getWrapperTable().addProcessWrapper(wrapper);
					validate();
					repaint();
				}
			});
		}

		return jButtonRun;
	}
	
	private ProcessWrapperTable getWrapperTable() {
		if (wrapperTable == null){
			wrapperTable = new ProcessWrapperTable();
			wrapperTable.setPreferredSize(new Dimension(200,0));
			wrapperTable.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					int rowIndex = wrapperTable.getTable().rowAtPoint(e.getPoint());
					
					if (rowIndex == -1) {
						return;
					}
					
					ProcessWrapper wrapper = wrapperTable.getRowWrapper(rowIndex).getSource();
					try {
						engine.runProcessWrapper(wrapper);
					} catch (IOException e1) {
						LOGGER.log(Level.SEVERE, "IOException running command", e1);
					}
				}
			});
		}
		
		return wrapperTable;
	}
}