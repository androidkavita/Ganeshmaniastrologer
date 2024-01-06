package com.callastro.model

import com.google.gson.annotations.SerializedName

data class GetBankDetail(
    @SerializedName("status"  ) var status  : Int?    = null,
    @SerializedName("message" ) var message : String? = null,
    @SerializedName("data"    ) var data    : GetBankDetailData?   = GetBankDetailData()
)
data class GetBankDetailData(
    @SerializedName("id"          ) var id         : Int?    = null,
    @SerializedName("astro_id"    ) var astroId    : Int?    = null,
    @SerializedName("bank_name"   ) var bankName   : String? = null,
    @SerializedName("account_no"  ) var accountNo  : String? = null,
    @SerializedName("holder_name" ) var holderName : String? = null,
    @SerializedName("ifsc_code"   ) var ifscCode   : String? = null,
    @SerializedName("branch"      ) var branch     : String? = null,
    @SerializedName("created_at"  ) var createdAt  : String? = null,
    @SerializedName("updated_at"  ) var updatedAt  : String? = null
)