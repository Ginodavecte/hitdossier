package sample.Model;


import sample.Controller.Controller_ArtiestLijst;
import sample.DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ModelArtiest {
    protected int geselecteerdeID ;

    public void setGeselecteerdeID(int geselecteerdeID) {
        this.geselecteerdeID = geselecteerdeID;
    }

    public class Artiestinfo{
        protected String naam;
        protected String[] singles;

        public Artiestinfo(String naam, ArrayList<String> singles) {
            this.naam = naam;
            this.singles = new String[singles.size()];
            for(int i =0 ; i<singles.size(); i++){
                this.singles[i] = singles.get(i);
            }
        }

        public String getNaam() {
            return naam;
        }

        public String[] getSingles() {
            return singles;
        }

        //@Override
        //public String toString(){
        //    return naam;
        //}

    }

    public Artiestinfo getArtiestInfo(){
        String naam;
        ArrayList<String> singles = new ArrayList();
        System.out.println(geselecteerdeID);
        //1e query voor ArtiestenInfo naam
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn= DB.getConnection();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT naam FROM `artiest` WHERE `id`='"+geselecteerdeID+"'");
            // Resultaat op het scherm
            rs.next();
            naam = rs.getString(1);
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            throw new Error("Fout bij uitvoeren query.");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) {
                } // ignore
                rs = null;
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                } // ignore
                stmt = null;
            }
        }

        //2e query voor ArtiestenInfo voor singles[]
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT titel FROM `single` WHERE `artiest`='"+geselecteerdeID+"'");
            // Resultaat op het scherm
            while (rs.next()) {
               singles.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            throw new Error("Fout bij uitvoeren query.");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) {
                } // ignore
                rs = null;
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                } // ignore
                stmt = null;
            }
        }

        return new Artiestinfo(naam, singles);

    }


    private static sample.Model.ModelArtiest ourInstance = new sample.Model.ModelArtiest();

    public static sample.Model.ModelArtiest getInstance() {
        return ourInstance;
    }

    public class ArtiestLijstItem {
        protected int id;
        protected String naam;

        public ArtiestLijstItem(int id, String naam) {

            this.id = id;
            this.naam = naam;
        }

        public int getId() {
            return id;
        }

        public String getNaam() {
            return naam;
        }



        @Override
        public String toString() {
            return naam;
        }
    }

    class ArtiestLijstIterator implements Iterator<ArtiestLijstItem> {

        protected ResultSet rs;
        protected Statement stmt;

        protected void readNext() {
            // Is er een resultset?
            // Zo ja, dan volgende lezen (rs.next())
            // Is er nog resultaat? Dan stoppen (return)
            try {
                if ((rs!=null) && (rs.next())) {
                    return; // Er is resultaat, dus stoppen zodat resources nog niet gesloten worden.
                }
            } catch (SQLException e) {
            }

            // Geen resultset (als gevolg van error) of geen resultaat meer?
            // Dan alles netjes sluiten
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) {
                } // ignore
                rs = null;
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) {
                } // ignore
                stmt = null;
            }
        }


        public ArtiestLijstIterator(String zoekArtiest) {


            Connection conn= DB.getConnection();

            try {
                stmt = conn.createStatement();
                String where="";
                if(zoekArtiest != null){
                where="WHERE naam LIKE '%"+zoekArtiest+"%'" ;
                }
                    rs = stmt.executeQuery("SELECT id, naam FROM artiest " + where + " ORDER BY naam");

            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
                throw new Error("Fout bij uitvoeren query.");
            } finally {
                readNext();
            }
        }

        @Override
        public boolean hasNext() {
            return rs!=null;
        }

        @Override
        public ArtiestLijstItem next() {
            if (rs==null) {
                throw new NoSuchElementException();
            }
            ArtiestLijstItem artiest=null;
            try {
                artiest = new ArtiestLijstItem(rs.getInt(1), rs.getString(2));
            } catch (SQLException e) {
                e.printStackTrace();
                throw new Error("Resultaat uit resultset halen mislukt.");
            }
            readNext();
            return artiest;
        }
    }

    public Iterable<ArtiestLijstItem> getArtiestlijst(String zoekArtiest) {
        return new Iterable<ArtiestLijstItem>() {
            @Override
            public Iterator<ArtiestLijstItem> iterator() {
                return new ArtiestLijstIterator(zoekArtiest);
            }
        };
    }

}
