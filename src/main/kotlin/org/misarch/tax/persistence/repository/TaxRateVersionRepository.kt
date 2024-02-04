package org.misarch.tax.persistence.repository

import com.infobip.spring.data.r2dbc.QuerydslR2dbcRepository
import org.misarch.tax.persistence.model.TaxRateVersionEntity
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

/**
 * Repository for [TaxRateVersionEntity]s
 */
@Repository
interface TaxRateVersionRepository : QuerydslR2dbcRepository<TaxRateVersionEntity, UUID> {

    /**
     * Finds the maximum version of a TaxRate
     *
     * @param taxRateId the id of the TaxRate
     * @return the maximum version of the TaxRate, null if there are no versions
     */
    @Query("SELECT MAX(taxRateVersionEntity.version) FROM TaxRateVersionEntity taxRateVersionEntity WHERE taxRateVersionEntity.taxRateId = :taxRateId")
    suspend fun findMaxVersionByProductVariantId(
        @Param("taxRateId")
        taxRateId: UUID
    ): Int?

}