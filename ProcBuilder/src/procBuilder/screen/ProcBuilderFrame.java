package procBuilder.screen;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ProcBuilderFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProcBuilderFrame frame = new ProcBuilderFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ProcBuilderFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		contentPane = new ProcessPanel();
		setContentPane(contentPane);
	}

}
