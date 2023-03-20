package com.anningtex.networkrequestchild.demo.anlogLogin

/**
 * @Author Song
 */
data class LoginAnLogEntity(
    var role: String? = null,
    var menu: MenuBean? = null,
    var isSimplePWD: Int,
    var token: String? = null,
    var isHeader: String? = null,
    var countryID: String? = null,
    var userName: String? = null
)

data class MenuBean(
    var module: List<ModuleBean>? = null, var urlList: List<*>? = null
)

data class ModuleBean(
    val name: String? = null, val id: Int
)