package net.lennartolsen.eventodense.server.models.point;

import net.lennartolsen.eventodense.server.datastore.ISqlRepository;
import net.lennartolsen.eventodense.server.datastore.PostgresHelper;

import java.sql.PreparedStatement;
import java.sql.SQLException;

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

    private void handleException(SQLException e){
        e.printStackTrace();
        System.err.println(e.getClass().getName()+": "+e.getMessage());
        System.exit(0);
    }
}
