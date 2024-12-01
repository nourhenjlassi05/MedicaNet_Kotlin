package iset.dsi.volley_medecin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class ModifyPatientActivity : AppCompatActivity() {
    private lateinit var edtNom: EditText
    private lateinit var edtPrenom: EditText
    private lateinit var edtDateNaissance: EditText
    private lateinit var edtAdresse: EditText
    private lateinit var btnSave: Button
    private var patientId: Int = -1  // Initialisation avec -1 pour vérifier l'erreur d'ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify_patient)

        edtNom = findViewById(R.id.edtNom)
        edtPrenom = findViewById(R.id.edtPrenom)
        edtDateNaissance = findViewById(R.id.edtDateNaissance)
        edtAdresse = findViewById(R.id.edtAdresse)
        btnSave = findViewById(R.id.btnSave)

        // Récupération de l'ID du patient passé dans l'Intent
        patientId = intent.getIntExtra("patient_id", -1)

        if (patientId == -1) {
            // Si l'ID est invalide, afficher un message et retourner à l'écran précédent
            Toast.makeText(this, "Erreur: ID du patient invalide", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            // Charger les détails du patient
            loadPatientDetails()
        }

        btnSave.setOnClickListener {
            val nom = edtNom.text.toString()
            val prenom = edtPrenom.text.toString()
            val dateNaissance = edtDateNaissance.text.toString()
            val adresse = edtAdresse.text.toString()

            if (nom.isNotEmpty() && prenom.isNotEmpty() && dateNaissance.isNotEmpty()) {
                modifyPatient(patientId, nom, prenom, dateNaissance, adresse)
            } else {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadPatientDetails() {
        val queue = Volley.newRequestQueue(this)
        val url = "http://10.0.2.2:3000/patients/$patientId"

        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                edtNom.setText(response.getString("nom"))
                edtPrenom.setText(response.getString("prenom"))
                edtDateNaissance.setText(response.getString("date_naissance"))
                edtAdresse.setText(response.getString("adresse"))
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "Erreur: ${error.message}", Toast.LENGTH_SHORT).show()
            })

        queue.add(request)
    }

    private fun modifyPatient(id: Int, nom: String, prenom: String, dateNaissance: String, adresse: String) {
        val queue = Volley.newRequestQueue(this)
        val url = "http://10.0.2.2:3000/patients/$id"

        val jsonObject = JSONObject().apply {
            put("nom", nom)
            put("prenom", prenom)
            put("date_naissance", dateNaissance)
            put("adresse", adresse)
        }

        val request = JsonObjectRequest(Request.Method.PUT, url, jsonObject,
            Response.Listener { response ->
                Toast.makeText(this, "Patient modifié", Toast.LENGTH_SHORT).show()

                // Ici, vous pouvez envoyer un signal pour rafraîchir la liste des patients
                val resultIntent = Intent()
                setResult(RESULT_OK, resultIntent)  // Envoyer un signal à la PatientListActivity
                finish()  // Retour à l'écran précédent après la mise à jour
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "Erreur: ${error.message}", Toast.LENGTH_SHORT).show()
            })

        queue.add(request)
    }

}
