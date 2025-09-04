package f1;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Rennleitung {
    private static final String RUNDETXT= "[Runde ";
    private static boolean running= true;
    private static int fertig=0;
    private static BlockingQueue<String> bq= new LinkedBlockingQueue<>();

    public static void main(String[] args) {
        Team rb= new Team("Red Bull");
        Team fe= new Team("Ferrari");
        Team me= new Team("Mercedes");
        Fahrer[] fahrers= new Fahrer[6];
        fahrers[0] = new Fahrer("Charles_Leclerc", 1.000165, Reifen.MEDIUM,fe,bq);
        fahrers[1] = new Fahrer("Carlos_Sainz", 1.001712, Reifen.MEDIUM,fe,bq);
        fahrers[2] = new Fahrer("Sergio_Perez", 1.000213, Reifen.SOFT,rb,bq);
        fahrers[3] = new Fahrer("Max_Verstappen", 1.000000, Reifen.SOFT,rb,bq);
        fahrers[4] = new Fahrer("George_Russel", 1.000418, Reifen.SOFT,me,bq);
        fahrers[5] = new Fahrer("Lewis_Hamilton", 1.007208, Reifen.HARD,me,bq);
        System.out.println("Willkommen in Monaco!");
        System.out.println("It's lights out and away we go!");
        for(int j=0; j< fahrers.length; j++) {
            fahrers[j].start();
        }
        /**
        for(double i=0; i<S_LAENGE; i++) {
            runde= (int) (km/R_LAENGE);
            if(runde== 24) {
                System.out.println("[Runde "+runde+"] Virtual Safety Car ist aktiv!");
                for(int j=0; j<6; j++) {
                    fahrers[j].setExtra(1.4000);
                }
            }
            if(runde== 34) {
                System.out.println("[Runde "+runde+"] Virtual Safety Car ist beendet!");
                for(int j=0; j<6; j++) {
                    fahrers[j].setExtra(0);
                }
            }
        }
         */
        boolean scstart= false;
        boolean scend= false;
        while(running) {
            try {
                String zs = bq.take();
                String[] zeile= zs.split(" ");
                int r= Integer.parseInt(zeile[0]);
                String name= zeile[1];
                String bedingung= zeile[2];
                switch (bedingung) {
                    case "gB":
                        System.out.println(RUNDETXT+r+"] "+name+" geht gleich an die Box!");
                        break;
                    case "Br":
                        System.out.println(RUNDETXT+r+"] "+name+" ist nach 25ms aus der Box gefahren!");
                        break;
                    case "Bg":
                        System.out.println(RUNDETXT+r+"] "+name+" ist an die Box gefahren!");
                        break;
                    case default:
                        return;
                }
                if(r==24) {
                    if(!scstart) {
                        System.out.println(RUNDETXT + r + "] Virtual Safety Car ist aktiv!");
                        scstart= true;
                    }
                    for(int i=0; i< fahrers.length;i++) {
                        fahrers[i].setExtra(1.4000);
                    }
                }
                if(r==34) {
                    if(!scend) {
                        System.out.println(RUNDETXT + r + "] Virtual Safety Car ist vorbei!");
                        scend= true;
                    }
                    for(int i=0; i< fahrers.length;i++) {
                        fahrers[i].setExtra(0);
                    }
                }
                if(bedingung.equals("fertig")) {
                    fertig++;
                    System.out.println(RUNDETXT+r+"] "+name+" ist im Ziel!");
                }
                if(fertig==6)running=false;
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                System.out.println("extecption" + ie.getMessage());
            }
        }
    }
}
