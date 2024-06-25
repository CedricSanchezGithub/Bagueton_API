package org.bagueton_api.form

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import java.util.*

// Définition de la classe ContactsFormEntity qui représente une entité de formulaire de contact.
data class ContactsFormEntity (
    var id: String? = null,
    var name: String? = null,
    var email: String? = null,
    var message: String? = null
)

// Utiliser des requêtes préparées avec des paramètres de remplacement (?)
// permet de créer un "patron" pour la requête SQL qui aide à prévenir les attaques par injection SQL.

@Service
class ContactsFormService(private val jdbcTemplate: JdbcTemplate) {

    // Méthode pour sauvegarder un formulaire de contact dans la base de données
    fun saveForm(form: ContactsFormEntity): Int {
        // Vérifie si l'ID est null et génère un UUID si nécessaire
        if (form.id == null) {
            form.id = UUID.randomUUID().toString()
        }
        // Requête SQL pour insérer le formulaire de contact dans la base de données
        val sql = "INSERT INTO contacts_form (id, name, email, message) VALUES (?, ?, ?, ?)"
        // Exécution de la requête SQL avec les paramètres du formulaire
        return jdbcTemplate.update(sql, form.id, form.name, form.email, form.message)
    }

    // Méthode pour lire tous les formulaires de contact de la base de données
    fun readAllForms(): List<ContactsFormEntity> {
        // Requête SQL pour sélectionner tous les enregistrements de la table contacts_form
        val sql = "SELECT * FROM contacts_form"
        // Exécution de la requête SQL et transformation des résultats en liste de ContactsFormEntity
        return jdbcTemplate.query(sql) { rs, _ ->
            ContactsFormEntity(
                id = rs.getString("id"),
                name = rs.getString("name"),
                email = rs.getString("email"),
                message = rs.getString("message")
            )
        }
    }
}


