package org.misarch.tax.service

import kotlinx.coroutines.reactor.awaitSingle
import org.misarch.tax.event.TaxEvents
import org.misarch.tax.graphql.input.CreateTaxRateInput
import org.misarch.tax.graphql.input.UpdateTaxRateInput
import org.misarch.tax.persistence.model.TaxRateEntity
import org.misarch.tax.persistence.model.TaxRateVersionEntity
import org.misarch.tax.persistence.repository.TaxRateRepository
import org.springframework.stereotype.Service

/**
 * Service for [TaxRateEntity]s
 *
 * @param repository the repository for [TaxRateEntity]s
 * @property taxRateVersionService service for [TaxRateVersionEntity]s
 * @property eventPublisher publisher for events
 */
@Service
class TaxRateService(
    repository: TaxRateRepository,
    private val taxRateVersionService: TaxRateVersionService,
    private val eventPublisher: org.misarch.tax.event.EventPublisher
) : BaseService<TaxRateEntity, TaxRateRepository>(repository) {

    /**
     * Creates a TaxRate, also creates the initial version
     *
     * @param input defines the TaxRate (and initial version) to be created
     * @return the created TaxRate
     */
    suspend fun createTaxRate(input: CreateTaxRateInput): TaxRateEntity {
        val taxRate = TaxRateEntity(
            name = input.name,
            description = input.description,
            currentVersionId = null,
            id = null
        )
        val savedTaxRate = repository.save(taxRate).awaitSingle()
        val taxRateVersion = taxRateVersionService.createTaxRateVersionInternal(input.initialVersion, savedTaxRate.id!!)
        savedTaxRate.currentVersionId = taxRateVersion.id
        val newSavedTaxRate =  repository.save(savedTaxRate).awaitSingle()
        eventPublisher.publishEvent(TaxEvents.TAX_RATE_CREATED, newSavedTaxRate.toEventDTO())
        eventPublisher.publishEvent(TaxEvents.TAX_RATE_VERSION_CREATED, taxRateVersion.toEventDTO())
        return newSavedTaxRate
    }

    /**
     * Updates a TaxRate
     *
     * @param input defines which TaxRate to update and how
     * @return the updated TaxRate
     */
    suspend fun updateTaxRate(input: UpdateTaxRateInput): TaxRateEntity {
        val taxRate = repository.findById(input.id).awaitSingle()
        if (input.name != null) {
            taxRate.name = input.name
        }
        if (input.description != null) {
            taxRate.description = input.description
        }
        val savedTaxRate = repository.save(taxRate).awaitSingle()
        return savedTaxRate
    }

}