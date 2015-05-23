package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

public class Window extends JFrame {

    //Membervariable
    private DrawPanel drawPanel;

    public Window() {
        this.setBackground(Color.black);  //setzt den Hintergrund des Frames auf schwarz
        this.setSize(getToolkit().getScreenSize()); //setzt die Größe des Frames auf Bilschirmgröße
        this.drawPanel = new DrawPanel(getToolkit().getScreenSize(), 10, 10); //setzt die Größe des Panels auf Bildschirmgröße
        this.setUndecorated(true); //Fullscreen-Mode
        this.setLayout(new BorderLayout()); //Layout: BorderLayout
        this.getContentPane().add(this.drawPanel, BorderLayout.CENTER); //fügt das Panel hinzu
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Thread thread = new Thread(drawPanel); //erzeugt neuen Thread
        thread.start(); //startet Thread
    }

    public static void main(String[] args) {
        Window window = new Window();
        
        //Key Listener zum Schließen des Programmes
        /*
         * Damit das Programm auch abgebrochen werden kann, verleihen wir dem
         * Frame einen KeyListener. Wird nun eine beliebige Taste gedrückt,
         * schließt man das Programm. Hier wird ein KeyAdapter verwendet, da es
         * nicht nötig ist, alle Methoden der Klasse KeyListener auszuimplementieren.
         */
        window.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                System.exit(1);
            }
        });
        window.setVisible(true); //macht das Fenster sichtbar
    }
}
