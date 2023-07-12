package kr.tripstore.kopringjpaexample.domain

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Table(
    indexes = [
        Index(columnList = "reservationCode", unique = true),
        Index(columnList = "createdAt"),
    ]
)
@Entity
class Reservation(
    @Column(nullable = false) var reservationCode: String,
) : AuditingFields() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @OneToOne(optional = false, cascade = [CascadeType.ALL])
    lateinit var productEventSnapshot: ProductEventSnapshot


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Reservation

        return when (id) {
            null -> {
                // 영속화되지 않은 경우, 각 필드 비교
                if (reservationCode != other.reservationCode) return false
                return productEventSnapshot == other.productEventSnapshot
            }
            // 영속화된 경우, id 비교
            else -> id == other.id
        }
    }

    override fun hashCode(): Int = when (id) {
        // 영속화되지 않은 경우, 각 필드 사용
        null -> {
            var result = reservationCode.hashCode()
            result = 31 * result + productEventSnapshot.hashCode()
            result
        }
        // 영속화된 경우, id 사용
        else -> id.hashCode()
    }

    override fun toString(): String {
        return "Reservation(id=$id, reservationCode=$reservationCode, ProductEventSnapshot=$productEventSnapshot)"
    }
}
