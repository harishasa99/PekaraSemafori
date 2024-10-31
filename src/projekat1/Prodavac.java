package projekat1;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Prodavac implements Runnable {
    private final Semaphore mutex;
    private final Queue<Musterija> redCekanja = new LinkedList<>();
    private static final int MAX_MUSTERIJA = 30;

    public Prodavac(Semaphore mutex) {
        this.mutex = mutex;
    }

    @Override
    public void run() {
        while (true) {
            try {
                mutex.acquire();
                if (!redCekanja.isEmpty()) {
                    Musterija musterija = redCekanja.poll();
                    if (musterija != null) {
                        // Ovde se vrsi isporuka hlebova
                        int zeljeniHlebovi = musterija.getZeljeniBrojHlebova();
                        if (Pekara.getDostupniHlebovi() >= zeljeniHlebovi) {
                            Pekara.smanjiHlebove(zeljeniHlebovi);
                            System.out.println("Musterija " + musterija.getId() + 
                                               " kupila " + zeljeniHlebovi + " hlebova. Preostalo: " + 
                                               Pekara.getDostupniHlebovi());
                        } else {
                            // Kada nema dovoljno hlebova, trazi se manji broj
                            boolean isporuceno = false;
                            for (int i = 1; i < zeljeniHlebovi; i++) {
                                if (Pekara.getDostupniHlebovi() >= i) {
                                    Pekara.smanjiHlebove(i);
                                    System.out.println("Musterija " + musterija.getId() + 
                                                       " kupila " + i + " hlebova. Preostalo: " + 
                                                       Pekara.getDostupniHlebovi());
                                    isporuceno = true;
                                    break;
                                }
                            }
                            if (!isporuceno) {
                                System.out.println("Nema dovoljno hlebova za musteriju " + musterija.getId() + ", prelazi se na sledeceg.");
                            }
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                mutex.release();
            }
        }
    }

    public void dodajUMredu(Musterija musterija) {
        if (redCekanja.size() < MAX_MUSTERIJA) {
            redCekanja.add(musterija);
            System.out.println("Musterija " + musterija.getId() + " je dodata u red. Ukupno u redu: " + redCekanja.size());
        } else {
            System.out.println("Red je pun, musterija " + musterija.getId() + " ne moze biti dodata.");
        }
    }
}
