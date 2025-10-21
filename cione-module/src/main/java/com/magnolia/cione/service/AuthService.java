package com.magnolia.cione.service;

import java.util.List;

import javax.jcr.AccessDeniedException;
import javax.jcr.InvalidItemStateException;
import javax.jcr.ItemExistsException;
import javax.jcr.ReferentialIntegrityException;
import javax.jcr.RepositoryException;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.version.VersionException;
import javax.ws.rs.core.Response;

import com.google.inject.ImplementedBy;
import com.magnolia.cione.dto.ChangePasswordDTO;
import com.magnolia.cione.dto.EmployeeDTO;
import com.magnolia.cione.dto.TermsDTO;
import com.magnolia.cione.dto.UserERPCioneDTO;
import com.magnolia.cione.exceptions.CioneException;

import info.magnolia.cms.security.User;

@ImplementedBy(AuthServiceImpl.class)
public interface AuthService {

	
	/**
	 * Register user in PUR
	 * @param numSocio
	 * @param idClientERP
	 * @throws CioneException
	 */
	public void registerUser(String numSocio, String idClientERP) throws CioneException;
	
	/**
	 * Update user in PUR
	 * @param numSocio
	 * @param oldRol
	 * @param newRol
	 * @throws CioneException
	 */	
	public Response updateUserRol(String numSocio, String oldRol, String newRol);
	
	public void addUserRol(String numSocio, String newRol);
	
	/**
	 * Remove OM level
	 * @param User
	 * @param userId
	
	public void removeOM(User user, String userid); */	
	
	public User getUserFromPUR(String idClient);
	
	/**
	 * Activate/Deactivate user pur
	 * @param idClient
	 * @param active
	 * @throws CioneException 
	 */
	public void setActivateUserInPUR(String idClient, Boolean active);
	
	
	/**
	 * Recovery password from user 
	 * @param idClient
	 * @throws CioneException
	 */
	public void recoveryPassword(String idClient) throws CioneException;
	
	

	public void sendToAuthorAuditoryUser(String idClient, String newsId) throws CioneException;
	
	public void updateAuditory(String idClient, String newsId) throws AccessDeniedException, ItemExistsException, ReferentialIntegrityException, ConstraintViolationException, InvalidItemStateException, VersionException, LockException, NoSuchNodeTypeException, RepositoryException;
	
				

	/**
	 * Check if an user exists into pur
	 * 
	 * @param username
	 * @return
	 */
	public boolean existsUserInPur(String idClient);
	 
	/**
	 * Change password to public user
	 * @param changePasswordDTO
	 * @throws CioneException
	 */
	public void changePassword(ChangePasswordDTO changePasswordDTO) throws CioneException;
	
	
	public List<UserERPCioneDTO> getUsersFromPur();
	
	public List<UserERPCioneDTO> getUsersFromPur(String username, String fullName);
	
	
	public String impersonateUser(String usernameToImpersonate, String usernameImpersonator) throws CioneException;
	
	public String removeImpersonateUser(String usernameImpersonator) throws CioneException;
	
	
	public boolean isCioneSuperUser(String username);
	
	/**
	 * Registra un empleado en PUR. Un empleado esta asociado a un socio y debe tener roles especificos
	 * @param empleadoDTO DTO con los datos del empleado
	 */
	public Response registerOrUpdateEmployee(EmployeeDTO employeeDTO);
	
	/**
	 * Elimina un empleado en PUR.
	 * @param empleadoDTO DTO con el id del empleado a eliminar
	 */
	public void removeEmployee(EmployeeDTO employeeDTO);

	void removeUserRol(String numSocio, String newRol);
	
	public Response submitTerms(TermsDTO termsDTO);
	
	/**
	 * Devuelve true en caso de que sea necesario mostrar las condiciones comerciales
	 */

	public boolean getTerms();
	
	/**
	 * Servicio para el logado del foro
	 * @param String url del endpoint del foro
	 * @param String userId id Usuario
	 */
	public Response loginForo(String url, String userId);
	
	/**
	 * Devuelve los datos encriptados para el sso contra flarum
	 * encripta el codUsuario, mail de Magnolia y el timestamp
	 * @return 
	 * 
	 */
	public String getNumSocioEncrypt();

	/**
	 * Devuelve la url para realizar el sso con la pagina de servicios
	 * @return String con la url compuesta por url ?rest_route=/simple-jwt-login/v1/autologin&JWT=token_generado&cione_auth=
	 * 
	 */
	public String getSSOServices();
	
}
