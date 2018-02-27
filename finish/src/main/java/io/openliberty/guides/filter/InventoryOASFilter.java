package io.openliberty.guides.filter;

import org.eclipse.microprofile.openapi.OASFilter;
import org.eclipse.microprofile.openapi.models.responses.APIResponse;

public class InventoryOASFilter implements OASFilter {
    
    @Override
    public APIResponse filterAPIResponse(APIResponse apiResponse) {
        if ("Missing description - to be filtered.".equals(apiResponse.getDescription())) {
            apiResponse.setDescription("Invalid hostname or the system service may not be running on the particular host.");
        }
        return apiResponse;
    }

}
