package net.lennartolsen.eventodense.server.models.point;

import net.lennartolsen.eventodense.server.datastore.ISqlRepository;
import net.lennartolsen.eventodense.server.datastore.PostgresHelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by lennartolsen on 26/10/2016.
 */
public class PointSqlRepository implements ISqlRepository {
    private static final String TABLE_NAME = "points";

    private static final int MAX_BATCH_SIZE = 1000;

    public int getMaxBatchSize(){ return MAX_BATCH_SIZE; }

    public String getTableName(){
        return TABLE_NAME;
    }

    public void closeConnection(PostgresHelper h) {
        h.closeConnection();
    }

    public boolean save(Point p){
        PostgresHelper h = new PostgresHelper();
        PreparedStatement stmt = h.getPreparedStatement(getInsertBase());
        stmt = prepareSave(p, stmt);
        int count = h.commitStatement(stmt);
        closeConnection(h);
        return count != 0;
    }

    public boolean saveBatch(Point[] ps){
        PostgresHelper h = new PostgresHelper(false);
        PreparedStatement stmt = h.getPreparedStatement(getInsertBase());
        int count = 0;
        boolean inserted = false;
        for(Point p : ps){
            stmt = prepareSave(p, stmt);
            try {
                stmt.addBatch();
            } catch (SQLException e) {
                handleException(e);
            }

            //Commit if reaching max batch size
            count++;
            if(count <= getMaxBatchSize()) {
                inserted = h.commitBatchStatement(stmt);
            }
        }
        inserted = h.commitBatchStatement(stmt);

        closeConnection(h);

        return inserted;
    }

    public ArrayList<Point> getPoints(int limit, int offset){
        ArrayList<Point> points = new ArrayList<>();
        PostgresHelper h = new PostgresHelper();
        String sql = getSelectBase();
        sql += limit != 0 ? " LIMIT " + limit: "";
        sql += offset != 0 ? " OFFSET " + offset: "";
        PreparedStatement st = h.getPreparedStatement(sql);

        try {
            ResultSet rs = st.executeQuery();

            while(rs.next()){
                points.add(buildPoint(rs));
            }
            rs.close();
            st.close();

        } catch (SQLException e){
            handleException(e);
            System.exit(0);
        }

        return points;
    }

    private String getInsertBase(){
        return "INSERT INTO " + getTableName() + " (id, " +
                "lat, " +
                "lng, " +
                "timestamp, " +
                "accuracy, " +
                "altitude, " +
                "eventid, " +
                "deviceid) VALUES " +
                "(?,?,?,?,?,?,?,?)";
    }

    private String getSelectBase(){
        return "SELECT * FROM " + getTableName();
    }

    private PreparedStatement prepareSave(Point p, PreparedStatement stmt){
        try {
            stmt.setString(1, p.getId());
            stmt.setDouble(2, p.getLat());
            stmt.setDouble(3, p.getLng());
            stmt.setInt(4, p.getTimestamp());
            stmt.setDouble(5, p.getAccuracy());
            stmt.setDouble(6, p.getAltitude());
            stmt.setString(7, p.getEventId());
            stmt.setString(8, p.getDeviceId());
        } catch (SQLException e){
            handleException(e);
        }
        return stmt;
    }

    private Point buildPoint(ResultSet rs){
        Point p = new Point();
        try {
            p.setId(rs.getString(1));
            p.setLat(rs.getDouble(2));
            p.setLng(rs.getDouble(3));
            p.setTimestamp(rs.getInt(4));
            p.setAccuracy(rs.getFloat(5));
            p.setAltitude(rs.getFloat(6));
            p.setEventId(rs.getString(7));
            p.setDeviceId(rs.getString(8));
        } catch (SQLException e){
            handleException(e);
        }
        return p;
    }

    private void handleException(SQLException e){
        e.printStackTrace();
        System.err.println(e.getClass().getName()+": "+e.getMessage());
        System.exit(0);
    }
}
