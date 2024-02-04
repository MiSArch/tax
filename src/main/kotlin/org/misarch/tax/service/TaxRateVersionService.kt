package org.misarch.tax.service

import kotlinx.coroutines.reactor.awaitSingle
import org.misarch.tax.event.EventPublisher
import org.misarch.tax.event.TaxEvents
import org.misarch.tax.graphql.input.CreateTaxRateVersionInput
import org.misarch.tax.graphql.input.TaxRateVersionInput
import org.misarch.tax.persistence.model.TaxRateEntity
import org.misarch.tax.persistence.model.TaxRateVersionEntity
import org.misarch.tax.persistence.repository.TaxRateRepository
import org.misarch.tax.persistence.repository.TaxRateVersionRepository
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.util.*

/**
 * Service for [TaxRateVersionEntity]s
 *
 * @param repository the repository for [TaxRateVersionEntity]s
 * @property taxRateRepository the repository for [TaxRateEntity]s
 * @property eventPublisher publisher for events
 */
@Service
class TaxRateVersionService(
    repository: TaxRateVersionRepository,
    private val taxRateRepository: TaxRateRepository,
    private val eventPublisher: EventPublisher
) : BaseService<TaxRateVersionEntity, TaxRateVersionRepository>(repository) {

    /**
     * Creates a TaxRateVersion
     * Also checks if the TaxRate exists
     *
     * @param input defines the TaxRateVersion to be created
     */
    suspend fun createTaxRateVersion(
        input: CreateTaxRateVersionInput
    ): TaxRateVersionEntity {
        if (!taxRateRepository.existsById(input.taxRateId).awaitSingle()) {
            throw IllegalArgumentException("TaxRate with id ${input.taxRateId} does not exist.")
        }
        val taxRateVersion = createTaxRateVersionInternal(input, input.taxRateId)
        eventPublisher.publishEvent(TaxEvents.TAX_RATE_VERSION_CREATED, taxRateVersion.toEventDTO())
        return taxRateVersion
    }

    /**
     * Creates a TaxRateVersion without checking if the TaxRate exists
     *
     * @param input defines the TaxRateVersion to be created
     * @param taxRateId the id of the TaxRate
     * @return the created TaxRateVersion
     */
    suspend fun createTaxRateVersionInternal(
        input: TaxRateVersionInput, taxRateId: UUID
    ): TaxRateVersionEntity {
        val version = repository.findMaxVersionByProductVariantId(taxRateId)?.plus(1) ?: 1
        val taxRateVersion = TaxRateVersionEntity(
            rate = input.rate,
            version = version,
            createdAt = OffsetDateTime.now(),
            taxRateId = taxRateId,
            id = null
        )
        return repository.save(taxRateVersion).awaitSingle()
    }

}