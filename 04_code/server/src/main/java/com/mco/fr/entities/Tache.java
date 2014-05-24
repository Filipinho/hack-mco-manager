package com.mco.fr.entities;

import javax.persistence.Entity;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;

import java.lang.Override;

import javax.xml.bind.annotation.XmlRootElement;

import com.mco.fr.entities.Activite;

import javax.persistence.ManyToOne;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@XmlRootElement
public class Tache implements Serializable
{

   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   private String libelle;

   @Column
   private Double duree;

   @Column
   private Double consomme;

   @Column
   private Double raf;

   @Column
   private String statut;

   @ManyToOne(optional=false)
   @JsonIgnore
   private Activite activite;

   public Long getId()
   {
      return this.id;
   }

   public void setId(final Long id)
   {
      this.id = id;
   }

   public int getVersion()
   {
      return this.version;
   }

   public void setVersion(final int version)
   {
      this.version = version;
   }

   @Override
   public boolean equals(Object that)
   {
      if (this == that)
      {
         return true;
      }
      if (that == null)
      {
         return false;
      }
      if (getClass() != that.getClass())
      {
         return false;
      }
      if (id != null)
      {
         return id.equals(((Tache) that).id);
      }
      return super.equals(that);
   }

   @Override
   public int hashCode()
   {
      if (id != null)
      {
         return id.hashCode();
      }
      return super.hashCode();
   }

   public String getLibelle()
   {
      return this.libelle;
   }

   public void setLibelle(final String libelle)
   {
      this.libelle = libelle;
   }

   public Double getDuree()
   {
      return this.duree;
   }

   public void setDuree(final Double duree)
   {
      this.duree = duree;
   }

   public Double getConsomme()
   {
      return this.consomme;
   }

   public void setConsomme(final Double consomme)
   {
      this.consomme = consomme;
   }

   public Double getRaf()
   {
      return this.raf;
   }

   public void setRaf(final Double raf)
   {
      this.raf = raf;
   }

   public String getStatut()
   {
      return this.statut;
   }

   public void setStatut(final String statut)
   {
      this.statut = statut;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (libelle != null && !libelle.trim().isEmpty())
         result += "libelle: " + libelle;
      if (duree != null)
         result += ", duree: " + duree;
      if (consomme != null)
         result += ", consomme: " + consomme;
      if (raf != null)
         result += ", raf: " + raf;
      if (statut != null && !statut.trim().isEmpty())
         result += ", statut: " + statut;
      return result;
   }

   @JsonIgnore
   public Activite getActivite()
   {
      return this.activite;
   }

   @JsonIgnore
   public void setActivite(final Activite activite)
   {
      this.activite = activite;
   }
}