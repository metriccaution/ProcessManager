package procBuilder.screen;

import javax.swing.JFrame;

import procBuilder.screen.processItemList.ProcessWrapperCreator;

public class ProcManFrame extends JFrame{
	public ProcManFrame() {
		setTitle("Command Manager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 750, 500);
		ProcessWrapperCreator contentPane = new ProcessWrapperCreator();
		setContentPane(contentPane);
		setVisible(true);
	}
}