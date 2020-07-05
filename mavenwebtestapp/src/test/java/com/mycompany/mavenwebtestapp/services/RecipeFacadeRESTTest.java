package com.mycompany.mavenwebtestapp.services;

import com.mycompany.mavenwebtestapp.Ingredients;
import com.mycompany.mavenwebtestapp.Recipe;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.ServletDeploymentContext;
import org.glassfish.jersey.test.TestProperties;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author Neeraj
 * This class shows how to test a rest service 
 * however it is not working as some dependency is not resolved and needs more investigation
 * 
 */
public class RecipeFacadeRESTTest extends JerseyTest {

    public RecipeFacadeRESTTest() {
    }

    @Override
    public Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new ResourceConfig(RecipeFacadeREST.class);
    }

    @Test
    @Ignore
    public void testFetchAll() {
        Response output = target("/recipe/1").request().get();
        assertEquals("should return status 200", 200, output.getStatus());
        assertNotNull("Should return list", output.getEntity());
    }

    @Test
    @Ignore
    public void testCreate() {
        Recipe recipe = new Recipe();
        Ingredients i1 = new Ingredients();
        Ingredients i2 = new Ingredients();

        recipe.setRecipeId(1);
        recipe.setCreationDate(new Date());
        recipe.setVeg(true);
        recipe.setSuitableFor(5);
        recipe.setInstructions("The New Cooking instructions here");

        i1.setIngId(1);
        i2.setIngId(2);
        i1.setItem("Onion");
        i2.setItem("Garlic");

        List<Ingredients> list = new ArrayList<>();
        list.add(i1);
        list.add(i2);

        recipe.setIngredientsList(list);
        Response output = target("/recipe/")
                .request()
                .post(Entity.entity(recipe, MediaType.APPLICATION_XML));

        assertEquals("Should return status 200", 200, output.getStatus());
        assertNotNull("Should return notification", output.getEntity());
    }

    @Override
    public TestContainerFactory getTestContainerFactory() {
        return new GrizzlyWebTestContainerFactory();
    }

    @Override
    public DeploymentContext configureDeployment() {
        return ServletDeploymentContext.forServlet(new ServletContainer((ResourceConfig) configure())).build();
    }
}
