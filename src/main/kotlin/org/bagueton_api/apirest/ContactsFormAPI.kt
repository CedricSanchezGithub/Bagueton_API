package org.bagueton_api.apirest

import org.bagueton_api.model.ContactsFormEntity
import org.bagueton_api.model.ContactsFormService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/bagueton")

class ContactsFormAPI (val contactsFormService: ContactsFormService) {

    @PostMapping("/form")
    fun createContactForm(@RequestBody form: ContactsFormEntity): ResponseEntity<ContactsFormEntity> {
        val savedForm = contactsFormService.saveForm(form)
        return ResponseEntity.ok(savedForm)
    }

}

