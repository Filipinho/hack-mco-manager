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
import com.mco.fr.entities.Competence;

/**
 * 
 */
@Stateless
@Path("/competences")
public class CompetenceEndpoint
{
   @PersistenceContext(unitName = "forge-default")
   private EntityManager em;

   @POST
   @Consumes("application/json")
   public Response create(Competence entity)
   {
      em.persist(entity);
      return Response.created(UriBuilder.fromResource(CompetenceEndpoint.class).path(String.valueOf(entity.getId())).build()).build();
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
      Competence entity = em.find(Competence.class, id);
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
      TypedQuery<Competence> findByIdQuery = em.createQuery("SELECT DISTINCT c FROM Competence c WHERE c.id = :entityId ORDER BY c.id", Competence.class);
      findByIdQuery.setParameter("entityId", id);
      Competence entity;
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
   public List<Competence> listAll()
   {
      final List<Competence> results = em.createQuery("SELECT DISTINCT c FROM Competence c ORDER BY c.id", Competence.class).getResultList();
      return results;
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Consumes("application/json")
   public Response update(Competence entity)
   {
      entity = em.merge(entity);
      return Response.noContent().build();
   }
}