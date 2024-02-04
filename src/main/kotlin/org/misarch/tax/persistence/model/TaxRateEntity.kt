package org.misarch.tax.persistence.model

import org.misarch.tax.event.model.TaxRateDTO
import org.misarch.tax.graphql.model.TaxRate
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

/**
 * TaxRate entity
 *
 * @property name the name of the TaxRate
 * @property description the description of the TaxRate
 * @property currentVersionId the id of the current version of the TaxRate
 * @property id the id of the TaxRate
 */
@Table
class TaxRateEntity(
    var name: String,
    var description: String,
    var currentVersionId: UUID?,
    @Id
    override val id: UUID? = null
) : BaseEntity<TaxRate> {

    companion object {
        /**
         * Querydsl entity
         */
        val ENTITY = QTaxRateEntity.taxRateEntity!!
    }

    override fun toDTO(): TaxRate {
        return TaxRate(
            id = id!!,
            name = name,
            description = description,
            currentVersionId = currentVersionId!!
        )
    }

    fun toEventDTO(): TaxRateDTO {
        return TaxRateDTO(
            id = id!!,
            name = name,
            description = description,
            currentVersionId = currentVersionId!!
        )
    }

}