package com.mco.fr.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.mco.fr.entities.Projet;
import com.mco.fr.entities.Competence;
import java.util.Iterator;

/**
 * Backing bean for Projet entities.
 * <p>
 * This class provides CRUD functionality for all Projet entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD framework or
 * custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class ProjetBean implements Serializable
{

   private static final long serialVersionUID = 1L;

   /*
    * Support creating and retrieving Projet entities
    */

   private Long id;

   public Long getId()
   {
      return this.id;
   }

   public void setId(Long id)
   {
      this.id = id;
   }

   private Projet projet;

   public Projet getProjet()
   {
      return this.projet;
   }

   @Inject
   private Conversation conversation;

   @PersistenceContext(type = PersistenceContextType.EXTENDED)
   private EntityManager entityManager;

   public String create()
   {

      this.conversation.begin();
      return "create?faces-redirect=true";
   }

   public void retrieve()
   {

      if (FacesContext.getCurrentInstance().isPostback())
      {
         return;
      }

      if (this.conversation.isTransient())
      {
         this.conversation.begin();
      }

      if (this.id == null)
      {
         this.projet = this.example;
      }
      else
      {
         this.projet = findById(getId());
      }
   }

   public Projet findById(Long id)
   {

      return this.entityManager.find(Projet.class, id);
   }

   /*
    * Support updating and deleting Projet entities
    */

   public String update()
   {
      this.conversation.end();

      try
      {
         if (this.id == null)
         {
            this.entityManager.persist(this.projet);
            return "search?faces-redirect=true";
         }
         else
         {
            this.entityManager.merge(this.projet);
            return "view?faces-redirect=true&id=" + this.projet.getId();
         }
      }
      catch (Exception e)
      {
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
         return null;
      }
   }

   public String delete()
   {
      this.conversation.end();

      try
      {
         Projet deletableEntity = findById(getId());
         Iterator<Competence> iterCompetences = deletableEntity.getCompetences().iterator();
         for (; iterCompetences.hasNext();)
         {
            Competence nextInCompetences = iterCompetences.next();
            nextInCompetences.getProjets().remove(deletableEntity);
            iterCompetences.remove();
            this.entityManager.merge(nextInCompetences);
         }
         this.entityManager.remove(deletableEntity);
         this.entityManager.flush();
         return "search?faces-redirect=true";
      }
      catch (Exception e)
      {
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
         return null;
      }
   }

   /*
    * Support searching Projet entities with pagination
    */

   private int page;
   private long count;
   private List<Projet> pageItems;

   private Projet example = new Projet();

   public int getPage()
   {
      return this.page;
   }

   public void setPage(int page)
   {
      this.page = page;
   }

   public int getPageSize()
   {
      return 10;
   }

   public Projet getExample()
   {
      return this.example;
   }

   public void setExample(Projet example)
   {
      this.example = example;
   }

   public void search()
   {
      this.page = 0;
   }

   public void paginate()
   {

      CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

      // Populate this.count

      CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
      Root<Projet> root = countCriteria.from(Projet.class);
      countCriteria = countCriteria.select(builder.count(root)).where(
            getSearchPredicates(root));
      this.count = this.entityManager.createQuery(countCriteria)
            .getSingleResult();

      // Populate this.pageItems

      CriteriaQuery<Projet> criteria = builder.createQuery(Projet.class);
      root = criteria.from(Projet.class);
      TypedQuery<Projet> query = this.entityManager.createQuery(criteria
            .select(root).where(getSearchPredicates(root)));
      query.setFirstResult(this.page * getPageSize()).setMaxResults(
            getPageSize());
      this.pageItems = query.getResultList();
   }

   private Predicate[] getSearchPredicates(Root<Projet> root)
   {

      CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
      List<Predicate> predicatesList = new ArrayList<Predicate>();

      String nom = this.example.getNom();
      if (nom != null && !"".equals(nom))
      {
         predicatesList.add(builder.like(root.<String> get("nom"), '%' + nom + '%'));
      }
      String description = this.example.getDescription();
      if (description != null && !"".equals(description))
      {
         predicatesList.add(builder.like(root.<String> get("description"), '%' + description + '%'));
      }

      return predicatesList.toArray(new Predicate[predicatesList.size()]);
   }

   public List<Projet> getPageItems()
   {
      return this.pageItems;
   }

   public long getCount()
   {
      return this.count;
   }

   /*
    * Support listing and POSTing back Projet entities (e.g. from inside an
    * HtmlSelectOneMenu)
    */

   public List<Projet> getAll()
   {

      CriteriaQuery<Projet> criteria = this.entityManager
            .getCriteriaBuilder().createQuery(Projet.class);
      return this.entityManager.createQuery(
            criteria.select(criteria.from(Projet.class))).getResultList();
   }

   @Resource
   private SessionContext sessionContext;

   public Converter getConverter()
   {

      final ProjetBean ejbProxy = this.sessionContext.getBusinessObject(ProjetBean.class);

      return new Converter()
      {

         @Override
         public Object getAsObject(FacesContext context,
               UIComponent component, String value)
         {

            return ejbProxy.findById(Long.valueOf(value));
         }

         @Override
         public String getAsString(FacesContext context,
               UIComponent component, Object value)
         {

            if (value == null)
            {
               return "";
            }

            return String.valueOf(((Projet) value).getId());
         }
      };
   }

   /*
    * Support adding children to bidirectional, one-to-many tables
    */

   private Projet add = new Projet();

   public Projet getAdd()
   {
      return this.add;
   }

   public Projet getAdded()
   {
      Projet added = this.add;
      this.add = new Projet();
      return added;
   }
}