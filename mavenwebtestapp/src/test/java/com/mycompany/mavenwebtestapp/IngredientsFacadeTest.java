package com.mycompany.mavenwebtestapp;

import java.util.ArrayList;
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
 */
public class IngredientsFacadeTest {

    private static Logger logger;
    private static EJBContainer container;
    private static RecipeFacade recipeFacade;
    private static IngredientsFacade ingredientsFacade;

    public IngredientsFacadeTest() {
    }

    /**
     * This method provides the initial setup of required objects in order to
     * avoid repeat your self
     */
    @BeforeClass
    public static void setup() throws NamingException {
        logger = Logger.getLogger(RecipeFacadeTest.class);
        container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        recipeFacade = (RecipeFacade) container.getContext().lookup("java:global/classes/RecipeFacade");
        ingredientsFacade = (IngredientsFacade) container.getContext().lookup("java:global/classes/IngredientsFacade");
    }

    /**
     * This method clean up the object setup by the #setup method after test
     * execution is completed
     */
    @AfterClass
    public static void tearDown() {
        container.close();
    }

    /**
     * Test case for creating an Ingredient for an existing Recipe
     */
    @Test
    public void testCreate() throws Exception {
        logger.info("create");
        Recipe recipe = new Recipe();
        Ingredients i1 = new Ingredients();
        Ingredients i2 = new Ingredients();

        recipe.setRecipeId(1);
        recipe.setCreationDate(new Date());
        recipe.setVeg(true);
        recipe.setInstructions("The New Cooking instructions here");
        recipe.setSuitableFor(5);
        i1.setRecipeId(recipe);
        i1.setIngId(1);
        i2.setIngId(2);
        i1.setItem("Onion");
        i2.setItem("Garlic");
        i2.setRecipeId(recipe);

        List<Ingredients> list = new ArrayList<>();
        list.add(i1);
        list.add(i2);
        recipeFacade.create(recipe);
        ingredientsFacade.create(i1);
        ingredientsFacade.create(i2);
    }

    /**
     * Test case for editing/updating an existing ingredient for an existing recipe
     */
    @Test
    public void testEdit() throws Exception {
        logger.info("edit");
        Recipe recipe = new Recipe();
        Ingredients i1 = new Ingredients();

        recipe.setRecipeId(1);
        recipe.setCreationDate(new Date());
        recipe.setVeg(true);
        recipe.setSuitableFor(5);
        recipe.setInstructions("The New Cooking instructions here");
        i1.setRecipeId(recipe);
        i1.setIngId(1);
        i1.setItem("White Onion");
        List<Ingredients> list = new ArrayList<>();
        list.add(i1);
        ingredientsFacade.edit(i1);
        Ingredients found = ingredientsFacade.find(1);
        Assert.assertNotNull(found);
        Assert.assertEquals(found.getItem(), i1.getItem());
    }

    /**
     * Test case to remove/delete an ingredient in an existing recipe
     */
    @Test
    public void testRemove() throws Exception {
        logger.info("Creating test data first  to remove");
        Recipe recipe = new Recipe();
        Ingredients i1 = new Ingredients();
        recipe.setInstructions("The New Cooking instructions here");
        recipe.setRecipeId(2);
        recipe.setCreationDate(new Date());
        recipe.setVeg(true);
        recipe.setSuitableFor(5);
        i1.setRecipeId(recipe);
        i1.setIngId(6);
        i1.setItem("Spring Onion");
        List<Ingredients> list = new ArrayList<>();
        list.add(i1);

        recipeFacade.create(recipe);
        ingredientsFacade.create(i1);
        ingredientsFacade.remove(i1);
        Ingredients notFound = ingredientsFacade.find(6);
        Assert.assertNull(notFound);
    }

    /**
     * Test case to find an existing ingredient in an existing recipe
     */
    @Test
    public void testFind() throws Exception {
        logger.info("creating test data for find first");

        Recipe recipe = new Recipe();
        Ingredients i1 = new Ingredients();

        recipe.setRecipeId(3);
        recipe.setCreationDate(new Date());
        recipe.setVeg(true);
        recipe.setSuitableFor(5);
        recipe.setInstructions("The New Cooking instructions here");
        i1.setRecipeId(recipe);
        i1.setIngId(1);
        i1.setItem("Cucumber");
        List<Ingredients> list = new ArrayList<>();
        list.add(i1);
        recipeFacade.create(recipe);
        ingredientsFacade.create(i1);

        Ingredients result = ingredientsFacade.find(i1.getIngId());
        assertEquals(i1, result);
    }

    /**
     * Test case to find all ingredients in all the recipes
     */
    @Test
    public void testFindAll() throws Exception {
        logger.info("creating test data for findAll first");

        Recipe recipe = new Recipe();
        Ingredients i1 = new Ingredients();

        recipe.setRecipeId(3);
        recipe.setCreationDate(new Date());
        recipe.setVeg(true);
        recipe.setSuitableFor(5);
        recipe.setInstructions("The New Cooking instructions here");
        i1.setRecipeId(recipe);
        i1.setIngId(1);
        i1.setItem("Cucumber");
        List<Ingredients> list = new ArrayList<>();
        list.add(i1);
        recipeFacade.create(recipe);
        ingredientsFacade.create(i1);
        assertTrue(ingredientsFacade.findAll().size() > 0);
    }

    /**
     * Test case to find a range of ingredients based on id
     */
    @Test
    public void testFindRange() throws Exception {
        System.out.println("findRange");
        int[] range = {1, 2};
        List<Ingredients> result = ingredientsFacade.findRange(range);
        assertEquals(2, result.size());
    }

    /**
     Test case to find total count of ingredients stored for all the recipes
     */
    @Test
    public void testCount() throws Exception {
        logger.info("creating test data for count first");

        Recipe recipe = new Recipe();
        Ingredients i1 = new Ingredients();

        recipe.setRecipeId(3);
        recipe.setCreationDate(new Date());
        recipe.setVeg(true);
        recipe.setSuitableFor(5);
        recipe.setInstructions("The New Cooking instructions here");
        i1.setRecipeId(recipe);
        i1.setIngId(1);
        i1.setItem("Cucumber");
        List<Ingredients> list = new ArrayList<>();
        list.add(i1);

        recipeFacade.create(recipe);
        ingredientsFacade.create(i1);
        int expResult = 2;
        int result = ingredientsFacade.count();
        assertEquals(expResult, result);
    }
}
