package com.magnolia.cione.admin.apps.elastic;

import javax.inject.Inject;

import info.magnolia.ui.api.app.SubAppContext;
import info.magnolia.ui.api.location.Location;
import info.magnolia.ui.api.view.View;
import info.magnolia.ui.framework.app.BaseSubApp;

public class ElasticDetailSubApp extends BaseSubApp{

    private String name;
    
    @Inject
    protected ElasticDetailSubApp(final SubAppContext subAppContext, final ElasticDetailSubAppView view) {
        super(subAppContext, view);
    }
 
    /**
     * Extracts the name to greet from the {@link Location}s parameter and passes it to the {@link ElasticDetailSubAppView}.
     */
    @Override
    protected void onSubAppStart() {
        this.name = getCurrentLocation().getParameter();
        getView().setGreeting(name);
    }
 
    @Override
    public ElasticDetailSubAppView getView() {
        return (ElasticDetailSubAppView)super.getView();
    }
 
    /**
     * Used to set the label on the tab.
     */
    @Override
    public String getCaption() {
        return name;
    }
 
    /**
     * In case there is a subApp instance running, here the decision is made whether this instance will handle the new {@link Location}.
     */
    @Override
    public boolean supportsLocation(Location location) {
        String newUser = location.getParameter();
        return getCurrentLocation().getParameter().equals(newUser);
    }
}
