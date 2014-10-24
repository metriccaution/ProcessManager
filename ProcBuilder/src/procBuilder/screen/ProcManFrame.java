package procBuilder.screen;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;

import procBuilder.engine.ProcessWrapper;
import procBuilder.screen.processItemList.ProcessWrapperCreator;

public class ProcManFrame extends JFrame{
	public ProcManFrame() {
		setTitle("Command Manager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 750, 500);
		ProcessWrapperCreator contentPane = new ProcessWrapperCreator();
		ProcessWrapper dummyWrapper = getDummyWrapper();
		contentPane.setFromProcessWrapper(dummyWrapper);
		setContentPane(contentPane);
		setVisible(true);
	}
	
	/**
	 * Test method
	 * @return
	 */
	public ProcessWrapper getDummyWrapper() {
		ProcessWrapper dummyWrapper = new ProcessWrapper();
		dummyWrapper.setName("TestName");
		dummyWrapper.setWorkingDirectory(Paths.get("C:\\"));
		ArrayList<String> list = new ArrayList<String>();
		list.add("Dummy");
		list.add("Test");
		list.add("Debug");
		list.add("Hello");
		dummyWrapper.setItems(list);
		HashMap<String, String> vals = new HashMap<String, String>();
		vals.put("Key", "Value");
		vals.put("Key2", "Value2");
		vals.put("Key3", "Value3");
		dummyWrapper.setEnv(vals);
		return dummyWrapper;
	}
}