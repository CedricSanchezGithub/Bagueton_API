package org.bagueton_api.model
//
//import jakarta.persistence.*
//import org.springframework.data.jpa.repository.JpaRepository
//import org.springframework.stereotype.Repository
//import org.springframework.stereotype.Service
//import java.time.LocalDate
//
//@Entity
//@Table(name = "bagueton_order")
//data class OrderBean(
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    var id: Long? = null,
//    val recipeId: Long? = null, // Juste l'ID de la recette, simplification
//    val date: LocalDate? = null
//)
//
//
//
//
//@Repository
//interface OrderRepository : JpaRepository<OrderBean, Long>
//
//
//@Service
//class OrderService(val orderRep:OrderRepository) {
//
//
//}