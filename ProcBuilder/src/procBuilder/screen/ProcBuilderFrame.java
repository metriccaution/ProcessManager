package procBuilder.screen;

import javax.swing.JFrame;

public class ProcBuilderFrame extends JFrame {

	/**
	 * Create the frame.
	 */
	public ProcBuilderFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 500, 300);
		setTitle("Process Builder");	
		setContentPane(new ProcessPanel());
		setVisible(true);
	}

}
