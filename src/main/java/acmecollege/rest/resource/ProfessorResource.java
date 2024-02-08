/**
 * File:  ProfessorResource.java
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
import acmecollege.entity.Professor;
import acmecollege.entity.SecurityUser;
import acmecollege.entity.Student;
import acmecollege.entity.StudentClub;
import javax.ws.rs.DELETE;
import static acmecollege.utility.MyConstants.PROFESSOR_SUBRESOURCE_NAME;
import static acmecollege.utility.MyConstants.STUDENT_COURSE_PROFESSOR_RESOURCE_PATH;
import static acmecollege.utility.MyConstants.RESOURCE_PATH_ID_ELEMENT;
import static acmecollege.utility.MyConstants.RESOURCE_PATH_ID_PATH;
import static acmecollege.utility.MyConstants.ADMIN_ROLE;
import static acmecollege.utility.MyConstants.USER_ROLE;

import java.util.List;

@Path(PROFESSOR_SUBRESOURCE_NAME)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProfessorResource {

    private static final Logger LOG = LogManager.getLogger();

    @EJB
    protected ACMECollegeService acmeService;

    @Inject
    protected SecurityContext sc;

    @GET
    @RolesAllowed({ADMIN_ROLE})
    public Response getProfessors() {
        LOG.debug("retrieving all professors ...");
        List<Professor> professors = acmeService.getAllProfessors();
        Response response = Response.ok(professors).build();
        return response;
    }
    
    @GET
	@RolesAllowed({ADMIN_ROLE, USER_ROLE})
	@Path(RESOURCE_PATH_ID_PATH)
	public Response getProfessorById(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
		LOG.debug("try to retrieve specific professor " + id);
		Professor professor = acmeService.getProfessorById(id);
		Response response= Response.ok(professor).build();
		return response;
	}
    
    @POST
	@RolesAllowed({ADMIN_ROLE})
	public Response addProfessor(Professor newProfessor) {
		LOG.debug("Adding a new professor = {}", newProfessor);
		Professor newProfessorWithIdTimestamps = acmeService.persistProfessor(newProfessor);
		Response response = Response.ok(newProfessorWithIdTimestamps).build();
		return response;
	}
    
    
    @DELETE
	@RolesAllowed({ADMIN_ROLE})
	@Path(RESOURCE_PATH_ID_PATH)
	public Response deleteProfessor(@PathParam(RESOURCE_PATH_ID_ELEMENT) int id) {
		LOG.debug("Updating a specific professor with id = {}", id);
		acmeService.deleteProfessorById(id);
		Response response = Response.ok().build();
		return response;
	}
    
    
}
