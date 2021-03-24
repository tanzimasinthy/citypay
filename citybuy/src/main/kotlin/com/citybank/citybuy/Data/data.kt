package com.citybank.citybuy.Data

data class register(
        val name:String? = null,
        val phone:String? = null,
        val password:String? = null,
        val address:String? = null,
        val email:String? = null,
        val user_pic:String? = null,
        val user_bank:String? = null
)

data class reg(
        var name:String? = null,
        var phone:String? = null,
        var password:String? = null,
        var bank:String? = null,
        var email:String? = null
)

data class login(
        val phone: String? = null,
        val password : String? = null
)

data class uni(
        var name:String
)