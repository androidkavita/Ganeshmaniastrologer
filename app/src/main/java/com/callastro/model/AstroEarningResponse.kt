package com.callastro.model

import com.google.gson.annotations.SerializedName

data class AstroEarningResponse (

    @SerializedName("status"  ) var status  : Int?    = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("data"    ) var data    : AstroEarningData?   = AstroEarningData()

)


data class AstroEarningTransactionHistory (

    @SerializedName("id"          ) var id          : Int?    = null,
    @SerializedName("transaction" ) var transaction : String? = null,
    @SerializedName("user_name"   ) var userName    : String? = null,
    @SerializedName("amount"      ) var amount      : String? = null,
    @SerializedName("pay_date"    ) var payDate     : String? = null

)



data class AstroEarningData (

    @SerializedName("id"                  ) var id                 : Int?                          = null,
    @SerializedName("wallet"              ) var wallet             : String?                       = null,
    @SerializedName("remainig"              ) var remainig             : String?                       = null,
    @SerializedName("withdrawal"              ) var withdrawal             : String?                       = null,
    @SerializedName("transaction_history" ) var transactionHistory : ArrayList<AstroEarningTransactionHistory> = arrayListOf()

)