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

import com.mco.fr.entities.Tache;
import com.mco.fr.entities.Activite;

/**
 * Backing bean for Tache entities.
 * <p>
 * This class provides CRUD functionality for all Tache entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD framework or
 * custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class TacheBean implements Serializable
{

   private static final long serialVersionUID = 1L;

   /*
    * Support creating and retrieving Tache entities
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

   private Tache tache;

   public Tache getTache()
   {
      return this.tache;
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
         this.tache = this.example;
      }
      else
      {
         this.tache = findById(getId());
      }
   }

   public Tache findById(Long id)
   {

      return this.entityManager.find(Tache.class, id);
   }

   /*
    * Support updating and deleting Tache entities
    */

   public String update()
   {
      this.conversation.end();

      try
      {
         if (this.id == null)
         {
            this.entityManager.persist(this.tache);
            return "search?faces-redirect=true";
         }
         else
         {
            this.entityManager.merge(this.tache);
            return "view?faces-redirect=true&id=" + this.tache.getId();
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
         Tache deletableEntity = findById(getId());
         Activite activite = deletableEntity.getActivite();
         activite.getTaches().remove(deletableEntity);
         deletableEntity.setActivite(null);
         this.entityManager.merge(activite);
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
    * Support searching Tache entities with pagination
    */

   private int page;
   private long count;
   private List<Tache> pageItems;

   private Tache example = new Tache();

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

   public Tache getExample()
   {
      return this.example;
   }

   public void setExample(Tache example)
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
      Root<Tache> root = countCriteria.from(Tache.class);
      countCriteria = countCriteria.select(builder.count(root)).where(
            getSearchPredicates(root));
      this.count = this.entityManager.createQuery(countCriteria)
            .getSingleResult();

      // Populate this.pageItems

      CriteriaQuery<Tache> criteria = builder.createQuery(Tache.class);
      root = criteria.from(Tache.class);
      TypedQuery<Tache> query = this.entityManager.createQuery(criteria
            .select(root).where(getSearchPredicates(root)));
      query.setFirstResult(this.page * getPageSize()).setMaxResults(
            getPageSize());
      this.pageItems = query.getResultList();
   }

   private Predicate[] getSearchPredicates(Root<Tache> root)
   {

      CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
      List<Predicate> predicatesList = new ArrayList<Predicate>();

      String libelle = this.example.getLibelle();
      if (libelle != null && !"".equals(libelle))
      {
         predicatesList.add(builder.like(root.<String> get("libelle"), '%' + libelle + '%'));
      }
      String statut = this.example.getStatut();
      if (statut != null && !"".equals(statut))
      {
         predicatesList.add(builder.like(root.<String> get("statut"), '%' + statut + '%'));
      }
      Activite activite = this.example.getActivite();
      if (activite != null)
      {
         predicatesList.add(builder.equal(root.get("activite"), activite));
      }

      return predicatesList.toArray(new Predicate[predicatesList.size()]);
   }

   public List<Tache> getPageItems()
   {
      return this.pageItems;
   }

   public long getCount()
   {
      return this.count;
   }

   /*
    * Support listing and POSTing back Tache entities (e.g. from inside an
    * HtmlSelectOneMenu)
    */

   public List<Tache> getAll()
   {

      CriteriaQuery<Tache> criteria = this.entityManager
            .getCriteriaBuilder().createQuery(Tache.class);
      return this.entityManager.createQuery(
            criteria.select(criteria.from(Tache.class))).getResultList();
   }

   @Resource
   private SessionContext sessionContext;

   public Converter getConverter()
   {

      final TacheBean ejbProxy = this.sessionContext.getBusinessObject(TacheBean.class);

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

            return String.valueOf(((Tache) value).getId());
         }
      };
   }

   /*
    * Support adding children to bidirectional, one-to-many tables
    */

   private Tache add = new Tache();

   public Tache getAdd()
   {
      return this.add;
   }

   public Tache getAdded()
   {
      Tache added = this.add;
      this.add = new Tache();
      return added;
   }
}