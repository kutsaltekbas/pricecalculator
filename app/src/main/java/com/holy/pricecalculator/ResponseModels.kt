import com.google.gson.annotations.SerializedName

data class ExchangeRateResponse(
    @SerializedName("totalCount")
    val totalCount: Int,
    @SerializedName("items")
    val items: List<ExchangeRateItem>
)

data class ExchangeRateItem(
    @SerializedName("Tarih")
    val tarih: String,
    @SerializedName("TP_DK_USD_A")
    val usdRate: String?,
    @SerializedName("TP_DK_EUR_A")
    val euroRate: String?,
    @SerializedName("TP_DK_GBP_A")
    val sterlinRate: String?,



)


