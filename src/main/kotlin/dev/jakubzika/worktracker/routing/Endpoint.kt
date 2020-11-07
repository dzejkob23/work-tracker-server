package dev.jakubzika.worktracker.routing

enum class Endpoint {

    /*********/
    /** API **/
    /*********/

    API_VERSION_V1 {
        override val url: String
            get() = "/v1"
    },
    USER {
        override val url: String
            get() = "${API_VERSION_V1.url}/user"
    },
    USER_LOGIN {
        override val url: String
            get() = "${API_VERSION_V1.url}/user/login"
    },
    USER_CREATE {
        override val url: String
            get() = "${API_VERSION_V1.url}/user/create"
    },
    USER_FIND {
        override val url: String
            get() = "${API_VERSION_V1.url}/user/find"
    },
    PROJECT {
        override val url: String
            get() = "${API_VERSION_V1.url}/project"
    },

    /*********/
    /** WEB **/
    /*********/

    HOME {
        override val url: String
            get() = "/"
    },
    LOGIN {
        override val url: String
            get() = "/login"
    },
    REGISTRATION {
        override val url: String
            get() = "/registration"
    },
    LOGOUT {
        override val url: String
            get() = "/logout"
    },
    WORK_SESSION_START {
        override val url: String
            get() = "/worksession/start"
    },;

    abstract val url: String

}