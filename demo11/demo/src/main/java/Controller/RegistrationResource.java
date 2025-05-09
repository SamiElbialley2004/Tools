package Controller;

import Entity.User;
import Service.UserService;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

@Path("/register")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RegistrationResource {

    @EJB
    private UserService userService;

    // 1.1 Registration
    @POST
    public Response register(UserDTO dto) {
        try {
            userService.register(dto.getEmail(), dto.getPassword(), dto.getFullName());
            return Response.status(201).entity("User registered.").build();
        } catch (Exception e) {
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    // 1.2 Login
    @POST
    @Path("/login")
    public Response login(LoginDTO dto) {
        boolean ok = userService.login(dto.getEmail(), dto.getPassword());
        if (ok) return Response.ok("Login successful.").build();
        return Response.status(401).entity("Invalid email or password.").build();
    }

    // Health check
    @GET
    @Path("/ping")
    public Response ping() {
        return Response.ok("pong").build();
    }

    // 1.3 Profile: fetch
    @GET
    @Path("/profile")
    public Response getProfile(@QueryParam("email") String email) {
        User u = userService.getUser(email);
        if (u == null) return Response.status(404).entity("User not found.").build();

        ProfileDTO p = new ProfileDTO();
        p.setEmail(u.getEmail());
        p.setFullName(u.getFullName());
        p.setBio(u.getBio());
        p.setRole(u.getRole());
        // do NOT send password
        return Response.ok(p).build();
    }

    // 1.3 Profile: update
    @PUT
    @Path("/profile")
    public Response updateProfile(ProfileDTO p) {
        try {
            userService.updateProfile(
                    p.getEmail(),
                    p.getNewEmail(),
                    p.getPassword(),
                    p.getFullName(),
                    p.getBio(),
                    p.getRole()
            );
            return Response.ok("Profile updated.").build();
        } catch (Exception e) {
            return Response.status(400).entity(e.getMessage()).build();
        }
    }
}
