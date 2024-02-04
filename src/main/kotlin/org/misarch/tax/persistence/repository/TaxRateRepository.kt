package org.misarch.tax.persistence.repository

import com.infobip.spring.data.r2dbc.QuerydslR2dbcRepository
import org.misarch.tax.persistence.model.TaxRateEntity
import org.springframework.stereotype.Repository
import java.util.*

/**
 * Repository for [TaxRateEntity]s
 */
@Repository
interface TaxRateRepository : QuerydslR2dbcRepository<TaxRateEntity, UUID>