
package com.mycompany.mavenwebtestapp.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Neeraj
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    
     @Override
    public Map<String, Object> getProperties() {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("jersey.config.server.provider.packages", "com.mycompany.mavenwebtestapp.services");
        return properties;
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.mycompany.mavenwebtestapp.services.IngredientsFacadeREST.class);
        resources.add(com.mycompany.mavenwebtestapp.services.RecipeFacadeREST.class);
    }
    
}
