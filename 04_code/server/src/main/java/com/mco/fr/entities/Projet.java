package com.mco.fr.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Projet implements Serializable {

	/**
    * 
    */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id = null;
	@Version
	@Column(name = "version")
	private int version = 0;

	@Column
	private String nom;

	@Column
	private String description;

	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
			name = "projet_competence", 
			joinColumns = { @JoinColumn(name = "projet_id") }, 
			inverseJoinColumns = { @JoinColumn(name = "competence_id") })
	private Set<Competence> competences = new HashSet<Competence>();
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
			name = "projet_activite", 
			joinColumns = { @JoinColumn(name = "projet_id") }, 
			inverseJoinColumns = { @JoinColumn(name = "activite_id") })
	private Set<Activite> activites = new HashSet<Activite>();
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
			name = "projet_equipe", 
			joinColumns = { @JoinColumn(name = "projet_id") }, 
			inverseJoinColumns = { @JoinColumn(name = "equipe_id") })
	private Set<Equipe> equipes = new HashSet<Equipe>();
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
			name = "projet_outils", 
			joinColumns = { @JoinColumn(name = "projet_id") }, 
			inverseJoinColumns = { @JoinColumn(name = "outils_id") })
	private Set<Outils> outils = new HashSet<Outils>();
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
			name = "projet_membre", 
			joinColumns = { @JoinColumn(name = "projet_id") }, 
			inverseJoinColumns = { @JoinColumn(name = "membre_id") })
	private Set<Membre> membres = new HashSet<Membre>();
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(final Long id) {
		this.id = id;
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(final int version) {
		this.version = version;
	}

	@Override
	public boolean equals(Object that) {
		if (this == that) {
			return true;
		}
		if (that == null) {
			return false;
		}
		if (getClass() != that.getClass()) {
			return false;
		}
		if (id != null) {
			return id.equals(((Projet) that).id);
		}
		return super.equals(that);
	}

	@Override
	public int hashCode() {
		if (id != null) {
			return id.hashCode();
		}
		return super.hashCode();
	}

	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the competences
	 */
	public Set<Competence> getCompetences() {
		return competences;
	}

	/**
	 * @param competences the competences to set
	 */
	public void setCompetences(Set<Competence> competences) {
		this.competences = competences;
	}

	/**
	 * @return the activites
	 */
	public Set<Activite> getActivites() {
		return activites;
	}

	/**
	 * @param activites the activites to set
	 */
	public void setActivites(Set<Activite> activites) {
		this.activites = activites;
	}

	/**
	 * @return the equipes
	 */
	public Set<Equipe> getEquipes() {
		return equipes;
	}

	/**
	 * @param equipes the equipes to set
	 */
	public void setEquipes(Set<Equipe> equipes) {
		this.equipes = equipes;
	}

	/**
	 * @return the outils
	 */
	public Set<Outils> getOutils() {
		return outils;
	}

	/**
	 * @param outils the outils to set
	 */
	public void setOutils(Set<Outils> outils) {
		this.outils = outils;
	}

	/**
	 * @return the membres
	 */
	public Set<Membre> getMembres() {
		return membres;
	}

	/**
	 * @param membres the membres to set
	 */
	public void setMembres(Set<Membre> membres) {
		this.membres = membres;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (nom != null && !nom.trim().isEmpty())
			result += "nom: " + nom;
		if (description != null && !description.trim().isEmpty())
			result += ", description: " + description;
		return result;
	}

}