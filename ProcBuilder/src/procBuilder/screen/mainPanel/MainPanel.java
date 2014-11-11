package procBuilder.screen.mainPanel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;

import procBuilder.engine.ProcessWrapper;
import procBuilder.screen.ScreenStatics;
import procBuilder.screen.processPanel.ProcessPanel;

public class MainPanel extends JPanel implements ScreenStatics{
	//TODO - Write main screen
	
	private ProcessPanel processPanel;
	private JPanel jPanelButtons;
	private JButton jButtonRun;
	
	public MainPanel() {
		setLayout(new BorderLayout());
		setBackground(BACKGROUND_COLOUR);
		
		add(getProcessPanel(), BorderLayout.CENTER);
		add(getJPanelButtons(), BorderLayout.PAGE_END);
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
		}
		
		return jPanelButtons;
	}
	
	private JButton getJButtonRun() {
		if (jButtonRun == null) {
			jButtonRun = new JButton("Run");
			jButtonRun.setMaximumSize(BUTTON_MAX);
		}
		
		return jButtonRun;
	}
}