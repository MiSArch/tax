package org.misarch.tax.graphql.model.connection

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.federation.directives.ShareableDirective
import com.querydsl.core.types.Expression
import com.querydsl.core.types.Predicate
import com.querydsl.core.types.dsl.ComparableExpression
import com.querydsl.sql.SQLQuery
import org.misarch.tax.graphql.model.TaxRate
import org.misarch.tax.graphql.model.connection.base.BaseConnection
import org.misarch.tax.graphql.model.connection.base.BaseOrder
import org.misarch.tax.graphql.model.connection.base.BaseOrderField
import org.misarch.tax.graphql.model.connection.base.OrderDirection
import org.misarch.tax.persistence.model.TaxRateEntity
import org.misarch.tax.persistence.repository.TaxRateRepository

/**
 * A GraphQL connection for [TaxRate]s.
 *
 * @param first The maximum number of items to return
 * @param skip The number of items to skip
 * @param predicate The predicate to filter the items by
 * @param order The order to sort the items by
 * @param repository The repository to fetch the items from
 * @param applyJoin A function to apply a join to the query
 */
@GraphQLDescription("A connection to a list of `TaxRate` values.")
@ShareableDirective
class TaxRateConnection(
    first: Int?,
    skip: Int?,
    predicate: Predicate?,
    order: TaxRateOrder?,
    repository: TaxRateRepository,
    applyJoin: (query: SQLQuery<*>) -> SQLQuery<*> = { it }
) : BaseConnection<TaxRate, TaxRateEntity>(
    first,
    skip,
    predicate,
    (order ?: TaxRateOrder.DEFAULT).toOrderSpecifier(TaxRateOrderField.ID),
    repository,
    TaxRateEntity.ENTITY,
    applyJoin
) {

    override val primaryKey: ComparableExpression<*> get() = TaxRateEntity.ENTITY.id
}

@GraphQLDescription("TaxRate order fields")
enum class TaxRateOrderField(override vararg val expressions: Expression<out Comparable<*>>) : BaseOrderField {
    @GraphQLDescription("Order TaxRates by their id")
    ID(TaxRateEntity.ENTITY.id),

    @GraphQLDescription("Order TaxRates by their name")
    NAME(TaxRateEntity.ENTITY.name, TaxRateEntity.ENTITY.id),
}

@GraphQLDescription("TaxRate order")
class TaxRateOrder(
    direction: OrderDirection?, field: TaxRateOrderField?
) : BaseOrder<TaxRateOrderField>(direction, field) {

    companion object {
        val DEFAULT = TaxRateOrder(OrderDirection.ASC, TaxRateOrderField.ID)
    }
}