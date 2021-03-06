package cmpe282.station.pubsub.inbound;

import static cmpe282.message.Subscriptions.SUB_RESERVATION;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gcp.pubsub.core.PubSubOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.gcp.pubsub.inbound.PubSubInboundChannelAdapter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Component;

import cmpe282.station.pubsub.CmpeSubscriber;
import cmpe282.station.pubsub.msghandler.ReservMsgHandler;

@Component
public class StationSubscriber extends CmpeSubscriber {

    @Bean
    public MessageChannel stationInChannel() {
	return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "stationInChannel")
    public MessageHandler stationMsgReceiver() {
	return new ReservMsgHandler();
    }

    @Bean
    public PubSubInboundChannelAdapter stationChannelAdapter(
	    @Qualifier("stationInChannel") MessageChannel inputChannel,
	    PubSubOperations pubSubTemplate) {
	return buildChannelAdapter(inputChannel, pubSubTemplate, SUB_RESERVATION.name());
    }

}
