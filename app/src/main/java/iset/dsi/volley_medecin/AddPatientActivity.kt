package iset.dsi.volley_medecin

import android.app.DatePickerDialog
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
import java.util.Calendar

class AddPatientActivity : AppCompatActivity() {
    private lateinit var edtNom: EditText
    private lateinit var edtPrenom: EditText
    private lateinit var edtDateNaissance: EditText
    private lateinit var edtAdresse: EditText
    private lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_patient)

        edtNom = findViewById(R.id.edtNom)
        edtPrenom = findViewById(R.id.edtPrenom)
        edtDateNaissance = findViewById(R.id.edtDateNaissance)
        edtAdresse = findViewById(R.id.edtAdresse)
        btnSave = findViewById(R.id.btnSave)

        edtDateNaissance.setOnClickListener {
            showDatePickerDialog()
        }
        btnSave.setOnClickListener {
            val nom = edtNom.text.toString()
            val prenom = edtPrenom.text.toString()
            val dateNaissance = edtDateNaissance.text.toString()
            val adresse = edtAdresse.text.toString()

            if (nom.isNotEmpty() && prenom.isNotEmpty() && dateNaissance.isNotEmpty()) {
                addPatient(nom, prenom, dateNaissance, adresse)
            }
        }
    }


    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                // Ajouter la date sélectionnée au champ
                val date = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear)
                edtDateNaissance.setText(date)
            },
            year, month, day
        )

        datePickerDialog.show()
    }
    private fun addPatient(nom: String, prenom: String, dateNaissance: String, adresse: String) {
        val queue = Volley.newRequestQueue(this)
        val url = "http://10.0.2.2:3000/patients"

        val jsonObject = JSONObject().apply {
            put("nom", nom)
            put("prenom", prenom)
            put("date_naissance", dateNaissance)
            put("adresse", adresse)
        }

        val request = JsonObjectRequest(
            Request.Method.POST, url, jsonObject,
            Response.Listener { response ->
                Toast.makeText(this, "Patient ajouté", Toast.LENGTH_SHORT).show()
                finish() // Retour à l'activité précédente
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "Erreur: ${error.message}", Toast.LENGTH_SHORT).show()
            })

        queue.add(request)
    }
}
