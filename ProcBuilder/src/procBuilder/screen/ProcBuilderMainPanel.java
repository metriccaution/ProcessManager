package procBuilder.screen;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;

import procBuilder.engine.ProcessWrapper;
import procBuilder.persistence.ProcessPersistence;
import procBuilder.screen.processItemList.ProcessWrapperCreator;
import procBuilder.screen.scrollTable.processWrapper.ProcessWrapperRowWrapper;
import procBuilder.screen.scrollTable.processWrapper.ProcessWrapperTable;

public class ProcBuilderMainPanel extends JPanel{
	private final static Logger LOGGER = Logger.getLogger(ProcBuilderMainPanel.class.getName());
	
	//LATER - Proper Path stuff
	private final static String FILE_LOCATION = System.getProperty("user.home") + "\\ProcManager\\";
	
	/*
	 * Card layout statics
	 */
	private final static String EDIT_PANEL = "EditPanel";
	
	/*
	 * Screen components
	 */
	private JPanel jPanelMain;
	private JPanel jPanelCreate;
	private JButton jButtonAddProcessWrapper;
	private JButton jButtonPersistWrappers;
	private ProcessWrapperTable processWrapperTable;
	private ProcessWrapperCreator processWrapperCreator;
	/*
	 * Non-screen components
	 */
	private ProcessPersistence wrapperPersistance;
	
	public ProcBuilderMainPanel() {
		initScreen();
		initPersistance();
	}
	
	private void initScreen() {
		setLayout(new BorderLayout());
		add(getProcessWrapperTable(), BorderLayout.LINE_START);
		add(getJPanelMain(), BorderLayout.CENTER);
	}
	
	private void initPersistance() {
		//Load the list of ProcessWrappers from file
		String pathString = FILE_LOCATION;
		wrapperPersistance = new ProcessPersistence(pathString);
		List<ProcessWrapper> persistedWrappers = wrapperPersistance.retrieveProcessWrapperList();
		
		if (persistedWrappers == null) {
			//Log a warning if things go wrong, but carry on - The program is still usuable
			LOGGER.warning("Null result from persisted wrappers in " + wrapperPersistance.getSaveLocation());
		}
		else {
			//Add all of the ProcessWrappers from the list to the table
			LOGGER.finest("Persisted " + persistedWrappers.size() + " wrappers from file in " + wrapperPersistance.getSaveLocation());
			
			ProcessWrapperTable table = getProcessWrapperTable();
			for (ProcessWrapper wrapper : persistedWrappers) {
				table.addProcessWrapper(wrapper);
			}
		}
	}
	
	public JPanel getJPanelMain() {
		if (jPanelMain == null) {
			jPanelMain = new JPanel();
			jPanelMain.setLayout(new CardLayout());
			jPanelMain.add(getJPanelCreate(), EDIT_PANEL);
		}
		
		return jPanelMain;
	}
	
	public JPanel getJPanelCreate() {
		if (jPanelCreate == null) {
			jPanelCreate = new JPanel();
			jPanelCreate.setLayout(new BorderLayout());
			jPanelCreate.add(getProcessWrapperCreator(), BorderLayout.CENTER);
			jPanelCreate.add(getJButtonPersistWrappers(), BorderLayout.PAGE_START);
			jPanelCreate.add(getJButtonAddProcessWrapper(), BorderLayout.PAGE_END);
		}
		
		return jPanelCreate;
	}
	
	private ProcessWrapperTable getProcessWrapperTable() {
		if (processWrapperTable == null) {
			processWrapperTable = new ProcessWrapperTable();
			processWrapperTable.setPreferredSize(new Dimension(100, 0));
		}
		
		return processWrapperTable;
	}
	
	private ProcessWrapperCreator getProcessWrapperCreator() {
		if (processWrapperCreator == null) {
			processWrapperCreator = new ProcessWrapperCreator();
			MouseListener ml = new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					super.mouseReleased(e);
					
					if (e.getClickCount() == 2) {
						JTable processTable = processWrapperTable.getTable();
						int selectedRowIndex = processTable.getSelectedRow();
						int realIndex = processTable.convertRowIndexToModel(selectedRowIndex);
						ProcessWrapperRowWrapper rowWrapper = processWrapperTable.getRowWrapper(realIndex);
						ProcessWrapper wrapper = rowWrapper.getSource();
						ProcessBuilder pb = wrapper.toProcessBuilder();
						try {
							pb.start();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						LOGGER.finest("Done");
					}
				}
			};
			processWrapperTable.addTableMouseListener(ml);
		}
		
		return processWrapperCreator;
	}
	
	private JButton getJButtonAddProcessWrapper() {
		if (jButtonAddProcessWrapper == null) {
			jButtonAddProcessWrapper = new JButton("Add process");
			jButtonAddProcessWrapper.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					ProcessWrapper wrapper = getProcessWrapperCreator().getWrapperFromValues();
					if (wrapper != null) {
						addProcecss(wrapper);
					}					
				}
			});
		}
		
		return jButtonAddProcessWrapper;
	}
	
	private JButton getJButtonPersistWrappers() {
		if (jButtonPersistWrappers == null) {
			jButtonPersistWrappers = new JButton("Persist wrappers");
			jButtonPersistWrappers.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					List<ProcessWrapperRowWrapper> rowWrappers = getProcessWrapperTable().getData();
					List<ProcessWrapper> wrappers = new ArrayList<ProcessWrapper>();
					for (ProcessWrapperRowWrapper row : rowWrappers) {
						wrappers.add(row.getSource());
					}
					try {
						wrapperPersistance.persistProcessWrapperList(wrappers);
					} catch (IOException e1) {
						LOGGER.log(Level.WARNING, "Error persisting wrappers", e1);
					}				
				}
			});
		}
		
		return jButtonPersistWrappers;
	}
	
	private void addProcecss(ProcessWrapper newWrapper) {
		getProcessWrapperTable().addProcessWrapper(newWrapper);
		validate();
		repaint();
	}
}