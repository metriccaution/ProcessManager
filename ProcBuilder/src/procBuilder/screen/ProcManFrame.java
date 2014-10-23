package procBuilder.screen;

import javax.swing.JFrame;

import procBuilder.engine.ProcessEngine;
import procBuilder.screen.processItemList.ProcessWrapperCreator;

public class ProcManFrame extends JFrame{
	public ProcManFrame() {
		setTitle("Command Manager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		ProcessWrapperCreator contentPane = new ProcessWrapperCreator();
		contentPane.setEngine(new ProcessEngine());
		setContentPane(contentPane);
		setVisible(true);
	}
}