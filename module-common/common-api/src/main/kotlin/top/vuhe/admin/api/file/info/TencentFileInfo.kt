package top.vuhe.admin.api.file.info

import top.vuhe.admin.api.file.FileInfo

class TencentFileInfo(bucketName: String) : FileInfo {
    override val dirName: String = "TencentOSS:$bucketName"
    override val sysTypeName: String = "腾讯云OSS"
    override val typeName: String = "Tencent OSS Drive"
    override val total: String = "请访问阿里云控制台查询"
    override val free: String = "请访问阿里云控制台查询"
    override val used: String = "请访问阿里云控制台查询"
    override val usage: String = "请访问阿里云控制台查询"
}
