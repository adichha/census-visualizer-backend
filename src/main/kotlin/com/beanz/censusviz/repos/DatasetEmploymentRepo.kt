package com.beanz.censusviz.repos

import com.beanz.censusviz.records.DDatasetDoubleCombinedRecord
import com.beanz.censusviz.records.DDatasetRecord
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import javax.persistence.Table

@Table(name = "employment")
interface DatasetEmploymentRepo : CrudRepository<DDatasetRecord, Int> {

    @Query("SELECT SUM(e.value) / SUM(p.value) as count, g.lat, g.lon FROM employment e NATURAL JOIN geocode_lut g, population p WHERE e.meta IN :metas AND e.age IN :ages AND e.sex = :sex AND e.geocode = g.geocode AND p.geocode = g.geocode GROUP BY g.geocode, g.lon, g.lat", nativeQuery = true)
    fun findAllByAgeInAndSexAndMetaIn(@Param("ages") ages: List<Int>, @Param("sex") sex: Int, @Param("metas") metas: List<Int>): List<DDatasetDoubleCombinedRecord>

}