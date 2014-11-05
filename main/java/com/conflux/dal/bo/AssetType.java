package com.conflux.dal.bo;

// Generated 17 Jan, 2013 8:44:06 PM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * AssetType generated by hbm2java
 */
@Entity
@Table(name = "asset_type", catalog = "risk_module")
public class AssetType implements java.io.Serializable {

	private Integer assetTypeId;
	private String name;
	private String description;
	private Set<AssetSubType> assetSubTypes = new HashSet<AssetSubType>(0);

	public AssetType() {
	}

	public AssetType(String name, String description,
			Set<AssetSubType> assetSubTypes) {
		this.name = name;
		this.description = description;
		this.assetSubTypes = assetSubTypes;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "asset_type_id", unique = true, nullable = false)
	public Integer getAssetTypeId() {
		return this.assetTypeId;
	}

	public void setAssetTypeId(Integer assetTypeId) {
		this.assetTypeId = assetTypeId;
	}

	@Column(name = "name", length = 45)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "description", length = 100)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "assetType")
	public Set<AssetSubType> getAssetSubTypes() {
		return this.assetSubTypes;
	}

	public void setAssetSubTypes(Set<AssetSubType> assetSubTypes) {
		this.assetSubTypes = assetSubTypes;
	}

}
