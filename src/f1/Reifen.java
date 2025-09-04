package f1;

public enum Reifen {
    SOFT (1,20),
    MEDIUM (1.0006, 40),
    HARD (1.0008, 60);


    private double geschwindigkeit;
    private int dauer;
    Reifen(double g, int lae) {
        this.geschwindigkeit= g;
        this.dauer= lae;
    }

    public double getGeschwindigkeit() {
        return geschwindigkeit;
    }

    public int getDauer() {
        return dauer;
    }
}
