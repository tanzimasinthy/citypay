package com.citybank.citybuy.Controller

import com.citybank.citybuy.Data.register
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

//@RestController
//@RequestMapping("/city")
//class ControllerCity {
//
//    @PostMapping("/register")
//    fun registration(@RequestBody reg:register):String
//    {
//        return reg.email.toString()
//    }
//
//
//    fun token_varify(token:String):Boolean
//    {
//        val k = "" // userRepo.getByToken(token)
//
//        try {
//            return  true//!k!!.phone.toString().isEmpty()
//        }catch (e:Exception)
//        {
//            return false
//        }
//    }
//
//    fun token(number:String):String
//    {
//        var tk = LocalDateTime.now().toString()
//        tk = tk.replace(",","")
//        tk = tk.replace(".","")
//        tk = tk.replace(":","")
//        tk = tk.replace("-","")
//        tk = tk+number+tk
//        return tk
//    }
//
//
//}