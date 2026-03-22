rootProject.name = "ticketport"

include("bootstrap","user-domain","user-application","user-infra","user-api")

project(":user-domain").projectDir = file("context/user/user-domain")
project(":user-application").projectDir = file("context/user/user-application")
project(":user-infra").projectDir = file("context/user/user-infra")
project(":user-api").projectDir = file("context/user/user-api")
