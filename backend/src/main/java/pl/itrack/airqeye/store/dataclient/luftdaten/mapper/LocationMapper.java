package pl.itrack.airqeye.store.dataclient.luftdaten.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.itrack.airqeye.store.measurement.domain.model.Location;
import pl.itrack.airqeye.store.measurement.infrastructure.mapper.DefaultMapperConfig;

@Mapper(config = DefaultMapperConfig.class)
interface LocationMapper {

  @Mapping(target = "elevation", source = "altitude")
  Location fromDto(pl.itrack.airqeye.store.dataclient.luftdaten.model.Location location);
}
