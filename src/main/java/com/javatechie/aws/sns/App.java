package com.javatechie.aws.sns;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.autoconfigure.context.ContextRegionProviderAutoConfiguration;
import org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(exclude = {ContextStackAutoConfiguration.class, ContextRegionProviderAutoConfiguration.class})
@RestController
public class App {

    @Autowired
    private AmazonSNSClient snsClient;

    String TOPIC_ARN = "arn:aws:sns:us-east-1:116872392176:snsTopicPoc";

    @GetMapping("/sendNotification")
    public String publishMessageToTopic() {
        PublishRequest publishRequest = new PublishRequest(TOPIC_ARN, buildEmailBody(), "Notificação: Falha após retentativas");
        snsClient.publish(publishRequest);
        return "Notificação enviada com sucesso !!";
    }

    private String buildEmailBody() {
        return "Time,\n" +
                "\n" +
                "\n" +
                "Houve um erro no fluxo de convivência." + "\n" +
                "CorrelationID: a777feff-e20b-4841-9d86-a9d2cddf3e0f \n" +
                "Para mais detalhes consultar splunk.";
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
