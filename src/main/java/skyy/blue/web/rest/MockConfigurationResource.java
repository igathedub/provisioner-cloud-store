

//package skyy.blue.web.rest;
//
//import lombok.extern.slf4j.Slf4j;
//import lombok.extern.slf4j.XSlf4j;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import skyy.blue.exportobjects.*;
//
//import java.util.List;
//
//import static java.util.Collections.singletonList;
//
///**
// * REST controller for managing {@link Element}.
// */
//@RestController
//@RequestMapping("/api")
//public class ConfigurationResource {
//
//    private final Logger log = LoggerFactory.getLogger(ConfigurationResource.class);
//
//    @PostMapping("/unprovisionednodes")
//    public ResponseEntity<Boolean> createProvisioners(List<Node> nodesToSave) {
//        log.debug(nodesToSave.toString());
//        return ResponseEntity.ok().body(true);
//    }
//
//    @GetMapping(value = "/configuration", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Configuration> getConfiguration() {
//        log.debug("REST request to get Configuration");
//        return ResponseEntity.ok().body(buildMockConfiguration());
//    }
//
//
//    private final List<Provisioner> provisioners = singletonList(Provisioner.builder()
//        .allocatedGroupRange(createRange("C000", "CC9A"))
//        .allocatedUnicastRange(createRange("0001", "199A"))
//        .uUID("0AD9BA0E33204AE5B9D29EEAF12D7808")
//        .provisionerName("Provisioner iPhone")
//        .allocatedSceneRange(createRange("3333", "0001")).build());
//
//    private final Element publishingElement = createActiveElement(0,"0000","Primary Element", null,"1001");
//
//    private final Element subscribingElement = createActiveElement(0,"0001","Primary Element", null,"1000");
//
//    private final List<Node> nodes = singletonList(Node.builder()
//        .features(Features.builder().proxy(2).friend(2).relay(2).lowPower(2).build())
//        .elements(List.of(subscribingElement,publishingElement))
//        .unicastAddress("0003")
//        .configComplete(false)
//        .netKeyIndices(singletonList(KeyIndex.builder().index(0L).updated(false).build()))
//        .defaultTTL(4L)
//        .vid("0000")
//        .cid("0059")
//        .appKeyIndices(singletonList(KeyIndex.builder().index(0L).updated(false).build()))
//        .blacklisted(false)
//        .uUID("1E6DB0B19E5EFE428F25C9F9D3FE766D")
//        .security("high")
//        .crpl("0028")
//        .pid("0000")
//        .name("Device56")
//        .deviceKey("2354471FCFAAD9AE8D538C3510C737FE")
//        .build());
//
//    private Configuration buildMockConfiguration() {
//        //TODO build from DB Objects
//        return Configuration.builder()
//            .meshUUID("1277B6E59E654DCE9C25220F2BA35A3B")
//            .provisioners(provisioners)
//            .nodes(nodes)
//            .build();
//    }
//
//    private static List<AllocatedRange> createRange(String low, String high) {
//        return singletonList(AllocatedRange.builder().lowAddress(low).highAddress(high).build());
//    }
//
//    private static Element createActiveElement(int index, String location, String name, String modelSubscribed, String modelPublishes) {
//        Element.ElementBuilder<?, ?> elementBuilder = Element.builder().index(index).location(location).name(name);
//        if (modelSubscribed != null) {
//            elementBuilder.models(singletonList(Model.builder()
//                .bind(singletonList(0L))
//                .subscribe(singletonList(modelSubscribed)).build()));
//        }
//        if (modelPublishes != null) {
//            elementBuilder.models(singletonList(Model.builder()
//                .bind(singletonList(0L))
//                .publish(Publish.builder()
//                    .index(0)
//                    .credentials(0)
//                    .ttl(255)
//                    .retransmit(Retransmit.builder()
//                        .count(0L)
//                        .interval(50L)
//                        .build())
//                    .address("C000")
//                    .period(0)
//                    .build()
//                ).build()));
//        }
//
//        return elementBuilder.build();
//    }
//    //.models(Collections.un createModelWithSubscription()).build();
//
//
//
//}
