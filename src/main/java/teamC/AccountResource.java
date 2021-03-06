package teamC;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.multipart.FormDataMultiPart;

@Path("accounts")
public class AccountResource {
	private final AccountDAO accDao = new AccountDAO();

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Account auth(final FormDataMultiPart form, @Context final HttpServletRequest request) throws WebApplicationException {
		Account log = new Account();
		log.setLoginId(form.getField("js-input-loginId").getValue());
		log.setLoginPass(form.getField("js-input-loginPass").getValue());

		HttpSession session = request.getSession(false);

		if (session == null){
		  /* まだ認証されていない */
		  session = request.getSession(true);
		}else{
		  Object loginCheck = session.getAttribute("login");
		  if (loginCheck == null){
		    /* まだ認証されていない */
		  }else{

		  }
		}

		boolean auth = accDao.auth(log).isLogined();

		if(auth==true){
			session.setAttribute("loginId", log.getLoginId());
			session.setAttribute("admin", log.isAdmin());
			session.setAttribute("userName", log.getUserName());
			System.out.println(session.getAttribute("loginId"));
			System.out.println(session.getAttribute("admin"));
			System.out.println(session.getAttribute("userName"));
		}
		return log;
	}
}
