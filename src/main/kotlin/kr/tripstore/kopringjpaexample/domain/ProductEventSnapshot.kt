package kr.tripstore.kopringjpaexample.domain

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import kr.tripstore.kopringjpaexample.domain.constant.ProductEventType

@Table(
    indexes = [
        Index(columnList = "createdAt"),
    ]
)
@Entity
class ProductEventSnapshot(
    @Column(nullable = false) var agencyName: String,
    @Column(nullable = false) var productEventType: ProductEventType,

    var productEventId: String? = null,
) : AuditingFields() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @JoinColumn(name = "productEventSnapshotId", nullable = false, updatable = false)
    @OneToMany(orphanRemoval = true, cascade = [CascadeType.ALL])
    var tags: MutableSet<ProductEventTag> = linkedSetOf()


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProductEventSnapshot

        return when (id) {
            null -> {
                // 영속화되지 않은 경우, 각 필드 비교
                if (agencyName != other.agencyName) return false
                if (productEventType != other.productEventType) return false
                if (productEventId != other.productEventId) return false
                return tags == other.tags
            }
            // 영속화된 경우, id 비교
            else -> id == other.id
        }
    }

    override fun hashCode(): Int = when (id) {
        null -> {
            var result = agencyName.hashCode()
            result = 31 * result + productEventType.hashCode()
            result = 31 * result + (productEventId?.hashCode() ?: 0)
            result = 31 * result + tags.hashCode()
            result
        }
        else -> id.hashCode()
    }

    override fun toString(): String {
        return "ProductEventSnapshot(id=$id, agencyName=$agencyName, productEventType=$productEventType, productEventId=$productEventId, tags=$tags)"
    }
}
