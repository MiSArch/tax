package org.misarch.tax.graphql

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Mutation
import graphql.schema.DataFetchingEnvironment
import org.misarch.tax.graphql.input.CreateTaxRateInput
import org.misarch.tax.graphql.input.CreateTaxRateVersionInput
import org.misarch.tax.graphql.input.UpdateTaxRateInput
import org.misarch.tax.graphql.model.TaxRate
import org.misarch.tax.graphql.model.TaxRateVersion
import org.misarch.tax.service.TaxRateService
import org.misarch.tax.service.TaxRateVersionService
import org.springframework.stereotype.Component

/**
 * Defines GraphQL mutations
 *
 * @property taxRateService service for TaxRate
 * @property taxRateVersionService service for TaxRateVersion
 */
@Component
class Mutation(
    private val taxRateService: TaxRateService,
    private val taxRateVersionService: TaxRateVersionService
) : Mutation {

    @GraphQLDescription("Creates a new TaxRate")
    suspend fun createTaxRate(
        @GraphQLDescription("Input for the createTaxRate mutation")
        input: CreateTaxRateInput,
        dfe: DataFetchingEnvironment
    ): TaxRate {
        dfe.authorizedUser.checkIsEmployee()
        return taxRateService.createTaxRate(input).toDTO()
    }

    @GraphQLDescription("Creates a new TaxRateVersion")
    suspend fun createTaxRateVersion(
        @GraphQLDescription("Input for the createTaxRateVersion mutation")
        input: CreateTaxRateVersionInput,
        dfe: DataFetchingEnvironment
    ): TaxRateVersion {
        dfe.authorizedUser.checkIsEmployee()
        return taxRateVersionService.createTaxRateVersion(input).toDTO()
    }

    @GraphQLDescription("Updates a TaxRate")
    suspend fun updateTaxRate(
        @GraphQLDescription("Input for the updateTaxRate mutation")
        input: UpdateTaxRateInput,
        dfe: DataFetchingEnvironment
    ): TaxRate {
        dfe.authorizedUser.checkIsEmployee()
        return taxRateService.updateTaxRate(input).toDTO()
    }

}