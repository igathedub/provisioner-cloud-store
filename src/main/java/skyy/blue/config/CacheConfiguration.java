package skyy.blue.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, skyy.blue.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, skyy.blue.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, skyy.blue.domain.User.class.getName());
            createCache(cm, skyy.blue.domain.Authority.class.getName());
            createCache(cm, skyy.blue.domain.User.class.getName() + ".authorities");
            createCache(cm, skyy.blue.domain.Customer.class.getName());
            createCache(cm, skyy.blue.domain.Customer.class.getName() + ".facilities");
            createCache(cm, skyy.blue.domain.Customer.class.getName() + ".users");
            createCache(cm, skyy.blue.domain.AppUser.class.getName());
            createCache(cm, skyy.blue.domain.AppUser.class.getName() + ".roles");
            createCache(cm, skyy.blue.domain.Facility.class.getName());
            createCache(cm, skyy.blue.domain.Facility.class.getName() + ".networks");
            createCache(cm, skyy.blue.domain.Role.class.getName());
            createCache(cm, skyy.blue.domain.MeshGroup.class.getName());
            createCache(cm, skyy.blue.domain.AppKey.class.getName());
            createCache(cm, skyy.blue.domain.NetKey.class.getName());
            createCache(cm, skyy.blue.domain.KeyIndex.class.getName());
            createCache(cm, skyy.blue.domain.Features.class.getName());
            createCache(cm, skyy.blue.domain.Retransmit.class.getName());
            createCache(cm, skyy.blue.domain.Publish.class.getName());
            createCache(cm, skyy.blue.domain.Model.class.getName());
            createCache(cm, skyy.blue.domain.Element.class.getName());
            createCache(cm, skyy.blue.domain.Element.class.getName() + ".models");
            createCache(cm, skyy.blue.domain.AllocatedRange.class.getName());
            createCache(cm, skyy.blue.domain.Node.class.getName());
            createCache(cm, skyy.blue.domain.Node.class.getName() + ".elements");
            createCache(cm, skyy.blue.domain.Node.class.getName() + ".netKeys");
            createCache(cm, skyy.blue.domain.Node.class.getName() + ".appKeys");
            createCache(cm, skyy.blue.domain.Provisioner.class.getName());
            createCache(cm, skyy.blue.domain.Provisioner.class.getName() + ".aallocatedGroupRanges");
            createCache(cm, skyy.blue.domain.Provisioner.class.getName() + ".aallocatedUnicastRanges");
            createCache(cm, skyy.blue.domain.Provisioner.class.getName() + ".aallocatedSceneRanges");
            createCache(cm, skyy.blue.domain.NetworkConfiguration.class.getName());
            createCache(cm, skyy.blue.domain.NetworkConfiguration.class.getName() + ".provisioners");
            createCache(cm, skyy.blue.domain.NetworkConfiguration.class.getName() + ".nodes");
            createCache(cm, skyy.blue.domain.NetworkConfiguration.class.getName() + ".groups");
            createCache(cm, skyy.blue.domain.NetworkConfiguration.class.getName() + ".netKeys");
            createCache(cm, skyy.blue.domain.NetworkConfiguration.class.getName() + ".appKeys");
            createCache(cm, skyy.blue.domain.NetKeyIndex.class.getName());
            createCache(cm, skyy.blue.domain.AllocatedGroupRange.class.getName());
            createCache(cm, skyy.blue.domain.AllocatedUnicastRange.class.getName());
            createCache(cm, skyy.blue.domain.AllocatedSceneRange.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }

}
