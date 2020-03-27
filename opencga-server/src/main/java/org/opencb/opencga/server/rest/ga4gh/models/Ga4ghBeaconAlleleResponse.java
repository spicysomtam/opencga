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
 * Response to a query for information about an allele, made against the  beacon. 
 */
@ApiModel(description = "Response to a query for information about an allele, made against the  beacon. ")
@JsonPropertyOrder({
  Ga4ghBeaconAlleleResponse.JSON_PROPERTY_BEACON_ID,
  Ga4ghBeaconAlleleResponse.JSON_PROPERTY_API_VERSION,
  Ga4ghBeaconAlleleResponse.JSON_PROPERTY_EXISTS,
  Ga4ghBeaconAlleleResponse.JSON_PROPERTY_ALLELE_REQUEST,
  Ga4ghBeaconAlleleResponse.JSON_PROPERTY_DATASET_ALLELE_RESPONSES,
  Ga4ghBeaconAlleleResponse.JSON_PROPERTY_ERROR,
  Ga4ghBeaconAlleleResponse.JSON_PROPERTY_INFO,
  Ga4ghBeaconAlleleResponse.JSON_PROPERTY_BEACON_HANDOVER
})
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJerseyServerCodegen", date = "2020-03-24T15:12:46.170Z[Europe/London]")
public class Ga4ghBeaconAlleleResponse   {
  public static final String JSON_PROPERTY_BEACON_ID = "beaconId";
  @JsonProperty(JSON_PROPERTY_BEACON_ID)
  private String beaconId;

  public static final String JSON_PROPERTY_API_VERSION = "apiVersion";
  @JsonProperty(JSON_PROPERTY_API_VERSION)
  private String apiVersion;

  public static final String JSON_PROPERTY_EXISTS = "exists";
  @JsonProperty(JSON_PROPERTY_EXISTS)
  private Boolean exists;

  public static final String JSON_PROPERTY_ALLELE_REQUEST = "alleleRequest";
  @JsonProperty(JSON_PROPERTY_ALLELE_REQUEST)
  private Ga4ghBeaconAlleleRequest alleleRequest;

  public static final String JSON_PROPERTY_DATASET_ALLELE_RESPONSES = "datasetAlleleResponses";
  @JsonProperty(JSON_PROPERTY_DATASET_ALLELE_RESPONSES)
  private List<Ga4ghBeaconDatasetAlleleResponse> datasetAlleleResponses = null;

  public static final String JSON_PROPERTY_ERROR = "error";
  @JsonProperty(JSON_PROPERTY_ERROR)
  private Ga4ghBeaconError error;

  public static final String JSON_PROPERTY_INFO = "info";
  @JsonProperty(JSON_PROPERTY_INFO)
  private Object info;

  public static final String JSON_PROPERTY_BEACON_HANDOVER = "beaconHandover";
  @JsonProperty(JSON_PROPERTY_BEACON_HANDOVER)
  private List<Ga4ghHandover> beaconHandover = null;

  public Ga4ghBeaconAlleleResponse beaconId(String beaconId) {
    this.beaconId = beaconId;
    return this;
  }

  /**
   * Identifier of the beacon, as defined in &#x60;Beacon&#x60;. 
   * @return beaconId
   **/
  @JsonProperty("beaconId")
  @ApiModelProperty(required = true, value = "Identifier of the beacon, as defined in `Beacon`. ")
  @NotNull 
  public String getBeaconId() {
    return beaconId;
  }

  public void setBeaconId(String beaconId) {
    this.beaconId = beaconId;
  }

  public Ga4ghBeaconAlleleResponse apiVersion(String apiVersion) {
    this.apiVersion = apiVersion;
    return this;
  }

  /**
   * Version of the API. If specified, the value must match &#x60;apiVersion&#x60; in Beacon
   * @return apiVersion
   **/
  @JsonProperty("apiVersion")
  @ApiModelProperty(value = "Version of the API. If specified, the value must match `apiVersion` in Beacon")
  
  public String getApiVersion() {
    return apiVersion;
  }

  public void setApiVersion(String apiVersion) {
    this.apiVersion = apiVersion;
  }

  public Ga4ghBeaconAlleleResponse exists(Boolean exists) {
    this.exists = exists;
    return this;
  }

  /**
   * Indicator of whether the given allele was observed in any of the datasets queried. This should be non-null, unless there was an error, in which case &#x60;error&#x60; has to be non-null.
   * @return exists
   **/
  @JsonProperty("exists")
  @ApiModelProperty(value = "Indicator of whether the given allele was observed in any of the datasets queried. This should be non-null, unless there was an error, in which case `error` has to be non-null.")
  
  public Boolean getExists() {
    return exists;
  }

  public void setExists(Boolean exists) {
    this.exists = exists;
  }

  public Ga4ghBeaconAlleleResponse alleleRequest(Ga4ghBeaconAlleleRequest alleleRequest) {
    this.alleleRequest = alleleRequest;
    return this;
  }

  /**
   * Get alleleRequest
   * @return alleleRequest
   **/
  @JsonProperty("alleleRequest")
  @ApiModelProperty(value = "")
  @Valid 
  public Ga4ghBeaconAlleleRequest getAlleleRequest() {
    return alleleRequest;
  }

