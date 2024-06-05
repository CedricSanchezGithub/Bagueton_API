package org.bagueton_api.model

import jakarta.persistence.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service

@Entity
@Table(name = "contacts_form")
data class ContactsFormEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: String? = null,
    var name: String? = null,
    var email: String? = null,
    var message: String? = null,

)

@Repository
interface ContactsFormRepository : JpaRepository<ContactsFormEntity, String>

@Service
class ContactsFormService(private val contactsFormRepository: ContactsFormRepository) {

    fun saveForm(form: ContactsFormEntity): ContactsFormEntity {
        return contactsFormRepository.save(form)
    }
    fun readAllForms(): List<ContactsFormEntity> {
        return contactsFormRepository.findAll()
    }
}