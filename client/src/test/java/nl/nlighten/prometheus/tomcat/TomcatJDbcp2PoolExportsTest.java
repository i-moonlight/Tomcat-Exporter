package nl.nlighten.prometheus.tomcat;

import io.prometheus.client.CollectorRegistry;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TomcatJDbcp2PoolExportsTest extends AbstractTomcatMetricsTest {

    @BeforeClass
    public static void setUp() throws Exception {
        setUpTomcat("org.apache.tomcat.dbcp.dbcp2.BasicDataSourceFactory");
        new TomcatDbcp2PoolExports().register();
        doRequest();
    }


    @AfterClass
    public static void tearDown() throws Exception {
        shutDownTomcat();
    }

    @Test
    public void testJDbcp2PoolMetrics() throws Exception {
        assertThat(CollectorRegistry.defaultRegistry.getSampleValue("tomcat_dbcp2_connections_active_total", new String[]{"pool"}, new String[]{"jdbc/db"}), is(0.0));
        assertThat(CollectorRegistry.defaultRegistry.getSampleValue("tomcat_dbcp2_connections_idle_total", new String[]{"pool"}, new String[]{"jdbc/db"}), is(1.0));
        assertThat(CollectorRegistry.defaultRegistry.getSampleValue("tomcat_dbcp2_connections_max", new String[]{"pool"}, new String[]{"jdbc/db"}), is(8.0));
    }
}