  public void setAlleleRequest(Ga4ghBeaconAlleleRequest alleleRequest) {
    this.alleleRequest = alleleRequest;
  }

  public Ga4ghBeaconAlleleResponse datasetAlleleResponses(List<Ga4ghBeaconDatasetAlleleResponse> datasetAlleleResponses) {
    this.datasetAlleleResponses = datasetAlleleResponses;
    return this;
  }

  public Ga4ghBeaconAlleleResponse addDatasetAlleleResponsesItem(Ga4ghBeaconDatasetAlleleResponse datasetAlleleResponsesItem) {
    if (this.datasetAlleleResponses == null) {
      this.datasetAlleleResponses = new ArrayList<Ga4ghBeaconDatasetAlleleResponse>();
    }
    this.datasetAlleleResponses.add(datasetAlleleResponsesItem);
    return this;
  }

  /**
   * Indicator of whether the given allele was  observed in individual datasets. This should be non-null if &#x60;includeDatasetResponses&#x60; in the corresponding &#x60;BeaconAlleleRequest&#x60; is true, and null otherwise.
   * @return datasetAlleleResponses
   **/
  @JsonProperty("datasetAlleleResponses")
  @ApiModelProperty(value = "Indicator of whether the given allele was  observed in individual datasets. This should be non-null if `includeDatasetResponses` in the corresponding `BeaconAlleleRequest` is true, and null otherwise.")
  @Valid 
  public List<Ga4ghBeaconDatasetAlleleResponse> getDatasetAlleleResponses() {
    return datasetAlleleResponses;
  }

  public void setDatasetAlleleResponses(List<Ga4ghBeaconDatasetAlleleResponse> datasetAlleleResponses) {
    this.datasetAlleleResponses = datasetAlleleResponses;
  }

  public Ga4ghBeaconAlleleResponse error(Ga4ghBeaconError error) {
    this.error = error;
    return this;
  }

  /**
   * Get error
   * @return error
   **/
  @JsonProperty("error")
  @ApiModelProperty(value = "")
  @Valid 
  public Ga4ghBeaconError getError() {
    return error;
  }

  public void setError(Ga4ghBeaconError error) {
    this.error = error;
  }

  public Ga4ghBeaconAlleleResponse info(Object info) {
    this.info = info;
    return this;
  }

  /**
   * Additional unspecified metadata about the response or its content. 
   * @return info
   **/
  @JsonProperty("info")
  @ApiModelProperty(example = "{\"additionalInfoKey\":\"additionalInfoValue\"}", value = "Additional unspecified metadata about the response or its content. ")
  @Valid 
  public Object getInfo() {
    return info;
  }

  public void setInfo(Object info) {
    this.info = info;
  }

  public Ga4ghBeaconAlleleResponse beaconHandover(List<Ga4ghHandover> beaconHandover) {
    this.beaconHandover = beaconHandover;
    return this;
  }

  public Ga4ghBeaconAlleleResponse addBeaconHandoverItem(Ga4ghHandover beaconHandoverItem) {
    if (this.beaconHandover == null) {
      this.beaconHandover = new ArrayList<Ga4ghHandover>();
    }
    this.beaconHandover.add(beaconHandoverItem);
    return this;
  }

  /**
   * Get beaconHandover
   * @return beaconHandover
   **/
  @JsonProperty("beaconHandover")
  @ApiModelProperty(value = "")
  @Valid 
  public List<Ga4ghHandover> getBeaconHandover() {
    return beaconHandover;
  }

  public void setBeaconHandover(List<Ga4ghHandover> beaconHandover) {
    this.beaconHandover = beaconHandover;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Ga4ghBeaconAlleleResponse beaconAlleleResponse = (Ga4ghBeaconAlleleResponse) o;
    return Objects.equals(this.beaconId, beaconAlleleResponse.beaconId) &&
        Objects.equals(this.apiVersion, beaconAlleleResponse.apiVersion) &&
        Objects.equals(this.exists, beaconAlleleResponse.exists) &&
        Objects.equals(this.alleleRequest, beaconAlleleResponse.alleleRequest) &&
        Objects.equals(this.datasetAlleleResponses, beaconAlleleResponse.datasetAlleleResponses) &&
        Objects.equals(this.error, beaconAlleleResponse.error) &&
        Objects.equals(this.info, beaconAlleleResponse.info) &&
        Objects.equals(this.beaconHandover, beaconAlleleResponse.beaconHandover);
  }

  @Override
  public int hashCode() {
    return Objects.hash(beaconId, apiVersion, exists, alleleRequest, datasetAlleleResponses, error, info, beaconHandover);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Ga4ghBeaconAlleleResponse {\n");

    sb.append("    beaconId: ").append(toIndentedString(beaconId)).append("\n");
    sb.append("    apiVersion: ").append(toIndentedString(apiVersion)).append("\n");
    sb.append("    exists: ").append(toIndentedString(exists)).append("\n");
    sb.append("    alleleRequest: ").append(toIndentedString(alleleRequest)).append("\n");
    sb.append("    datasetAlleleResponses: ").append(toIndentedString(datasetAlleleResponses)).append("\n");
    sb.append("    error: ").append(toIndentedString(error)).append("\n");
    sb.append("    info: ").append(toIndentedString(info)).append("\n");
    sb.append("    beaconHandover: ").append(toIndentedString(beaconHandover)).append("\n");
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
