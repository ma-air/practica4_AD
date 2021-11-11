package practica_4;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {
		Scanner entrada = new Scanner(System.in);
		boolean salir = false;
		Lista rrhh = new Lista();
		
		//EMPLEADO 1
		Empleado worker1 = new Empleado();
		worker1.setNif("12345678F");
		worker1.setNombre("Augusto");
		worker1.setApellidos("Su�rez");
		worker1.setSalario(2000);
		rrhh.anadirEmpleado(worker1);

		// EMPLEADO 2
		Empleado worker2 = new Empleado();
		worker2.setNif("87456123F");
		worker2.setNombre("Alfonsino");
		worker2.setApellidos("Eguez");
		worker2.setSalario(1500);
		rrhh.anadirEmpleado(worker2);

		// EMPLEADO 3
		Empleado worker3 = new Empleado();
		worker3.setNif("152374683F");
		worker3.setNombre("Patata");
		worker3.setApellidos("Patatoe");
		worker3.setSalario(2500);
		rrhh.anadirEmpleado(worker3);
		
		while (!salir) {
			try {

				System.out.print("\n0. SALIR \n" + "1. Consulta \n"
						+ "2. Insercci�n \n" + "3. Modificaci�n \n"
						+ "4. Borrado\n" + "5. Lista General \n"
						+ "******ELIGE EL NUMERO DE LA PRACTICA******: ");

				int opcion = entrada.nextInt();
				entrada.nextLine();
				if (opcion == 0) {
					System.out.println("HEMOS SALIDO SATISFACTORIAMENTE");
					salir = true;
				} else if (opcion == 1) { //HECHO
					rrhh.conultar();
				} else if (opcion == 2) { //HECHO
					rrhh.inserccion();
				} else if (opcion == 3) { //HECHO
					rrhh.modificacion();
				} else if (opcion == 4) { //HECHO
					rrhh.eliminacion();
				} else if (opcion == 5) { //HECHO
					rrhh.listar();
				} else {
					System.out.println("\nOpcion no contemplada en el menu. ");
				}
			} catch (InputMismatchException e) {
				System.err.println("\nDebes insertar un n�mero\n");
				entrada.next();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		entrada.close();
	}

}
