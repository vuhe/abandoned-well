rootProject.name = "WellManagement"

include(":common-api")
include(":common-spring")

include(":func-schedule")
include(":func-system")
include(":func-well")

include(":module-starter")


project(":common-api").projectDir = file("$rootDir/module-common/common-api")
project(":common-spring").projectDir = file("$rootDir/module-common/common-spring")

project(":func-schedule").projectDir = file("$rootDir/module-function/func-schedule")
project(":func-system").projectDir = file("$rootDir/module-function/func-system")
project(":func-well").projectDir = file("$rootDir/module-function/func-well")

project(":module-starter").projectDir = file("$rootDir/module-starter")
