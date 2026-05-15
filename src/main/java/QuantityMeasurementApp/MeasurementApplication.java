package QuantityMeasurementApp;

import com.quantity.measurement.controller.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.quantity.measurement.controller.Controller;
import com.quantity.measurement.repository.Repository;
import com.quantity.measurement.repositoryImpl.DatabaseRepository;
import com.quantity.measurement.serviceImpl.ServiceImpl;

@SpringBootApplication
public class MeasurementApplication {

	private static final Logger LOGGER =
			LoggerFactory.getLogger(MeasurementApplication.class);

	public static void main(String[] args) {

		SpringApplication.run(
				MeasurementApplication.class,
				args
		);

		Repository repository =
				new DatabaseRepository();

		var service =
				new ServiceImpl(repository);

		var controller =
				new Controller(service);

		// Example
		var result = controller.performAdd(
				new com.quantity.measurement.dto.QuantityDTO(
						1.0,
						"FEET",
						"LENGTH"
				),
				new com.quantity.measurement.dto.QuantityDTO(
						12.0,
						"INCH",
						"LENGTH"
				),
				"FEET"
		);

		LOGGER.info(
				"Result Value: {}",
				result.getValue()
		);
	}
}