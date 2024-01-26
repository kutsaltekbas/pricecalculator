import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.holy.pricecalculator.MainService
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MyViewModel : ViewModel() {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://evds2.tcmb.gov.tr/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: MainService.ApiService = retrofit.create(MainService.ApiService::class.java)

    var inputValue by mutableStateOf("")
    var resultText by mutableStateOf("0")
    var resultText1 by mutableStateOf("0")
    var resultText2 by mutableStateOf("0")

    var responseData by mutableStateOf<ExchangeRateResponse?>(null)

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun fetchExchangeRate() {
        try {
            val dateToday = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
            val dateTomorrow = LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))

            val response = apiService.getExchangeRate(
                series = "TP.DK.USD.A-TP.DK.EUR.A-TP.DK.GBP.A",
                startDate = dateToday,
                endDate = dateTomorrow,
                type = "json",
                key = "vlKCXO5kZo"
            )

            if (response.isSuccessful) {
                responseData = response.body()
            } else {
                println("error")
            }
        } catch (e: Exception) {
            println(e.message)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun performAsyncTask() {
        viewModelScope.launch {
            fetchExchangeRate()
        }
    }
}
