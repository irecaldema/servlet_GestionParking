import com.zubiri.parking.ParkingVehiculos;
import com.zubiri.parking.Vehiculo;
import com.zubiri.parking.Coche;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GestiorParking
 */
@WebServlet(
		description = "gestion del parking", 
		urlPatterns = { "/Gestor" }				
		)
public class GestorParking extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GestorParking() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (ParkingVehiculos.getVehiculos().size()==0){
			//lectura del archivo
		ParkingVehiculos.leerVehiculos();
		//ParkingVehiculos.anyadirVehiculo(new Coche(4,true,"marca_prueba","0000AAA",true,50));
		//ParkingVehiculos.anyadirVehiculo(new Coche(4,true,"ferrari","0001ABA",true,100));
		//ParkingVehiculos.anyadirVehiculo(new Coche(4,true,"fiat","0002ACA",true,10));
		}
		String gestion=request.getParameter("gestion");
		System.out.println(gestion);
		if (gestion.equals("mostrar_vehiculos")){
			System.out.println("empieza mostrando");
			response(response,ParkingVehiculos.getVehiculos());
		}else if(gestion.equals("buscar_matricula")){
			System.out.println("empieza buscando");
			String matricula=request.getParameter("matricula");
			Vehiculo encontrado = new Coche();
			try{
				encontrado = ParkingVehiculos.buscarVehiculo(matricula);
				response(response, encontrado);
			}catch(ArrayIndexOutOfBoundsException e){
				response(response, "no se encontro el vehiculo");
			}			
		}else if(gestion.equals("anyadir_vehiculo")){
			System.out.println("empieza anyadiendo");
			int n_ruedas = Integer.parseInt(request.getParameter("numruedas"));
			boolean motor = Boolean.parseBoolean(request.getParameter("motor"));
			String marca = request.getParameter("marca");
			String matricula = request.getParameter("matricula");
			boolean automatico = Boolean.parseBoolean(request.getParameter("automatico"));
			int consumo = Integer.parseInt(request.getParameter("consumo"));	
			System.out.println("new coche");
			Coche nuevo = new Coche(n_ruedas,motor,marca,matricula,automatico,consumo);
			ParkingVehiculos.anyadirVehiculosFichero(nuevo);
			if(ParkingVehiculos.buscarVehiculo(matricula)==nuevo){
				response(response, "vehiculo anyadido");
			}else{
				response(response, "error al anyadir vehiculo");
			}
		}else if(gestion.equals("borrar_vehiculo")){
			System.out.println("borrando");
			String matricula = request.getParameter("matricula");
			ParkingVehiculos.borrarVehiculosFichero(matricula);
			ParkingVehiculos.borrarVehiculo(matricula);
			response(response, "Se ha borrado el vehiculo");
		}else if(gestion.equals("modificar_vehiculo")){

		}
		//ParkingVehiculos pv = new ParkingVehiculos();
		//response(response,"prueba");
		System.out.println("fin");		
	}
	
	private void response(HttpServletResponse response, ArrayList<Vehiculo> vehiculos)
			throws IOException {
			PrintWriter out = response.getWriter();
			out.println("<html>");
			out.println("<body>");
			out.println("<p>-------------------------------</p>");
			for (int i=0;i<vehiculos.size();i++){				
				out.println("<b>matricula:</b> "+vehiculos.get(i).getMatricula()+" | ");
				out.print("<b>marca:</b> "+vehiculos.get(i).getMarca()+"");
				out.println("<p>-------------------------------</p>");
			}
			out.println("<a href='index.html'><button/>volver</a>");
			out.println("</body>");
			out.println("</html>");
		}
	
	private void response(HttpServletResponse response,String msg)
			throws IOException {
			PrintWriter out = response.getWriter();
			out.println("<html>");
			out.println("<body>");				
			out.println("<p>"+msg+"</p>");
			out.println("<a href='index.html'><button/>volver</a>");
			out.println("</body>");
			out.println("</html>");
		}
	
	private void response(HttpServletResponse response, Vehiculo coche)
			throws IOException {
			PrintWriter out = response.getWriter();
			out.println("<html>");
			out.println("<body>");
			out.println("<p>"+coche.getMarca()+"</p>");
			out.println("<p>"+coche.getMatricula()+"</p>");
			out.println("<a href='index.html'><button/>volver</a>");
			out.println("</body>");
			out.println("</html>");
		}

}
