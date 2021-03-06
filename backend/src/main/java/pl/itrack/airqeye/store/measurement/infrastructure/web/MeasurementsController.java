package pl.itrack.airqeye.store.measurement.infrastructure.web;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.itrack.airqeye.store.measurement.domain.enumeration.Feeder;
import pl.itrack.airqeye.store.measurement.domain.model.Measurement;
import pl.itrack.airqeye.store.measurement.domain.service.FeederRegistry;
import pl.itrack.airqeye.store.measurement.domain.service.HasUpdatableDataFeed;
import pl.itrack.airqeye.store.measurement.infrastructure.service.MeasurementServiceAdapter;

@RestController
public class MeasurementsController {

  private static final String URI_MEASUREMENTS = "/measurements";
  private static final String URI_SELECTED_MEASUREMENTS =
      URI_MEASUREMENTS + "/{feeder}/{feederInstallationId}";

  private FeederRegistry feederRegistry;

  private MeasurementServiceAdapter measurementService;

  public MeasurementsController(FeederRegistry feeders, MeasurementServiceAdapter service) {
    this.feederRegistry = feeders;
    this.measurementService = service;
  }

  /**
   * Retrieves and provides data previously persisted in DB from different providers mixed together,
   * to be further displayed and filtered according to user needs.
   *
   * @return the latest measurements from all providers
   */
  @GetMapping(URI_MEASUREMENTS)
  public List<Measurement> getMeasurements() {

    refreshData();
    return measurementService.retrieveMeasurements();
  }

  /**
   * Provides the latest measurements related to given feeder's installation.
   *
   * @param feederInstallationId - feeder's installation id
   * @param feeder               - feeder indication
   * @return the latest measurements related to given installation
   */
  @GetMapping(URI_SELECTED_MEASUREMENTS)
  public List<Measurement> getMeasurement(
      @PathVariable final Long feederInstallationId,
      @PathVariable final Feeder feeder) {

    refreshData();
    return measurementService.retrieveMeasurements(feederInstallationId, feeder);
  }

  // TODO: Data should be separately retrieved and persisted by some background job.
  private void refreshData() {
    feederRegistry.getRegisteredDataClients()
        .forEach(HasUpdatableDataFeed::refreshDataIfRequired);
  }
}
