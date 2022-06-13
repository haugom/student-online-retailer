package student.onlineretailer

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

interface ResourceBean {
    fun GetDb(): String
    fun GetLogs(): String
    fun GetSecure(): Boolean
}

@Component
@ConfigurationProperties(prefix="resources")
class ResourceBeanImpl : ResourceBean {
    lateinit var db: String
    lateinit var logs: String
    var secure: Boolean = false

    override fun GetDb(): String {
        return this.db
    }

    override fun GetLogs(): String {
        return this.logs
    }

    override fun GetSecure(): Boolean {
        return this.secure
    }
}
