package com.citybank.citybuy.Interfaces

import com.citybank.citybuy.Data.City_Rating
import org.springframework.data.jpa.repository.JpaRepository

interface Rating_Repository: JpaRepository<City_Rating, Long> {
}