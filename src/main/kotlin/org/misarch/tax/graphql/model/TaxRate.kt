package org.misarch.tax.graphql.model

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import com.expediagroup.graphql.generator.federation.directives.FieldSet
import com.expediagroup.graphql.generator.federation.directives.KeyDirective
import graphql.schema.DataFetchingEnvironment
import org.misarch.tax.graphql.dataloader.TaxRateVersionDataLoader
import org.misarch.tax.graphql.model.connection.TaxRateVersionConnection
import org.misarch.tax.graphql.model.connection.TaxRateVersionOrder
import org.misarch.tax.persistence.model.TaxRateVersionEntity
import org.misarch.tax.persistence.repository.TaxRateVersionRepository
import org.springframework.beans.factory.annotation.Autowired
import java.util.*
import java.util.concurrent.CompletableFuture

@GraphQLDescription("A TaxRate.")
@KeyDirective(fields = FieldSet("id"))
class TaxRate(
    id: UUID,
    @property:GraphQLDescription("The name of the TaxRate")
    val name: String,
    @property:GraphQLDescription("The description of the TaxRate")
    val description: String,
    private val currentVersionId: UUID
) : Node(id) {

    @GraphQLDescription("The current version of the TaxRate.")
    fun currentVersion(
        dfe: DataFetchingEnvironment
    ): CompletableFuture<TaxRateVersion> {
        return dfe.getDataLoader<UUID, TaxRateVersion>(TaxRateVersionDataLoader::class.simpleName!!)
            .load(currentVersionId, dfe)
    }

    @GraphQLDescription("Get all associated versions")
    suspend fun versions(
        @GraphQLDescription("Number of items to return")
        first: Int? = null,
        @GraphQLDescription("Number of items to skip")
        skip: Int? = null,
        @GraphQLDescription("Ordering")
        orderBy: TaxRateVersionOrder? = null,
        @GraphQLIgnore
        @Autowired
        taxRateVersionRepository: TaxRateVersionRepository
    ): TaxRateVersionConnection {
        return TaxRateVersionConnection(
            first,
            skip,
            TaxRateVersionEntity.ENTITY.taxRateId.eq(id),
            orderBy,
            taxRateVersionRepository
        )
    }


}