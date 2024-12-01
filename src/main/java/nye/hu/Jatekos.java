package nye.hu;

public class Jatekos {
    private final String nev;
    private final char korong;

    public Jatekos(String nev, char korong) {
        this.nev = nev;
        this.korong = korong;
    }

    public String getNev() {
        return nev;
    }

    public char getKorong() {
        return korong;
    }
}
