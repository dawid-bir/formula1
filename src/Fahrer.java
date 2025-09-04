import java.util.concurrent.BlockingQueue;

public class Fahrer extends Thread {

    private String name;
    private double fgeschwindigkeit;
    private double rgeschwindigkeit;
    private int reifenrunden;
    private double km= 0;
    private int runde= 0;
    private double extra= 0;
    private Reifen reifen;
    private Team team;
    private BlockingQueue<String> bq;

    public Fahrer(String name, double fg, Reifen r, Team t, BlockingQueue<String> bq) {
        this.name= name;
        this.fgeschwindigkeit= fg;
        this.reifen=r;
        this.team= t;
        this.bq=bq;
    }

    public void setReifenrunden(int reifenrunden) {
        this.reifenrunden = reifenrunden;
    }

    public int getReifenrunden() {
        return this.reifenrunden;
    }

    public void setRgeschwindigkeit(double rgeschwindigkeit) {
        this.rgeschwindigkeit = rgeschwindigkeit;
    }

    public String getFName() {
        return name;
    }

    public void setExtra(double zahl) {
        this.extra= zahl;
    }

    @Override
    public void run() {
        while(runde<78) {
            if(km>3.337) {
                km -= 3.337;
                runde++;
                if (reifen.getDauer() - 1 == runde) {
                    bq.add(runde+" "+name+ " gB");
                }
                if(fahrercheck()) {
                    try {
                        Thread.sleep(25);
                        bq.add(runde+" "+name+ " Br");
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println();
                }
                try {
                    Thread.sleep((long) (100*(fgeschwindigkeit+reifen.getGeschwindigkeit()+extra)));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                bq.add(runde+" "+name+ " RundeFertig");
            }
            km= km+1;
        }
        bq.add(runde+" "+name+" fertig");
    }

    public boolean fahrercheck() {
        int letzrund= 78-runde;
        if(reifen.getDauer() == runde) {
            synchronized (team) {
                bq.add(runde+" "+name+" Bg");
                if (letzrund < 20) {
                    reifen= Reifen.SOFT;
                } else if (letzrund < 40) {
                    reifen= Reifen.MEDIUM;
                } else {
                    reifen= Reifen.HARD;
                }
                return true;
            }
        }
        return false;
    }

}
