package com.mco.fr.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@XmlRootElement
public class Competence implements Serializable {

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
	private String famille;

	@Column
	private String libelle;

	@Column
	private String commentaire;

	@ManyToMany(mappedBy = "competences")
	@JsonIgnore
	private Set<Projet> projets = new HashSet<Projet>();

	@ManyToMany(mappedBy = "competences")
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
			return id.equals(((Competence) that).id);
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

	public String getFamille() {
		return this.famille;
	}

	public void setFamille(final String famille) {
		this.famille = famille;
	}

	public String getLibelle() {
		return this.libelle;
	}

	public void setLibelle(final String libelle) {
		this.libelle = libelle;
	}

	public String getCommentaire() {
		return this.commentaire;
	}

	public void setCommentaire(final String commentaire) {
		this.commentaire = commentaire;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (famille != null && !famille.trim().isEmpty())
			result += "famille: " + famille;
		if (libelle != null && !libelle.trim().isEmpty())
			result += ", libelle: " + libelle;
		if (commentaire != null && !commentaire.trim().isEmpty())
			result += ", commentaire: " + commentaire;
		return result;
	}

	@JsonIgnore
	public Set<Projet> getProjets() {
		return this.projets;
	}

	@JsonIgnore
	public void setProjets(final Set<Projet> projets) {
		this.projets = projets;
	}

}