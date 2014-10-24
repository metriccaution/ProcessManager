package procBuilder.screen;

import javax.swing.JFrame;

public class ProcManFrame extends JFrame{
	public ProcManFrame() {
		setTitle("Command Manager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 750, 500);
		ProcBuilderMainPanel contentPane = new ProcBuilderMainPanel();
		setContentPane(contentPane);
		setVisible(true);
	}
}