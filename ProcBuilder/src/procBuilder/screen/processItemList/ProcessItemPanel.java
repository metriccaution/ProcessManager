package procBuilder.screen.processItemList;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import procBuilder.engine.AbstractProcessItem;
import procBuilder.engine.StringProcessItem;
import procBuilder.screen.ProcBuilderScreenConstants;

/**
 * A single item representing a command in a ProcessBuilder
 * @author David
 *
 */
public class ProcessItemPanel extends JPanel implements ProcBuilderScreenConstants {
	
	/*
	 * Screen variables
	 */
	private JButton jButtonMoveUp;
	private JButton jButtonMoveDown;
	private JButton jButtonDelete;
	private JPanel	jPanelButtons;
	private JTextField jTextFieldValue;

	/*
	 * Non-screen variables
	 */
	private ProcessItemList parent;

	public ProcessItemPanel(ProcessItemList parent) {
		this.parent = parent;
		//TODO - Size & alignment
		BoxLayout layout = new BoxLayout(this, BoxLayout.X_AXIS);
		setLayout(layout);

		add(getJTextFieldValue());
		add(getJPanelButtons());
	}
	
	public void setEditableMode(boolean editable) {
		getJPanelButtons().setVisible(editable);
	}
	
	public AbstractProcessItem getValue() {
		String text = getJTextFieldValue().getText();
		return new StringProcessItem(text);
	}

	/*
	 * Screen gets/sets
	 */
	private JPanel getJPanelButtons() {
		if (jPanelButtons == null) {
			jPanelButtons = new JPanel();

			jPanelButtons.setLayout(new GridLayout(3, 1));
			
			int width = BUTTON_PREFERRED_SIZE.width;
			int height = BUTTON_PREFERRED_SIZE.height * 3;
			jPanelButtons.setMaximumSize(new Dimension(width, height));
			
			jPanelButtons.add(getJButtonMoveUp());
			jPanelButtons.add(getJButtonMoveDown());
			jPanelButtons.add(getJButtonDelete());
		}

		return jPanelButtons;
	}

	private JButton getJButtonMoveUp() {
		if (jButtonMoveUp == null) {
			jButtonMoveUp = new JButton("Up");
			jButtonMoveUp.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					parent.moveUp(ProcessItemPanel.this);
				}
			});
		}

		return jButtonMoveUp;
	}

	private JButton getJButtonMoveDown() {
		if (jButtonMoveDown == null) {
			jButtonMoveDown = new JButton("Down");
			jButtonMoveDown.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					parent.moveDown(ProcessItemPanel.this);
				}
			});
		}

		return jButtonMoveDown;
	}
	
	private JButton getJButtonDelete() {
		if (jButtonDelete == null) {
			jButtonDelete = new JButton("Delete");
			jButtonDelete.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					parent.deletePanel(ProcessItemPanel.this);
				}
			});
		}
		
		return jButtonDelete;
	}
	
	private JTextField getJTextFieldValue() {
		if (jTextFieldValue == null) {
			jTextFieldValue = new JTextField();
			jTextFieldValue.setPreferredSize(TEXT_FIELD_PREFERRED_SIZE);
			jTextFieldValue.setMaximumSize(TEXT_FIELD_MAX_SIZE);
		}
		
		return jTextFieldValue;
	}
}