/*
 * GA4GH Beacon API Specification
 * Schemas to be used as default by the Beacon.
 *
 * The version of the OpenAPI document: 1.0
 * Contact: beacon@ga4gh.org
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package org.opencb.opencga.server.rest.ga4gh.models.schemas;

import java.util.Objects;

import com.google.gson.annotations.SerializedName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Blabla 
 */
@ApiModel(description = "Blabla ")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2020-03-24T09:26:11.315Z[Europe/London]")
public class Ga4ghVariant {
  public static final String SERIALIZED_NAME_VARIANT_DETAILS = "variantDetails";
  @SerializedName(SERIALIZED_NAME_VARIANT_DETAILS)
  private Ga4ghVariantDetails variantDetails;

  public static final String SERIALIZED_NAME_INFO = "info";
  @SerializedName(SERIALIZED_NAME_INFO)
  private Object info;


  public Ga4ghVariant variantDetails(Ga4ghVariantDetails variantDetails) {
    
    this.variantDetails = variantDetails;
    return this;
  }

   /**
   * Get variantDetails
   * @return variantDetails
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public Ga4ghVariantDetails getVariantDetails() {
    return variantDetails;
  }


  public void setVariantDetails(Ga4ghVariantDetails variantDetails) {
    this.variantDetails = variantDetails;
  }


  public Ga4ghVariant info(Object info) {
    
    this.info = info;
    return this;
  }

   /**
   * Additional unspecified metadata about the dataset response or its  content. 
   * @return info
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(example = "{\"additionalInfoKey1\":[\"additionalInfoValue1\",\"additionalInfoValue2\"],\"additionalInfoKey2\":\"additionalInfoValue3\"}", value = "Additional unspecified metadata about the dataset response or its  content. ")

  public Object getInfo() {
    return info;
  }


  public void setInfo(Object info) {
    this.info = info;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Ga4ghVariant variant = (Ga4ghVariant) o;
    return Objects.equals(this.variantDetails, variant.variantDetails) &&
        Objects.equals(this.info, variant.info);
  }

  @Override
  public int hashCode() {
    return Objects.hash(variantDetails, info);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Ga4ghVariant {\n");
    sb.append("    variantDetails: ").append(toIndentedString(variantDetails)).append("\n");
    sb.append("    info: ").append(toIndentedString(info)).append("\n");
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
