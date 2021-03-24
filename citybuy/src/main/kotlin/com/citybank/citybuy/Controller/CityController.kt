package com.citybank.citybuy.Controller

import com.citybank.citybuy.Data.*
import com.citybank.citybuy.Interfaces.ProductPicture_Repository
import com.citybank.citybuy.Interfaces.Product_Repository
import com.citybank.citybuy.Interfaces.User_Repository
import com.citybank.citybuy.Services.FileStorage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime
import java.util.*

@RestController
@RequestMapping("/city")
class ControllerCity(val userRepo:User_Repository, val productRepo:Product_Repository, val picRepo:ProductPicture_Repository) {

    @Autowired
    lateinit var fileStorage: FileStorage

    @PostMapping("/reg")
    fun registration(@RequestBody reg: register):String
    {
        var num = userRepo.getByPhone(reg.phone)

        return try
        {
            if (num!!.name!!.isEmpty())
            {
                return "1"
            }
            else
            {
                return "User already exists"
            }
        }
        catch (e: Exception)
        {
            var tk = token(reg.phone!!)
            var k = City_User(0,reg.name,reg.phone,reg.password,reg.email,tk)
            userRepo.save(k)
            return k.email.toString()
        }
    }






    @PostMapping("/register")
    fun uploadMultipartFile(
            //@RequestParam("uploadfile") file: MultipartFile,
            //model: Model,
            //@RequestParam ("name") name:String,
            //@RequestParam ("phone") phone:String,
            //@RequestParam ("password") password:String,
            //@RequestParam ("bank") bank:String,
            //@RequestParam ("email") email:String,
    @RequestBody register:reg
    ): uni {




        var num = userRepo.getByPhone(register.phone)

        return try
        {
            if (num!!.name!!.isEmpty())
            {
                var tk = token(register.phone!!)
                var k = City_User(0,register.name,register.phone,register.password,register.email,register.phone+".png",register.bank,tk)
                userRepo.save(k)
                //fileStorage.store(file,phone+".png")
                //model.addAttribute("message", "File uploaded successfully! -> filename = " + file.originalFilename)
                var name = uni(tk)
                return name
            }
            else
            {
                var name = uni( "User already exists")
                return name
            }
        }
        catch (e: Exception)
        {
            var tk = token(register.phone!!)
            var k = City_User(0,register.name,register.phone,register.password,register.email,register.phone+".png",register.bank,tk)
            userRepo.save(k)
            //fileStorage.store(file,phone+".png")
            //model.addAttribute("message", "File uploaded successfully! -> filename = " + file.originalFilename)
            var name = uni(tk)
            return name
        }
    }



    @PostMapping("/productpost")
    fun product_post(
            @RequestParam("uploadfile") file: MultipartFile,
            model: Model,
            @RequestParam ("product_name") product_name:String,
            @RequestParam ("product_des") product_des:String,
            @RequestParam ("product_price") product_price:String,
            @RequestParam ("token") token:String
    ){
        var user = userRepo.getByToken(token)
        var ts = token(user!!.phone!!)+".png"
        var pr = productRepo.save(City_Product(0,user!!.id_user.toString(),product_name,product_des,product_price,user.name,user.phone))
        picRepo.save(City_Product_Picture(0,ts,pr.id_product.toString()))
        fileStorage.store(file,ts)
        model.addAttribute("message", "File uploaded successfully! -> filename = " + file.originalFilename)
    }

    @PostMapping("/productpicpost")
    fun product_pic_post(
            @RequestParam("uploadfile") file: MultipartFile,
            model: Model,
            @RequestParam ("product_id") product_id:String,
            @RequestParam ("token") token:String
    )
    {
        if (token_varify(token))
        {
            var user = userRepo.getByToken(token)
            var ts = token(user!!.phone!!)+".png"
            fileStorage.store(file,ts)
            picRepo.save(City_Product_Picture(0,ts,product_id))
            model.addAttribute("message", "File uploaded successfully! -> filename = " + file.originalFilename)
        }
    }






    @PostMapping("/login")
    fun login(@RequestBody lgn: login) : uni
    {
        var k = userRepo.getByPhone(lgn.phone)

        return try
        {
            if (k!!.phone!!.isEmpty())
            {
                var name = uni("user dosent exist")
                return name
            }
            else
            {
                if (lgn.phone==k.phone && lgn.password==k.password)
                {
                    var s = userRepo.findById(k.id_user!!)
                    if (s.isPresent)
                    {
                        var data = s.get()
                        var token = token(lgn.phone!!)
                        data.token = token
                        userRepo.save(data)
                        var name = uni(token)
                        return name
                    }
                    else
                    {
                        var name = uni("user dosent exist")
                        return name
                    }
                }
                else
                {
                    var name = uni("password didnt match")
                    return name
                }
            }
        }
        catch (e:Exception)
        {
            var name = uni("user dosent exist")
            return name
        }
    }



    @PostMapping("/profile")
    fun profile(@RequestParam ("token") token:String): City_User {
        return if (token_varify(token))
        {
            userRepo.getByToken(token)!!
        }
        else
        {
            City_User(null,null,null,null,null,null,null,null)
        }
    }

    @PostMapping("/user")
    fun user(
            @RequestParam("token") token: String,
            @RequestParam("user") user: String
    ): City_User {
        return if (token_varify(token))
        {
            userRepo.getById(user.toLong())!!
        }
        else
        {
            return City_User(null,null,null,null,null,null,null,null)
        }
    }






















data class sinthy (
            var saima:String,
            var sinthy:String,
            var sharon:String
        )

    @PostMapping("/max")
    fun max(@RequestBody sharon:sinthy ): String {
        return sharon.saima+sharon.sharon+sharon.sinthy
    }


















    @GetMapping("/delete")
    fun delete():String
    {
        //userRepo.deleteAll()
        var user = userRepo.findAll()
        var pic = picRepo.findAll()
        productRepo.deleteAll()
        picRepo.deleteAll()

        user.map {
            fileStorage.delete(it.user_picture!!)
            userRepo.delete(it)
        }
        pic.map {
            fileStorage.delete(it.product_url!!)
        }
        return "delete"
    }

    fun token_varify(token:String):Boolean
    {
        val k = "" // userRepo.getByToken(token)

        try {
            return  true//!k!!.phone.toString().isEmpty()
        }catch (e:Exception)
        {
            return false
        }
    }

    fun token(number:String):String
    {
        var tk = LocalDateTime.now().toString()
        tk = tk.replace(",","")
        tk = tk.replace(".","")
        tk = tk.replace(":","")
        tk = tk.replace("-","")
        tk = tk+number+tk
        return tk
    }


}