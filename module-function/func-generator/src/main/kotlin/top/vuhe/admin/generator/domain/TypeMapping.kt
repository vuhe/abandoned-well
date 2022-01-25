package top.vuhe.admin.generator.domain

/**
 * 类型映射
 *
 * @author vuhe
 */
enum class TypeMapping(
    val funcType: String, val ktType: String,
    val import: String = "", val default: String = "null"
) {
    Boolean("boolean", "Boolean?"),
    Int("int", "Int?"),
    Short("short", "Short?"),
    Long("long", "Long?"),
    Float("float", "Float?"),
    Double("double", "Double?"),
    Decimal("decimal", "BigDecimal?", import = "java.math.BigDecimal"),
    Varchar("varchar", "String", default = "\"\""),
    Text("text", "String", default = "\"\""),
    Blob("blob", "ByteArray", default = "ByteArray(0)"),
    Bytes("bytes", "ByteArray", default = "ByteArray(0)"),
    Timestamp("timestamp", "Instant?", import = "java.time.Instant"),
    Datetime("datetime", "LocalDateTime?", import = "java.time.LocalDateTime"),
    Date("date", "LocalDate?", import = "java.time.LocalDate"),
    Time("time", "Time?", import = "java.time.Time"),
    MonthDay("monthDay", "MonthDay?", import = "java.time.MonthDay"),
    YearMonth("yearMonth", "YearMonth?", import = "java.time.YearMonth"),
    Year("yearMonth", "Year?", import = "java.time.Year");
}
