// tag::copyright[]
/*******************************************************************************
 * Copyright (c) 2017, 2022 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - Initial implementation
 *******************************************************************************/
// end::copyright[]
package io.openliberty.guides.inventory.model;

import java.util.Properties;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

// tag::SystemData[]
@Schema(name = "SystemData",
       description = "POJO that represents a single inventory entry.")
// end::SystemData[]
public class SystemData {

    // tag::Hostname[]
    @Schema(required = true)
    // end::Hostname[]
    private final String hostname;

    // tag::Properties[]
    @Schema(required = true)
    // end::Properties[]
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
