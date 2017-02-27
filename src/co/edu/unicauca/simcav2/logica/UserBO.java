package co.edu.unicauca.simcav2.logica;

import javax.faces.context.FacesContext;

/**
 * Esta clase se encarga de la lógica relacionada con el acceso del usuario 
 * 
 * @version 1.0 10 Sept 2015
 * @author Giovanny Palacios - DivTIC Unicauca
 */
public class UserBO {

	/**
	 * Este método se encarga de validar el acceso del usuario y su información en el servidor LDAP. 
	 * Debía recibir parámetros, en este ejemplo se accede sin parámetros de validación.
	 * 
	 * @return 1, si se realizó correctamente la validación, 0 en caso contrario.
	 */
	public int validateUser() {
		int result = 0;
		/* 
		 * Si el Project Stage está en Development, no se realiza la validación en el LDAP
		 */
		if (FacesContext.getCurrentInstance().getApplication().getProjectStage().toString().equals("Development")) result = 1;
		 // else result = validateLDAP(parametros);
		
		if (result == 1) {			
			
			// this.loadProfiles(user); aquí se deben cargar los perfiles que tenga el usuario
			System.out.println("Usuario Validado, resultado: " + result);
		}
		return result;
	}
}
