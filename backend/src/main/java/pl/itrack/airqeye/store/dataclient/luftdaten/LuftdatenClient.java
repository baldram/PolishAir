package pl.itrack.airqeye.store.dataclient.luftdaten;

import java.util.Collections;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.itrack.airqeye.store.dataclient.luftdaten.model.LuftdatenMeasurement;

@Component
@FeignClient(name = "feeder-luftdaten",
    decode404 = true,
    url = "${feeder-luftdaten.url}")
public interface LuftdatenClient {

  @RequestMapping(value = "/static/v2/data.dust.min.json",
      method = RequestMethod.GET,
      produces = {
          MediaType.APPLICATION_JSON_VALUE
      })
  ResponseEntity<List<LuftdatenMeasurement>> retrieveData();

  default List<LuftdatenMeasurement> getMeasurements() {
    return this.retrieveData() != null ? this.retrieveData().getBody() : Collections.emptyList();
  }
}
