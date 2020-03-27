/*
 * GA4GH Beacon API Specification
 * A Beacon is a web service for genetic data sharing that can be queried for  information about variants, individuals or samples.
 *
 * The version of the OpenAPI document: 2.0
 * Contact: beacon@ga4gh.org
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package org.opencb.opencga.server.rest.ga4gh.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Data use of a resource based on consent codes.
 */
@ApiModel(description = "Data use of a resource based on consent codes.")
@JsonPropertyOrder({
  Ga4ghConsentCodeDataUse.JSON_PROPERTY_PRIMARY_CATEGORY,
  Ga4ghConsentCodeDataUse.JSON_PROPERTY_SECONDARY_CATEGORIES,
  Ga4ghConsentCodeDataUse.JSON_PROPERTY_REQUIREMENTS,
  Ga4ghConsentCodeDataUse.JSON_PROPERTY_VERSION
})
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJerseyServerCodegen", date = "2020-03-24T15:12:46.170Z[Europe/London]")
public class Ga4ghConsentCodeDataUse   {
  public static final String JSON_PROPERTY_PRIMARY_CATEGORY = "primaryCategory";
  @JsonProperty(JSON_PROPERTY_PRIMARY_CATEGORY)
  private Ga4ghConsentCodeDataUseCondition primaryCategory;

  public static final String JSON_PROPERTY_SECONDARY_CATEGORIES = "secondaryCategories";
  @JsonProperty(JSON_PROPERTY_SECONDARY_CATEGORIES)
  private List<Ga4ghConsentCodeDataUseCondition> secondaryCategories = null;

  public static final String JSON_PROPERTY_REQUIREMENTS = "requirements";
  @JsonProperty(JSON_PROPERTY_REQUIREMENTS)
  private List<Ga4ghConsentCodeDataUseCondition> requirements = null;

  public static final String JSON_PROPERTY_VERSION = "version";
  @JsonProperty(JSON_PROPERTY_VERSION)
  private String version;

  public Ga4ghConsentCodeDataUse primaryCategory(Ga4ghConsentCodeDataUseCondition primaryCategory) {
    this.primaryCategory = primaryCategory;
    return this;
  }

  /**
   * Get primaryCategory
   * @return primaryCategory
   **/
  @JsonProperty("primaryCategory")
  @ApiModelProperty(required = true, value = "")
  @NotNull @Valid 
  public Ga4ghConsentCodeDataUseCondition getPrimaryCategory() {
    return primaryCategory;
  }

  public void setPrimaryCategory(Ga4ghConsentCodeDataUseCondition primaryCategory) {
    this.primaryCategory = primaryCategory;
  }

  public Ga4ghConsentCodeDataUse secondaryCategories(List<Ga4ghConsentCodeDataUseCondition> secondaryCategories) {
    this.secondaryCategories = secondaryCategories;
    return this;
  }

  public Ga4ghConsentCodeDataUse addSecondaryCategoriesItem(Ga4ghConsentCodeDataUseCondition secondaryCategoriesItem) {
    if (this.secondaryCategories == null) {
      this.secondaryCategories = new ArrayList<Ga4ghConsentCodeDataUseCondition>();
    }
    this.secondaryCategories.add(secondaryCategoriesItem);
    return this;
  }

  /**
   * Get secondaryCategories
   * @return secondaryCategories
   **/
  @JsonProperty("secondaryCategories")
  @ApiModelProperty(value = "")
  @Valid 
  public List<Ga4ghConsentCodeDataUseCondition> getSecondaryCategories() {
    return secondaryCategories;
  }

  public void setSecondaryCategories(List<Ga4ghConsentCodeDataUseCondition> secondaryCategories) {
    this.secondaryCategories = secondaryCategories;
  }

  public Ga4ghConsentCodeDataUse requirements(List<Ga4ghConsentCodeDataUseCondition> requirements) {
    this.requirements = requirements;
    return this;
  }

  public Ga4ghConsentCodeDataUse addRequirementsItem(Ga4ghConsentCodeDataUseCondition requirementsItem) {
    if (this.requirements == null) {
      this.requirements = new ArrayList<Ga4ghConsentCodeDataUseCondition>();
    }
    this.requirements.add(requirementsItem);
    return this;
  }

  /**
   * Get requirements
   * @return requirements
   **/
  @JsonProperty("requirements")
  @ApiModelProperty(value = "")
  @Valid 
  public List<Ga4ghConsentCodeDataUseCondition> getRequirements() {
    return requirements;
  }

  public void setRequirements(List<Ga4ghConsentCodeDataUseCondition> requirements) {
    this.requirements = requirements;
  }

  public Ga4ghConsentCodeDataUse version(String version) {
    this.version = version;
    return this;
  }

  /**
   * Version of the data use specification.
   * @return version
   **/
  @JsonProperty("version")
  @ApiModelProperty(example = "0.1", required = true, value = "Version of the data use specification.")
  @NotNull 
  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Ga4ghConsentCodeDataUse consentCodeDataUse = (Ga4ghConsentCodeDataUse) o;
    return Objects.equals(this.primaryCategory, consentCodeDataUse.primaryCategory) &&
        Objects.equals(this.secondaryCategories, consentCodeDataUse.secondaryCategories) &&
        Objects.equals(this.requirements, consentCodeDataUse.requirements) &&
        Objects.equals(this.version, consentCodeDataUse.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(primaryCategory, secondaryCategories, requirements, version);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Ga4ghConsentCodeDataUse {\n");

    sb.append("    primaryCategory: ").append(toIndentedString(primaryCategory)).append("\n");
    sb.append("    secondaryCategories: ").append(toIndentedString(secondaryCategories)).append("\n");
    sb.append("    requirements: ").append(toIndentedString(requirements)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
