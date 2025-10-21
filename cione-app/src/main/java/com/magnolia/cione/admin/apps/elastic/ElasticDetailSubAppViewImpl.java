package com.magnolia.cione.admin.apps.elastic;

import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class ElasticDetailSubAppViewImpl implements ElasticDetailSubAppView{

    VerticalLayout layout = new VerticalLayout();
    
    public ElasticDetailSubAppViewImpl() {
        layout.setMargin(true);
        layout.setSpacing(true);
    }
    @Override
    public Component asVaadinComponent() {
        return layout;
    }
    @Override
    public void setGreeting(String name) {
        layout.addComponent(new Label("Hello " + name + "!"));
    }

}
