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
import com.mco.fr.entities.Outils;

/**
 * 
 */
@Stateless
@Path("/outils")
public class OutilsEndpoint
{
   @PersistenceContext(unitName = "forge-default")
   private EntityManager em;

   @POST
   @Consumes("application/json")
   public Response create(Outils entity)
   {
      em.persist(entity);
      return Response.created(UriBuilder.fromResource(OutilsEndpoint.class).path(String.valueOf(entity.getId())).build()).build();
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
      Outils entity = em.find(Outils.class, id);
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
      TypedQuery<Outils> findByIdQuery = em.createQuery("SELECT DISTINCT o FROM Outils o WHERE o.id = :entityId ORDER BY o.id", Outils.class);
      findByIdQuery.setParameter("entityId", id);
      Outils entity;
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
   public List<Outils> listAll()
   {
      final List<Outils> results = em.createQuery("SELECT DISTINCT o FROM Outils o ORDER BY o.id", Outils.class).getResultList();
      return results;
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Consumes("application/json")
   public Response update(Outils entity)
   {
      entity = em.merge(entity);
      return Response.noContent().build();
   }
}