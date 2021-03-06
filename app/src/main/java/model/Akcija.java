package model;

import java.time.LocalDate;
import java.util.Date;

public class Akcija {

    private int id;
    private int procenat;
    private LocalDate odKad;
    private LocalDate doKad;
    private String tekst;
    private int prodavac_id;
    private int artikal_id;

    public Akcija(){

    }

    public Akcija(int procenat, LocalDate odKad, LocalDate doKad, String tekst, int prodavac_id) {
        this.procenat = procenat;
        this.odKad = odKad;
        this.doKad = doKad;
        this.tekst = tekst;
        this.prodavac_id = prodavac_id;
    }

    public Akcija(int procenat, LocalDate odKad, LocalDate doKad, String tekst, int prodavac_id, int artikal_id) {
        this.procenat = procenat;
        this.odKad = odKad;
        this.doKad = doKad;
        this.tekst = tekst;
        this.prodavac_id = prodavac_id;
        this.artikal_id = artikal_id;
    }

    public Akcija(int id, int procenat, LocalDate odKad, LocalDate doKad, String tekst, int prodavac_id, int artikal_id) {
        this.id = id;
        this.procenat = procenat;
        this.odKad = odKad;
        this.doKad = doKad;
        this.tekst = tekst;
        this.prodavac_id = prodavac_id;
        this.artikal_id = artikal_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProcenat() {
        return procenat;
    }

    public void setProcenat(int procenat) {
        this.procenat = procenat;
    }

    public LocalDate getOdKad() {
        return odKad;
    }

    public void setOdKad(LocalDate odKad) {
        this.odKad = odKad;
    }

    public LocalDate getDoKad() {
        return doKad;
    }

    public void setDoKad(LocalDate doKad) {
        this.doKad = doKad;
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public int getProdavac_id() {
        return prodavac_id;
    }

    public void setProdavac_id(int prodavac_id) {
        this.prodavac_id = prodavac_id;
    }

    public int getArtikal_id() {
        return artikal_id;
    }

    public void setArtikal_id(int artikal_id) {
        this.artikal_id = artikal_id;
    }

    @Override
    public String toString() {
        return "Akcija{" +
                "procenat=" + procenat +
                ", odKad=" + odKad +
                ", doKad=" + doKad +
                ", tekst='" + tekst + '\'' +
                '}';
    }
}
