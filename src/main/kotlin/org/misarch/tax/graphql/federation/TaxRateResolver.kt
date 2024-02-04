package org.misarch.tax.graphql.federation

import com.expediagroup.graphql.generator.federation.execution.FederatedTypePromiseResolver
import graphql.schema.DataFetchingEnvironment
import org.misarch.tax.graphql.dataloader.TaxRateDataLoader
import org.misarch.tax.graphql.model.TaxRate
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.CompletableFuture

/**
 * Federated resolver for [TaxRate]s.
 */
@Component
class TaxRateResolver : FederatedTypePromiseResolver<TaxRate> {
    override val typeName: String
        get() = TaxRate::class.simpleName!!

    override fun resolve(
        environment: DataFetchingEnvironment, representation: Map<String, Any>
    ): CompletableFuture<TaxRate?> {
        val id = representation["id"] as String?
        return if (id == null) {
            CompletableFuture.completedFuture(null)
        } else {
            environment.getDataLoader<UUID, TaxRate>(TaxRateDataLoader::class.simpleName!!)
                .load(UUID.fromString(id))
        }
    }
}