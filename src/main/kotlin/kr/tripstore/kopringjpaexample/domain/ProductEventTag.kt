package kr.tripstore.kopringjpaexample.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table

@Table(
    indexes = [
        Index(columnList = "tagName"),
        Index(columnList = "createdAt"),
    ]
)
@Entity
class ProductEventTag(
    @Column(nullable = false) var tagId: Long,
    @Column(nullable = false) var tagName: String,
) : AuditingFields() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ProductEventTag) return false

        return when (id) {
            // 영속화되지 않은 경우, 각 필드 비교
            null -> {
                if (tagId != other.tagId) return false
                return tagName == other.tagName
            }
            // 영속화된 경우, id 비교
            else -> id == other.id
        }
    }

    override fun hashCode(): Int = when (id) {
        // 영속화되지 않은 경우, 각 필드 사용
        null -> {
            var result = tagId.hashCode()
            result = 31 * result + tagName.hashCode()
            result
        }
        // 영속화된 경우, id 사용
        else -> id.hashCode()
    }

    override fun toString(): String {
        return "ProductEventTag(id=$id, tagId=$tagId, tagName='$tagName')"
    }
}
