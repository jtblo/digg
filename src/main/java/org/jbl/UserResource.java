package org.jbl;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jbl.UserDomain.UserRequest;
import org.jbl.UserDomain.UserResponse;
import org.jboss.resteasy.reactive.RestQuery;

@Path("/user")
public class UserResource {

    @Inject
    UserService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addUsers(UserRequest request) {
        service.addUser(request.user());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public UserResponse getAllUsers() {
        return new UserResponse(service.getAllUsers());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/page")
    public UserResponse getUsersPaged(
       @RestQuery("page") @DefaultValue("1") int page,
       @RestQuery("pageSize") @DefaultValue("10") int pageSize
    ) {
        if(page <= 0) {
            page = 1;
        }
        return new UserResponse(service.getUsersPaged(page, pageSize));
    }
}
