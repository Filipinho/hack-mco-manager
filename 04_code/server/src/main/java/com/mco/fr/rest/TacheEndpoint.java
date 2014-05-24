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
import com.mco.fr.entities.Tache;

/**
 * 
 */
@Stateless
@Path("/taches")
public class TacheEndpoint
{
   @PersistenceContext(unitName = "forge-default")
   private EntityManager em;

   @POST
   @Consumes("application/json")
   public Response create(Tache entity)
   {
      em.persist(entity);
      return Response.created(UriBuilder.fromResource(TacheEndpoint.class).path(String.valueOf(entity.getId())).build()).build();
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
      Tache entity = em.find(Tache.class, id);
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
      TypedQuery<Tache> findByIdQuery = em.createQuery("SELECT DISTINCT t FROM Tache t LEFT JOIN FETCH t.activite WHERE t.id = :entityId ORDER BY t.id", Tache.class);
      findByIdQuery.setParameter("entityId", id);
      Tache entity;
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
   public List<Tache> listAll()
   {
      final List<Tache> results = em.createQuery("SELECT DISTINCT t FROM Tache t LEFT JOIN FETCH t.activite ORDER BY t.id", Tache.class).getResultList();
      return results;
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Consumes("application/json")
   public Response update(Tache entity)
   {
      entity = em.merge(entity);
      return Response.noContent().build();
   }
}