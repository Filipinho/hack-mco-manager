package com.mco.fr.entities;

import javax.persistence.Entity;

import java.io.Serializable;

import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Version;

import java.lang.Override;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@XmlRootElement
public class Membre implements Serializable {

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

	@Column(length = 3)
	private String trigramme;

	@Column
	private String nom;

	@Column
	private String prenom;

	@Column
	private String email;

	@Column
	private String organisation;
	
	@ManyToMany(mappedBy = "membres")
	@JsonIgnore
	private Set<Projet> projets = new HashSet<Projet>();
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
			name = "membre_equipe", 
			joinColumns = { @JoinColumn(name = "membre_id") }, 
			inverseJoinColumns = { @JoinColumn(name = "equipe_id") })
	private Set<Equipe> equipes = new HashSet<Equipe>();
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
			name = "membre_activite", 
			joinColumns = { @JoinColumn(name = "membre_id") }, 
			inverseJoinColumns = { @JoinColumn(name = "activite_id") })
	private Set<Activite> activites = new HashSet<Activite>();
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
			name = "membre_outils", 
			joinColumns = { @JoinColumn(name = "membre_id") }, 
			inverseJoinColumns = { @JoinColumn(name = "outils_id") })
	private Set<Outils> outils = new HashSet<Outils>();
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
			name = "membre_competence", 
			joinColumns = { @JoinColumn(name = "membre_id") }, 
			inverseJoinColumns = { @JoinColumn(name = "competence_id") })
	private Set<Competence> competences = new HashSet<Competence>();
	
//	@ManyToMany(fetch=FetchType.EAGER)
//	@JoinTable(
//			name = "membre_competence", 
//			joinColumns = { @JoinColumn(name = "membre_id") }, 
//			inverseJoinColumns = { @JoinColumn(name = "competence_id") })
//	private Set<Competence> competences = new HashSet<Competence>();

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the version
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(int version) {
		this.version = version;
	}

	/**
	 * @return the trigramme
	 */
	public String getTrigramme() {
		return trigramme;
	}

	/**
	 * @param trigramme the trigramme to set
	 */
	public void setTrigramme(String trigramme) {
		this.trigramme = trigramme;
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
	 * @return the prenom
	 */
	public String getPrenom() {
		return prenom;
	}

	/**
	 * @param prenom the prenom to set
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the organisation
	 */
	public String getOrganisation() {
		return organisation;
	}

	/**
	 * @param organisation the organisation to set
	 */
	public void setOrganisation(String organisation) {
		this.organisation = organisation;
	}

	/**
	 * @return the projets
	 */
	public Set<Projet> getProjets() {
		return projets;
	}

	/**
	 * @param projets the projets to set
	 */
	public void setProjets(Set<Projet> projets) {
		this.projets = projets;
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

	@Override
	public int hashCode() {
		if (id != null) {
			return id.hashCode();
		}
		return super.hashCode();
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
			return id.equals(((Membre) that).id);
		}
		return super.equals(that);
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (trigramme != null && !trigramme.trim().isEmpty())
			result += "trigramme: " + trigramme;
		if (nom != null && !nom.trim().isEmpty())
			result += ", nom: " + nom;
		if (prenom != null && !prenom.trim().isEmpty())
			result += ", prenom: " + prenom;
		if (email != null && !email.trim().isEmpty())
			result += ", email: " + email;
		if (organisation != null && !organisation.trim().isEmpty())
			result += ", organisation: " + organisation;
		return result;
	}
}