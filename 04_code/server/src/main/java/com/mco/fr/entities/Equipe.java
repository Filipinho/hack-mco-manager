package com.mco.fr.entities;

import javax.persistence.Entity;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.Version;

import java.lang.Override;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@XmlRootElement
public class Equipe implements Serializable {

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
	
	@ManyToMany(mappedBy = "equipes")
	@JsonIgnore
	private Set<Projet> projets = new HashSet<Projet>();
	
	@ManyToMany(mappedBy = "equipes")
	@JsonIgnore
	private Set<Membre> membres = new HashSet<Membre>();

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
	 * @return the famille
	 */
	public String getFamille() {
		return famille;
	}

	/**
	 * @param famille the famille to set
	 */
	public void setFamille(String famille) {
		this.famille = famille;
	}

	/**
	 * @return the projets
	 */
	@JsonIgnore
	public Set<Projet> getProjets() {
		return projets;
	}

	/**
	 * @param projets the projets to set
	 */
	@JsonIgnore
	public void setProjets(Set<Projet> projets) {
		this.projets = projets;
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
			return id.equals(((Equipe) that).id);
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

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (famille != null && !famille.trim().isEmpty())
			result += "famille: " + famille;
		return result;
	}
}