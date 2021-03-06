package procBuilder.screen.mainPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import procBuilder.engine.ProcessGenerator;
import procBuilder.engine.ProcessWrapper;
import procBuilder.persistence.ProcessPersistence;
import procBuilder.screen.ScreenStatics;
import procBuilder.screen.processPanel.ProcessPanel;
import procBuilder.screen.scrollTable.wrapperTable.ProcessWrapperWrapper;
import procBuilder.screen.scrollTable.wrapperTable.WrapperTable;

public class MainPanel extends JPanel implements ScreenStatics {
	private ProcessPanel processPanel;
	private JPanel jPanelButtons, jPanelFactory;
	private JButton jButtonRun, jButtonReadOnly, jButtonNewProcess, jButtonDelete, jButtonSave, jButtonFactory;
	private JTextField jTextFieldFactoryDir;
	private WrapperTable wrapperTable;
	private ProcessPersistence persistence;
	
	private ProcessWrapperWrapper currentSelection;
	private boolean readOnly;
	
	public MainPanel() {
		initPersistence();
		
		readOnly = false;
		setReadOnly(readOnly);
		
		currentSelection = null;
		
		setLayout(new BorderLayout());
		setBackground(BACKGROUND_COLOUR);
		
		add(getWrapperTable(), BorderLayout.LINE_START);
		add(getProcessPanel(), BorderLayout.CENTER);
		add(getJPanelButtons(), BorderLayout.PAGE_END);
	}
	
	private void initPersistence() {
		persistence = ProcessPersistence.factoryDefaultProcessPersistence();
		List<ProcessWrapper> persistedProcesses = persistence.retrieveProcessWrapperList();
		if (persistedProcesses != null) {
			addProcessWrappers(persistedProcesses);
		}
	}
	
	private void runProcess(ProcessWrapper pw) {
		ProcessBuilder pb = pw.toProcessBuilder();
		try {
			pb.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void addProcessWrappers(List<ProcessWrapper> wrappers) {
		for (ProcessWrapper wrapper : wrappers) {
			ProcessWrapperWrapper row = new ProcessWrapperWrapper(wrapper);
			getWrapperTable().addRow(row);
		}
	}
	
	private void setReadOnly(boolean readOnly) {
		getProcessPanel().setReadOnly(readOnly);
	}
	
	private void newProcessWrapper() {
		ProcessWrapper pw = new ProcessWrapper();
		pw.setName("New Process");
		List<String> commands = new ArrayList<String>();
		commands.add("");
		pw.setItems(commands);
		getProcessPanel().setFromProcessWrapper(pw);
		getWrapperTable().addProcess(pw);
		int index = getWrapperTable().getData().size() - 1;
		getWrapperTable().getTable().setRowSelectionInterval(index, index);
		updateTable();
	}
	
	private void updateTable() {
		getWrapperTable().revalidate();
		getWrapperTable().repaint();
	}
	
	private void deleteSelected() {
		ProcessWrapperWrapper rowWrapper = getWrapperTable().getSelectedRow();
		if (rowWrapper == null) {
			return;
		}
		
		currentSelection = null;
		getWrapperTable().removeRow(rowWrapper);
		rowWrapper = getWrapperTable().getSelectedRow();
		if (rowWrapper != null) {
			ProcessWrapper pw = rowWrapper.getWrapper();
			getProcessPanel().setFromProcessWrapper(pw);
		}
		
		updateTable();
	}
	
	private void persistTable() {
		//First save what we're looking at
		if (currentSelection != null) {
			ProcessWrapper newProcess = getProcessPanel().getProcessWrapper();
			currentSelection.setWrapper(newProcess);
		}
		else {
			ProcessWrapper newProcess = getProcessPanel().getProcessWrapper();
			ProcessWrapperWrapper newRow = new ProcessWrapperWrapper(newProcess);
			getWrapperTable().addRow(newRow);
		}
		updateTable();
		
		List<ProcessWrapper> processes = new ArrayList<ProcessWrapper>();
		List<ProcessWrapperWrapper> tableValues = getWrapperTable().getData();
		for (ProcessWrapperWrapper rowWrapper : tableValues) {
			ProcessWrapper pw = rowWrapper.getWrapper();
			processes.add(pw);
		}
		
		if (processes.size() == 0) {
			return;
		}
		
		try {
			persistence.persistProcessWrapperList(processes);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Screen gets/sets	
	 */
	private ProcessPanel getProcessPanel() {
		if (processPanel == null) {
			processPanel = new ProcessPanel();
		}
		
		return processPanel;
	}
	
	private JPanel getJPanelButtons() {
		if (jPanelButtons == null) {
			jPanelButtons = new JPanel();
			jPanelButtons.setLayout(new FlowLayout(FlowLayout.LEADING));
			
			jPanelButtons.add(getJButtonRun());
			jPanelButtons.add(getJButtonReadOnly());
			jPanelButtons.add(getJButtonNewProcess());
			jPanelButtons.add(getJButtonDelete());
			jPanelButtons.add(getJButtonSave());
			jPanelButtons.add(getJPanelFactory());
		}
		
		return jPanelButtons;
	}
	
	private JPanel getJPanelFactory() {
		if (jPanelFactory == null) {
			jPanelFactory = new JPanel();
			jPanelFactory.setLayout(new FlowLayout(FlowLayout.LEADING));
			jPanelFactory.add(getJButtonFactory());
			jPanelFactory.add(getJTextFieldFactoryDir());
		}
		
		return jPanelFactory;
	}
	
	private JButton getJButtonFactory() {
		if (jButtonFactory == null) {
			jButtonFactory = new JButton("Factory");
			jButtonFactory.setMaximumSize(BUTTON_MAX);
			jButtonFactory.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					List<String> commands = new ArrayList<String>();
					commands.add("cmd");
					commands.add("/c");
					commands.add("start");
					
					Map<String, String> env = new HashMap<String, String>();
					String extension = ".exe";
					try {
						List<ProcessWrapper> newList = ProcessGenerator.generateProcesses(Paths.get(getJTextFieldFactoryDir().getText()), commands, env, extension);
						addProcessWrappers(newList);
						updateTable();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			});
		}
		
		return jButtonFactory;
	}
	
	private JTextField getJTextFieldFactoryDir() {
		if (jTextFieldFactoryDir == null) {
			jTextFieldFactoryDir = new JTextField();
			jTextFieldFactoryDir.setMaximumSize(TEXT_MAX);
			jTextFieldFactoryDir.setPreferredSize(new Dimension(200, 20));
		}
		
		return jTextFieldFactoryDir;
	}
	
	private JButton getJButtonRun() {
		if (jButtonRun == null) {
			jButtonRun = new JButton("Run");
			jButtonRun.setMaximumSize(BUTTON_MAX);
			jButtonRun.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					ProcessPanel panel = getProcessPanel();
					ProcessWrapper wrapper = panel.getProcessWrapper();
					runProcess(wrapper);
				}
			});
		}
		
		return jButtonRun;
	}
	
