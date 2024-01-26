package com.holy.pricecalculator

import ExchangeRateResponse
import MyViewModel
import android.graphics.drawable.Icon
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.holy.pricecalculator.ui.theme.PriceCalculatorTheme
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {

    private val viewModel: MyViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            PriceCalculatorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    MyScreen(viewModel = viewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyScreen(viewModel: MyViewModel) {

    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(Unit) {
        viewModel.performAsyncTask()

    }

    Column(
        modifier = Modifier
            .fillMaxSize().background(Color(0xffF4F8FB))
        ,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        viewModel.responseData?.let { data ->
            val doubleValue: Double? = (viewModel.responseData!!.items[0].usdRate)?.toDoubleOrNull()
            val doubleValue1: Double? = (viewModel.responseData!!.items[0].euroRate)?.toDoubleOrNull()
            val doubleValue2: Double? = (viewModel.responseData!!.items[0].sterlinRate)?.toDoubleOrNull()


            Text(text = "-DÖVİZ HESAPLAYICI-",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xff102E53),
                    ),
                )
            Box(modifier = Modifier.height(80.dp))
            Text("Güncel dolar kuru",
                style = TextStyle(
                    fontSize = 18.sp,
                    color = Color.Gray),
                )
            Text(text = "${data.items[0].usdRate} TL",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xff102E53),
                    ),
                )
            Box(modifier = Modifier.height(30.dp))
            Row(

                Modifier
                    .fillMaxWidth()
                    ,
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.width(150.dp),
                    ) {
                    Text(
                        text = "- TRY -",
                        style = TextStyle(fontSize = 18.sp),
                        )
                    Text(
                        text = "${viewModel.inputValue}TL",
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xff102E53),
                            ),
                        )

                }
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = null
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.width(150.dp).height(400.dp),
                    ) {
                    Text(
                        text = "- USD -",
                        style = TextStyle(fontSize = 18.sp),
                        )
                    Text(
                        text = "${viewModel.resultText}\$",
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xff102E53),
                            ),
                        )
                    Text(
                        text = "- EURO -",
                        style = TextStyle(fontSize = 18.sp),
                    )
                    Text(
                        text = "${viewModel.resultText1}\$",
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xff102E53),
                        ),
                    )
                    Text(
                        text = "- STERLIN -",
                        style = TextStyle(fontSize = 18.sp),
                    )
                    Text(
                        text = "${viewModel.resultText2}\$",
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xff102E53),
                        ),
                    )

                }

            }
            Box(
                modifier = Modifier.height(30.dp),
                )
            BasicTextField(
                value = viewModel.inputValue,
                onValueChange = {
                    if (it.length <= 6) {
                        viewModel.inputValue = it
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                textStyle = LocalTextStyle.current.copy(fontSize = 20.sp, textAlign = TextAlign.Center,),

                modifier = Modifier
                    .width(90.dp)
                    .height(50.dp)
                    .padding(vertical = 8.dp)
                    .background(Color.White)
                    .border(1.dp, Color(0xff59C08D), shape = RectangleShape)
                    .align(Alignment.CenterHorizontally)



            )
            Box(modifier = Modifier.height(20.dp))
            Button(

                colors = ButtonDefaults.buttonColors(
                     containerColor = Color(0xff59C08D)
                ),
                onClick = {

                    var result = (viewModel.inputValue.toDoubleOrNull()?.div(doubleValue!!))
                    var result1 = (viewModel.inputValue.toDoubleOrNull()?.div(doubleValue1!!))
                    var result2 = (viewModel.inputValue.toDoubleOrNull()?.div(doubleValue2!!))

                    viewModel.resultText = String.format("%.2f",result)
                    viewModel.resultText1 = String.format("%.2f",result1)
                    viewModel.resultText2 = String.format("%.2f",result2)

                    keyboardController?.hide()
                },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Icon(imageVector = Icons.Default.Done, contentDescription = null)
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Hesapla", style = TextStyle(fontWeight = FontWeight.Bold))
            }
        }?: run {
            Box(modifier = Modifier.height(300.dp))
            Text(text = "Yükleniyor...")
        }


    }

}


