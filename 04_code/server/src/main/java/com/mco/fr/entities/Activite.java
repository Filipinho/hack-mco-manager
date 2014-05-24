package com.mco.fr.entities;

import javax.persistence.Entity;

import java.io.Serializable;

import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.Version;

import java.lang.Override;

import javax.xml.bind.annotation.XmlRootElement;

import java.util.Set;
import java.util.HashSet;

import com.mco.fr.entities.Tache;

import javax.persistence.OneToMany;
import javax.persistence.CascadeType;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@XmlRootElement
public class Activite implements Serializable {

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
	private String famille;

	@Column
	private String code;

	@Column
	private String libelle;

	@Column
	private Double duree;

	@Column
	private String commentaire;

	@OneToMany(mappedBy = "activite", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	private Set<Tache> taches = new HashSet<Tache>();

	@ManyToMany(mappedBy = "activites")
	@JsonIgnore
	private Set<Projet> projets = new HashSet<Projet>();
	
	@ManyToMany(mappedBy = "activites")
	@JsonIgnore
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

	/**
	 * @return the membres
	 */
	@JsonIgnore
	public Set<Membre> getMembres() {
		return membres;
	}

	/**
	 * @param membres the membres to set
	 */
	@JsonIgnore
	public void setMembres(Set<Membre> membres) {
		this.membres = membres;
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
			return id.equals(((Activite) that).id);
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
	 * @param nom
	 *            the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * @return the famille
	 */
	public String getFamille() {
		return famille;
	}

	/**
	 * @param famille
	 *            the famille to set
	 */
	public void setFamille(String famille) {
		this.famille = famille;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the libelle
	 */
	public String getLibelle() {
		return libelle;
	}

	/**
	 * @param libelle
	 *            the libelle to set
	 */
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	/**
	 * @return the duree
	 */
	public Double getDuree() {
		return duree;
	}

	/**
	 * @param duree
	 *            the duree to set
	 */
	public void setDuree(Double duree) {
		this.duree = duree;
	}

	/**
	 * @return the commentaire
	 */
	public String getCommentaire() {
		return commentaire;
	}

	/**
	 * @param commentaire
	 *            the commentaire to set
	 */
	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	/**
	 * @return the taches
	 */
	public Set<Tache> getTaches() {
		return taches;
	}

	/**
	 * @param taches
	 *            the taches to set
	 */
	public void setTaches(Set<Tache> taches) {
		this.taches = taches;
	}

	/**
	 * @return the projets
	 */
	public Set<Projet> getProjets() {
		return projets;
	}

	/**
	 * @param projets
	 *            the projets to set
	 */
	public void setProjets(Set<Projet> projets) {
		this.projets = projets;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (nom != null && !nom.trim().isEmpty())
			result += "nom: " + nom;
		if (famille != null && !famille.trim().isEmpty())
			result += ", famille: " + famille;
		if (code != null && !code.trim().isEmpty())
			result += ", code: " + code;
		if (libelle != null && !libelle.trim().isEmpty())
			result += ", libelle: " + libelle;
		if (duree != null)
			result += ", duree: " + duree;
		if (commentaire != null && !commentaire.trim().isEmpty())
			result += ", commentaire: " + commentaire;
		return result;
	}

}