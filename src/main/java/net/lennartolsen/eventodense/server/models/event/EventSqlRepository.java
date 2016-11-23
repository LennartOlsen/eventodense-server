package net.lennartolsen.eventodense.server.models.event;

import net.lennartolsen.eventodense.server.datastore.ISqlRepository;
import net.lennartolsen.eventodense.server.datastore.PostgresHelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by lennartolsen on 21/11/2016.
 */
public class EventSqlRepository implements ISqlRepository {
    private static final String TABLE_NAME = "events";

    public String getTableName(){
        return TABLE_NAME;
    }

    public void closeConnection(PostgresHelper h) {
        h.closeConnection();
    }

    public boolean save(Event e){
        System.out.println(e.toString());
        System.out.println(getInsertBase());
        PostgresHelper h = new PostgresHelper();
        PreparedStatement stmt = h.getPreparedStatement(getInsertBase());
        stmt = prepareSave(e, stmt);
        int count = h.commitStatement(stmt);
        closeConnection(h);
        return count != 0;
    }

    public ArrayList<Event> get(int limit, int offset){
        ArrayList<Event> events = new ArrayList<>();
        PostgresHelper h = new PostgresHelper();
        String sql = getSelectBase();
        sql += limit != 0 ? " LIMIT " + limit: "";
        sql += offset != 0 ? " OFFSET " + offset: "";
        PreparedStatement st = h.getPreparedStatement(sql);

        executeQuery(events, h, st);

        return events;
    }

    public ArrayList<Event> get(String id){
        ArrayList<Event> events = new ArrayList<>();
        PostgresHelper h = new PostgresHelper();
        String sql = getSelectBase();
        sql += " WHERE id = \'" + id + "\'";
        PreparedStatement st = h.getPreparedStatement(sql);

        executeQuery(events, h, st);

        return events;
    }

    private void executeQuery(ArrayList<Event> events, PostgresHelper h, PreparedStatement st) {
        try {
            ResultSet rs = st.executeQuery();

            while(rs.next()){
                events.add(build(rs));
            }
            rs.close();
            st.close();

            closeConnection(h);

        } catch (SQLException e){
            handleException(e);
        }
    }

    private String getInsertBase(){
        return "INSERT INTO " + getTableName() + " (id, " +
                "lat, " +
                "lng, " +
                "starttime, " +
                "endtime, " +
                "name, " +
                "description, " +
                "imageid," +
                "radius," +
                "color) VALUES " +
                "(?,?,?,?,?,?,?,?,?,?)";
    }

    private String getSelectBase(){
        return "SELECT * FROM " + getTableName();
    }

    private PreparedStatement prepareSave(Event e, PreparedStatement stmt){
        try {
            stmt.setString(1, e.getId());
            stmt.setDouble(2, e.getLat());
            stmt.setDouble(3, e.getLng());
            stmt.setInt(4, e.getEndTime());
            stmt.setDouble(5, e.getStartTime());
            stmt.setString(6, e.getName());
            stmt.setString(7, e.getDescription());
            stmt.setString(8, e.getImageId());
            stmt.setInt(9, e.getRadius());
            stmt.setString(10, e.getColor());
        } catch (SQLException ex){
            handleException(ex);
        }
        return stmt;
    }

    private Event build(ResultSet rs){
        Event e = new Event();
        try {
            e.setId(rs.getString(1));
            e.setLat(rs.getDouble(2));
            e.setLng(rs.getDouble(3));
            e.setStartTime(rs.getInt(4));
            e.setEndTime(rs.getInt(5));
            e.setName(rs.getString(6));
            e.setDescription(rs.getString(7));
            e.setImageId(rs.getString(8));
            e.setRadius(rs.getInt(9));
            e.setColor(rs.getString(10));
        } catch (SQLException ex){
            handleException(ex);
        }
        return e;
    }

    private void handleException(SQLException e){
        e.printStackTrace();
        System.err.println(e.getClass().getName()+": "+e.getMessage());
        System.exit(0);
    }
}
