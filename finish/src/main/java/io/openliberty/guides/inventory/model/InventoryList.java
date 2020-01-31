// tag::copyright[]
/*******************************************************************************
 * Copyright (c) 2017, 2020 IBM Corporation and others.
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

import java.util.List;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

// tag::InventoryList[]
@Schema(name="InventoryList", description="POJO that represents the inventory contents.")
// end::InventoryList[]
public class InventoryList {

    @Schema(required = true)
    // tag::Systems[]
    private List<SystemData> systems;
    // end::Systems[]

    public InventoryList(List<SystemData> systems) {
        this.systems = systems;
    }

    public List<SystemData> getSystems() {
        return systems;
    }

    public int getTotal() {
        return systems.size();
    }
}
