package org.misarch.tax.persistence.model

import org.misarch.tax.event.model.TaxRateVersionDTO
import org.misarch.tax.graphql.model.TaxRateVersion
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * TaxRate entity
 *
 * @property rate the rate of the TaxRateVersion
 * @property version the version of the TaxRateVersion
 * @property createdAt the timestamp the TaxRateVersion was created
 * @property taxRateId the id of the associated TaxRate
 * @property id the id of the TaxRateVersion
 */
@Table
class TaxRateVersionEntity(
    val rate: Double,
    val version: Int,
    val createdAt: OffsetDateTime,
    val taxRateId: UUID,
    @Id
    override val id: UUID? = null
) : BaseEntity<TaxRateVersion> {

    companion object {
        /**
         * Querydsl entity
         */
        val ENTITY = QTaxRateVersionEntity.taxRateVersionEntity!!
    }

    override fun toDTO(): TaxRateVersion {
        return TaxRateVersion(
            id = id!!,
            rate = rate,
            createdAt = createdAt,
            version = version,
            taxRateId = taxRateId
        )
    }

    fun toEventDTO(): TaxRateVersionDTO {
        return TaxRateVersionDTO(
            id = id!!,
            rate = rate,
            createdAt = createdAt.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME),
            version = version,
            taxRateId = taxRateId
        )
    }

}