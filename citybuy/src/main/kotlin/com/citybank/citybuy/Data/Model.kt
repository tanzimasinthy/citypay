package com.citybank.citybuy.Data

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class City_User(
        @Id @GeneratedValue(strategy = GenerationType.AUTO) val id_user: Long? = null,
        var name:String? = null,
        var phone:String? = null,
        var password:String? = null,
        var email:String? = null,
        var user_picture:String? = null,
        var user_bank:String? = null,
        var token:String? = null
)

@Entity
class City_Product(
        @Id @GeneratedValue(strategy = GenerationType.AUTO) val id_product: Long? = null,
        var seller_id:String? = null,
        var product_name:String? = null,
        var product_desc:String? = null,
        var product_price:String? = null,
        var seller_name:String? = null,
        var seller_number:String? = null
)

@Entity
class City_Product_Picture(
        @Id @GeneratedValue(strategy = GenerationType.AUTO) val id_product_picture: Long? = null,
        var product_url:String? = null,
        var id_product:String? = null
)

@Entity
class City_Rating(
        @Id @GeneratedValue(strategy = GenerationType.AUTO) val id_rating: Long? = null,
)