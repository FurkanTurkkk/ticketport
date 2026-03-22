rootProject.name = "ticketport"

include("exception","bootstrap","user-domain","user-application","user-infra")

project(":exception").projectDir = file("common/exception")

project(":user-domain").projectDir = file("context/user/user-domain")
project(":user-application").projectDir = file("context/user/user-application")
project(":user-infra").projectDir = file("context/user/user-infra")