	private JButton getJButtonReadOnly() {
		if (jButtonReadOnly == null) {
			jButtonReadOnly = new JButton("Read Only");
			jButtonReadOnly.setMaximumSize(BUTTON_MAX);
			jButtonReadOnly.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					readOnly = !readOnly;
					setReadOnly(readOnly);
				}
			});
		}
		
		return jButtonReadOnly;
	}
	
	private JButton getJButtonNewProcess() {
		if (jButtonNewProcess == null) {
			jButtonNewProcess = new JButton("New");
			jButtonNewProcess.setMaximumSize(BUTTON_MAX);
			jButtonNewProcess.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					newProcessWrapper();
				}
			});
		}
		
		return jButtonNewProcess;
	}
	
	private JButton getJButtonDelete() {
		if (jButtonDelete == null) {
			jButtonDelete = new JButton("Delete");
			jButtonDelete.setMaximumSize(BUTTON_MAX);
			jButtonDelete.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					deleteSelected();
				}
			});
		}
		
		return jButtonDelete;
	}
	
	private JButton getJButtonSave() {
		if (jButtonSave == null) {
			jButtonSave = new JButton("Save");
			jButtonSave.setMaximumSize(BUTTON_MAX);
			jButtonSave.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					persistTable();
				}
			});
		}
		
		return jButtonSave;
	}
	
	private WrapperTable getWrapperTable() {
		if (wrapperTable == null) {
			wrapperTable = new WrapperTable();
			wrapperTable.setPreferredSize(new Dimension(100, 0));
			wrapperTable.addTableMouseListener(new MouseAdapter() {
				
				@Override
				public void mouseReleased(MouseEvent e){
					JTable table = wrapperTable.getTable();
					int selectedRow = table.rowAtPoint(e.getPoint());
					//Change to new wrapper
					//First update the current one
					if (currentSelection != null) {
						ProcessWrapper newProcess = getProcessPanel().getProcessWrapper();
						currentSelection.setWrapper(newProcess);
					}
					//Then switch over
					ProcessWrapperWrapper rowWrapper = getWrapperTable().getRowWrapper(selectedRow);
					ProcessWrapper processWrapper = rowWrapper.getWrapper();
					getProcessPanel().setFromProcessWrapper(processWrapper);
					currentSelection = rowWrapper;
					
					getWrapperTable().revalidate();
					getWrapperTable().repaint();
					
					if (e.getClickCount() == 2) {
						//Double click - Run
						runProcess(processWrapper);
					}
				}
			});
		}
		
		return wrapperTable;
	}
}
