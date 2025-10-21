package com.magnolia.cione.admin.apps.elastic;

import javax.inject.Inject;

import com.magnolia.cione.utils.CioneAppUtils;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.VerticalLayout;

import info.magnolia.i18nsystem.SimpleTranslator;
 
public class ElasticSubAppViewImpl implements ElasticSubAppView {
    private VerticalLayout layout = new VerticalLayout();
    private Listener listener;
    private final SimpleTranslator i18n;
    private final RichTextArea rtarea = new RichTextArea();
 
    @Inject
    public ElasticSubAppViewImpl(SimpleTranslator i18n) {
        this.i18n=i18n;
        layout.setStyleName("elastic-app-style");
        layout.setMargin(true);
        layout.setSpacing(false);
        layout.setHeight("50%");
        layout.setWidth("60%");
        //Label label = new Label("Selecciona el tipo de documentacion que desea indexar");
        Label label = new Label(i18n.translate("cione-app.component.label.selection"));
        label.addStyleName("label-selection");
        layout.addComponent(label);
        
        addButton(CioneAppUtils.ALBARANES);
        addButton(CioneAppUtils.FACTURAS);
    }
 
    @Override
    public void setListener(Listener listener) {
        this.listener = listener;
    }
 
    @Override
    public Component asVaadinComponent() {
        return layout;
    }
 
    @Override
    public void addButton(final String name) {
        Button button = new Button("Index " + name + "!");
        button.addStyleName("buttom-elastic");
        button.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
            	layout.removeComponent(rtarea);
                listener.indexLauncher(name);
            }
        });
        layout.addComponent(button);

    }

/*    @Override
    public void addMessage(final String message) {
    	
    	
    	
    	Label label = new Label("Proceso de volcado ejecutado ");
        label.addStyleName("elastic-response");
        
        Notification.show(message);
        
        final RichTextArea rtarea = new RichTextArea();
        //rtarea.setCaption("RESULTADO OPERACION");
        //rtarea.setValue("<h1>Configuration Example</h1>\n");
        rtarea.setValue(message);
        	rtarea.setReadOnly(true);
        layout.addComponent(rtarea);
        
        Button button = new Button("volver");
        button.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                listener.volver();
            }
        });
        layout.addComponent(label);
        layout.addComponent(button);
    }*/
    
    @Override
    public void addMessage(final String message) {
        //rtarea.setCaption("RESULTADO OPERACION");
        //rtarea.setValue("<h1>Configuration Example</h1>\n");
        rtarea.addStyleName("richArea-elastic");
        rtarea.setValue(message);
        	rtarea.setReadOnly(true);
        layout.addComponent(rtarea);
    } 
    


}