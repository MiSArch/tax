package org.misarch.tax.graphql.dataloader

import org.misarch.tax.graphql.model.TaxRateVersion
import org.misarch.tax.persistence.model.TaxRateVersionEntity
import org.misarch.tax.persistence.repository.TaxRateVersionRepository
import org.springframework.stereotype.Component

/**
 * Data loader for [TaxRateVersion]s
 *
 * @param repository repository for [TaxRateVersion]s
 */
@Component
class TaxRateVersionDataLoader(
    repository: TaxRateVersionRepository
) : IdDataLoader<TaxRateVersion, TaxRateVersionEntity>(repository)