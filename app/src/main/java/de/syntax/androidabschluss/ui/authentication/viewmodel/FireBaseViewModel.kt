package de.syntax.androidabschluss.ui.authentication.viewmodel

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import de.syntax.androidabschluss.R
import de.syntax.androidabschluss.data.model.Notes
import java.util.UUID

class FireBaseViewModel (application: Application) :AndroidViewModel(application) {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()



    // currentuser ist null wenn niemand eingeloggt ist
    private val _currentUser = MutableLiveData<FirebaseUser?>(firebaseAuth.currentUser)
    val currentUser: LiveData<FirebaseUser?>
        get() = _currentUser

    private val _notesCollection = MutableLiveData<QuerySnapshot>()
    val notesCollection: LiveData<QuerySnapshot>
        get() = _notesCollection




    /* Wenn ein Nutzer eingeloggt ist werden die Notes direkt der UserID zugeordnet
    und können nur von ihm gelöscht und bearbeitet bzw eingesehen werden. Ist kein User eingeloggt,
    so werden die Notes im Standart Verzeichnis gespeichert ist aufgrund der Login und SignUp Thematik
    jedoch nicht möglich
     */
    private fun getNotesCollectionPath(): String {
        val currentUser = _currentUser.value
        return if (currentUser != null) {
            "users/${currentUser.uid}/notes" // Pfad zum Dokument des Users
        } else {
            "notes" // Standart Verzeichnis
        }
    }


    private val notesCollectionDb = db.collection(getNotesCollectionPath())

    init {
        notesCollectionDb.addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.e(TAG, "Listen failed: $error")
                return@addSnapshotListener
            }
            if (snapshot != null) {
                _notesCollection.value = snapshot
            } else {
                Log.e(TAG, "Current data is null!")
            }
        }
    }
    // Hiermit werden Notizen hinzugefügt
    fun addNote(notes: Notes): String {
        val id = UUID.randomUUID().toString() // Bekommt eine RandomID die nicht vom Firestore festgelegt wird
        notes.id = id // Set the ID on the Notes object
        val documentReference = notesCollectionDb.document(id) // Erstellt ein Dokumentenverweis
        documentReference.set(notes) // Speichert das Dokument (Note)
            .addOnSuccessListener { Log.d(TAG, "Note added with ID: $id") }
            .addOnFailureListener { error -> Log.w(TAG, "Error adding note", error) }
        return id
    }

    // Ruft die Notizen aus der Datenbank (Firestore) ab
    fun getNotesById(id: String): LiveData<Notes?> {
        val notesLiveData = MutableLiveData<Notes?>()
        val documentReference = notesCollectionDb.document(id) // Erstellt ein Dokumentenverweis
        documentReference.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val notes = document.toObject(Notes::class.java)
                    notesLiveData.value = notes
                } else {
                    Log.d(TAG, "No note found with ID: $id")
                }
            }
            .addOnFailureListener { error -> Log.w(TAG, "Error getting note", error) }
        return notesLiveData
    }
    // Ermöglicht das bearbeiten bzw. abspeichern von bearbeiteten Notizen
    fun updateNote(note: Notes) {
        val documentReference = notesCollectionDb.document(note.id)
        documentReference.update(mapOf(
            "title" to note.title,
            "noteText" to note.noteText,
            "dateTime" to note.dateTime,
            "subTitle" to note.subTitle
        ))
            .addOnSuccessListener { Log.d(TAG, "Note updated with ID: ${note.id}") }
            .addOnFailureListener { error -> Log.w(TAG, "Error updating note", error) }
    }
    // Ermöglicht das Löschen der Note (über den Mülleimer im EditNoteFragment möglich)
    fun deleteNote(noteId: String) {
        val documentReference = notesCollectionDb.document(noteId)
        documentReference.delete()
            .addOnSuccessListener {
                Log.d("DeleteNote", "Note deleted successfully!")
            }
            .addOnFailureListener { error ->
                Log.w("DeleteNote", "Error deleting note", error)
            }
    }

    // Registrierungs Logik. Falls ein Account mit der Email schon existiert, Toast Hinweis.
    fun signup(email: String, password: String, username: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { user ->
            if (user.isSuccessful) {

                saveUsername(username)
                login(email, password)
            } else {
                val exception = user.exception
                val message = when (exception) {
                    is FirebaseAuthUserCollisionException -> R.string.accound_alreadyExist
                    else -> R.string.signup_failed
                }
                Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show()
                Log.e(TAG, "Signup failed: ${user.exception}")
            }
        }
    }
    // Funktion zum Speichern des Benutzernamens
    private fun saveUsername(username: String) {
        // Hier speicherst du den Benutzernamen in Firebase Firestore oder im Authentifizierungsdokument
        // Ich werde hier nur ein Beispiel zeigen, wie du den Benutzernamen im Authentifizierungsdokument speichern könntest
        val user = firebaseAuth.currentUser
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(username)
            .build()

        user?.updateProfile(profileUpdates)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Username saved successfully")
                } else {
                    Log.e(TAG, "Failed to save username", task.exception)
                }
            }
    }
    /* Einloggen Logik. Falls Email oder Passwort nicht eingetragen wurde, Toast Hinweis.
    Falls Passwort oder Email nicht richtig, Toast Hinweis.
     */
    fun login(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                _currentUser.value = firebaseAuth.currentUser
            } else {
                val exception = it.exception
                val message = when (exception) {
                    is FirebaseAuthInvalidCredentialsException -> R.string.credentials_incorrect
                    else -> R.string.login_failed
                }
                Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show()
                Log.e(TAG, "Login failed: ${it.exception}")
            }
        }
    } // Passwort zurücksetzen. Eine Email wird an die angegebene Email Adresse versand.
    fun resetPassword(email: String) {
        firebaseAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(getApplication(), R.string.reset_successfull, Toast.LENGTH_SHORT).show()
                } else {
                    val exception = it.exception
                    val message = when (exception) {
                        is FirebaseAuthInvalidUserException -> R.string.invalid_User
                        else -> R.string.reset_failed
                    }
                    Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show()
                }
            }
    }
    // Passwort kann im Settingsfragment geändert werden. wenn beide Eingaben übereinstimmen
    fun changePassword(currentUser: FirebaseUser, newPassword: String, confirmPassword: String) {
        if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(getApplication(), R.string.password_input_empty, Toast.LENGTH_SHORT).show()
            return
        }

        if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(getApplication(), R.string.password_not_match, Toast.LENGTH_SHORT).show()
            return
        }
        // Nutzer Token wird erneuert, da aus Sicherheitsgründen erforderlich
        currentUser.reload()
        currentUser.updatePassword(newPassword)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    // Passwort wude erfolgreich zurückgesetzt
                    Toast.makeText(getApplication(), R.string.password_changed_success, Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val exception = it.exception
                    val message = when (exception) {
                        is FirebaseAuthWeakPasswordException -> R.string.weak_password
                        else -> R.string.password_change_failed
                    }
                    Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show()
                    Log.e("FireBaseViewModel", "Password change failed", exception)
                }
            }
    }
    // Ausloggen Logik
    fun logout() {
        firebaseAuth.signOut()
        _currentUser.value = firebaseAuth.currentUser
    }
}