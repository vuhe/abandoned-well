package top.vuhe.admin.api.file

interface FileInfo {
    /**
     * 盘符路径
     */
    val dirName: String

    /**
     * 盘符类型
     */
    val sysTypeName: String

    /**
     * 文件类型
     */
    val typeName: String

    /**
     * 总大小
     */
    val total: String

    /**
     * 剩余大小
     */
    val free: String

    /**
     * 已经使用量
     */
    val used: String

    /**
     * 资源的使用率
     */
    val usage: String
}
