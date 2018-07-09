package io.openliberty.guides.inventory.model;

import java.util.Properties;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name="SystemData", description="POJO that represents a single inventory entry.")
public class SystemData {

    @Schema(required = true) 
    private final String hostname;
    
    @Schema(required = true) 
    private final Properties properties;

    public SystemData(String hostname, Properties properties) {
        this.hostname = hostname;
        this.properties = properties;
    }

    public String getHostname() {
        return hostname;
    }

    public Properties getProperties() {
        return properties;
    }

    @Override
    public boolean equals(Object host) {
        if (host instanceof SystemData) {
            return hostname.equals(((SystemData) host).getHostname());
        }
        return false;
    }
}