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
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Ga4ghHandover
 */
@JsonPropertyOrder({
  Ga4ghHandover.JSON_PROPERTY_HANDOVER_TYPE,
  Ga4ghHandover.JSON_PROPERTY_NOTE,
  Ga4ghHandover.JSON_PROPERTY_URL
})
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJerseyServerCodegen", date = "2020-03-24T15:12:46.170Z[Europe/London]")
public class Ga4ghHandover   {
  public static final String JSON_PROPERTY_HANDOVER_TYPE = "handoverType";
  @JsonProperty(JSON_PROPERTY_HANDOVER_TYPE)
  private Ga4ghHandoverType handoverType;

  public static final String JSON_PROPERTY_NOTE = "note";
  @JsonProperty(JSON_PROPERTY_NOTE)
  private String note;

  public static final String JSON_PROPERTY_URL = "url";
  @JsonProperty(JSON_PROPERTY_URL)
  private String url;

  public Ga4ghHandover handoverType(Ga4ghHandoverType handoverType) {
    this.handoverType = handoverType;
    return this;
  }

  /**
   * Get handoverType
   * @return handoverType
   **/
  @JsonProperty("handoverType")
  @ApiModelProperty(required = true, value = "")
  @NotNull @Valid 
  public Ga4ghHandoverType getHandoverType() {
    return handoverType;
  }

  public void setHandoverType(Ga4ghHandoverType handoverType) {
    this.handoverType = handoverType;
  }

  public Ga4ghHandover note(String note) {
    this.note = note;
    return this;
  }

  /**
   * An optional text including considerations on the handover link  provided. 
   * @return note
   **/
  @JsonProperty("note")
  @ApiModelProperty(example = "This handover link provides access to a summarized VCF. To access  the VCF containing the details for each sample filling an  application is required. See Beacon contact information details. ", value = "An optional text including considerations on the handover link  provided. ")
  
  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public Ga4ghHandover url(String url) {
    this.url = url;
    return this;
  }

  /**
   * URL endpoint to where the handover process could progress (in RFC  3986 format). 
   * @return url
   **/
  @JsonProperty("url")
  @ApiModelProperty(example = "\"https://api.mygenomeservice.org/handover/9dcc48d7-fc88-11e8-9110-b0c592dbf8c0/\" ", required = true, value = "URL endpoint to where the handover process could progress (in RFC  3986 format). ")
  @NotNull 
  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Ga4ghHandover handover = (Ga4ghHandover) o;
    return Objects.equals(this.handoverType, handover.handoverType) &&
        Objects.equals(this.note, handover.note) &&
        Objects.equals(this.url, handover.url);
  }

  @Override
  public int hashCode() {
    return Objects.hash(handoverType, note, url);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Ga4ghHandover {\n");

    sb.append("    handoverType: ").append(toIndentedString(handoverType)).append("\n");
    sb.append("    note: ").append(toIndentedString(note)).append("\n");
    sb.append("    url: ").append(toIndentedString(url)).append("\n");
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
