package projekat1;

import java.util.concurrent.Semaphore;

public class Pekara implements Runnable {
    private static final int KAPACITET_PEKARE = 20;
    private static int dostupniHlebovi = 0;
    private final Semaphore mutex;

    public Pekara(Semaphore mutex) {
        this.mutex = mutex;
    }

    @Override
    public void run() {
        try {
            while (true) {
                mutex.acquire();
                if (dostupniHlebovi == 0) {
                    System.out.println("Pekar pece novih 20 hlebova...");
                    dostupniHlebovi += KAPACITET_PEKARE;
                    Thread.sleep(3000); // Simulacija vremena pecenja
                    System.out.println("Hlebovi su ispeceni! Dostupno: " + dostupniHlebovi);
                }
                mutex.release();
                Thread.sleep(1000); // Provera svakih sekundu
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static int getDostupniHlebovi() {
        return dostupniHlebovi;
    }

    public static void smanjiHlebove(int kolicina) {
        dostupniHlebovi -= kolicina;
    }
}
