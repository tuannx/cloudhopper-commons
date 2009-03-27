
package com.cloudhopper.commons.sql.proxool;

// third party imports
import org.apache.log4j.Logger;
import org.logicalcobwebs.proxool.ProxoolException;
import org.logicalcobwebs.proxool.ProxoolFacade;
import org.logicalcobwebs.proxool.admin.SnapshotIF;

// my imports
import com.cloudhopper.commons.sql.DataSourceConfiguration;
import com.cloudhopper.commons.sql.ManagedDataSource;
import com.cloudhopper.commons.sql.adapter.BasicDataSource;
import com.cloudhopper.commons.sql.adapter.DataSourceAdapter;

/**
 * An implementation of a Proxool "Managed" DataSource.
 * 
 * @author joelauer
 */
public class ProxoolManagedDataSource extends ManagedDataSource {

    private static final Logger logger = Logger.getLogger(ProxoolManagedDataSource.class);

    public ProxoolManagedDataSource(DataSourceAdapter adapter, DataSourceConfiguration config, BasicDataSource ds) {
        super(adapter, config, ds);
    }

    public Integer getIdleConnectionCount() {
        try {
            SnapshotIF snapshot = ProxoolFacade.getSnapshot(getConfiguration().getName());
            return snapshot.getAvailableConnectionCount();
        } catch (ProxoolException e) {
            logger.error(e);
            return null;
        }
    }

    public Integer getBusyConnectionCount() {
        try {
            SnapshotIF snapshot = ProxoolFacade.getSnapshot(getConfiguration().getName());
            return snapshot.getActiveConnectionCount();
        } catch (ProxoolException e) {
            logger.error(e);
            return null;
        }
    }

    public Integer getConnectionCount() {
        try {
            SnapshotIF snapshot = ProxoolFacade.getSnapshot(getConfiguration().getName());
            return (int)snapshot.getConnectionCount();
        } catch (ProxoolException e) {
            logger.error(e);
            return null;
        }
    }

}
