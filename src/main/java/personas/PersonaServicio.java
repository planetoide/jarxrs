package personas;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("servicio")
public class PersonaServicio {
	// http://localhost:8080/personas/services/servicio/json
	@Path("json")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Persona getJsonRespuesta(){
		Persona yo = new Persona();
		yo.setNombre("ELvis");
		yo.setEdad(35);
		return yo;
	}
	
	//método que recibe parámetros
	@Path("{parametros}")
	@GET
	public Response bienvenido(@PathParam("parametros")String nombre){
		return Response.status(200).entity("bienvenido " + nombre).build();
	}
	
	//método que recibe varios parámetros desde la url
	@Path("{nombre}/{id}/{edad}")
	@GET
	public Response obtenerDatos(
			@PathParam("nombre")String name, 
			@PathParam("id")int id, 
			@PathParam("edad")int edad){
		
		return Response.status(200).entity("usuario: " + name + " edad: " + edad + " id: " + id).build();
	}
	
	//Método para el consumo de servicios
	@Path("consumo")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void consumoRest(Persona p){
		System.out.println("se consume un json que tiene una estructura de Persona");
		System.out.println("el nombre es: " + p.getNombre());
		System.out.println("la edad es: " + p.getEdad());
	}
	
	//método que recibe parámetros desde la url, pero sin especificarlos en el @Path, usa QueryParam
	@Path("queryparam")
	@GET
	public Response queryParametros(
			//http://localhost:8080/personas/services/servicio/queryparam?parametro1=hola&parametro2=mundo
				@QueryParam("parametro1") String parametro1,
				@QueryParam("parametro2") String parametro2
			){
		String salida = "parametro1: " + parametro1 + " parametro2" + parametro2;
		return Response.status(200).entity(salida).build();
	}
	
	//establecer valores por defecto en parámetros
	@Path("prevalores")
	@GET
	public Response valoresPred(
			//establece por defecto parametro1=valor1 y parametro2=valor2
			//url http://localhost:8080/personas/services/servicio/prevalores  
			@DefaultValue("valor1")@QueryParam("parametro1")String parametro1,
			@DefaultValue("valor2")@QueryParam("parametro2")String parametro2
			){
		String salida = "parametro1: " + parametro1 + "parametro2" + parametro2;
		return Response.status(200).entity(salida).build();
	}
	
	//Recuperar valores de url con @Context
	@Path("/contextvalor")
	@GET
	public Response valoresContexto(@Context UriInfo uri){
		//http://localhost:8080/personas/services/servicio/contextvalor?parameter1=hola&parameter2=mundo&parameter2=adios
		String parameter1 = uri.getQueryParameters().getFirst("parameter1");
		List parameter2   = uri.getQueryParameters().get("parameter2");
		String output = "Parameter1 :"+parameter1+" Parameter2 : "+parameter2.toString();
		return Response.status(200).entity(output).build();
	}
	
	//pasar parámetros por la url, los parámetros son paras de valores separados por punto y coma, usa @MatrixParam
	@Path("parametrosmatrix")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getMatrix(@MatrixParam("nueva") String nueva, @MatrixParam("id") int id){
		//url ejemplo http://localhost:8080/personas/services/servicio/parametrosmatrix;nueva=juantxo;id=2015
		 return "id: " + id + " persona " + nueva;
	}
	
	//recuperar los parámetros de un formulario
	@Path("crearPersona")
	@POST
	public Response crearP(
		//url del action services/servicio/crearPersona
		@FormParam("nombre") String nombre,
        @FormParam("edad") int edad){
		return Response.status(200).entity("nuevo usuario creado " + nombre + " edad " + edad).build();
	}
}
