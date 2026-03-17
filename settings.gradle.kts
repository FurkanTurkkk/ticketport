rootProject.name = "ticketport"

include("bootstrap")
include("user")
include("user-domain")
include("user-application")
include("exception")

project(":user").projectDir = file("context/user")
project(":user-domain").projectDir = file("context/user/user-domain")
project(":user-application").projectDir = file("context/user/user-application")
project(":exception").projectDir = file("common/exception")
