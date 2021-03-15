package app;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;


public class App {
	
	//Declaracion de variables
	
	static int PUERTO = 14147;
	static final String HOST = "localhost";
	static String USUARIO = "admin";
	static String PASSWORD = "admin";
	

	public static void main(String[] args) throws IOException {
		//Conexion FTP
		FTPClient ftpClient = new FTPClient();
		
		ftpClient.connect(HOST);
		if (ftpClient.getReplyCode() == 220) {
			System.out.println(ftpClient.getReplyString());
			System.out.println("Servidor en marcha");
		}
		System.out.println("1. Iniciar sesion\n2. Cerrar sesion\n3. Listar ficheros y directorios\n4. Subir fichero"
				+ "\n5. Descargar fichero del servidor\n6. Eliminar fichero\n7. Eliminar directorio"
				+ "\n8. Crear directorio\n9. Cambiar el directorio actual\n10. Establecer conexion remotamente"
				+ "\n11. Añadir interfaz grafica");
		
		
		 		Scanner sc = new Scanner(System.in);
		 		
		 		
		 		//1. Iniciar sesion
				System.out.println("Nombre de usuario:\n");
				String usuario = sc.nextLine();
				System.out.println("Contraseña:\n");
				String contraseña = sc.nextLine();
				boolean isLogged;
				if (usuario.contentEquals("") && contraseña.contentEquals("")) {
					isLogged = ftpClient.login(USUARIO, PASSWORD);
				} else {
					isLogged = ftpClient.login(usuario, contraseña);
				}

				if (isLogged) {
					System.out.println("Login correcto...");
				} else {
					System.out.println("Login incorrecto...");
				}

				
				
				//3. Listar ficheros y directorios
				
				System.out.println("Directorio actual: " + ftpClient.printWorkingDirectory());

				FTPFile[] files = ftpClient.listFiles();
				System.out.println("Ficheros en el directorio actual: " + files.length);
				for (int i = 0; i < files.length; i++) {
					System.out.println(files[i]);
				}

				//4. Subir fichero"
				System.out.println("Indica la ruta a la que quieres subir el fichero:");
				
				FileInputStream input = new FileInputStream("C:\\Users\\Oscar-PC\\Desktop\\hola.txt"); 
				ftpClient.storeFile("hola.txt", input); 
				System.out.println("Fichero subido con exito");
				
				
				//5. Descargar fichero del servidor
				System.out.println("Introduce el nombre del fichero que se quiere descargar");
	            OutputStream output = new FileOutputStream("C:\\Users\\Oscar-PC\\Desktop\\ficheroServer.txt");
	            ftpClient.retrieveFile("ficheroServer.txt", output);
	            System.out.println("Fichero descargado con exito");
				
	            //6. Eliminar fichero
				System.out.println("Introduce el nombre del fichero a eliminar");
				ftpClient.deleteFile(sc.nextLine());
				
				//7. Eliminar directorio"
				System.out.println("¿Que directorio deseas eliminar?");
				ftpClient.removeDirectory(sc.nextLine());
				//8. Crear directorio
				System.out.println("¿Que directorio deseas crear?");
				ftpClient.makeDirectory(sc.nextLine());		
				//9. Cambiar el directorio actual
				System.out.println("¿Cual sera su nueva direccion?");
				String nuevoDirectorio = (sc.nextLine());
				if (nuevoDirectorio.equals("..")) {
					ftpClient.changeToParentDirectory();

				} else {
					ftpClient.changeWorkingDirectory(nuevoDirectorio);

				}

				System.out.println("Directorio actual: " + ftpClient.printWorkingDirectory());
		//Desconexion
		ftpClient.disconnect();
		System.out.println("Conexion cerrada");
	}
}
