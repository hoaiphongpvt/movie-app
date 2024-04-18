package com.example.project_android.viewmodel

import androidx.lifecycle.ViewModel
import com.example.project_android.data.models.entity.Credit
import com.example.project_android.data.models.entity.Image
import com.example.project_android.data.models.entity.Person
import com.example.project_android.data.models.network.PersonCreditResponse
import com.example.project_android.data.models.network.PersonImageResponse
import com.example.project_android.data.services.ApiServices
import com.example.project_android.data.services.PersonApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CastDetailsViewModel: ViewModel() {
    private val apiService = ApiServices.getInstance().create(PersonApiInterface::class.java)
    fun getPersonData(personID: String, callback: (Person?) -> Unit) {
        val call: Call<Person> = apiService.getPersonDetails(personID)
        call.enqueue(object : Callback<Person> {
            override fun onResponse(call: Call<Person>, response: Response<Person>) {
                if (response.isSuccessful) {
                    val personResponse = response.body()
                    callback(personResponse)
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<Person>, t: Throwable) {
                callback(null)
            }
        })
    }

    fun getListImagesPerson(personID: String, callback: (List<Image>) -> Unit) {
        val call: Call<PersonImageResponse> = apiService.getListImagesPerson(personID)

        call.enqueue(object : Callback<PersonImageResponse> {

            override fun onResponse(
                call: Call<PersonImageResponse>,
                response: Response<PersonImageResponse>
            ) {
                if (response.isSuccessful) {
                    val personImageResponse = response.body()
                    val images = personImageResponse?.results ?: emptyList()
                    callback(images)
                }
            }

            override fun onFailure(call: Call<PersonImageResponse>, t: Throwable) {
                callback(emptyList())
            }
        })
    }

    fun getCombinedCredits(personID: String, callback: (List<Credit>) -> Unit) {
        val call: Call<PersonCreditResponse> = apiService.getCombinedCredits(personID)

        call.enqueue(object : Callback<PersonCreditResponse> {

            override fun onResponse(
                call: Call<PersonCreditResponse>,
                response: Response<PersonCreditResponse>
            ) {
                if (response.isSuccessful) {
                    val personCreditsResponse = response.body()
                    val personCredits = personCreditsResponse?.results ?: emptyList()
                    callback(personCredits)
                }
            }

            override fun onFailure(call: Call<PersonCreditResponse>, t: Throwable) {
                callback(emptyList())
            }
        })
    }
}