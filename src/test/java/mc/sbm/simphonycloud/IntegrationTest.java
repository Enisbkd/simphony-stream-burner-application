package mc.sbm.simphonycloud;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import mc.sbm.simphonycloud.config.AsyncSyncConfiguration;
import mc.sbm.simphonycloud.config.EmbeddedRedis;
import mc.sbm.simphonycloud.config.EmbeddedSQL;
import mc.sbm.simphonycloud.config.JacksonConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = { SimphonyStreamBurnerApplicationApp.class, JacksonConfiguration.class, AsyncSyncConfiguration.class })
@EmbeddedRedis
@EmbeddedSQL
public @interface IntegrationTest {
}
