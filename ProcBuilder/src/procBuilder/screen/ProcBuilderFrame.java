package procBuilder.screen;

import javax.swing.JFrame;

import procBuilder.screen.mainPanel.MainPanel;

public class ProcBuilderFrame extends JFrame {

	/**
	 * Create the frame.
	 */
	public ProcBuilderFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 750, 500);
		setTitle("Process Builder");	
		setContentPane(new MainPanel());
		setVisible(true);
	}

}
