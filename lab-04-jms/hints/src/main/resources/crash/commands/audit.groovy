package crash.commands

import com.lse.spring.example.jms.AuditClient
import org.crsh.cli.*
import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.annotation.Configurable

@Configurable
@Usage("provide a way send an audit message event")
class audit {

    @Command
    Object main(
            @Man ("audit \"<message>\" ")
            @Usage("the audit message to send, use quotes for whitespace.")
            @Argument String message) {
        if (message == null) {
            message = "<empty>";
        }
        AuditClient client = fetchClient();
        client.audit(message);
        return "sent";
    }

    AuditClient fetchClient() {
        BeanFactory beanFactory =
                context.attributes['spring.beanfactory']
        try {
            return beanFactory.getBean(AuditClient)
        } catch (Exception) {
            return null;
        }
    }
}