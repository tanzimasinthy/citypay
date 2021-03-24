package com.citybank.citybuy.Interfaces

import com.citybank.citybuy.Data.City_User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface User_Repository:JpaRepository<City_User, Long> {

    @Query("select  p from City_User p where p.phone  = :phone")
    fun getByPhone(@Param("phone") phone: String?): City_User?

    @Query("select  p from City_User  p where p.token  = :token")
    fun getByToken(@Param("token") token: String): City_User?

    @Query("select  p from City_User  p where p.id_user  = :id")
    fun getById(@Param("id") id: Long): City_User?

}