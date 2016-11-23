package net.lennartolsen.eventodense.server.datastore;

/**
 * Created by lennartolsen on 26/10/2016.
 */
public interface ISqlRepository {
    String getTableName();

    /**
     * MUST BE CALLED AFTER EVERY INSERT
     * @param h helper to close
     */
    void closeConnection(PostgresHelper h);
}
