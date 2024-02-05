package org.misarch.tax.graphql

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Query
import graphql.schema.DataFetchingEnvironment
import org.misarch.tax.graphql.dataloader.TaxRateDataLoader
import org.misarch.tax.graphql.dataloader.TaxRateVersionDataLoader
import org.misarch.tax.graphql.model.TaxRate
import org.misarch.tax.graphql.model.TaxRateVersion
import org.misarch.tax.graphql.model.connection.TaxRateConnection
import org.misarch.tax.graphql.model.connection.TaxRateOrder
import org.misarch.tax.persistence.repository.TaxRateRepository
import org.misarch.tax.persistence.repository.TaxRateVersionRepository
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.CompletableFuture

/**
 * Defines GraphQL queries
 *
 * @property taxRateRepository repository for TaxRate
 * @property taxRateVersionRepository repository for TaxRateVersion
 */
@Component
class Query(
    private val taxRateRepository: TaxRateRepository,
    private val taxRateVersionRepository: TaxRateVersionRepository
) : Query {

    @GraphQLDescription("Get a TaxRate by id")
    fun taxRate(
        @GraphQLDescription("The id of the TaxRate")
        id: UUID,
        dfe: DataFetchingEnvironment
    ): CompletableFuture<TaxRate> {
        return dfe.getDataLoader<UUID, TaxRate>(TaxRateDataLoader::class.simpleName!!).load(id)
    }

    @GraphQLDescription("Get a TaxRateVersion by id")
    fun taxRateVersion(
        @GraphQLDescription("The id of the TaxRateVersion")
        id: UUID,
        dfe: DataFetchingEnvironment
    ): CompletableFuture<TaxRateVersion> {
        return dfe.getDataLoader<UUID, TaxRateVersion>(TaxRateVersionDataLoader::class.simpleName!!).load(id)
    }

    @GraphQLDescription("Get all TaxRates")
    suspend fun taxRates(
        @GraphQLDescription("Number of items to return")
        first: Int? = null,
        @GraphQLDescription("Number of items to skip")
        skip: Int? = null,
        @GraphQLDescription("Ordering")
        orderBy: TaxRateOrder? = null
    ): TaxRateConnection {
        return TaxRateConnection(first, skip, null, orderBy, taxRateRepository)
    }

}