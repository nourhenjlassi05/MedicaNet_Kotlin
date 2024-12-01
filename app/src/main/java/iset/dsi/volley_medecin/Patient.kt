package iset.dsi.volley_medecin

data class Patient(
    val id: Int,
    val nom: String,
    val prenom: String,
    val dateNaissance: String,
    val adresse: String?
)
