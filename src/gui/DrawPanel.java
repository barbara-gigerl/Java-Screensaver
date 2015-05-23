package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Random;
import javax.swing.JPanel;

class DrawPanel extends JPanel implements Runnable {
    //Membervariable
    //Position 
    private int x, y; //Position, an der gezeichnet werden soll    
    private int dx, dy; //Werte, um die sich die x- bzw. y-Werte erhöhen
    //Zeit, nach der sich die Richtung ändert
    private int time;
    //Farben 
    private Color actualColor;  //Farbe, mit der gezeichnet wird
    private int color;  //Zeit, nach der sich die Farbe ändert
    private boolean isViolett; //gibt an, ob die aktuelle Farbe violett ist
    private Random random;
    private int option;
   
    
    //Konstruktor
    /*
     * Parameter:
     *              - size: Dimension: Größe des Panels
     *              - dx bzw. dy: int: Werte, um die sich die x- bzw. y-Werte erhöhen
     * Aufgabe:     
     *              - initialisiert die Memberfariablen
     */
    public DrawPanel(Dimension size, int dx, int dy) {
        this.setSize(size);     //Setzen der Größe des Panels

        random = new Random(); //Erzeugen der Ranom-Instanz

        //Initialisierung von dx und dy
        this.dx = dx;
        this.dy = dy;

        //Die aktuelle Farbe ist violett, nach 20 Kreisen soll sich die Farbe ändern
        actualColor = new Color(147, 112, 219);
        color = 20;
        isViolett = true;

        setTimeValue(); //Zeit erhält zufällige Werte

        //Der Anfangspunkt soll zufällig sein
        x = random.nextInt(this.getSize().width - 10);
        y = random.nextInt(this.getSize().height - 10);

        option = random.nextInt(4) + 1;
    
        changeD();
    }

    // + paint(g: Graphics): void
    /*
     * Parameter:
     *              - g: Graphics
     * Aufgabe:
     *              - zeichnet die Kreise
     */
    @Override
    public void paint(Graphics g) {
        //Zeichnungen sollen mit Graphics2D gemacht werden, da dies neuer ist
        //und mehr Funktionen zur Verfügung stellt
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(actualColor);  //Setzen der Farbe, mit der gezeichnet wird
        g2d.fillOval(x, y, 10, 10);  //Zeichnen des Kreises 
    }

    // + run(): void
    /*
     * Aufgabe:   
     *              - Methode, die vom Thread ausgeführt wird
     */
    @Override
    public void run() {

        while (true) //Endlosschleife: es soll immer gezeichnet werden
        {
            boolean collision = checkCollision();  //prüft, ob der Kreis, der gezeichnet werden soll, außerhalb des Bildschirmes liegt
            if (collision) {
                changeD();
            }

            //Ändern der Positionen
            x += dx;
            y += dy;

            try {
                Thread.sleep(50);  //Thread soll 10 ms pausieren
            } catch (InterruptedException ex) {
                System.out.println(ex.toString());
            }

            //Zähler für Zeit und Farbe werden dekrementiert
            time--;
            color--;

            //Ist die Zeit 0, wird eine neue Initialisiert und die Richtung ändert sich
            if (time == 0) {
                changeD();
                setTimeValue();
            }

            //Ist die Farbe 0, ändert sie ihren Wert auf die jeweils andere
            if (color == 0) {
                isViolett = changeColor();
            }
            repaint(); //ruft die Methode abermals auf
        }
    }

    // - checkCollision(): void
    /*
     * Aufabe:   
     *              - findet heraus, ob der Kreis noch innerhalb des Panels gezeichnet werden kann
     */
    private boolean checkCollision() {
        if ((x < 10 || x > this.getWidth()) || (y < 10 || y > this.getHeight())) {
            return true;
        }
        return false;

    }

    // - setTimeValue(): void
    /*
     * Aufgabe:
     *              - gibt Zeit, nach der sich die Richtung ändert, einen Zufallswert
     */
    private void setTimeValue() {
        time = random.nextInt(100) + 50;
    }

    // - changeD(): void
    /*
     * Aufgabe: 
     *             - ändert die Richtung, in die gezeichnet wird nach folgendem Prinzip
     *               +ist die letzte Richtung eine gerade Zahl, zeichtet man nach oben oder unten
     *               +ist die letzte Richtung eine ungerade Zahl, zeichnet man nach links oder rechts
     *               +auf eine gerade Zahl darf nur eine ungerade folgen, sprich, zeichnet man nach links oder rechts darf dann nur nach oben oder unten gezeichnet werden
     */
    private void changeD() {

        int newOption = 0;
        if ((option % 2) == 0) {
            newOption = random.nextInt(4) + 1;
            while ((newOption % 2) != 1) {
                newOption = random.nextInt(4) + 1;
            }
        }

        if ((option % 2) != 0) {
            newOption = random.nextInt(4) + 1;
            while ((newOption % 2) != 0) {
                newOption = random.nextInt(4) + 1;
            }
        }

        switch (newOption) {
            case 1: {
                dx = 3;
                dy = 0;
            }
            break;

            case 2: {
                dx = 0;
                dy = 3;
            }
            break;

            case 3: {
                dx = -3;
                dy = 0;
            }
            break;

            case 4: {
                dx = 0;
                dy = -3;
            }
            break;
        }

        option = newOption;
    }

    // - changeColor(): boolean
    /*
     * Aufgabe: 
     *              - ändert die Farbe, mit der gezeichnet wird
     * 
     * Rückgabewert:
     *              - false, bei default oder wenn die aktuelle Farbe grün ist
     *              - true, wenn die aktuelle Farbe violett ist
     * 
     */
    private boolean changeColor() {
        color = 20;
        if (isViolett) {
            actualColor = new Color(46, 189, 87);
            return false;
        }

        if (!(isViolett)) {
            actualColor = new Color(147, 112, 219);
            return true;
        }

        return false;
    }
}


/*
 * xxx-Vollbildmodus
 * xxx-Hintergrundfarbe Schwarz
 * xxx-zufälliger Punkt
 * xxx-zufällige Richtung, richtung ändert sich, nicht quer, nicht gleiche richtrung
 * xxx-ändert in regelmäßigen Abständen seine Farbe
 * xxx-unterschiedliche Länge
 */