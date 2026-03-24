rootProject.name = "ticketport"

include("bootstrap","security",
    "user-domain","user-application","user-infra","user-api",
    "event-domain","event-application")
project(":security").projectDir = file("common/security")

project(":user-domain").projectDir = file("context/user/user-domain")
project(":user-application").projectDir = file("context/user/user-application")
project(":user-infra").projectDir = file("context/user/user-infra")
project(":user-api").projectDir = file("context/user/user-api")

project(":event-domain").projectDir = file("context/event/event-domain")
project(":event-application").projectDir = file("context/event/event-application")