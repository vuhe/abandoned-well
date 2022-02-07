package top.vuhe.admin.well.mapper

import org.ktorm.dsl.*
import org.ktorm.schema.*
import org.springframework.stereotype.Repository
import top.vuhe.admin.spring.database.mapper.CurdMapper
import top.vuhe.admin.well.domina.WellRegion

/**
 * 井区域接口
 *
 * @author vuhe
 */
@Repository
class RegionMapper : CurdMapper<WellRegion>("well_region") {
    override val id = varchar("id").primaryKey().bind(WellRegion::id)
    private val city = varchar("city").bind(WellRegion::city)
    private val county = varchar("county").bind(WellRegion::county)
    private val districtCode = varchar("district_code").bind(WellRegion::districtCode)
    private val regionCodeId = varchar("region_code").bind(WellRegion::regionCodeId)
    private val sort = int("sort").bind(WellRegion::sort, 0)
    private val next = int("count").bind(WellRegion::next, 0)

    override fun Query.listFilter(param: WellRegion): Query {
        return whereWithConditions {
            if (param.city.isNotEmpty()) it.add(city like "%${param.city}%")
            if (param.county.isNotEmpty()) it.add(county like "%${param.county}%")
        }
    }
}
