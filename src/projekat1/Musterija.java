package projekat1;

import java.util.Random;

public class Musterija implements Runnable {
    private final int id;
    private final Prodavac prodavac;
    private final int zeljeniBrojHlebova;

    public Musterija(int id, Prodavac prodavac) {
        this.id = id;
        this.prodavac = prodavac;
        // Random broj hlebova izmedju 1 i 5
        this.zeljeniBrojHlebova = new Random().nextInt(5) + 1;
    }

    @Override
    public void run() {
        prodavac.dodajUMredu(this);
    }

    public int getId() {
        return id;
    }

    public int getZeljeniBrojHlebova() {
        return zeljeniBrojHlebova;
    }
}
