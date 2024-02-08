/**
 * File:  ClubMembershipResource.java
 * Course materials (23S) CST 8277
 * 
 * @Date 2023-12-09
 * 
 * @Author  Group 02
 *   040694208, AJ Fayad
 *   041016930, Bryan Edler
 *   041072910, Morgan Bakelmun
 *   041063710, Xiangu Dai
 *
 */
package acmecollege.rest.resource;


import static acmecollege.utility.MyConstants.ADMIN_ROLE;
import static acmecollege.utility.MyConstants.CLUB_MEMBERSHIP_RESOURCE_NAME;
import static acmecollege.utility.MyConstants.USER_ROLE;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import acmecollege.ejb.ACMECollegeService;
import acmecollege.entity.ClubMembership;



@Path(CLUB_MEMBERSHIP_RESOURCE_NAME)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClubMembershipResource
{
	private static final Logger LOG = LogManager.getLogger();

    @EJB
    protected ACMECollegeService service;
    
    @Inject
    protected SecurityContext sc;
    
    @GET
    @RolesAllowed({ADMIN_ROLE})
    public Response getClubMembership() {
        List<ClubMembership> cMemberships = service.getAllClubMemberships();
        Response response = Response.ok(cMemberships).build();
        return response;
    }
    
    @GET
    @Path("/{cardId}")
    @RolesAllowed({ADMIN_ROLE, USER_ROLE})
    public Response getClubMembershipByCardId(@PathParam("cardId") int cardId) {
    	Response response = null;
    	
        ClubMembership result = service.getClubMembershipById(cardId);
        response = Response.status(result == null ? Status.NOT_FOUND : Status.OK).entity(result).build();
    
        return response;
    }
    
    @RolesAllowed({ADMIN_ROLE})
    @PUT
    @Path("/{cmId}")
    public Response updateClubMembership(@PathParam("cmId") int cmId, ClubMembership clubMembershipWithUpdates) {
        Response response = null;
        ClubMembership result = service.updateClubMembership(cmId, clubMembershipWithUpdates);
        response = Response.ok(result).build();
        return response;
    }

}
