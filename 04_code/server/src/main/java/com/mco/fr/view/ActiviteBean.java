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

import com.mco.fr.entities.Activite;
import com.mco.fr.entities.Tache;
import java.util.Iterator;

/**
 * Backing bean for Activite entities.
 * <p>
 * This class provides CRUD functionality for all Activite entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD framework or
 * custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class ActiviteBean implements Serializable
{

   private static final long serialVersionUID = 1L;

   /*
    * Support creating and retrieving Activite entities
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

   private Activite activite;

   public Activite getActivite()
   {
      return this.activite;
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
         this.activite = this.example;
      }
      else
      {
         this.activite = findById(getId());
      }
   }

   public Activite findById(Long id)
   {

      return this.entityManager.find(Activite.class, id);
   }

   /*
    * Support updating and deleting Activite entities
    */

   public String update()
   {
      this.conversation.end();

      try
      {
         if (this.id == null)
         {
            this.entityManager.persist(this.activite);
            return "search?faces-redirect=true";
         }
         else
         {
            this.entityManager.merge(this.activite);
            return "view?faces-redirect=true&id=" + this.activite.getId();
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
         Activite deletableEntity = findById(getId());
         Iterator<Tache> iterTaches = deletableEntity.getTaches().iterator();
         for (; iterTaches.hasNext();)
         {
            Tache nextInTaches = iterTaches.next();
            nextInTaches.setActivite(null);
            iterTaches.remove();
            this.entityManager.merge(nextInTaches);
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
    * Support searching Activite entities with pagination
    */

   private int page;
   private long count;
   private List<Activite> pageItems;

   private Activite example = new Activite();

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

   public Activite getExample()
   {
      return this.example;
   }

   public void setExample(Activite example)
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
      Root<Activite> root = countCriteria.from(Activite.class);
      countCriteria = countCriteria.select(builder.count(root)).where(
            getSearchPredicates(root));
      this.count = this.entityManager.createQuery(countCriteria)
            .getSingleResult();

      // Populate this.pageItems

      CriteriaQuery<Activite> criteria = builder.createQuery(Activite.class);
      root = criteria.from(Activite.class);
      TypedQuery<Activite> query = this.entityManager.createQuery(criteria
            .select(root).where(getSearchPredicates(root)));
      query.setFirstResult(this.page * getPageSize()).setMaxResults(
            getPageSize());
      this.pageItems = query.getResultList();
   }

   private Predicate[] getSearchPredicates(Root<Activite> root)
   {

      CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
      List<Predicate> predicatesList = new ArrayList<Predicate>();

      String nom = this.example.getNom();
      if (nom != null && !"".equals(nom))
      {
         predicatesList.add(builder.like(root.<String> get("nom"), '%' + nom + '%'));
      }
      String famille = this.example.getFamille();
      if (famille != null && !"".equals(famille))
      {
         predicatesList.add(builder.like(root.<String> get("famille"), '%' + famille + '%'));
      }
      String code = this.example.getCode();
      if (code != null && !"".equals(code))
      {
         predicatesList.add(builder.like(root.<String> get("code"), '%' + code + '%'));
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

   public List<Activite> getPageItems()
   {
      return this.pageItems;
   }

   public long getCount()
   {
      return this.count;
   }

   /*
    * Support listing and POSTing back Activite entities (e.g. from inside an
    * HtmlSelectOneMenu)
    */

   public List<Activite> getAll()
   {

      CriteriaQuery<Activite> criteria = this.entityManager
            .getCriteriaBuilder().createQuery(Activite.class);
      return this.entityManager.createQuery(
            criteria.select(criteria.from(Activite.class))).getResultList();
   }

   @Resource
   private SessionContext sessionContext;

   public Converter getConverter()
   {

      final ActiviteBean ejbProxy = this.sessionContext.getBusinessObject(ActiviteBean.class);

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

            return String.valueOf(((Activite) value).getId());
         }
      };
   }

   /*
    * Support adding children to bidirectional, one-to-many tables
    */

   private Activite add = new Activite();

   public Activite getAdd()
   {
      return this.add;
   }

   public Activite getAdded()
   {
      Activite added = this.add;
      this.add = new Activite();
      return added;
   }
}