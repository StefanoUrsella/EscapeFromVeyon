// Menu.java
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Menu extends JPanel {

    public Menu(ActionListener onStartGame) {
        // Usiamo GridBagLayout per centrare facilmente tutti i componenti
        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 20, 20); // Spazio tra i componenti

        // Titolo del Gioco
        JLabel title = new JLabel("FALLING BOMBS");
        title.setFont(new Font("TimesRoman", Font.BOLD, 50));
        title.setForeground(Color.RED);
        add(title, gbc);

        // Pulsante Gioca
        gbc.gridy = 1;
        JButton playButton = new JButton("GIOCA");
        playButton.setFont(new Font("TimesRoman", Font.BOLD, 24));
        playButton.setBackground(Color.DARK_GRAY);
        playButton.setForeground(Color.WHITE);
        playButton.setFocusPainted(false);
        playButton.addActionListener(onStartGame);
        add(playButton, gbc);

        // Pulsante Esci
        gbc.gridy = 2;
        JButton exitButton = new JButton("ESCI");
        exitButton.setFont(new Font("TimesRoman", Font.BOLD, 24));
        exitButton.setBackground(Color.DARK_GRAY);
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusPainted(false);
        exitButton.addActionListener(e -> System.exit(0)); // Chiude il programma
        add(exitButton, gbc);
    }
}