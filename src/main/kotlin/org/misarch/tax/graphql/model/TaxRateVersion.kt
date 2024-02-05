package org.misarch.tax.graphql.model

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.federation.directives.FieldSet
import com.expediagroup.graphql.generator.federation.directives.KeyDirective
import graphql.schema.DataFetchingEnvironment
import org.misarch.tax.graphql.dataloader.TaxRateDataLoader
import java.time.OffsetDateTime
import java.util.*
import java.util.concurrent.CompletableFuture

@GraphQLDescription("A TaxRateVersion.")
@KeyDirective(fields = FieldSet("id"))
class TaxRateVersion(
    id: UUID,
    @property:GraphQLDescription("The rate of the TaxRateVersion")
    val rate: Double,
    @property:GraphQLDescription("Version of the TaxRateVersion")
    val version: Int,
    @property:GraphQLDescription("Time stamp when the TaxRateVersion was created")
    val createdAt: OffsetDateTime,
    private val taxRateId: UUID
) : Node(id) {

    @GraphQLDescription("The current version.")
    fun currentVersion(
        dfe: DataFetchingEnvironment
    ): CompletableFuture<TaxRate> {
        return dfe.getDataLoader<UUID, TaxRate>(TaxRateDataLoader::class.simpleName!!)
            .load(taxRateId, dfe)
    }

}