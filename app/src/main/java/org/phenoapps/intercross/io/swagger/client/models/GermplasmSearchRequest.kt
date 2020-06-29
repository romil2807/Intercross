/**
 * BrAPI-Germplasm
 * The Breeding API (BrAPI) is a Standardized REST ful Web Service API Specification for communicating Plant Breeding Data. BrAPI allows for easy data sharing between databases and tools involved in plant breeding. <div class=\"brapi-section\"> <h2 class=\"brapi-section-title\">General Reference Documentation</h2> <div class=\"gen-info-link\"><a href=\"https://github.com/plantbreeding/API/blob/master/Specification/GeneralInfo/URL_Structure.md\">URL Structure</a></div> <div class=\"gen-info-link\"><a href=\"https://github.com/plantbreeding/API/blob/master/Specification/GeneralInfo/Response_Structure.md\">Response Structure</a></div> <div class=\"gen-info-link\"><a href=\"https://github.com/plantbreeding/API/blob/master/Specification/GeneralInfo/Date_Time_Encoding.md\">Date/Time Encoding</a></div> <div class=\"gen-info-link\"><a href=\"https://github.com/plantbreeding/API/blob/master/Specification/GeneralInfo/Location_Encoding.md\">Location Encoding</a></div> <div class=\"gen-info-link\"><a href=\"https://github.com/plantbreeding/API/blob/master/Specification/GeneralInfo/Error_Handling.md\">Error Handling</a></div> <div class=\"gen-info-link\"><a href=\"https://github.com/plantbreeding/API/blob/master/Specification/GeneralInfo/Search_Services.md\">Search Services</a></div> </div>  <div class=\"brapi-section\"> <h2 class=\"brapi-section-title\">BrAPI Core</h2> <div class=\"brapi-section-description\">The BrAPI Core module contains high level entities used for organization and management. This includes Programs, Trials, Studies, Locations, People, and Lists</div> <div class=\"version-number\">V2.0</div> <div class=\"link-btn\"><a href=\"https://github.com/plantbreeding/API/tree/master/Specification/BrAPI-Core\">GitHub</a></div> <div class=\"link-btn\"><a href=\"https://app.swaggerhub.com/apis/PlantBreedingAPI/BrAPI-Core\">SwaggerHub</a></div> <div class=\"link-btn\"><a href=\"https://brapicore.docs.apiary.io\">Apiary</a></div> <div class=\"stop-float\"></div> </div>  <div class=\"brapi-section\"> <h2 class=\"brapi-section-title\">BrAPI Phenotyping</h2> <div class=\"brapi-section-description\">The BrAPI Phenotyping module contains entities related to phenotypic observations. This includes Observation Units, Observations, Observation Variables, Traits, Scales, Methods, and Images</div> <div class=\"version-number\">V2.0</div> <div class=\"link-btn\"><a href=\"https://github.com/plantbreeding/API/tree/master/Specification/BrAPI-Phenotyping\">GitHub</a></div> <div class=\"link-btn\"><a href=\"https://app.swaggerhub.com/apis/PlantBreedingAPI/BrAPI-Phenotyping\">SwaggerHub</a></div> <div class=\"link-btn\"><a href=\"https://brapiphenotyping.docs.apiary.io\">Apiary</a></div> <div class=\"stop-float\"></div> </div>  <div class=\"brapi-section\"> <h2 class=\"brapi-section-title\">BrAPI Genotyping</h2> <div class=\"brapi-section-description\">The BrAPI Genotyping module contains entities related to genotyping analysis. This includes Samples, Markers, Variant Sets, Variants, Call Sets, Calls, References, Reads, and Vendor Orders</div> <div class=\"version-number\">V2.0</div> <div class=\"link-btn\"><a href=\"https://github.com/plantbreeding/API/tree/master/Specification/BrAPI-Genotyping\">GitHub</a></div> <div class=\"link-btn\"><a href=\"https://app.swaggerhub.com/apis/PlantBreedingAPI/BrAPI-Genotyping\">SwaggerHub</a></div> <div class=\"link-btn\"><a href=\"https://brapigenotyping.docs.apiary.io\">Apiary</a></div> <div class=\"stop-float\"></div> </div>  <div class=\"current-brapi-section brapi-section\"> <h2 class=\"brapi-section-title\">BrAPI Germplasm</h2> <div class=\"brapi-section-description\">The BrAPI Germplasm module contains entities related to germplasm management. This includes Germplasm, Germplasm Attributes, Seed Lots, Crosses, Pedigree, and Progeny</div> <div class=\"version-number\">V2.0</div> <div class=\"link-btn\"><a href=\"https://github.com/plantbreeding/API/tree/master/Specification/BrAPI-Germplasm\">GitHub</a></div> <div class=\"link-btn\"><a href=\"https://app.swaggerhub.com/apis/PlantBreedingAPI/BrAPI-Germplasm\">SwaggerHub</a></div> <div class=\"link-btn\"><a href=\"https://brapigermplasm.docs.apiary.io\">Apiary</a></div> <div class=\"stop-float\"></div> </div>  <style> .link-btn{ float: left;  margin: 2px 10px 0 0;  padding: 0 5px;  border-radius: 5px;  background-color: #ddd; } .stop-float{   clear: both; } .version-number{   float: left;    margin: 5px 10px 0 5px; } .brapi-section-title{   margin: 0 10px 0 0;   font-size: 20px; } .current-brapi-section{   font-weight: bolder;   border-radius: 5px;    background-color: #ddd; } .brapi-section{   padding: 5px 5px;  } .brapi-section-description{   margin: 5px 0 0 5px; } </style>
 *
 * OpenAPI spec version: 2.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */
