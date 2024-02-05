package org.misarch.tax.event.model

import java.util.*

/**
 * TaxRateVersion DTO for events
 *
 * @property id the id of the created TaxRateVersion
 * @property rate the rate of the created TaxRateVersion
 * @property version the version of the created TaxRateVersion
 * @property createdAt the timestamp of the created TaxRateVersion
 * @property taxRateId the id of the associated TaxRate
 */
data class TaxRateVersionDTO(
    val id: UUID,
    val rate: Double,
    val version: Int,
    val createdAt: String,
    val taxRateId: UUID
)