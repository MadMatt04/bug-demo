package si.mkejzar.ignite.bugdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.UUID;

import static java.util.UUID.randomUUID;

/**
 * @author matijak
 * @since 20/11/2017
 */
@SuppressWarnings("HardcodedLineSeparator")
public class SchemaGenerator {

    private static final String TASK_GROUP_TABLE_DDL = "CREATE TABLE IF NOT EXISTS M_TASK_GROUP (\n" +
            "  entity_id              VARCHAR(36),\n" +
            "  entity_version         INTEGER,\n" +
            "  entity_order           INTEGER,\n" +
            "  definition_id          VARCHAR(36),\n" +
            "  m_owner_id             VARCHAR(36),\n" +
            "  m_parent_id            VARCHAR(36),\n" +
            "  m_parent_definition_id VARCHAR(36),\n" +
            "  m_parent_owner_id      VARCHAR(36),\n" +
            "  execution_type         VARCHAR(15),\n" +
            "  PRIMARY KEY (m_owner_id, definition_id, entity_id)\n" +
            ") WITH \"template=partitioned,affinityKey=m_owner_id,backups=1,atomicity=transactional,cache_name=M_TASK_GROUP,key_type=PlanItemKey,value_type=MTaskGroup\";";

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public void generateSchemaAndInitialData() {
        System.out.println("SchemaGenerator.generateSchemaAndInitialData");
        jdbcTemplate.execute(TASK_GROUP_TABLE_DDL);
        insertTask(null);
    }

    public void insertTask(UUID entityId) {
        UUID id = entityId != null ? entityId : randomUUID();
        jdbcTemplate.execute("INSERT INTO M_TASK_GROUP (" +
                                     "entity_id, " +
                                     "entity_version, " +
                                     "entity_order," +
                                     "definition_id," +
                                     "m_owner_id," +
                                     "m_parent_id," +
                                     "m_parent_definition_id," +
                                     "m_parent_owner_id, " +
                                     "execution_type" +
                                     ") VALUES (" +
                                     wrapInQuotes(id) + ", 0, 0, " +
                                     wrapInQuotes(randomUUID()) + ", " + wrapInQuotes(randomUUID()) + ", " + wrapInQuotes(randomUUID()) + ", " +
                                     wrapInQuotes(randomUUID()) + ", " + wrapInQuotes(randomUUID()) + ", " +
                                     wrapInQuotes("SEQUENTIAL") +

                                     ");"
        );
    }

    private String wrapInQuotes(String value) {
        return '\'' + value + '\'';
    }

    private String wrapInQuotes(UUID uuid) {
        return wrapInQuotes(uuid.toString());
    }
}
