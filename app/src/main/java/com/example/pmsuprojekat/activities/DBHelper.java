package com.example.pmsuprojekat.activities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Akcija;
import model.Artikal;
import model.Korisnik;
import model.Kupac;
import model.Porudzbina;
import model.Prodavac;
import model.Stavka;


public class  DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME="Login.db";

    public DBHelper(Context context) {
        super(context, "Login.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table users(id INTEGER PRIMARY KEY AUTOINCREMENT,ime TEXT, prezime TEXT, username TEXT NOT NULL, password TEXT, uloga TEXT, blokiran boolean)");
        MyDB.execSQL("create Table kupci(id INTEGER PRIMARY KEY AUTOINCREMENT ,ime TEXT, prezime TEXT, username TEXT NOT NULL, password TEXT, adresa TEXT)");
        MyDB.execSQL("create Table prodavci(id INTEGER PRIMARY KEY AUTOINCREMENT ,ime TEXT, prezime TEXT, username TEXT NOT NULL, password TEXT, poslujeOd LocaleDate, email TEXT, adresa TEXT, naziv TEXT )");
        MyDB.execSQL("create Table akcije(id INTEGER PRIMARY KEY, procenat INTEGER, odKad LocaleDate, doKad LocaleDate, tekst TEXT, prodavac_id INTEGER, artikal_id INTEGER, FOREIGN KEY(prodavac_id) REFERENCES prodavci(id), FOREIGN KEY(artikal_id) REFERENCES artikli(id))");
        MyDB.execSQL("create Table stavke(id INTEGER PRIMARY KEY, kolicina INTEGER, artikal_id INTEGER, FOREIGN KEY(artikal_id) REFERENCES artikli(id))");

        MyDB.execSQL("Insert into users(id,ime,prezime,username,password,uloga,blokiran) VALUES (1,'milorad','miloradovic','miloradm','321','administrator',0)");
        MyDB.execSQL("Insert into users(id,ime,prezime,username,password,uloga,blokiran) VALUES (2,'milan','milanovic','milanm','321','administrator',0)");
        MyDB.execSQL("Insert into users(id,ime,prezime,username,password,uloga,blokiran) VALUES (3,'nenad','nenadovic','nenadn','123','prodavac',false)");
        MyDB.execSQL("Insert into users(id,ime,prezime,username,password,uloga,blokiran) VALUES (4,'ivan','ivanovic','ivani','123','prodavac',0)");
        MyDB.execSQL("Insert into users(id,ime,prezime,username,password,uloga,blokiran) VALUES (5,'predrag','predragovic','predragp','123','prodavac',0)");
        MyDB.execSQL("Insert into users(id,ime,prezime,username,password,uloga,blokiran) VALUES (6,'miodrag','miodragovic','miodragm','123','prodavac',0)");
        MyDB.execSQL("Insert into users(id,ime,prezime,username,password,uloga,blokiran) VALUES (7,'stefan','stefanovic','stefans','123','kupac',0)");

        MyDB.execSQL("Insert into kupci(id,ime,prezime,username,password, adresa) VALUES (1,'stefan','stefanovic','stefans','123','nenadska 50')");

        MyDB.execSQL("Insert into prodavci(id,ime,prezime,username,password,poslujeOd,email,adresa,naziv) VALUES (1,'nenad','nenadovic','nenadn','123','2021-04-04','nenad@nenadovic.com','nenadska 50','Fruity')");
        MyDB.execSQL("Insert into prodavci(id,ime,prezime,username,password,poslujeOd,email,adresa,naziv) VALUES (2,'predrag','predragovic','predragp','123','2021-05-05','predrag@predragovic.com','fruskogorska 32','GlassDOO')");
        MyDB.execSQL("Insert into prodavci(id,ime,prezime,username,password,poslujeOd,email,adresa,naziv) VALUES (3,'ivan','ivanovic','ivani','123','2021-03-04','ivan@ivanovic.com','ivanoviceva 40','Shop&GO')");
        MyDB.execSQL("Insert into prodavci(id,ime,prezime,username,password,poslujeOd,email,adresa,naziv) VALUES (4,'miodrag','miodragovic','miodragm','123','2021-04-06','miodragovic@miki.com','Miodragska 32','Shuffler')");

        MyDB.execSQL("create Table artikli(id INTEGER PRIMARY KEY AUTOINCREMENT, naziv TEXT, opis TEXT, cena DOUBLE, putanja TEXT,prodavac_id INTEGER, image BLOB, akcija_id INTEGER, FOREIGN KEY (prodavac_id) REFERENCES prodavci (id))");

        MyDB.execSQL("create Table porudzbine(id INTEGER PRIMARY KEY AUTOINCREMENT, satnica LocalDate, dostavljeno boolean, ocena INTEGER, komentar TEXT, anonimanKomentar boolean, arhiviranKomentar boolean, " +
                "kupac_id INTEGER NOT NULL, stavka_id INTEGER NOT NULL, FOREIGN KEY(kupac_id) REFERENCES kupci(id),FOREIGN KEY(stavka_id) REFERENCES stavke(id))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {
        MyDB.execSQL("drop Table if exists users");
        MyDB.execSQL("drop Table if exists kupci");
        MyDB.execSQL("drop Table if exists prodavci");
        MyDB.execSQL("drop Table if exists artikli");
        MyDB.execSQL("drop Table if exists akcije");
        MyDB.execSQL("drop Table if exists stavke");
        MyDB.execSQL("drop Table if exists porudzbine");

    }

    public Boolean insertData(String ime, String prezime, String username, String password, String uloga, boolean blokiran){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ime", ime);
        contentValues.put("prezime", prezime);
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("uloga", uloga);
        contentValues.put("blokiran",false);

        long result = MyDB.insert("users", null, contentValues);
        if(result==-1) return false;
        else
            return true;

    }

    public Boolean insertKupci(String ime, String prezime, String username, String password, String adresa){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ime", ime);
        contentValues.put("prezime", prezime);
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("adresa", adresa);

        long result = MyDB.insert("kupci", null, contentValues);
        if(result==-1) return false;
        else
            return true;

    }

    public void insertStavke(Stavka stavka){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", stavka.getId());
        contentValues.put("kolicina", stavka.getKolicina());
        contentValues.put("artikal_id", stavka.getArtikal_id());

        MyDB.insert("stavke", null, contentValues);
    }

    public void insertPorudzbinu(Porudzbina porudzbina){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("satnica", String.valueOf(porudzbina.getSatnica()));
        contentValues.put("dostavljeno", porudzbina.isDostavljeno());
        contentValues.put("ocena", porudzbina.getOcena());
        contentValues.put("komentar", porudzbina.getKomentar());
        contentValues.put("anonimanKomentar", porudzbina.isAnonimanKomentar());
        contentValues.put("arhiviranKomentar", porudzbina.isArhiviranKomentar());
        contentValues.put("kupac_id", porudzbina.getKupac_id());
        contentValues.put("stavka_id", porudzbina.getStavka_id());

        MyDB.insert("porudzbine", null, contentValues);

    }

    public Boolean insertProdavci(String ime, String prezime, String username, String password, LocalDate poslujeOd, String email, String adresa, String naziv){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ime", ime);
        contentValues.put("prezime", prezime);
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("poslujeOd", String.valueOf(poslujeOd));
        contentValues.put("email", email);
        contentValues.put("adresa", adresa);
        contentValues.put("naziv", naziv);

        long result = MyDB.insert("prodavci", null, contentValues);
        if(result==-1) return false;
        else
            return true;

    }


    public void insertArtikal(Artikal artikal){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("naziv", artikal.getNaziv());
        contentValues.put("opis", artikal.getOpis());
        contentValues.put("cena", artikal.getCena());
        contentValues.put("putanja", artikal.getPutanjaSlike());
        contentValues.put("prodavac_id", artikal.getProdavac_id());
        contentValues.put("image", artikal.getImage());

        MyDB.insert("artikli", null, contentValues);

    }

    public void insertAkcija(Akcija akcija){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", akcija.getId());
        contentValues.put("procenat", akcija.getProcenat());
        contentValues.put("odKad", String.valueOf(akcija.getOdKad()));
        contentValues.put("doKad", String.valueOf(akcija.getDoKad()));
        contentValues.put("tekst", akcija.getTekst());
        contentValues.put("prodavac_id", akcija.getProdavac_id());
        contentValues.put("artikal_id", akcija.getArtikal_id());


        MyDB.insert("akcije", null, contentValues);

    }

    public Korisnik findKorisnik(String usernamee){
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from users where username=?", new String[] {usernamee});
        if(cursor.getCount() == 1){
            cursor.moveToFirst();
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String ime = cursor.getString(1);
            String prezime = cursor.getString(2);
            String username = cursor.getString(3);
            String password = cursor.getString(4);
            String uloga = cursor.getString(5);
            boolean blokiran = Boolean.parseBoolean(cursor.getString(6));

            Korisnik korisnik = new Korisnik(id,ime,prezime,username,password,uloga,blokiran);

            return korisnik;
        }
        else{
            return null;
        }

    }

    public Kupac findKupca(String username){
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from kupci where username=?", new String[] {username});
        if(cursor.getCount() == 1){
            cursor.moveToFirst();
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String ime = cursor.getString(1);
            String prezime = cursor.getString(2);
            String usernamee = cursor.getString(3);
            String password = cursor.getString(4);
            String adresa = cursor.getString(5);

            Kupac kupac = new Kupac(id,ime,prezime,username,password,adresa);

            return kupac;
        }
        else{
            return null;
        }

    }

    public Kupac findKupca2(String kupacId){
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from kupci where id=?", new String[] {String.valueOf(kupacId)});
        if(cursor.getCount() == 1){
            cursor.moveToFirst();
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String ime = cursor.getString(1);
            String prezime = cursor.getString(2);
            String username = cursor.getString(3);
            String password = cursor.getString(4);
            String adresa = cursor.getString(5);

            Kupac kupac = new Kupac(id,ime,prezime,username,password,adresa);

            return kupac;
        }
        else{
            return null;
        }

    }

    public Artikal findArtikal(Integer artikalId){
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from artikli where id=?", new String[] {String.valueOf(artikalId)});
        if(cursor.getCount() == 1){
            cursor.moveToFirst();
            Integer id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String naziv = cursor.getString(1);
            String opis = cursor.getString(2);
            double cena = cursor.getDouble(3);
            String putanja = cursor.getString(4);
            Integer prodavac_id = cursor.getInt(5);
            byte[] image = cursor.getBlob(6);
            Integer akcija_id = cursor.getInt(7);

            Artikal artikal = new Artikal(id,naziv,opis,cena,putanja,prodavac_id,image,akcija_id);

            return artikal;
        }
        else{
            return null;
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Akcija findAkcija(int akcijaId){
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from akcije where id=?", new String[] {String.valueOf(akcijaId)});
        if(cursor.getCount() == 1){
            cursor.moveToFirst();
            int id = cursor.getInt(0);
            int procenat = cursor.getInt(1);
            LocalDate odKad = LocalDate.parse(cursor.getString(2));
            LocalDate doKad = LocalDate.parse(cursor.getString(3));
            String tekst = cursor.getString(4);
            int prodavac_id = cursor.getInt(5);
            int artikal_id = cursor.getInt(6);
            Akcija akcija = new Akcija(id,procenat,odKad,doKad,tekst,prodavac_id,artikal_id);

            return akcija;
        }
        else{
            return null;
        }

    }

    public Stavka findStavka(Integer stavka_id){
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from stavke where id=?", new String[] {String.valueOf(stavka_id)});
        if(cursor.getCount() == 1){
            cursor.moveToFirst();
            Integer id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            Integer kolicina = cursor.getInt(1);
            Integer artikal_id = cursor.getInt(2);
            Stavka stavka = new Stavka(id,kolicina,artikal_id);

            return stavka;
        }
        else{
            return null;
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public Prodavac findProdavac(String username){
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from prodavci where username=?", new String[] {username});
        if(cursor.getCount() == 1){
            cursor.moveToFirst();
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String ime = cursor.getString(1);
            String prezime = cursor.getString(2);
            String usernamee = cursor.getString(3);
            String password = cursor.getString(4);
            LocalDate poslujeOd = LocalDate.parse(cursor.getString(5));
            String email = cursor.getString(6);
            String adresa = cursor.getString(7);
            String naziv = cursor.getString(8);

            Prodavac prodavac = new Prodavac(id,ime,prezime,username,password,poslujeOd,email,adresa,naziv);

            return prodavac;
        }
        else{
            return null;
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<Porudzbina> getPorudzbineKupca(String kupacId){
        SQLiteDatabase MyDB = this.getReadableDatabase();
        List<Porudzbina> porudzbine = new ArrayList<>();
        Cursor cursor = MyDB.rawQuery("select * from porudzbine where kupac_id=? and komentar like 'Nije unesen' and dostavljeno=true", new String[] {kupacId});
        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                LocalDate satnica = LocalDate.parse(cursor.getString(1));
                boolean dostavljeno = Boolean.parseBoolean(cursor.getString(2));
                int ocena = cursor.getInt(3);
                String komentar = cursor.getString(4);
                boolean anonimanKomentar = Boolean.parseBoolean(cursor.getString(5));
                boolean arhiviranKomentar = Boolean.parseBoolean(cursor.getString(6));
                Integer kupac_id = cursor.getInt(7);
                Integer stavka_id = cursor.getInt(8);
                porudzbine.add(new Porudzbina(id,satnica,dostavljeno,ocena,komentar,anonimanKomentar,arhiviranKomentar,kupac_id,stavka_id));

            }while(cursor.moveToNext());
        }
        cursor.close();
        return porudzbine;
    }

    public List<Artikal> getArtikliProdavca(String prodavacId){
        //String sql = "select * from artikli where prodavac_id=?";
        SQLiteDatabase MyDB = this.getReadableDatabase();
        List<Artikal> artikli = new ArrayList<>();
        Cursor cursor = MyDB.rawQuery("select * from artikli where prodavac_id=?", new String[] {prodavacId});
        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                String naziv = cursor.getString(1);
                String opis = cursor.getString(2);
                Double cena = Double.parseDouble(cursor.getString(3));
                String putanja = cursor.getString(4);
                Integer prodavac_id = cursor.getInt(5);
                byte[] image = cursor.getBlob(6);
                int akcija_id = cursor.getInt(5);
                artikli.add(new Artikal(id,naziv,opis,cena,putanja,prodavac_id,image,akcija_id));
            }while(cursor.moveToNext());
        }
        cursor.close();
        return artikli;

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<Akcija> getAkcijeProdavca(String prodavacId){
        SQLiteDatabase MyDB = this.getReadableDatabase();
        List<Akcija> akcije = new ArrayList<>();
        Cursor cursor = MyDB.rawQuery("select * from akcije where prodavac_id=?", new String[] {prodavacId});
        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                Integer procenat = cursor.getInt(1);
                LocalDate odKad = LocalDate.parse(cursor.getString(2));
                LocalDate doKad = LocalDate.parse(cursor.getString(3));
                String tekst = cursor.getString(4);
                Integer prodavac_id = cursor.getInt(5);
                Integer artikal_id = cursor.getInt(6);
                akcije.add(new Akcija(id,procenat,odKad,doKad,tekst,prodavac_id,artikal_id));

            }while(cursor.moveToNext());
        }
        cursor.close();
        return akcije;
    }


    public Boolean checkusername(String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ?", new String[] {username});
        if(cursor.getCount() > 0)
            return true;
        else
            return false;
    }

        public Boolean checkusernamepassword(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ? and password = ?", new String[] {username,password});
        if(cursor.getCount() > 0)
            return true;
        else
            return false;

        }

        //CRUD OPERACIJE ZA ARTIKLE ---

        public List<Artikal> getArtikli(){
            String sql = "select * from artikli ";
            SQLiteDatabase MyDB = this.getReadableDatabase();
            List<Artikal> artikli = new ArrayList<>();
            Cursor cursor = MyDB.rawQuery(sql,null);
            if(cursor.moveToFirst()){
                do{
                    int id = Integer.parseInt(cursor.getString(0));
                    String naziv = cursor.getString(1);
                    String opis = cursor.getString(2);
                    Double cena = Double.parseDouble(cursor.getString(3));
                    String putanja = cursor.getString(4);
                    artikli.add(new Artikal(id,naziv,opis,cena,putanja));
                }while(cursor.moveToNext());
            }
            cursor.close();
            return artikli;

        }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<Porudzbina> getPorudzbineNearhivirani(){
        String sql = "select * from porudzbine where arhiviranKomentar=false and anonimanKomentar=false and komentar NOT LIKE 'Nije unesen'";
        SQLiteDatabase MyDB = this.getReadableDatabase();
        List<Porudzbina> porudzbine = new ArrayList<>();
        Cursor cursor = MyDB.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                LocalDate satnica = LocalDate.parse(cursor.getString(1));
                boolean dostavljeno = Boolean.parseBoolean(cursor.getString(2));
                int ocena = cursor.getInt(3);
                String komentar = cursor.getString(4);
                boolean anonimanKomentar = Boolean.parseBoolean(cursor.getString(5));
                boolean arhiviranKomentar = Boolean.parseBoolean(cursor.getString(6));
                Integer kupac_id = cursor.getInt(7);
                Integer stavka_id = cursor.getInt(8);
                porudzbine.add(new Porudzbina(id,satnica,dostavljeno,ocena,komentar,anonimanKomentar,arhiviranKomentar,kupac_id,stavka_id));
            }while(cursor.moveToNext());
        }
        cursor.close();
        return porudzbine;

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<Porudzbina> getPorudzbineNearhiviraniAnonimni(){
        String sql = "select * from porudzbine where arhiviranKomentar=false and anonimanKomentar=true and komentar NOT LIKE 'Nije unesen'";
        SQLiteDatabase MyDB = this.getReadableDatabase();
        List<Porudzbina> porudzbine = new ArrayList<>();
        Cursor cursor = MyDB.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                LocalDate satnica = LocalDate.parse(cursor.getString(1));
                boolean dostavljeno = Boolean.parseBoolean(cursor.getString(2));
                int ocena = cursor.getInt(3);
                String komentar = cursor.getString(4);
                boolean anonimanKomentar = Boolean.parseBoolean(cursor.getString(5));
                boolean arhiviranKomentar = Boolean.parseBoolean(cursor.getString(6));
                Integer kupac_id = cursor.getInt(7);
                Integer stavka_id = cursor.getInt(8);
                porudzbine.add(new Porudzbina(id,satnica,dostavljeno,ocena,komentar,anonimanKomentar,arhiviranKomentar,kupac_id,stavka_id));
            }while(cursor.moveToNext());
        }
        cursor.close();
        return porudzbine;

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<Porudzbina> getPorudzbine(){
        String sql = "select * from porudzbine where dostavljeno=false";
        SQLiteDatabase MyDB = this.getReadableDatabase();
        List<Porudzbina> porudzbine = new ArrayList<>();
        Cursor cursor = MyDB.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                LocalDate satnica = LocalDate.parse(cursor.getString(1));
                boolean dostavljeno = Boolean.parseBoolean(cursor.getString(2));
                int ocena = cursor.getInt(3);
                String komentar = cursor.getString(4);
                boolean anonimanKomentar = Boolean.parseBoolean(cursor.getString(5));
                boolean arhiviranKomentar = Boolean.parseBoolean(cursor.getString(6));
                Integer kupac_id = cursor.getInt(7);
                Integer stavka_id = cursor.getInt(8);
                porudzbine.add(new Porudzbina(id,satnica,dostavljeno,ocena,komentar,anonimanKomentar,arhiviranKomentar,kupac_id,stavka_id));
            }while(cursor.moveToNext());
        }
        cursor.close();
        return porudzbine;

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<Stavka> getStavke(){
        String sql = "select * from stavke ";
        SQLiteDatabase MyDB = this.getReadableDatabase();
        List<Stavka> stavke = new ArrayList<>();
        Cursor cursor = MyDB.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                Integer kolicina = cursor.getInt(1);
                Integer artikal_id = cursor.getInt(2);

                stavke.add(new Stavka(id,kolicina,artikal_id));
            }while(cursor.moveToNext());
        }
        cursor.close();
        return stavke;

    }


    public List<Korisnik> getKorisnike(){
            String sql = "select * from users";
            SQLiteDatabase MyDB = this.getReadableDatabase();
            List<Korisnik> korisnici = new ArrayList<>();
            Cursor cursor = MyDB.rawQuery(sql,null);
            if(cursor.moveToFirst()){
                do{
                    int id = Integer.parseInt(cursor.getString(0));
                    String ime = cursor.getString(1);
                    String prezime = cursor.getString(2);
                    String username = cursor.getString(3);
                    String password = cursor.getString(4);
                    String uloga = cursor.getString(5);
                    Boolean blokiran = Boolean.parseBoolean(cursor.getString(6));
                    korisnici.add(new Korisnik(id,ime,prezime,username,password,uloga,blokiran));
                }while(cursor.moveToNext());
            }
            cursor.close();
            return korisnici;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<Akcija> getAkcije(){
        String sql = "select * from akcije";
        SQLiteDatabase MyDB = this.getReadableDatabase();
        List<Akcija> akcije = new ArrayList<>();
        Cursor cursor = MyDB.rawQuery(sql,null);
        if(cursor.moveToFirst()){
            do{
                int id = Integer.parseInt(cursor.getString(0));
                Integer procenat = cursor.getInt(1);
                LocalDate odKad = LocalDate.parse(cursor.getString(2));
                LocalDate doKad = LocalDate.parse(cursor.getString(3));
                String tekst = cursor.getString(4);
                Integer prodavac_id = cursor.getInt(5);
                Integer artikal_id = cursor.getInt(6);
                akcije.add(new Akcija(id,procenat,odKad, doKad, tekst, prodavac_id, artikal_id));
            }while(cursor.moveToNext());
        }
        cursor.close();
        return akcije;
    }


    public void updateArtikal(Artikal artikal){
        ContentValues contentValues = new ContentValues();
        contentValues.put("naziv", artikal.getNaziv());
        contentValues.put("opis", artikal.getOpis());
        contentValues.put("cena", artikal.getCena());
        contentValues.put("putanja", artikal.getPutanjaSlike());
        SQLiteDatabase MyDB = this.getWritableDatabase();
        MyDB.update("artikli",contentValues, "id = ?", new String[] {String.valueOf(artikal.getId())});

    }

    public void postaviAkciju(Artikal artikal){
        ContentValues contentValues = new ContentValues();
        contentValues.put("akcija_id", artikal.getAkcija_id());
        SQLiteDatabase MyDB = this.getWritableDatabase();
        MyDB.update("artikli",contentValues, "id = ?", new String[] {String.valueOf(artikal.getId())});

    }

    public void skiniAkciju(Artikal artikal){
        ContentValues contentValues = new ContentValues();
        contentValues.put("akcija_id", (Integer) null);
        SQLiteDatabase MyDB = this.getWritableDatabase();
        MyDB.update("akcije",contentValues, "id = ?", new String[] {String.valueOf(artikal.getId())});

    }

        public void updateKorisnik(Korisnik korisnik){
        ContentValues contentValues = new ContentValues();
        contentValues.put("blokiran", korisnik.isBlokiran());
        SQLiteDatabase MyDB = this.getWritableDatabase();
        MyDB.update("users",contentValues, "id = ?", new String[] {String.valueOf(korisnik.getId())});

    }

        public void updatePorudzbinu(Porudzbina porudzbina){
            ContentValues contentValues = new ContentValues();
            contentValues.put("komentar", porudzbina.getKomentar());
            contentValues.put("ocena", porudzbina.getOcena());
            contentValues.put("anonimanKomentar", porudzbina.isAnonimanKomentar());
            SQLiteDatabase MyDB = this.getWritableDatabase();
            MyDB.update("porudzbine",contentValues, "id = ?", new String[] {String.valueOf(porudzbina.getId())});

    }

        public void arhivirajKomentar(Porudzbina porudzbina){
            ContentValues contentValues = new ContentValues();
            contentValues.put("arhiviranKomentar", porudzbina.isArhiviranKomentar());
            SQLiteDatabase MyDB = this.getWritableDatabase();
            MyDB.update("porudzbine",contentValues, "id = ?", new String[] {String.valueOf(porudzbina.getId())});

    }


    public void dostaviPorudzbinu(Porudzbina porudzbina){
        ContentValues contentValues = new ContentValues();
        contentValues.put("dostavljeno", porudzbina.isDostavljeno());
        SQLiteDatabase MyDB = this.getWritableDatabase();
        MyDB.update("porudzbine",contentValues, "id = ?", new String[] {String.valueOf(porudzbina.getId())});

    }


    public void deleteArtikal(int id){
            SQLiteDatabase MyDB = this.getWritableDatabase();
            MyDB.delete("artikli", "id = ?", new String[] {String.valueOf(id)});
    }

    public void deleteAkcija(int id){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        MyDB.delete("akcije", "id = ?", new String[] {String.valueOf(id)});
    }

        public Cursor viewData(){
            SQLiteDatabase MyDB = this.getReadableDatabase();
            String query = "Select * from prodavci";
            Cursor cursor = MyDB.rawQuery(query, null);
            return cursor;
    }


}
