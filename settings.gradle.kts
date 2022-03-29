rootProject.name = "WellManagement"

include(":common-api")
include(":common-spring")

include(":func-system")
include(":func-well")


project(":common-api").projectDir = file("$rootDir/module-common/common-api")
project(":common-spring").projectDir = file("$rootDir/module-common/common-spring")

project(":func-system").projectDir = file("$rootDir/module-function/func-system")
project(":func-well").projectDir = file("$rootDir/module-function/func-well")
