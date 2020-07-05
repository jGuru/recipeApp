package com.mycompany.mavenwebtestapp;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Neeraj
 * This class represents domain object for Ingredients
 * This is technically an Entity class and used serialize and de-serialize the Ingredients object
 */
@Entity
@Table(name = "INGREDIENTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ingredients.findAll", query = "SELECT i FROM Ingredients i")
    , @NamedQuery(name = "Ingredients.findByIngId", query = "SELECT i FROM Ingredients i WHERE i.ingId = :ingId")
    , @NamedQuery(name = "Ingredients.findByItem", query = "SELECT i FROM Ingredients i WHERE i.item = :item")})
public class Ingredients implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ING_ID")
    private Integer ingId;
    @Size(max = 255)
    @Column(name = "ITEM")
    private String item;
    @JoinColumn(name = "RECIPE_ID", referencedColumnName = "RECIPE_ID")
    @ManyToOne()
    private Recipe recipeId;

    public Ingredients() {
    }

    public Ingredients(Integer ingId) {
        this.ingId = ingId;
    }

    public Integer getIngId() {
        return ingId;
    }

    public void setIngId(Integer ingId) {
        this.ingId = ingId;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Recipe getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Recipe recipeId) {
        this.recipeId = recipeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ingId != null ? ingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Ingredients)) {
            return false;
        }
        Ingredients other = (Ingredients) object;
        if ((this.ingId == null && other.ingId != null) || (this.ingId != null && !this.ingId.equals(other.ingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.mavenwebtestapp.Ingredients[ ingId=" + ingId + " ]";
    }
    
}
