package projekat1;

import java.util.concurrent.Semaphore;

public class Factory {
    public static void main(String[] args) {
        Semaphore mutex = new Semaphore(1);
        Pekara pekara = new Pekara(mutex);
        Prodavac prodavac = new Prodavac(mutex);

        Thread pekarThread = new Thread(pekara);
        pekarThread.start();

        Thread prodavacThread = new Thread(prodavac);
        prodavacThread.start();

        for (int i = 0; i < 30; i++) { 
            Musterija musterija = new Musterija(i, prodavac);
            new Thread(musterija).start();
            try {
                Thread.sleep(500); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
