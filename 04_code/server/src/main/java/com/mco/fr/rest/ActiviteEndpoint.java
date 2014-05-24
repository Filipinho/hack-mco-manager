package com.mco.fr.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import com.mco.fr.entities.Activite;

/**
 * 
 */
@Stateless
@Path("/activites")
public class ActiviteEndpoint
{
   @PersistenceContext(unitName = "forge-default")
   private EntityManager em;

   @POST
   @Consumes("application/json")
   public Response create(Activite entity)
   {
      em.persist(entity);
      return Response.created(UriBuilder.fromResource(ActiviteEndpoint.class).path(String.valueOf(entity.getId())).build()).build();
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
      Activite entity = em.find(Activite.class, id);
      if (entity == null)
      {
         return Response.status(Status.NOT_FOUND).build();
      }
      em.remove(entity);
      return Response.noContent().build();
   }

   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces("application/json")
   public Response findById(@PathParam("id") Long id)
   {
      TypedQuery<Activite> findByIdQuery = em.createQuery("SELECT DISTINCT a FROM Activite a LEFT JOIN FETCH a.taches WHERE a.id = :entityId ORDER BY a.id", Activite.class);
      findByIdQuery.setParameter("entityId", id);
      Activite entity;
      try
      {
         entity = findByIdQuery.getSingleResult();
      }
      catch (NoResultException nre)
      {
         entity = null;
      }
      if (entity == null)
      {
         return Response.status(Status.NOT_FOUND).build();
      }
      return Response.ok(entity).build();
   }

   @GET
   @Produces("application/json")
   public List<Activite> listAll()
   {
      final List<Activite> results = em.createQuery("SELECT DISTINCT a FROM Activite a LEFT JOIN FETCH a.taches ORDER BY a.id", Activite.class).getResultList();
      
      return results;
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Consumes("application/json")
   public Response update(Activite entity)
   {
      entity = em.merge(entity);
      return Response.noContent().build();
   }
}