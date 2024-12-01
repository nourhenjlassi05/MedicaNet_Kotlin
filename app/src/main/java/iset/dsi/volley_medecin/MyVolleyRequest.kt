package iset.dsi.volley_medecin



import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class MyVolleyRequest private constructor(context: Context) {

    private val requestQueue: RequestQueue = Volley.newRequestQueue(context)

    companion object {
        private var INSTANCE: MyVolleyRequest? = null

        @Synchronized
        fun getInstance(context: Context): MyVolleyRequest {
            if (INSTANCE == null) {
                INSTANCE = MyVolleyRequest(context)
            }
            return INSTANCE!!
        }
    }

    // GET request
    fun getRequest(url: String, onResponse: (String) -> Unit) {
        val stringRequest = StringRequest(Request.Method.GET, url, { response ->
            onResponse(response)
        }, { error ->
            error.printStackTrace()
        })
        requestQueue.add(stringRequest)
    }

    // POST request
    fun postRequest(url: String, body: Map<String, String>, onResponse: (String) -> Unit) {
        val jsonObject = JSONObject(body)
        val stringRequest = object : StringRequest(Method.POST, url,
            { response -> onResponse(response) },
            { error -> error.printStackTrace() }) {
            override fun getParams(): MutableMap<String, String> {
                val params = mutableMapOf<String, String>()
                body.forEach { (key, value) ->
                    params[key] = value
                }
                return params
            }
        }
        requestQueue.add(stringRequest)
    }

    // PUT request
    fun putRequest(url: String, id: String, body: Map<String, String>, onResponse: (String) -> Unit) {
        val jsonObject = JSONObject(body)
        val stringRequest = object : StringRequest(Method.PUT, "$url/$id",
            { response -> onResponse(response) },
            { error -> error.printStackTrace() }) {
            override fun getParams(): MutableMap<String, String> {
                val params = mutableMapOf<String, String>()
                body.forEach { (key, value) ->
                    params[key] = value
                }
                return params
            }
        }
        requestQueue.add(stringRequest)
    }

    // DELETE request
    fun deleteRequest(url: String, id: String, onResponse: (String) -> Unit) {
        val stringRequest = StringRequest(
            Request.Method.DELETE, "$url/$id",
            { response -> onResponse(response) },
            { error -> error.printStackTrace() })
        requestQueue.add(stringRequest)
    }
}
