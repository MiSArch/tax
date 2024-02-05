package org.misarch.tax.graphql.model.connection

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.federation.directives.ShareableDirective
import com.querydsl.core.types.Expression
import com.querydsl.core.types.Predicate
import com.querydsl.core.types.dsl.ComparableExpression
import com.querydsl.sql.SQLQuery
import org.misarch.tax.graphql.model.TaxRateVersion
import org.misarch.tax.graphql.model.connection.base.BaseConnection
import org.misarch.tax.graphql.model.connection.base.BaseOrder
import org.misarch.tax.graphql.model.connection.base.BaseOrderField
import org.misarch.tax.graphql.model.connection.base.OrderDirection
import org.misarch.tax.persistence.model.TaxRateVersionEntity
import org.misarch.tax.persistence.repository.TaxRateVersionRepository

/**
 * A GraphQL connection for [TaxRateVersion]s.
 *
 * @param first The maximum number of items to return
 * @param skip The number of items to skip
 * @param predicate The predicate to filter the items by
 * @param order The order to sort the items by
 * @param repository The repository to fetch the items from
 * @param applyJoin A function to apply a join to the query
 */
@GraphQLDescription("A connection to a list of `TaxRateVersion` values.")
@ShareableDirective
class TaxRateVersionConnection(
    first: Int?,
    skip: Int?,
    predicate: Predicate?,
    order: TaxRateVersionOrder?,
    repository: TaxRateVersionRepository,
    applyJoin: (query: SQLQuery<*>) -> SQLQuery<*> = { it }
) : BaseConnection<TaxRateVersion, TaxRateVersionEntity>(
    first,
    skip,
    predicate,
    (order ?: TaxRateVersionOrder.DEFAULT).toOrderSpecifier(TaxRateVersionOrderField.ID),
    repository,
    TaxRateVersionEntity.ENTITY,
    applyJoin
) {

    override val primaryKey: ComparableExpression<*> get() = TaxRateVersionEntity.ENTITY.id
}

@GraphQLDescription("TaxRateVersion order fields")
enum class TaxRateVersionOrderField(override vararg val expressions: Expression<out Comparable<*>>) : BaseOrderField {
    @GraphQLDescription("Order TaxRateVersions by their id")
    ID(TaxRateVersionEntity.ENTITY.id),

    @GraphQLDescription("Order TaxRateVersions by their version")
    VERSION(TaxRateVersionEntity.ENTITY.version, TaxRateVersionEntity.ENTITY.id),

    @GraphQLDescription("Order TaxRateVersions by their creation date")
    CREATED_AT(TaxRateVersionEntity.ENTITY.createdAt, TaxRateVersionEntity.ENTITY.id)
}

@GraphQLDescription("TaxRateVersion order")
class TaxRateVersionOrder(
    direction: OrderDirection?, field: TaxRateVersionOrderField?
) : BaseOrder<TaxRateVersionOrderField>(direction, field) {

    companion object {
        val DEFAULT = TaxRateVersionOrder(OrderDirection.ASC, TaxRateVersionOrderField.ID)
    }
}