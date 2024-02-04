package org.misarch.tax.event.model

import java.util.*

/**
 * TaxRate DTO used for events
 *
 * @property id id of the TaxRate
 * @property name the name of the TaxRate
 * @property description the description of the TaxRate
 * @property currentVersionId the current version of the TaxRate
 */
data class TaxRateDTO(
    val id: UUID,
    val name: String,
    val description: String,
    val currentVersionId: UUID
)