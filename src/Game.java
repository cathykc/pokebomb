import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Game implements Runnable {
	
	// run this
	public void run() {
		
		// new frame labeled game name
		final JFrame frame = new JFrame("PokeBomb");
		
		// location of frame on computer screen
		frame.setLocation(500,300);

		// status of game
		final JPanel status_panel = new JPanel();
		frame.add(status_panel, BorderLayout.SOUTH);
		final JLabel status = new JLabel("Running...");
		status_panel.add(status);

		// playing area
		final PokeField field = new PokeField(status);
		frame.add(field, BorderLayout.CENTER);

		// reset button
		final JPanel control_panel = new JPanel();
		frame.add(control_panel, BorderLayout.NORTH);
		final JButton reset = new JButton("New Game");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				field.reset();
			}
		});
		control_panel.add(reset);

		// put frame on screen
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		// start game
		field.reset();
	}

	// main method to initialize all GUI elements and run game
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Game());
	}
}
