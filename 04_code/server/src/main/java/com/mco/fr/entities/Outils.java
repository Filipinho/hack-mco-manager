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
public class Outils implements Serializable {

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
	private String type;

	@Column
	private String label;

	@Column
	private String description;

	@ManyToMany(mappedBy = "outils")
	@JsonIgnore
	private Set<Projet> projets = new HashSet<Projet>();
	
	@ManyToMany(mappedBy = "outils")
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
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
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
			return id.equals(((Outils) that).id);
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
		if (type != null && !type.trim().isEmpty())
			result += "type: " + type;
		if (label != null && !label.trim().isEmpty())
			result += ", label: " + label;
		if (description != null && !description.trim().isEmpty())
			result += ", description: " + description;
		return result;
	}
}