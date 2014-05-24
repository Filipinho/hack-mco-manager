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

import com.mco.fr.entities.Membre;

/**
 * Backing bean for Membre entities.
 * <p>
 * This class provides CRUD functionality for all Membre entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD framework or
 * custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class MembreBean implements Serializable
{

   private static final long serialVersionUID = 1L;

   /*
    * Support creating and retrieving Membre entities
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

   private Membre membre;

   public Membre getMembre()
   {
      return this.membre;
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
         this.membre = this.example;
      }
      else
      {
         this.membre = findById(getId());
      }
   }

   public Membre findById(Long id)
   {

      return this.entityManager.find(Membre.class, id);
   }

   /*
    * Support updating and deleting Membre entities
    */

   public String update()
   {
      this.conversation.end();

      try
      {
         if (this.id == null)
         {
            this.entityManager.persist(this.membre);
            return "search?faces-redirect=true";
         }
         else
         {
            this.entityManager.merge(this.membre);
            return "view?faces-redirect=true&id=" + this.membre.getId();
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
         Membre deletableEntity = findById(getId());

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
    * Support searching Membre entities with pagination
    */

   private int page;
   private long count;
   private List<Membre> pageItems;

   private Membre example = new Membre();

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

   public Membre getExample()
   {
      return this.example;
   }

   public void setExample(Membre example)
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
      Root<Membre> root = countCriteria.from(Membre.class);
      countCriteria = countCriteria.select(builder.count(root)).where(
            getSearchPredicates(root));
      this.count = this.entityManager.createQuery(countCriteria)
            .getSingleResult();

      // Populate this.pageItems

      CriteriaQuery<Membre> criteria = builder.createQuery(Membre.class);
      root = criteria.from(Membre.class);
      TypedQuery<Membre> query = this.entityManager.createQuery(criteria
            .select(root).where(getSearchPredicates(root)));
      query.setFirstResult(this.page * getPageSize()).setMaxResults(
            getPageSize());
      this.pageItems = query.getResultList();
   }

   private Predicate[] getSearchPredicates(Root<Membre> root)
   {

      CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
      List<Predicate> predicatesList = new ArrayList<Predicate>();

      String trigramme = this.example.getTrigramme();
      if (trigramme != null && !"".equals(trigramme))
      {
         predicatesList.add(builder.like(root.<String> get("trigramme"), '%' + trigramme + '%'));
      }
      String nom = this.example.getNom();
      if (nom != null && !"".equals(nom))
      {
         predicatesList.add(builder.like(root.<String> get("nom"), '%' + nom + '%'));
      }
      String prenom = this.example.getPrenom();
      if (prenom != null && !"".equals(prenom))
      {
         predicatesList.add(builder.like(root.<String> get("prenom"), '%' + prenom + '%'));
      }
      String email = this.example.getEmail();
      if (email != null && !"".equals(email))
      {
         predicatesList.add(builder.like(root.<String> get("email"), '%' + email + '%'));
      }
      String organisation = this.example.getOrganisation();
      if (organisation != null && !"".equals(organisation))
      {
         predicatesList.add(builder.like(root.<String> get("organisation"), '%' + organisation + '%'));
      }

      return predicatesList.toArray(new Predicate[predicatesList.size()]);
   }

   public List<Membre> getPageItems()
   {
      return this.pageItems;
   }

   public long getCount()
   {
      return this.count;
   }

   /*
    * Support listing and POSTing back Membre entities (e.g. from inside an
    * HtmlSelectOneMenu)
    */

   public List<Membre> getAll()
   {

      CriteriaQuery<Membre> criteria = this.entityManager
            .getCriteriaBuilder().createQuery(Membre.class);
      return this.entityManager.createQuery(
            criteria.select(criteria.from(Membre.class))).getResultList();
   }

   @Resource
   private SessionContext sessionContext;

   public Converter getConverter()
   {

      final MembreBean ejbProxy = this.sessionContext.getBusinessObject(MembreBean.class);

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

            return String.valueOf(((Membre) value).getId());
         }
      };
   }

   /*
    * Support adding children to bidirectional, one-to-many tables
    */

   private Membre add = new Membre();

   public Membre getAdd()
   {
      return this.add;
   }

   public Membre getAdded()
   {
      Membre added = this.add;
      this.add = new Membre();
      return added;
   }
}