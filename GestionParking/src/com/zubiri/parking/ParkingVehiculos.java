package com.zubiri.parking;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ParkingVehiculos {
	
	private static ArrayList<Vehiculo> vehiculos = new ArrayList<Vehiculo>();
	
	public ParkingVehiculos() {
		
	}
	
	public ParkingVehiculos(Scanner sc) throws Exception {
		
		int seleccion = -1;
		
		do {
			try {
				System.out.println("Cuantos vehiculos quieres insertar? ");
				seleccion = sc.nextInt();
			} catch (InputMismatchException exception) {
				System.out.println("Introduce un número");
				sc.nextLine();
			}
		} while (seleccion < 0);
		
		for (int i = 0; i < seleccion; i++) {
			int seleccionvehiculo = 0;
			do {
				try {
					System.out.println("Que quieres insertar?");
					System.out.println("BICICLETA:--------1");
					System.out.println("COCHE:----------2");

					seleccionvehiculo = sc.nextInt();
				
					switch(seleccionvehiculo) {
						case 1:
							Bicicleta bicicleta = new Bicicleta(sc);
							vehiculos.add(bicicleta);
							break;
						case 2:
							Coche coche = new Coche(sc);
							vehiculos.add(coche);
							break;
						default:
							System.out.println("No ha insertado la opcion correcta.");
							break;			
					}
				} catch (InputMismatchException e){
					System.out.println("Eso no es numero");
					sc.nextLine();
				}	
			} while (seleccionvehiculo == 0);
		}		
	}

	public static ArrayList<Vehiculo> getVehiculos() {
		return vehiculos;
	}

	public static void setVehiculos(ArrayList<Vehiculo> vehiculos) {
		ParkingVehiculos.vehiculos = vehiculos;
	}

	// Añadir
	public static void anyadirVehiculo(Vehiculo vehiculo){
		vehiculos.add(vehiculo);
	}
	
	public static void anyadirCoche(Coche coche){
		vehiculos.add(coche);
	}
	
	public static void anyadirBicicleta(Bicicleta bicicleta){
		vehiculos.add(bicicleta);
	}
	
	// Buscar
	public static Vehiculo buscarVehiculo(String matricula) {
		int i;
		int posicion=-1;
		for(i =0; i<vehiculos.size(); i++) {
			if (vehiculos.get(i).getMatricula().equalsIgnoreCase(matricula)) {
				//System.out.println(vehiculos.get(i).formatted());
				posicion=i;
				break;
			}
		}
		if (i == vehiculos.size()) {
			System.out.println("No se ha encontrado la matricula");
		}
		return vehiculos.get(posicion);
	}
	
	// Modificar
	public static void modificarVehiculo(String matricula) {
		for(int i=0; i<vehiculos.size(); i++) {
			if (vehiculos.get(i).getMatricula().equalsIgnoreCase(matricula)) {
				Scanner sc = new Scanner(System.in);
				Coche coche = new Coche(sc);
				vehiculos.set(i, coche);
			}
		}
	}
	
	// Borrar	
	public static void borrarVehiculo(String matricula){
		for (int b = 0; b < vehiculos.size(); b++) {
			if (vehiculos.get(b).getMatricula().equalsIgnoreCase(matricula)) {
				vehiculos.remove(b);
			}
		}
	}
	
	// Mostrar
	public static void mostrarParkingVehiculos() {
		if (getVehiculos().size() == 0) {
			System.out.println("No se han cargado los vehículos");
		}
		
		for (int i=0; i<getVehiculos().size(); i++) {
			Vehiculo vehi = getVehiculos().get(i);
			vehi.mostrarVehiculo();
			System.out.println();
		}
	}
	
	public String formattedParkingVehiculos() {
		String parkingStr = "";
		
		for (int i = 0; i < vehiculos.size(); i++) {
			parkingStr += vehiculos.get(i).formatted();
		}

		return parkingStr;
	}
	
	public static void leerVehiculos() {
		
		// Leer "clientes.txt"
		try {
			
			BufferedReader br2 = new BufferedReader(new FileReader("ficheros/vehiculos.txt"));

			String linea2 = br2.readLine();

			// Creamos un array de tipo String para separar los campos del fichero
			String [] camposSeparados2 = null;

			while (linea2 != null) {

				// Creamos los objetos que participan en el fichero "vehiculos.txt"
				Coche coche = new Coche();

				// Separamos las lineas obtenidas (linea2) mediante ", " y lo guardamos en "camposSeparados2"
				camposSeparados2 = linea2.split(", ");

				// Introducimos los valores capturados del fichero en los objetos creados
				coche.setNumRuedas(Integer.parseInt(camposSeparados2[0]));
				coche.setMotor(Boolean.parseBoolean(camposSeparados2[1]));
				coche.setMarca(camposSeparados2[2]);
				coche.setMatricula(camposSeparados2[3]);
				coche.setAutomatico(Boolean.parseBoolean(camposSeparados2[4]));
				coche.setConsumo100km(Integer.parseInt(camposSeparados2[5]));

				// Añadimos el objeto "cliente" al ArrayList "arrayCliente"
				vehiculos.add(coche);

				linea2 = br2.readLine();
			}
			br2.close();
			
		} catch(IOException e) {
			System.out.println("Error E/S: "+e);
		}
	}
}