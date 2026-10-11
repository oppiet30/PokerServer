package com.hyphenated.card;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.Map;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.type.format.jackson.JacksonJsonFormatMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class PersistenceJsonMapperTest extends AbstractSpringTest {
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private JacksonJsonFormatMapper persistenceJsonMapper;

    @Test
    public void hibernateUsesExplicitApplicationMapper() {
        var factory = entityManagerFactory.unwrap(SessionFactoryImplementor.class);
        assertSame(persistenceJsonMapper, factory.getSessionFactoryOptions().getJsonFormatMapper());
        Map<String, String> value = Map.of("player", "Alice");
        String json = persistenceJsonMapper.toString(value, Map.class);
        assertEquals(value, persistenceJsonMapper.fromString(json, Map.class));
    }
}
