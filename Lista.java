package practica_4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lista {

	static File fichero;
	ArrayList<Empleado> lista_Empleados;

	// CONSTRUCTORES

	Lista() throws IOException {
		this.fichero = new File("src/", "Empleados.txt");
		this.lista_Empleados = new ArrayList<Empleado>();
	}

	// METODOS GET/SET
	public ArrayList<Empleado> getLista_Empleados() {
		return lista_Empleados;
	}

	public void setLista_Empleados(ArrayList<Empleado> lista_Empleados) {
		this.lista_Empleados = lista_Empleados;
	}

	// METODOS IMPLEMENTADOS
	// ESCRIBIR EN FICHERO LA LISTA (IMPRIMIR)
	public void escribirEmpleado() throws IOException {
		try {
			FileWriter fr = new FileWriter(fichero); //SE CREA EL FICHERO EN EL QUE SE VA A ESCRIBIR
			BufferedWriter escribir = new BufferedWriter(fr); // SE CREA EL FLUJO INTERMEDIO PARA PODER ESCRIBIR
			for (int i = 0; i < lista_Empleados.size(); i++) { // AL SER UN ARRAYLIST SE PASA UNO A UNO ESCRIBIENDO
				escribir.write(lista_Empleados.get(i).toString()); // SE ESCRIBE UNO A UNO
				escribir.newLine(); // SE PONE UN ESPACIO ENTRE CADA EMPLEADO

			}
			escribir.close(); // SE CIERRA EL FLUJO INTERMEDIO
		} catch (FileNotFoundException e) {
			System.err.println("Error");
		}
	}

	// LEER ARCHIVO HASTA QUE COINCIDA EL NIF CON EL BUSCADO
	public static ArrayList consultarEnLista(String nif) {
		ArrayList<Empleado> lista_workers = new ArrayList<Empleado>();
		Scanner scanner;
		try {
			// SE PASA EL FLUJO AL SCANNER
			scanner = new Scanner(fichero);
			boolean acabado = false;
			while (scanner.hasNextLine() && !acabado) {
				// SCANNER LEER LINEA A LINEA DESDE EL ARCHIVO
				String linea = scanner.next();
				Scanner delimitar = new Scanner(linea);
				// SE DELIMITA QUE ANTES O DESPUES DE DOS PUNTOS (:) EXISTA
				// CUALQUIER COSA PARTE DE LA CADENA RECIBIDA
				String dni, nombre, apellidos;
				double salario;
				delimitar.useDelimiter("\s*:\s*");
				dni = scanner.next();
				scanner.next();
				nombre = scanner.next();
				scanner.next();
				apellidos = scanner.next();
				scanner.next();
				salario = Double.parseDouble(scanner.next());
				Empleado e = new Empleado();
				e.setNif(dni);
				e.setNombre(nombre);
				e.setApellidos(apellidos);
				e.setSalario(salario);
				lista_workers.add(e);
				if (e.getNif().equals(nif)) { //SI EL EMPLEADO AUXILIAR E SIGUAL AL NIF QUE BUSCAMOS
					System.err.println(e.toString()); //IMPRIMIMOS SUS DATOS
					acabado = true; 
				}
			}

			// SE CIERRA SCANNER
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return lista_workers;
	}

	// HACER UNA CONSULTA
	public void conultar() {
		Scanner entrada = new Scanner(System.in);
		System.out.print(
				"Has seleccionado CONSULTAR, introduce el NIF del mepleado que desee consultar: ");
		String nif = entrada.next();

		try {
			if (validarNif(nif) == true && existeEmpleado(nif) == true) {

				System.out.println("Se esta buscando al empleado... \n");
				consultarEnLista(nif);
			} else if (validarNif(nif) == false) {
				System.err.println(
						"Por favor, introduce un NIF con 8 dígitos y una letra");
			} else if (existeEmpleado(nif) == false) {
				System.err.println("No existe el empleado con NIF: " + nif);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// LISTAR LOS EMPLEADOS
	public void listar() throws IOException {
		String todo = "";
		try (BufferedReader br = new BufferedReader(
				new FileReader(this.fichero))) {
			String linea;
			while ((linea = br.readLine()) != null)
				todo = todo + linea + "\n";
		}
		System.out.println(todo);
	}

	// TODO MODIFICAR EMPLEADO
	// VALIDAR EL NIF QUE TE DA EL CLIENTE
	public boolean validarNif(String nif) throws Exception {

		boolean aux = false;
		try {
			Pattern pat = Pattern.compile("[0-9]{8,9}[A-Z a-z]");
			Matcher mat = pat.matcher(nif);
			if (mat.matches() == true) {
				aux = true;
			}
		} catch (Exception e) {
			System.err.println("Introduce 8 dígitos y una letra.");
		}
		return aux;

	}

	// VALIDAR EL NOMBRE Y APELLIDOS QUE TE DA EL CLIENTE
	public boolean validarNombre(String nombre) throws Exception {

		boolean aux = false;
		try {
			Pattern pat = Pattern.compile("[A-Z a-z]{0,10}");
			Matcher mat = pat.matcher(nombre);
			if (mat.matches() == true) {
				aux = true;
			}
		} catch (Exception e) {
			System.err.println("Introduce 10 cáracteres como máx.");
		}
		return aux;

	}

	// BUSCAR EMPLEADO EN LA LISTA
	public String buscarEmpleado(String nif) {
		Empleado aux = new Empleado();
		for (int i = 0; i < this.lista_Empleados.size(); i++) {
			if (nif.equals(this.lista_Empleados.get(i).nif)) {
				aux = this.lista_Empleados.get(i);
			}
		}
		return aux.toString();
	}

	// EXISTE EMPLEADO EN LA LISTA
	public boolean existeEmpleado(String nif) {
		boolean aux = false;
		for (int i = 0; i < this.lista_Empleados.size(); i++) {
			if (nif.equals(this.lista_Empleados.get(i).nif)) {
				aux = true;
			}
		}
		return aux;
	}

	// ANADIR A LA LISTA DE EMPLEADOS
	public void anadirEmpleado(Empleado worker) {
		lista_Empleados.add(worker);
		try {
			escribirEmpleado();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// HACER LA INSERCCION DEL NUEVO EMPLEADO
	public void inserccion() {
		Scanner entrada = new Scanner(System.in);
		System.out.print(
				"Has seleccionado INSERCCIÓN, introduce el NIF del empleado que desee insertar: ");
		String nif = entrada.next();

		try {
			if (validarNif(nif) == true && existeEmpleado(nif) == true) {
				System.out.println("Ya existe el empleado con NIF: " + nif);

			} else if (existeEmpleado(nif) == false) {
				System.out.println("Introduce el nombre: ");
				String nombre = entrada.next();
				if (validarNombre(nombre) == true) {
					System.out.println("Introduce los apellidos: ");
					String apellidos = entrada.next();
					if (validarNombre(apellidos) == true) {
						System.out.println("Introduce el salario: ");
						double salario = entrada.nextDouble();
						Empleado aux = new Empleado(nif, nombre, apellidos,
								salario);
						anadirEmpleado(aux);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// ELIMINAR UN EMPLEADO
	public void eliminacion() {
		Scanner entrada = new Scanner(System.in);
		System.out.print(
				"Has seleccionado BORRADO, introduce el NIF del mepleado que desee borrar: ");
		String nif = entrada.next();

		try {
			if (validarNif(nif) == true && existeEmpleado(nif) == true) {
				borrar(nif);
			} else if (validarNif(nif) == false) {
				System.err.println(
						"Por favor, introduce un NIF con 8 dígitos y una letra");
			} else if (existeEmpleado(nif) == false) {
				System.err.println("No existe el empleado con NIF: " + nif);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// BORRAR EMPLEADO
	public void borrar(String nif) throws IOException {

		if (existeEmpleado(nif)) {
			ArrayList<Empleado> lista_workers = new ArrayList<Empleado>();
			Scanner scanner;
			try {
				// SE PASA EL FLUJO AL SCANNER
				scanner = new Scanner(fichero);
				int conta = -1;
				for (int i = 0; i < lista_Empleados.size()
						&& scanner.hasNextLine(); i++) {
					conta++;
					// SCANNER LEER LINEA A LINEA DESDE EL ARCHIVO
					String linea = scanner.next();
					Scanner delimitar = new Scanner(linea);
					// SE DELIMITA QUE ANTES O DESPUES DE DOS PUNTOS (:) EXISTA
					// CUALQUIER COSA PARTE DE LA CADENA RECIBIDA
					String dni, nombre, apellidos;
					double salario;
					delimitar.useDelimiter("\s*:\s*");
					dni = scanner.next();
					scanner.next();
					nombre = scanner.next();
					scanner.next();
					apellidos = scanner.next();
					scanner.next();
					salario = Double.parseDouble(scanner.next());
					Empleado e = new Empleado();
					e.setNif(dni);
					e.setNombre(nombre);
					e.setApellidos(apellidos);
					e.setSalario(salario);
					lista_workers.add(e);
					if (e.getNif().equals(nif)) {
						System.err.println("Vamos a eliminar el empleado: \n"
								+ e.toString());
						lista_workers.remove(conta);
						lista_Empleados.remove(conta);
					} else {
						escribirEmpleado();
					}

				}

				// SE CIERRA SCANNER
				scanner.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			System.err.println("El empleado con NIF " + nif
					+ " no aparece en nuestra lista.");
		}
	}
	// MODIFICACION UN EMPLEADO
	public void modificacion() {
		Scanner entrada = new Scanner(System.in);
		System.out.print(
				"Has seleccionado MODIFICAR, introduce el NIF del mepleado que desee modificar: ");
		String nif = entrada.next();

		try {
			if (validarNif(nif) == true && existeEmpleado(nif) == true) {
				modificar(nif);
			} else if (validarNif(nif) == false) {
				System.err.println(
						"Por favor, introduce un NIF con 8 dígitos y una letra");
			} else if (existeEmpleado(nif) == false) {
				System.err.println("No existe el empleado con NIF: " + nif);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// MODIFICAR EMPLEADO
	public void modificar(String nif) throws IOException {
		if (existeEmpleado(nif)) {
			ArrayList<Empleado> lista_workers = new ArrayList<Empleado>();
			Scanner scanner;
			Scanner entrada = new Scanner(System.in);
			try {
				// SE PASA EL FLUJO AL SCANNER
				scanner = new Scanner(fichero);
				int conta = -1;
				for (int i = 0; i < lista_Empleados.size()
						&& scanner.hasNextLine(); i++) {
					conta++;
					// SCANNER LEER LINEA A LINEA DESDE EL ARCHIVO
					String linea = scanner.next();
					Scanner delimitar = new Scanner(linea);
					// SE DELIMITA QUE ANTES O DESPUES DE DOS PUNTOS (:)
					// EXISTA
					// CUALQUIER COSA PARTE DE LA CADENA RECIBIDA
					String dni, nombre, apellidos;
					double salario;
					delimitar.useDelimiter("\s*:\s*");
					dni = scanner.next();
					scanner.next();
					nombre = scanner.next();
					scanner.next();
					apellidos = scanner.next();
					scanner.next();
					salario = Double.parseDouble(scanner.next());
					Empleado e = new Empleado();
					e.setNif(dni);
					e.setNombre(nombre);
					e.setApellidos(apellidos);
					e.setSalario(salario);
					lista_workers.add(e);
					if (e.getNif().equals(nif)) {
						System.err.println("Vamos a modificar el empleado: \n"
								+ e.toString());
						System.out.print("A cuanto aumenta su salario: ");
						double wage = entrada.nextDouble();
						e.setSalario(wage);
						lista_Empleados.get(conta).setSalario(wage);
					} else {
						escribirEmpleado();
					}

				}

				// SE CIERRA SCANNER
				scanner.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			System.err.println("El empleado con NIF " + nif
					+ " no aparece en nuestra lista.");
		}
	}
}
