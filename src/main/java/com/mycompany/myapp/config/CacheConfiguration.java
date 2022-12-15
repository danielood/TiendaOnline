package com.mycompany.myapp.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

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
            createCache(cm, com.mycompany.myapp.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.mycompany.myapp.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.mycompany.myapp.domain.User.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Authority.class.getName());
            createCache(cm, com.mycompany.myapp.domain.User.class.getName() + ".authorities");
            createCache(cm, com.mycompany.myapp.domain.Producto.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Producto.class.getName() + ".plataformas");
            createCache(cm, com.mycompany.myapp.domain.Producto.class.getName() + ".ventas");
            createCache(cm, com.mycompany.myapp.domain.Cliente.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Direccion.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Venta.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Venta.class.getName() + ".productos");
            createCache(cm, com.mycompany.myapp.domain.Venta.class.getName() + ".videoJuegos");
            createCache(cm, com.mycompany.myapp.domain.Venta.class.getName() + ".carritos");
            createCache(cm, com.mycompany.myapp.domain.VideoJuegos.class.getName());
            createCache(cm, com.mycompany.myapp.domain.VideoJuegos.class.getName() + ".valoraciones");
            createCache(cm, com.mycompany.myapp.domain.VideoJuegos.class.getName() + ".plataformas");
            createCache(cm, com.mycompany.myapp.domain.VideoJuegos.class.getName() + ".categorias");
            createCache(cm, com.mycompany.myapp.domain.VideoJuegos.class.getName() + ".ventas");
            createCache(cm, com.mycompany.myapp.domain.Imagen.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Compannia.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Categoria.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Categoria.class.getName() + ".videoJuegos");
            createCache(cm, com.mycompany.myapp.domain.Plataforma.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Plataforma.class.getName() + ".videoJuegos");
            createCache(cm, com.mycompany.myapp.domain.Plataforma.class.getName() + ".productos");
            createCache(cm, com.mycompany.myapp.domain.Valoraciones.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Valoraciones.class.getName() + ".videoJuegos");
            createCache(cm, com.mycompany.myapp.domain.Carrito.class.getName());
            createCache(cm, com.mycompany.myapp.domain.Carrito.class.getName() + ".ventas");
            createCache(cm, com.mycompany.myapp.domain.Carrito.class.getName() + ".videoJuegos");
            createCache(cm, com.mycompany.myapp.domain.VideoJuegos.class.getName() + ".carritos");
            createCache(cm, com.mycompany.myapp.domain.Carrito.class.getName() + ".productos");
            createCache(cm, com.mycompany.myapp.domain.Producto.class.getName() + ".carritos");
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
