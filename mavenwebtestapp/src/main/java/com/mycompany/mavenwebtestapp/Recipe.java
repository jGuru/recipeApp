package com.mycompany.mavenwebtestapp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Neeraj
 * 
 * This class represent the domain object Recipe
 * This is technically an Entity class and used serialize and de-serialize the Recipe object
 */
@Entity
@Table(name = "RECIPE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Recipe.findAll", query = "SELECT r FROM Recipe r")
    , @NamedQuery(name = "Recipe.findByRecipeId", query = "SELECT r FROM Recipe r WHERE r.recipeId = :recipeId")
    , @NamedQuery(name = "Recipe.findByCreationDate", query = "SELECT r FROM Recipe r WHERE r.creationDate = :creationDate")
    , @NamedQuery(name = "Recipe.findBySuitableFor", query = "SELECT r FROM Recipe r WHERE r.suitableFor = :suitableFor")
    , @NamedQuery(name = "Recipe.findByVeg", query = "SELECT r FROM Recipe r WHERE r.veg = :veg")})
public class Recipe implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "RECIPE_ID")
    private Integer recipeId;
    @Column(name = "CREATION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @Column(name = "SUITABLE_FOR")
    private Integer suitableFor;
    @Column(name = "VEG")
    private boolean veg;
    @Column(name = "INSTRUCTIONS")
    private String instructions;

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
    
    @OneToMany(mappedBy = "recipeId", cascade = CascadeType.PERSIST)
    private List<Ingredients> ingredientsList;

    public Recipe() {
    }

    public Recipe(Integer recipeId) {
        this.recipeId = recipeId;
    }

    public Integer getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getSuitableFor() {
        return suitableFor;
    }

    public void setSuitableFor(Integer suitableFor) {
        this.suitableFor = suitableFor;
    }

    public boolean getVeg() {
        return veg;
    }

    public void setVeg(boolean veg) {
        this.veg = veg;
    }

    @XmlTransient
    public List<Ingredients> getIngredientsList() {
        return ingredientsList;
    }

    public void setIngredientsList(List<Ingredients> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (recipeId != null ? recipeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Recipe)) {
            return false;
        }
        Recipe other = (Recipe) object;
        if ((this.recipeId == null && other.recipeId != null) || (this.recipeId != null && !this.recipeId.equals(other.recipeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.mavenwebtestapp.Recipe[ recipeId=" + recipeId + " ]";
    }
    
}
