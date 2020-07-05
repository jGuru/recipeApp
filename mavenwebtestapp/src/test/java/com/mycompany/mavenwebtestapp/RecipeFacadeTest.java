package com.mycompany.mavenwebtestapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.NamingException;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author Neeraj
 * This class provides jUnit test cases for CRUD operation that AbstractFacade defines for Recipe Entity class
 * NOTE: The test case will only be run if the proper test data is available in DB so before executing test 
 * make sure the test data is available else the test case will fail
 */
public class RecipeFacadeTest {

    private static Logger logger;
    private static EJBContainer container;
    private static RecipeFacade recipeFacade;
    private static IngredientsFacade ingredientsFacade;

    public RecipeFacadeTest() {
    }

    /**
     This method provides the initial setup of required objects
     * in order to avoid repeat your self
     */
    @BeforeClass
    public static void setup() throws NamingException {
        logger = Logger.getLogger(RecipeFacadeTest.class);
        container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        recipeFacade = (RecipeFacade) container.getContext().lookup("java:global/classes/RecipeFacade");
        ingredientsFacade = (IngredientsFacade) container.getContext().lookup("java:global/classes/IngredientsFacade");
    }

    /**
     This method clean up the object setup by the #setup method 
     * after test execution is completed
     */
    @AfterClass
    public static void tearDown() {
        container.close();
    }
    /**
     Test case for create operation for Recipe Entity class
     */
    
    @Test
    public void testCreate() throws Exception {
        logger.info("Create Test execution");
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
        recipeFacade.create(recipe);
        ingredientsFacade.create(i1);
        ingredientsFacade.create(i2);
    }

    /**
     Test case for updating/edit operation for Recipe Entity class
     */
    @Test
    public void testEdit() throws Exception {

        logger.info("Edit Test case");
        Recipe recipe = new Recipe(1);
        recipe.setSuitableFor(3);
        Ingredients i1 = new Ingredients(3);
        i1.setItem("Termaric");
        recipe.setCreationDate(new Date());
        recipe.setInstructions("The New Cooking instructions here");
        recipe.setIngredientsList(Arrays.asList(i1));

        recipeFacade.edit(recipe);
        Recipe found = recipeFacade.find(1);
        Assert.assertNotNull(found);
        Assert.assertEquals(1, found.getIngredientsList().size());
    }

    /**
     Test case for remove/delete operation for Recipe Entity class
     */
    @Test
    public void testRemove() throws Exception {

        logger.info("Remove Test case");
        Recipe entity = new Recipe(1);
        RecipeFacade instance = (RecipeFacade) container.getContext().lookup("java:global/classes/RecipeFacade");
        instance.remove(entity);
        Recipe recipe = instance.find(1);
        Assert.assertNull(recipe);
    }

    /**
     Test case to find recipe specified by an ID;
     */
    @Test
    public void testFind() throws Exception {
        logger.info("Creating test data for remove first and then remove");
        logger.info("Remove Test case");
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

        recipeFacade.create(recipe);
        ingredientsFacade.create(i1);
        ingredientsFacade.create(i2);
        Recipe expResult = new Recipe(1);
        Recipe result = recipeFacade.find(1);
        assertEquals(result.getRecipeId().intValue(), 1);
    }
    
    /**
     Test case to find all the records of the Recipe in DB
     * NOTE: Here assumption is made that initially we do not have any data in it hence count is 0
     * hence this method check if the returned object is not null
     * When test data is properly setup then we can assert the size of the list like below
       assertTrue(result.size()>0);
     */

    @Test
    public void testFindAll() throws Exception {
        logger.info("finaAll Test case");
        List<Recipe> result = recipeFacade.findAll();
        assertNotNull(result);
    }

    /**
     * Test case for findRange Operation for the given range
     */
    @Test
    public void testFindRange() throws Exception {
        logger.info("findRange Test case");
        int[] range = {1, 2};
        List<Recipe> result = recipeFacade.findRange(range);
        assertEquals(1, result.size());
    }

    /**
     * Test case to return total count of the Recipe records
     * NOTE: Assumption initially we do not have any record hence count is 0
     */
    @Test
    public void testCount() throws Exception {
        logger.info("testing count");
        RecipeFacade instance = (RecipeFacade) container.getContext().lookup("java:global/classes/RecipeFacade");
        assertTrue(instance.count() == 0);
    }

}
