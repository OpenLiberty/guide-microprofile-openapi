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
package io.openliberty.guides.inventory;

import java.util.Properties;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponseSchema;
import io.openliberty.guides.inventory.model.InventoryList;

@RequestScoped
@Path("/systems")
public class InventoryResource {

    @Inject
    InventoryManager manager;

    @GET
    @Path("/{hostname}")
    @Produces(MediaType.APPLICATION_JSON)
    // tag::host-property[]
    // tag::APIResponse[]
    @APIResponse(
        responseCode = "404",
        description = "Missing description",
        content = @Content(mediaType = "application/json"))
    // end::APIResponse[]
    //tag::APIResponseSchema[]
    @APIResponseSchema(value = Properties.class,
        responseDescription = "JVM system properties of a particular host.",
        responseCode = "200")
    // end::APIResponseSchema[]
    // tag::Operation[]
    @Operation(
        summary = "Get JVM system properties for particular host",
        description = "Retrieves and returns the JVM system properties from the system "
        + "service running on the particular host.")
    // end::Operation[]
    // end::host-property[]
    public Response getPropertiesForHost(
        // tag::Parameter[]
        @Parameter(
            description = "The host for whom to retrieve "
                + "the JVM system properties for.",
            required = true,
            example = "foo",
            schema = @Schema(type = SchemaType.STRING))
        // end::Parameter[]
        @PathParam("hostname") String hostname) {
        // Get properties for host
        Properties props = manager.get(hostname);
        if (props == null) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("{ \"error\" : "
                                   + "\"Unknown hostname " + hostname
                                   + " or the resource may not be "
                                   + "running on the host machine\" }")
                           .build();
        }

        //Add to inventory to host
        manager.add(hostname, props);
        return Response.ok(props).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    // tag::listContents[]
    @APIResponseSchema(value = InventoryList.class,
        responseDescription = "host:properties pairs stored in the inventory.",
        responseCode = "200")
    @Operation(
        summary = "List inventory contents.",
        description = "Returns the currently stored host:properties pairs in the "
        + "inventory.")
    // end::listContents[]
    public InventoryList listContents() {
        return manager.list();
    }

}
