package sample.Model;

import sample.DB;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Gino on 20-6-2016.
 */
public class ModelSingle {
    protected int geselecteerdeID ;

    public void setGeselecteerdeID(int geselecteerdeID) {
        this.geselecteerdeID = geselecteerdeID;
    }

    public class Singleinfo{
        protected String titel;
        protected String artiest;
        protected int jaar;
        protected int aantal_wekentop40;
        protected int hoogst_posTop40;

        public Singleinfo(String titel,String artiest, int jaar, int aantal_wekentop40,int hoogst_posTop40) {
            this.titel = titel;
            this.artiest = artiest;
            this.jaar = jaar;
            this.aantal_wekentop40 = aantal_wekentop40;
            this.hoogst_posTop40 = hoogst_posTop40;
        }

        public String getTitel() {
            return titel;
        }

        public String getArtiest() {return artiest;}

        public int getJaar(){return jaar;}

        public int getAantal_wekentop40(){return aantal_wekentop40;}

        public int getHoogst_posTop40(){return hoogst_posTop40;}

        //@Override
        //public String toString(){
        //    return jaar;
        //}

    }

    public Singleinfo getSingleInfo(){
        String titel;
        String artiest;
        int jaar;
        int aantal_wekentop40;
        int hoogst_posTop40;
        System.out.println(geselecteerdeID);
        //1e query voor ArtiestenInfo naam
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn= DB.getConnection();
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery( "SELECT single.titel,artiest.naam,MIN(jaar) " +
                    "FROM `single`,`artiest`,`hitlijst_editie`,`hitlijst_notering` " +
                    "WHERE `artiest`.`id`= `single`.`artiest` " +
                    "AND single.id = hitlijst_notering.single " +
                    "AND hitlijst_notering.hitlijst_editie = hitlijst_editie.id " +
                    "AND  `single`.`id`='"+geselecteerdeID+"'");//NIEUWE QUERY MAKEN aantalwekentop40
            // Resultaat op het scherm
            rs.next();
            titel = rs.getString(1);
            artiest = rs.getString(2);
            jaar = rs.getInt(3);
            //aantal_wekentop40= rs.getInt(4);
            //System.out.println(titel);
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

        //2e query voor aantal weken in de TOP-40
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery( "SELECT count(single) " +
                                    "FROM `single`,`hitlijst_editie`,`hitlijst_notering` " +
                                    "WHERE `single`.`id` = `hitlijst_notering`.`single` " +
                                    "AND `hitlijst_editie`.`id` = `hitlijst_notering`.`hitlijst_editie` " +
                                    "AND `hitlijst_editie`.`hitlijst` = 1 " +
                                    "AND  `single`.`id`='"+geselecteerdeID+"'");
            // Resultaat op het scherm
            rs.next();
            aantal_wekentop40= rs.getInt(1);
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
        //3e query voor hoogst behaalde postitie in de TOP-40
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery( "   SELECT MIN(positie) " +
                                        "FROM `hitlijst_notering`,`hitlijst_editie`,`single` " +
                                        "WHERE single.id = hitlijst_notering.single " +
                                        "AND single.id ='"+geselecteerdeID+"'");
            // Resultaat op het scherm
            rs.next();
            hoogst_posTop40= rs.getInt(1);
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

        return new Singleinfo(titel,artiest,jaar,aantal_wekentop40,hoogst_posTop40);//

    }


    private static sample.Model.ModelSingle ourInstance = new sample.Model.ModelSingle();

    public static sample.Model.ModelSingle getInstance() {
        return ourInstance;
    }

    public class SingleLijstItem {
        protected int id;
        protected String titel;
        protected String naam;

        public SingleLijstItem(int id,String titel, String naam) {

            this.id = id;
            this.titel = titel;
            this.naam = naam;
        }

        public int getId(){return id;}

        public String getTitel() {
            return titel;
        }

        public String getNaam() {
            return naam;
        }



        @Override
        public String toString() {
            return titel;
        }

    }

    class SingleLijstIterator implements Iterator<SingleLijstItem> {

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


        public SingleLijstIterator(String zoekSingle) {


            Connection conn= DB.getConnection();

            try {
                stmt = conn.createStatement();
                String and="";
                if(zoekSingle != null){
                    and="AND `single`.`titel` LIKE '%"+zoekSingle+"%'" ;
                }
                rs = stmt.executeQuery("SELECT single.id,single.titel,artiest.naam " +
                        "               FROM `artiest`,`single` " +
                        "               WHERE `artiest`.`id`= `single`.`artiest`" +
                        "               "+ and  +" ORDER BY `artiest`.`naam` ");

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
        public SingleLijstItem next() {
            if (rs==null) {
                throw new NoSuchElementException();
            }
            SingleLijstItem single=null;
            try {
                single = new SingleLijstItem(rs.getInt(1),rs.getString(2), rs.getString(3));
            } catch (SQLException e) {
                e.printStackTrace();
                throw new Error("Resultaat uit resultset halen mislukt.");
            }
            readNext();
            return single;
        }
    }

    public Iterable<SingleLijstItem> getSinglelijst(String zoekSingle) {
        return new Iterable<SingleLijstItem>() {
            @Override
            public Iterator<SingleLijstItem> iterator() {
                return new SingleLijstIterator(zoekSingle);
            }
        };
    }
}
