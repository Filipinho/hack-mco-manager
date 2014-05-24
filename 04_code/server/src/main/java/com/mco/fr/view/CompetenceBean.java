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

import com.mco.fr.entities.Competence;
import com.mco.fr.entities.Projet;
import java.util.Iterator;

/**
 * Backing bean for Competence entities.
 * <p>
 * This class provides CRUD functionality for all Competence entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD framework or
 * custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class CompetenceBean implements Serializable
{

   private static final long serialVersionUID = 1L;

   /*
    * Support creating and retrieving Competence entities
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

   private Competence competence;

   public Competence getCompetence()
   {
      return this.competence;
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
         this.competence = this.example;
      }
      else
      {
         this.competence = findById(getId());
      }
   }

   public Competence findById(Long id)
   {

      return this.entityManager.find(Competence.class, id);
   }

   /*
    * Support updating and deleting Competence entities
    */

   public String update()
   {
      this.conversation.end();

      try
      {
         if (this.id == null)
         {
            this.entityManager.persist(this.competence);
            return "search?faces-redirect=true";
         }
         else
         {
            this.entityManager.merge(this.competence);
            return "view?faces-redirect=true&id=" + this.competence.getId();
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
         Competence deletableEntity = findById(getId());
         Iterator<Projet> iterProjets = deletableEntity.getProjets().iterator();
         for (; iterProjets.hasNext();)
         {
            Projet nextInProjets = iterProjets.next();
            nextInProjets.getCompetences().remove(deletableEntity);
            iterProjets.remove();
            this.entityManager.merge(nextInProjets);
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
    * Support searching Competence entities with pagination
    */

   private int page;
   private long count;
   private List<Competence> pageItems;

   private Competence example = new Competence();

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

   public Competence getExample()
   {
      return this.example;
   }

   public void setExample(Competence example)
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
      Root<Competence> root = countCriteria.from(Competence.class);
      countCriteria = countCriteria.select(builder.count(root)).where(
            getSearchPredicates(root));
      this.count = this.entityManager.createQuery(countCriteria)
            .getSingleResult();

      // Populate this.pageItems

      CriteriaQuery<Competence> criteria = builder.createQuery(Competence.class);
      root = criteria.from(Competence.class);
      TypedQuery<Competence> query = this.entityManager.createQuery(criteria
            .select(root).where(getSearchPredicates(root)));
      query.setFirstResult(this.page * getPageSize()).setMaxResults(
            getPageSize());
      this.pageItems = query.getResultList();
   }

   private Predicate[] getSearchPredicates(Root<Competence> root)
   {

      CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
      List<Predicate> predicatesList = new ArrayList<Predicate>();

      String famille = this.example.getFamille();
      if (famille != null && !"".equals(famille))
      {
         predicatesList.add(builder.like(root.<String> get("famille"), '%' + famille + '%'));
      }
      String libelle = this.example.getLibelle();
      if (libelle != null && !"".equals(libelle))
      {
         predicatesList.add(builder.like(root.<String> get("libelle"), '%' + libelle + '%'));
      }
      String commentaire = this.example.getCommentaire();
      if (commentaire != null && !"".equals(commentaire))
      {
         predicatesList.add(builder.like(root.<String> get("commentaire"), '%' + commentaire + '%'));
      }

      return predicatesList.toArray(new Predicate[predicatesList.size()]);
   }

   public List<Competence> getPageItems()
   {
      return this.pageItems;
   }

   public long getCount()
   {
      return this.count;
   }

   /*
    * Support listing and POSTing back Competence entities (e.g. from inside an
    * HtmlSelectOneMenu)
    */

   public List<Competence> getAll()
   {

      CriteriaQuery<Competence> criteria = this.entityManager
            .getCriteriaBuilder().createQuery(Competence.class);
      return this.entityManager.createQuery(
            criteria.select(criteria.from(Competence.class))).getResultList();
   }

   @Resource
   private SessionContext sessionContext;

   public Converter getConverter()
   {

      final CompetenceBean ejbProxy = this.sessionContext.getBusinessObject(CompetenceBean.class);

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

            return String.valueOf(((Competence) value).getId());
         }
      };
   }

   /*
    * Support adding children to bidirectional, one-to-many tables
    */

   private Competence add = new Competence();

   public Competence getAdd()
   {
      return this.add;
   }

   public Competence getAdded()
   {
      Competence added = this.add;
      this.add = new Competence();
      return added;
   }
}