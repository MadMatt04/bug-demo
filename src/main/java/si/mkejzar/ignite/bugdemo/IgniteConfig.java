package si.mkejzar.ignite.bugdemo;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteSpringBean;
import org.apache.ignite.configuration.DataRegionConfiguration;
import org.apache.ignite.configuration.DataStorageConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.lang.System.getProperty;

/**
 * @author matijak
 * @since 26/10/2017
 */
@Configuration
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
//@AutoConfigureBefore(HibernateJpaAutoConfiguration.class)
//@DependsOn("jdbcTemplate")
public class IgniteConfig {

    @Bean
    public Ignite ignite(IgniteConfiguration igniteConfiguration) {
        IgniteSpringBean igniteSpringBean = new AutoActiveIgniteSpringBean();
        igniteSpringBean.setConfiguration(igniteConfiguration);


        return igniteSpringBean;
    }

    @Bean
    public IgniteConfiguration igniteConfiguration(DataStorageConfiguration dataStorageConfiguration) {
        return new IgniteConfiguration()
                .setActiveOnStart(true)
                .setClientMode(false)
                .setWorkDirectory(getProperty("user.dir") + getProperty("file.separator") + "ignite" + getProperty("file.separator") + "work")
                .setDataStorageConfiguration(dataStorageConfiguration)
                ;
    }


    @Bean
    public DataStorageConfiguration dataStorageConfiguration() {

        return new DataStorageConfiguration()
                .setDefaultDataRegionConfiguration(
                        new DataRegionConfiguration()
                                .setName("tasksRegion")
                                .setPersistenceEnabled(false)
                                .setInitialSize(512L * 1024L * 1024L)
                );
    }

    @Bean(initMethod = "generateSchemaAndInitialData")
    public SchemaGenerator schemaGenerator() {
        return new SchemaGenerator();
    }




}
