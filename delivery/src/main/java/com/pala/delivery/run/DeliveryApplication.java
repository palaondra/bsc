package com.pala.delivery.run;

import com.pala.delivery.configuration.DeliveryApplicationConf;
import com.pala.delivery.data.Backfill;
import com.pala.delivery.data.BackfillFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.validation.BindingResultUtils;

import javax.validation.Valid;
import java.util.Scanner;

@SpringBootApplication
@Import(DeliveryApplicationConf.class)
public class DeliveryApplication {

	private static Logger LOG = LoggerFactory.getLogger(DeliveryApplication.class);

	public static void main(String[] args) {
		LOG.info("Starting Delivery console application");
		SpringApplication app = new SpringApplicationBuilder()
			.sources(DeliveryApplication.class)
			.web(WebApplicationType.NONE)
			.build();

		LOG.info("Stopping Delivery console application");
		app.run(args).close();
	}

}
