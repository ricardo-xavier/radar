package radarws;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

public class Service {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String version() {
		return "radarws v0.1.0(10/05/2020)";
	}
	
	@POST
	@Path("/atualiza")
	@Consumes(MediaType.APPLICATION_JSON)
	public String atualiza(String json) {
		System.out.println(new Date() + " radarws atualiza");
		System.out.println(json);
		Gson gson = new Gson();
		Individuo individuo = gson.fromJson(json, Individuo.class);					
		IndividuoDao.atualiza(individuo);
		return "ok";
	}
	

	@GET
	@Path("/vizinhanca/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String vizinhanca(@PathParam("id") String id) {
		System.out.println(new Date() + " radarws vizinhanca " + id);
		Vizinhanca vizinhanca = new Vizinhanca();
		Individuo individuo = IndividuoDao.recupera(id);
		if (individuo != null) {
			vizinhanca.setVizinhos(IndividuoDao.getVizinhanca(individuo));
		}
		Gson gson = new Gson();
		return gson.toJson(vizinhanca);		
	}

}

// 10/05/2020 - 0.1.0 - versão inicial