package io.swagger.client.models

/**
 * 
 * @param commonCropNames Common name for the crop which this program is for
 * @param germplasmDbIds List of IDs which uniquely identify germplasm to search for
 * @param germplasmNames List of human readable names to identify germplasm to search for
 * @param studyDbIds List of study identifiers to search for
 * @param studyNames List of study names to filter search results
 * @param externalReferenceIDs List of external reference IDs. Could be a simple strings or a URIs. (use with `externalReferenceSources` parameter)
 * @param externalReferenceSources List of identifiers for the source system or database of an external reference (use with `externalReferenceIDs` parameter)
 * @param accessionNumbers List unique identifiers for accessions within a genebank
 * @param collections A specific panel/collection/population name this germplasm belongs to.
 * @param genus List of Genus names to identify germplasm
 * @param germplasmPUIs List of Permanent Unique Identifiers to identify germplasm
 * @param parentDbIds Search for Germplasm with these parents
 * @param progenyDbIds Search for Germplasm with these children
 * @param species List of Species names to identify germplasm
 * @param synonyms List of alternative names or IDs used to reference this germplasm
 */
data class GermplasmSearchRequest (

    /* Common name for the crop which this program is for */
    val commonCropNames: kotlin.Array<kotlin.String>? = null,
    /* List of IDs which uniquely identify germplasm to search for */
    val germplasmDbIds: kotlin.Array<kotlin.String>? = null,
    /* List of human readable names to identify germplasm to search for */
    val germplasmNames: kotlin.Array<kotlin.String>? = null,
    /* List of study identifiers to search for */
    val studyDbIds: kotlin.Array<kotlin.String>? = null,
    /* List of study names to filter search results */
    val studyNames: kotlin.Array<kotlin.String>? = null,
    /* List of external reference IDs. Could be a simple strings or a URIs. (use with `externalReferenceSources` parameter) */
    val externalReferenceIDs: kotlin.Array<kotlin.String>? = null,
    /* List of identifiers for the source system or database of an external reference (use with `externalReferenceIDs` parameter) */
    val externalReferenceSources: kotlin.Array<kotlin.String>? = null,
    /* List unique identifiers for accessions within a genebank */
    val accessionNumbers: kotlin.Array<kotlin.String>? = null,
    /* A specific panel/collection/population name this germplasm belongs to. */
    val collections: kotlin.Array<kotlin.String>? = null,
    /* List of Genus names to identify germplasm */
    val genus: kotlin.Array<kotlin.String>? = null,
    /* List of Permanent Unique Identifiers to identify germplasm */
    val germplasmPUIs: kotlin.Array<kotlin.String>? = null,
    /* Search for Germplasm with these parents */
    val parentDbIds: kotlin.Array<kotlin.String>? = null,
    /* Search for Germplasm with these children */
    val progenyDbIds: kotlin.Array<kotlin.String>? = null,
    /* List of Species names to identify germplasm */
    val species: kotlin.Array<kotlin.String>? = null,
    /* List of alternative names or IDs used to reference this germplasm */
    val synonyms: kotlin.Array<kotlin.String>? = null,
    /* Which result page is requested. The page indexing starts at 0 (the first page is 'page'= 0). Default is `0`. */
    val page: kotlin.Int? = null,
    /* The size of the pages to be returned. Default is `1000`. */
    val pageSize: kotlin.Int? = null
) {
}