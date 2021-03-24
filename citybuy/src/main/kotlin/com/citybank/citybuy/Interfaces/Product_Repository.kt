package com.citybank.citybuy.Interfaces

import com.citybank.citybuy.Data.City_Product
import org.springframework.data.jpa.repository.JpaRepository

interface Product_Repository: JpaRepository<City_Product, Long> {
}