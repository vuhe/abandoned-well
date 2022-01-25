package top.vuhe.admin.api.file.info

import top.vuhe.admin.api.file.FileInfo

class AliyunFileInfo(bucketName: String): FileInfo {
    override val dirName: String = "AliyunOSS:$bucketName"
    override val sysTypeName: String = "阿里云OSS"
    override val typeName: String = "Ali OSS Drive"
    override val total: String = "请访问阿里云控制台查询"
    override val free: String = "请访问阿里云控制台查询"
    override val used: String = "请访问阿里云控制台查询"
    override val usage: String = "请访问阿里云控制台查询"
}
