package net.lennartolsen.eventodense.server.datastore;

import java.lang.reflect.Executable;
import java.sql.*;

/**
 * Created by lennartolsen on 26/10/2016.
 */
public class PostgresHelper {

    private Connection c;

    public PostgresHelper(){
        setConnection();
    }

    public PostgresHelper(boolean autoCommit){
        setConnection();
        try {
            c.setAutoCommit(autoCommit);
        } catch (SQLException e){
            handleException(e);
        }
    }


    private Statement getStatement() {
        Statement stmt = null;
        try {
            stmt = c.createStatement();
        } catch (SQLException e){
            handleException(e);
        }
        return stmt;
    }

    public PreparedStatement getPreparedStatement(String sql) {
        PreparedStatement stmt = null;
        try {
            stmt = c.prepareStatement(sql);
        } catch (SQLException e){
            handleException(e);
        }
        return stmt;
    }

    public int commitStatement(PreparedStatement stmt){
        int updated = 0;
        try{
            updated = stmt.executeUpdate();
            c.commit();
        } catch (SQLException e) {
            handleException(e);
        }
        return updated;
    }

    public boolean commitBatchStatement(PreparedStatement stmt){
        try{
            stmt.executeBatch();
            c.commit();
        } catch (SQLException e) {
            handleException(e);
        }
        return true;
    }

    public void closeConnection(){
        try{
            c.close();
        } catch (SQLException e) {
            handleException(e);
        }
    }

    private void setConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/EventOdense",
                            "eventodense", "123");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
    }

    private void handleException(SQLException e){
        e.printStackTrace();
        System.err.println(e.getClass().getName()+": "+e.getMessage());
        System.exit(0);
    }
}
