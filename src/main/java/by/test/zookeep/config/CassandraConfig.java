package by.test.zookeep.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.CassandraSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import static java.util.Objects.requireNonNull;

/**
 * Class configuration Cassandra using JavaConfig
 */
@Configuration
@PropertySource("classpath:cassandra.properties")
@ComponentScan("by.test.zookeep")
@EnableCassandraRepositories(basePackages = {"by.test.zookeep.repositories"})
public class CassandraConfig extends AbstractCassandraConfiguration {
    @Value("${cassandra.keyspace}")
    private String cassandraKeySpace;
    @Value("${cassandra.host}")
    private String cassandraHost;
    @Value("${cassandra.port}")
    private int cassandraPort;

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getKeyspaceName() {
        return cassandraKeySpace;
    }

    /**
     * Create a {@link CassandraClusterFactoryBean}
     */
    @Bean
    public CassandraClusterFactoryBean cluster() {
        CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
        cluster.setContactPoints(cassandraHost);
        cluster.setPort(cassandraPort);
        return cluster;
    }

    /**
     * Create a {@link CassandraMappingContext}
     */
    @Bean
    public CassandraMappingContext mappingContext() {
        return new CassandraMappingContext();
    }

    /**
     * Create {@link MappingCassandraConverter}
     *
     * @return {@link CassandraConverter}
     * @see #mappingContext()
     */
    @Bean
    public CassandraConverter converter() {
        return new MappingCassandraConverter(mappingContext());
    }

    /**
     * Create {@link CassandraSessionFactoryBean}
     *
     * @see #cluster()
     * @see #converter()
     * @see #getSchemaAction()
     */
    @Bean
    public CassandraSessionFactoryBean session() {
        CassandraSessionFactoryBean session = new CassandraSessionFactoryBean();
        session.setCluster(requireNonNull(cluster().getObject()));
        session.setKeyspaceName(getKeyspaceName());
        session.setConverter(converter());
        session.setSchemaAction(getSchemaAction());
        return session;
    }

    /**
     * @return {@link SchemaAction}
     */
    @Bean
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE;
    }
}
