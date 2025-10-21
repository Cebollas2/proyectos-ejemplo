
package com.magnolia.cione.admin.apps.elastic;
 
import com.google.inject.ImplementedBy;

import info.magnolia.ui.api.view.View;
 

@ImplementedBy(ElasticSubAppViewImpl.class)
public interface ElasticSubAppView extends View {
    void setListener(Listener listener);
    void addButton(String user);
    void addMessage(String message);
 
    public interface Listener {
        void indexLauncher(String user);
        void volver();
    }
}