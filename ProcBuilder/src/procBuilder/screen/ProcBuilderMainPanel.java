package procBuilder.screen;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;

import procBuilder.engine.ProcessWrapper;
import procBuilder.screen.processItemList.ProcessWrapperCreator;
import procBuilder.screen.scrollTable.processWrapper.ProcessWrapperRowWrapper;
import procBuilder.screen.scrollTable.processWrapper.ProcessWrapperTable;

public class ProcBuilderMainPanel extends JPanel{
	
	private JButton jButtonAddProcessWrapper;
	private ProcessWrapperTable processWrapperTable;
	private ProcessWrapperCreator processWrapperCreator;
	
	public ProcBuilderMainPanel() {
		setLayout(new BorderLayout());
		add(getProcessWrapperTable(), BorderLayout.LINE_START);
		add(getProcessWrapperCreator(), BorderLayout.CENTER);
		add(getJButtonAddProcessWrapper(), BorderLayout.PAGE_START); 
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
						//return data.get(summaryTable.convertRowIndexToModel(selectedRowIndex));
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
					}
				}
			};
			processWrapperTable.addMouseListener(ml);
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
	
	private void addProcecss(ProcessWrapper newWrapper) {
		getProcessWrapperTable().addProcessWrapper(newWrapper);
		validate();
		repaint();
	}
}