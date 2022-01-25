rootProject.name = "AdminTemplate"

include(":common-api")
include(":common-spring")

include(":func-generator")
include(":func-schedule")
include(":func-system")

include(":module-starter")


project(":common-api").projectDir = file("$rootDir/module-common/common-api")
project(":common-spring").projectDir = file("$rootDir/module-common/common-spring")

project(":func-generator").projectDir = file("$rootDir/module-function/func-generator")
project(":func-schedule").projectDir = file("$rootDir/module-function/func-schedule")
project(":func-system").projectDir = file("$rootDir/module-function/func-system")

project(":module-starter").projectDir = file("$rootDir/module-starter")
