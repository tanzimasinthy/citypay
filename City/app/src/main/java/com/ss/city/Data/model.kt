package com.ss.city.Data

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Reader


data class register_response(
    val name:String? = null
)
data class response_register(
    val name:String? = null
)
{

    class Deserializer : ResponseDeserializable<register_response> {
        override fun deserialize(content: String): register_response? = Gson().fromJson(content, register_response::class.java)!!
    }

    class ListDeserializer : ResponseDeserializable<List<response_register>> {
        override fun deserialize(reader: Reader): List<response_register> {
            val type = object : TypeToken<List<response_register>>() {}.type
            return Gson().fromJson(reader, type)
        }
    }
}