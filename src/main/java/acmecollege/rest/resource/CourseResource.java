/**
 * File:  CourseResource.java
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

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import static acmecollege.utility.MyConstants.COURSE_RESOURCE_NAME;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.security.enterprise.SecurityContext;
import static acmecollege.utility.MyConstants.ADMIN_ROLE;
import static acmecollege.utility.MyConstants.USER_ROLE;

import java.util.List;

import acmecollege.ejb.ACMECollegeService;
import acmecollege.entity.Course;

@Path(COURSE_RESOURCE_NAME)
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CourseResource {
	
	private static final Logger LOG = LogManager.getLogger();
	
	@EJB
    protected ACMECollegeService acmeService;
	
	@Inject
    protected SecurityContext sc;
	
	 @GET
	 @RolesAllowed({ADMIN_ROLE, USER_ROLE})
	 public Response getAllCourses() {
	        LOG.debug("retrieving all courses ...");
		 List<Course> courses = acmeService.getAllCourses();
		 Response response = Response.ok(courses).build();
		 return response;
	 }
	 
	 @GET
	 @RolesAllowed({ADMIN_ROLE, USER_ROLE})
	 @Path("/{cid}")
	 public Response getCourseWithId(@PathParam("cid") int cid) {
		 Course course = acmeService.getCourseWithId(cid);
		 Response response = Response.ok(course).build();
		 return response;
		 
	 }
	 
	  
	    @POST
	    @RolesAllowed({ADMIN_ROLE})
	    public Response addCourse(Course newCourse) {
	        Response response = null;
	        Course newCourseWithIdTimestamps = acmeService.persistCourse(newCourse);
	        response = Response.ok(newCourseWithIdTimestamps).build();
	        return response;
	    }
	    
	    @DELETE
	  	@RolesAllowed({ADMIN_ROLE})
	  	@Path("/{id}")
	  	public Response deleteCourse(@PathParam("id") int id) {
	  		acmeService.deleteCourseById(id);
	  		Response response = Response.ok().build();
	  		return response;
	  	}
	

}
