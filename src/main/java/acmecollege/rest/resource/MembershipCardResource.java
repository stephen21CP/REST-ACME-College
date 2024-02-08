/**
 * File:  MembershipCardResource.java
 * Course materials (23S) CST 8277
 * 
 * @Date 2023-12-09
 * 
 * @Author:  Group 02
 *   040694208, AJ Fayad
 *   041016930, Bryan Edler
 *   041072910, Morgan Bakelmun
 *   041063710, Xiangu Dai
 *
 */
package acmecollege.rest.resource;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.soteria.WrappingCallerPrincipal;

import acmecollege.ejb.ACMECollegeService;
import acmecollege.entity.Course;
import acmecollege.entity.MembershipCard;
import acmecollege.entity.SecurityUser;
import acmecollege.entity.Student;
import acmecollege.entity.StudentClub;
import static acmecollege.utility.MyConstants.MEMBERSHIP_CARD_RESOURCE_NAME;
import static acmecollege.utility.MyConstants.CLUB_MEMBERSHIP_RESOURCE_NAME;
import static acmecollege.utility.MyConstants.STUDENT_CLUB_RESOURCE_NAME;
import static acmecollege.utility.MyConstants.RESOURCE_PATH_ID_ELEMENT;
import static acmecollege.utility.MyConstants.RESOURCE_PATH_ID_PATH;
import static acmecollege.utility.MyConstants.ADMIN_ROLE;
import static acmecollege.utility.MyConstants.USER_ROLE;

import java.util.List;

@Path(MEMBERSHIP_CARD_RESOURCE_NAME)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class MembershipCardResource {
	private static final Logger LOG = LogManager.getLogger();

    @EJB
    protected ACMECollegeService service;
    
    @Inject
    protected SecurityContext sc;
    
    @GET
    @RolesAllowed({ADMIN_ROLE})
    public Response getMembershipCards() {
        List<MembershipCard> mCards = service.getAllMembershipCards();
        Response response = Response.ok(mCards).build();
        return response;
    }
    
    @GET
    @RolesAllowed({ADMIN_ROLE, USER_ROLE})
    @Path(RESOURCE_PATH_ID_PATH)
    public Response getMembershipCardById(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
        MembershipCard mc = service.getMembershipCardById(id);
        Response response = Response.ok(mc).build();
        return response;
    }
    
    @POST
    @RolesAllowed({ADMIN_ROLE})
    public Response addMembershipCard(MembershipCard newMembershipCard) {
    	MembershipCard newMembershipCardWithIdTimestamps = service.persistMembershipCard(newMembershipCard);
        Response response = Response.ok(newMembershipCardWithIdTimestamps).build();
        return response;
    }
    
  
    
    @DELETE
	@RolesAllowed({ADMIN_ROLE})
	@Path(RESOURCE_PATH_ID_PATH)
	public Response deleteMembershipCard(@PathParam(RESOURCE_PATH_ID_ELEMENT) int mid) {
		service.deleteMembershipCardById(mid);
		Response response = Response.ok().build();
		return response;
	}
    
    
    
}
