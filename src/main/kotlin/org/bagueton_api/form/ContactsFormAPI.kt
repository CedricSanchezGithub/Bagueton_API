package org.bagueton_api.form

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/bagueton")
class ContactsFormAPI(val contactsFormService: ContactsFormService) {

    @PostMapping("/form")
    fun createContactForm(@RequestBody form: ContactsFormEntity): ResponseEntity<Any> {
        return try {
            println("Appel de la fonction createContactForm")
            contactsFormService.saveForm(form)
            ResponseEntity.ok("Form successfully saved")
        } catch (e: DataIntegrityViolationException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Data integrity violation: ${e.message}")
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Failed to save contact form: ${e.message}")
        }
    }

    @GetMapping("/readform")
    fun readContactForm(): List<ContactsFormEntity> {
        println("Appel de la fonction readContactForm")
        return contactsFormService.readAllForms()
    }
}