// tag::copyright[]
/*******************************************************************************
 * Copyright (c) 2017, 2018 IBM Corporation and others.
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
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import io.openliberty.guides.inventory.model.InventoryList;

@RequestScoped
@Path("/systems")
public class InventoryResource {

    @Inject
    InventoryManager manager;

    @GET
    @Path("/{hostname}")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponses(value = {
        @APIResponse(
            responseCode = "404",
            description = "Missing description - to be filtered.",
            content = @Content(
                mediaType = "none")),
        @APIResponse(
            responseCode = "200",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Properties.class)))})
    @Operation(
        summary = "Get JVM system properties for particular host", 
        description = "Retrieves and returns the JVM system properties from the system service "
                    + "running on the particular host.")
    public Response getPropertiesForHost(
        @Parameter(
            name = "hostname",
            description = "The host for whom to retrieve the JVM system properties for.", 
            required = true,
            example = "foo",
            schema = @Schema(type = SchemaType.STRING, implementation = String.class)) 
        @PathParam("hostname") String hostname) {
        Properties props = manager.get(hostname);
        if (props == null) {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("ERROR: Unknown hostname or the system service may not be "
                                 + "running on " + hostname)
                           .build();
        }
        return Response.ok(props).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(
        responseCode = "200",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(type = SchemaType.ARRAY, implementation = InventoryList.class)))
    @Operation(
        summary = "List inventory contents.",
        description = "Returns the currently stored host:properties pairs in the inventory.")
    public InventoryList listContents() {
        return manager.list();
    }
}
