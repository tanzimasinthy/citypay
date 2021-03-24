package com.citybank.citybuy.Interfaces

import com.citybank.citybuy.Data.City_Product
import com.citybank.citybuy.Data.City_Product_Picture
import org.springframework.data.jpa.repository.JpaRepository

interface ProductPicture_Repository: JpaRepository<City_Product_Picture, Long> {
}