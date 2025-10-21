package com.magnolia.cione.admin.apps.elastic;
 
import java.io.IOException;
import java.util.Properties;

import javax.inject.Inject;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.magnolia.cione.utils.CioneAppUtils;

import info.magnolia.i18nsystem.SimpleTranslator;
import info.magnolia.ui.api.app.SubAppContext;
import info.magnolia.ui.api.location.DefaultLocation;
import info.magnolia.ui.api.location.LocationController;
import info.magnolia.ui.api.message.Message;
import info.magnolia.ui.api.message.MessageType;
import info.magnolia.ui.framework.app.BaseSubApp;
import info.magnolia.ui.vaadin.overlay.MessageStyleTypeEnum;

 
// As the presenter's duty, it implements the View's Listener providing the View's callback method to the presenter.
public class ElasticSubApp extends BaseSubApp<ElasticSubAppView> implements ElasticSubAppView.Listener {
    private LocationController locationController;
    private static final Logger log = LoggerFactory.getLogger(ElasticSubApp.class);
    private static final Properties prop = new Properties();
    private final SimpleTranslator i18n;	
     
    // The View is injected into the presenter's constructor. The according View's implementation is defined by IOC component provider.
    @Inject
    public ElasticSubApp(final SubAppContext subAppContext, ElasticSubAppView view, LocationController locationController, SimpleTranslator i18n) {
        super(subAppContext, view);
        this.locationController = locationController;
        this.i18n=i18n;
        try {
			prop.load(ElasticSubApp.class.getClassLoader().getResourceAsStream("config-app.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
     
    @Override
    protected void onSubAppStart() {
        // Register this presenter as the view's listener.
        getView().setListener(this);
        // Call the view to display something.

    }
     
    // Implements the View's Listener method. Is the View's callback function to the presenter.
    @Override
    public void indexLauncher(String index) {
    	if (index.equals(CioneAppUtils.ALBARANES))
    		generateView("/volcado-albaran");
    	if (index.equals(CioneAppUtils.FACTURAS))
    		generateView("/volcado-factura");
        //locationController.goTo(new DefaultLocation(DefaultLocation.LOCATION_TYPE_APP, getAppContext().getName(), "greeter", index));
    }
    
    private void generateView(String service) {
		String response = launchIndex(service);
        final Message messageUI = new Message();
        messageUI.setMessage(response);
        messageUI.setType(MessageType.INFO);
        messageUI.setSubject("elastic");
        //messageUI.setSubject(i18n.translate("ui-framework.app.appdescriptorReadError.subject"));
       
        // Name of the view as defined in the configuration
        //messageUI.setView("elastic");
		getSubAppContext().getAppContext().sendLocalMessage(messageUI);
		getSubAppContext().openNotification(MessageStyleTypeEnum.INFO, true, "OK!");
		
		getView().addMessage(response);
    }
    
    @Override
    public void volver() {
    	locationController.goTo(new DefaultLocation(DefaultLocation.LOCATION_TYPE_APP, "elastic", "main"));
    	
    	
    	//locationController.goTo(new DefaultLocation(DefaultLocation.LOCATION_TYPE_APP, getAppContext().getName(), "greeter", index));
    }
    
    private String launchIndex(String service) {
    	String message;
    	ResteasyClient client = null;
		try {
			//client = new ResteasyClientBuilder().build();
			client = (ResteasyClient)ClientBuilder.newClient();
			ResteasyWebTarget target = client
					.target(prop.getProperty(CioneAppUtils.API_ELASTIC_PATH) + service);
			
			Response response = target.request(MediaType.TEXT_HTML).get();
			
			message = response.readEntity(String.class);
			if (response.getStatus() != 200) {
				log.error("Error en el volcado de : " + service + " :" + response.getStatus());
				message = "Error en el volcado de : " + service + " :" + response.getStatus();
			} else {
				log.debug("Proceso de volcado realizado correctamente ");
			}
			client.close();
		} catch (Exception e) {
			log.error(i18n.translate("cione-app.error.dump"),e);
			message = "Error " + e.getCause();
		} finally {
			if (client != null)
				client.close();
		}
		return message;
    }
    
}