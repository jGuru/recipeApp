package com.mycompany.mavenwebtestapp.controller;

import com.mycompany.mavenwebtestapp.Ingredients;
import com.mycompany.mavenwebtestapp.IngredientsFacade;
import com.mycompany.mavenwebtestapp.Recipe;
import com.mycompany.mavenwebtestapp.RecipeFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Neeraj
 */
@WebServlet(name = "RecipeServlet", urlPatterns = {"/recipeServlet"})
public class RecipeServlet extends HttpServlet {

    @EJB
    private RecipeFacade recipeFacade;
    @EJB
    private IngredientsFacade ingredientsFacade;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException  {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>ReCipe App</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Welcome to Recipe App </h1>");
            String create=request.getParameter("create");
            
            if(create!=null && create.equalsIgnoreCase("insert"))
            {
             
                    String vegetarian=request.getParameter("veg");
                    String numberOfPeople=request.getParameter("suitableFor");
                    String items[]=request.getParameterValues("ingredients");
                    String cookingInstruction=request.getParameter("instructions");
                    
                    Recipe r=new Recipe();
                    r.setRecipeId(recipeFacade.count()+1);
                    r.setCreationDate(new Date());
                    r.setInstructions(cookingInstruction);
                    r.setSuitableFor(Integer.parseInt(numberOfPeople));
                    recipeFacade.create(r);
                    if(vegetarian!=null){
                        r.setVeg(true);
                    }
                    else{
                        r.setVeg(false);
                    }
                    int maxCount=ingredientsFacade.count();
                    List<Ingredients> iList=new ArrayList<>();
                    
                    for(String s: items){
                       
                        Ingredients ing=new Ingredients(maxCount++);
                        ing.setItem(s);
                        
                        ing.setRecipeId(r);
                        ingredientsFacade.create(ing);
                        iList.add(ing);
                    }
                    r.setIngredientsList(iList);
                    recipeFacade.edit(r);
                    
               }
            
           
            if(create==null||create.equalsIgnoreCase("insert"))
            {
                
                List<Recipe> recipeList = recipeFacade.findAll();
             if (recipeList.size()>0) 
             {
                out.println("<table border=1><tr><td>Recipe ID</td><td>Creation Date</td>");
                out.println("<td>Is Vegetarian </td><td>Suitable For</td>");
                out.println("<td>Ingredients</td><td>Cooking Instructions</td></tr>");
                for (Iterator it = recipeList.iterator(); it.hasNext();) {
                    Recipe r = (Recipe) it.next();

                    out.println("<tr><td>"+r.getRecipeId()+"</td>"); 
                    try{
                    String dateStr = r.getCreationDate().toString();
                    DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
                    Date date = (Date)formatter.parse(dateStr);
                    System.out.println(date); 
                    
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    //dd-MM-yyyy HH:mm
                    String formattedDate = cal.get(Calendar.DATE) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.YEAR) +" "+cal.get(Calendar.HOUR)+":"+cal.get(Calendar.MINUTE);
                     out.println("<td>"+formattedDate+"</td>"); 
                    }
                    catch(ParseException e)
                    {
                        out.println("<td>Something went wrong with this recipe date</td>");
                        e.printStackTrace();
                    }
                   
                    String veg="";
                    if(r.getVeg()){
                        veg="Yes";
                    }
                    else{
                        veg="No";
                    }
                    out.println("<td>"+veg+"</td>");
                    out.println("<td>"+r.getSuitableFor()+"</td>");
                    List<Ingredients> iList=r.getIngredientsList();
                    int i=0;
                    out.println("<td>");
                    for(Iterator itr=iList.iterator();itr.hasNext();)
                    {
                        i++;
                        Ingredients ingredients=(Ingredients)itr.next();
                            out.println(i+":"+ingredients.getItem()+"<br/>");
                    }
                    out.println("</td>"); 
                    out.println("<td>"+r.getInstructions()+"</td></tr>");
                }
            }
        
             out.println("</table>");
             out.println("<a href=recipeServlet?create=true>Click here to Create your recipe</a>");
          }
            else if(create.equalsIgnoreCase("true")){
                 out.println("<form action=recipeServlet method=get>");
                 out.println("<table>");
                 out.println("<tr><td>Is Vegetarian?</td><td><input type=checkbox name=veg /></td></tr>");
                 out.println("<tr><td>Suitable for</td><td><input type=text name=suitableFor /></td></tr>");
                 out.println("<tr><td>Ingredients</td><td>");
                 out.println("<select name=ingredients multiple=multiple>");
                 out.println("<option value=Cucumber >Cucumber</option>");
                 out.println("<option value=Termaric >Termaric</option>");
                 out.println("<option value=Onion >Onion</option>");
                 out.println("<option value=Garlic >Garlic</option>");
                 out.println("</select>");
                 out.println("</td></tr>");
                 out.println("<tr>");
                 out.println("<td>Cooking Instructions</td>");
                 out.println("<td><textarea name=instructions rows=4 cols=20>");
                 out.println("Cooking instructions goes here");
                 out.println("</textarea></td>");
                 out.println("</tr>");
                 out.println("<input type=hidden name=create value=insert />");
                 out.println("<tr><td colspan=2 align=center><input type=submit value=Create Recipe></td></tr>");               
                 out.println("</table>");
                 out.println("</form>");
            }
          
            
            
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
