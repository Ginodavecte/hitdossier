package sample.Model;

import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ModelTop40 {

    protected int week;
    protected int jaar;

    public void setWeek(int week) {
        System.out.println("WEEK :"+ week);
        this.week = week;
    }

    public void setJaar(int jaar)
    {
        System.out.println("Jaar :"+jaar);
        this.jaar = jaar;
    }

    // Model voor de tabel
    public class TableRowTop40 {

        protected String  titel, artiest;
        protected int positie;

        public TableRowTop40(int positie, String titel, String artiest) {
            this.titel = titel;
            this.artiest = artiest;
            this.positie = positie;
        }

        public String getTitel() {
            return titel;
        }

        public String getArtiest() {
            return artiest;
        }

        public int getPositie() {
            return positie;
        }
    }

    protected class Top40Iterator implements Iterator<TableRowTop40> {

        protected ResultSet rs;

        public Top40Iterator() {
            // Query opbouwen
//            String where = "";
//            if (week >= 0) {
//                where = "week=\"" + week + "\"";
//            }
//            if (jaar >=0) {
//                if (where.length() > 0) where += " AND ";
//                where += "jaar=\"" + jaar + "\"";
//            }
//            if (where.length() > 0) where = " WHERE " + where;
//SELECT hitlijst_notering.positie,single.titel,artiest.naam
// FROM `hitlijst_editie` JOIN hitlijst ON (hitlijst_editie.hitlijst = hitlijst.id) JOIN hitlijst_notering ON (hitlijst_editie.id = hitlijst_notering.hitlijst_editie) JOIN single ON (hitlijst_notering.single = single.id) JOIN artiest ON (artiest.id = single.artiest)
// WHERE hitlijst.naam = "Top 40" AND hitlijst_editie.jaar = '1995' AND hitlijst_editie.week = '1'
            String query =  "SELECT hitlijst_notering.positie,single.titel,artiest.naam " +
                    "FROM `hitlijst_editie` " +
                    "JOIN hitlijst ON (hitlijst_editie.hitlijst = hitlijst.id) " +
                    "JOIN hitlijst_notering ON (hitlijst_editie.id = hitlijst_notering.hitlijst_editie) " +
                    "JOIN single ON (hitlijst_notering.single = single.id) " +
                    "JOIN artiest ON (artiest.id = single.artiest) " +
                    "WHERE hitlijst.naam = \"Top 40\" " +
                    "AND hitlijst_editie.jaar = "+jaar+" " +
                    "AND hitlijst_editie.week = "+week+" " +
                    "ORDER BY `hitlijst_notering`.`positie` ASC";
            System.out.println("Query: " + query);

            // Query uitvoeren
            Connection conn= DB.getConnection();
            Statement stmt = null;
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery(query);
                hasData=rs.next(); // Alvast één rij lezen

            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());

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

                throw new Error("Fout bij uitvoeren query.");

            } finally {

            }
        }

        protected boolean hasData;

        @Override
        public boolean hasNext() {
            return hasData;
        }

        @Override
        public TableRowTop40 next() {
            try {;
                TableRowTop40 result=new TableRowTop40(rs.getInt(1), rs.getString(2), rs.getString(3));
                hasData=rs.next();
                return result;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public Iterable<TableRowTop40> getTop40() {

        return new Iterable<TableRowTop40>() {
            @Override
            public Iterator<TableRowTop40> iterator() {
                return new Top40Iterator();
            }
        };

    }

}