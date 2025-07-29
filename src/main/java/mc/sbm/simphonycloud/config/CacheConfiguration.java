package mc.sbm.simphonycloud.config;

import java.net.URI;
import java.util.concurrent.TimeUnit;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.redisson.Redisson;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.redisson.jcache.configuration.RedissonConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;

    @Bean
    public javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration(JHipsterProperties jHipsterProperties) {
        MutableConfiguration<Object, Object> jcacheConfig = new MutableConfiguration<>();

        URI redisUri = URI.create(jHipsterProperties.getCache().getRedis().getServer()[0]);

        Config config = new Config();
        // Fix Hibernate lazy initialization https://github.com/jhipster/generator-jhipster/issues/22889
        config.setCodec(new org.redisson.codec.SerializationCodec());
        if (jHipsterProperties.getCache().getRedis().isCluster()) {
            ClusterServersConfig clusterServersConfig = config
                .useClusterServers()
                .setMasterConnectionPoolSize(jHipsterProperties.getCache().getRedis().getConnectionPoolSize())
                .setMasterConnectionMinimumIdleSize(jHipsterProperties.getCache().getRedis().getConnectionMinimumIdleSize())
                .setSubscriptionConnectionPoolSize(jHipsterProperties.getCache().getRedis().getSubscriptionConnectionPoolSize())
                .addNodeAddress(jHipsterProperties.getCache().getRedis().getServer());

            if (redisUri.getUserInfo() != null) {
                clusterServersConfig.setPassword(redisUri.getUserInfo().substring(redisUri.getUserInfo().indexOf(':') + 1));
            }
        } else {
            SingleServerConfig singleServerConfig = config
                .useSingleServer()
                .setConnectionPoolSize(jHipsterProperties.getCache().getRedis().getConnectionPoolSize())
                .setConnectionMinimumIdleSize(jHipsterProperties.getCache().getRedis().getConnectionMinimumIdleSize())
                .setSubscriptionConnectionPoolSize(jHipsterProperties.getCache().getRedis().getSubscriptionConnectionPoolSize())
                .setAddress(jHipsterProperties.getCache().getRedis().getServer()[0]);

            if (redisUri.getUserInfo() != null) {
                singleServerConfig.setPassword(redisUri.getUserInfo().substring(redisUri.getUserInfo().indexOf(':') + 1));
            }
        }
        jcacheConfig.setStatisticsEnabled(true);
        jcacheConfig.setExpiryPolicyFactory(
            CreatedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS, jHipsterProperties.getCache().getRedis().getExpiration()))
        );
        return RedissonConfiguration.fromInstance(Redisson.create(config), jcacheConfig);
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cm) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cm);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer(javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration) {
        return cm -> {
            createCache(cm, mc.sbm.simphonycloud.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            createCache(cm, mc.sbm.simphonycloud.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            createCache(cm, mc.sbm.simphonycloud.domain.User.class.getName(), jcacheConfiguration);
            createCache(cm, mc.sbm.simphonycloud.domain.Authority.class.getName(), jcacheConfiguration);
            createCache(cm, mc.sbm.simphonycloud.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            createCache(cm, mc.sbm.simphonycloud.domain.BarCodeTrans.class.getName(), jcacheConfiguration);
            createCache(cm, mc.sbm.simphonycloud.domain.CheckTrans.class.getName(), jcacheConfiguration);
            createCache(cm, mc.sbm.simphonycloud.domain.CodeRaison.class.getName(), jcacheConfiguration);
            createCache(cm, mc.sbm.simphonycloud.domain.CommissionServiceBI.class.getName(), jcacheConfiguration);
            createCache(cm, mc.sbm.simphonycloud.domain.CommissionServiceTrans.class.getName(), jcacheConfiguration);
            createCache(cm, mc.sbm.simphonycloud.domain.DetailLineBI.class.getName(), jcacheConfiguration);
            createCache(cm, mc.sbm.simphonycloud.domain.ElementMenu.class.getName(), jcacheConfiguration);
            createCache(cm, mc.sbm.simphonycloud.domain.EmployeeCnC.class.getName(), jcacheConfiguration);
            createCache(cm, mc.sbm.simphonycloud.domain.FamilyGroup.class.getName(), jcacheConfiguration);
            createCache(cm, mc.sbm.simphonycloud.domain.GuestCheckBI.class.getName(), jcacheConfiguration);
            createCache(cm, mc.sbm.simphonycloud.domain.GuestCheckBI.class.getName() + ".detailLineBILists", jcacheConfiguration);
            createCache(cm, mc.sbm.simphonycloud.domain.Hierarchie.class.getName(), jcacheConfiguration);
            createCache(cm, mc.sbm.simphonycloud.domain.HttpCallAudit.class.getName(), jcacheConfiguration);
            createCache(cm, mc.sbm.simphonycloud.domain.Location.class.getName(), jcacheConfiguration);
            createCache(cm, mc.sbm.simphonycloud.domain.MajorGroup.class.getName(), jcacheConfiguration);
            createCache(cm, mc.sbm.simphonycloud.domain.MenuItemDefinitionsCnC.class.getName(), jcacheConfiguration);
            createCache(cm, mc.sbm.simphonycloud.domain.MenuItemMastersCnC.class.getName(), jcacheConfiguration);
            createCache(cm, mc.sbm.simphonycloud.domain.MenuItemPricesCnC.class.getName(), jcacheConfiguration);
            createCache(cm, mc.sbm.simphonycloud.domain.ModePaiementBI.class.getName(), jcacheConfiguration);
            createCache(cm, mc.sbm.simphonycloud.domain.ModePaiementTrans.class.getName(), jcacheConfiguration);
            createCache(cm, mc.sbm.simphonycloud.domain.OrderChannel.class.getName(), jcacheConfiguration);
            createCache(cm, mc.sbm.simphonycloud.domain.OrderType.class.getName(), jcacheConfiguration);
            createCache(cm, mc.sbm.simphonycloud.domain.OrganizationLocation.class.getName(), jcacheConfiguration);
            createCache(cm, mc.sbm.simphonycloud.domain.PartieDeJournee.class.getName(), jcacheConfiguration);
            createCache(cm, mc.sbm.simphonycloud.domain.PointDeVenteCnC.class.getName(), jcacheConfiguration);
            createCache(cm, mc.sbm.simphonycloud.domain.PointDeVenteTrans.class.getName(), jcacheConfiguration);
            createCache(cm, mc.sbm.simphonycloud.domain.RemiseBI.class.getName(), jcacheConfiguration);
            createCache(cm, mc.sbm.simphonycloud.domain.RemiseTrans.class.getName(), jcacheConfiguration);
            createCache(cm, mc.sbm.simphonycloud.domain.Societe.class.getName(), jcacheConfiguration);
            createCache(cm, mc.sbm.simphonycloud.domain.TaxeBI.class.getName(), jcacheConfiguration);
            createCache(cm, mc.sbm.simphonycloud.domain.TaxeClassTrans.class.getName(), jcacheConfiguration);
            createCache(cm, mc.sbm.simphonycloud.domain.TaxeRateTrans.class.getName(), jcacheConfiguration);
            // jhipster-needle-redis-add-entry
        };
    }

    private void createCache(
        javax.cache.CacheManager cm,
        String cacheName,
        javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration
    ) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
