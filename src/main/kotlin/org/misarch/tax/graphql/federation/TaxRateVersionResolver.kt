package org.misarch.tax.graphql.federation

import com.expediagroup.graphql.generator.federation.execution.FederatedTypePromiseResolver
import graphql.schema.DataFetchingEnvironment
import org.misarch.tax.graphql.dataloader.TaxRateVersionDataLoader
import org.misarch.tax.graphql.model.TaxRateVersion
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.CompletableFuture

/**
 * Federated resolver for [TaxRateVersion]s.
 */
@Component
class TaxRateVersionResolver : FederatedTypePromiseResolver<TaxRateVersion> {
    override val typeName: String
        get() = TaxRateVersion::class.simpleName!!

    override fun resolve(
        environment: DataFetchingEnvironment, representation: Map<String, Any>
    ): CompletableFuture<TaxRateVersion?> {
        val id = representation["id"] as String?
        return if (id == null) {
            CompletableFuture.completedFuture(null)
        } else {
            environment.getDataLoader<UUID, TaxRateVersion>(TaxRateVersionDataLoader::class.simpleName!!)
                .load(UUID.fromString(id))
        }
    }
}