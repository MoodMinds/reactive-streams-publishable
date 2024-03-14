# [Project Reactor](https://projectreactor.io) implementation of the [Reactive Streams](https://github.com/MoodMinds/reactive-streams)

Wrappers around the [Project Reactor](https://projectreactor.io)'s `Flux` and `Mono` adapting to the `Publishable` (`FluxPublishable` and `MonoPublishable`)
extension of [Reactive Streams](https://github.com/MoodMinds/reactive-streams)' `SubscribeSupport`.

## Usage example

```java
import org.moodminds.reactive.FluxPublishable;
import org.moodminds.reactive.MonoPublishable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.moodminds.reactive.FluxPublishable.flux;
import static org.moodminds.reactive.MonoPublishable.mono;

class Sample {

    void sample() {
        Mono<String> mono = Mono.just("a");
        Flux<String> flux = Flux.just("a", "b", "c");

        MonoPublishable<String, ?> monoPublishable = mono(mono);
        FluxPublishable<String, ?> fluxPublishable = flux(flux);
    }
}
```

## Maven configuration

Artifacts can be found on [Maven Central](https://search.maven.org/) after publication.

```xml
<dependency>
    <groupId>org.moodminds.reactive</groupId>
    <artifactId>reactive-streams-publishable</artifactId>
    <version>${version}</version>
</dependency>
```

## Building from Source

You may need to build from source to use **Reactive Streams Publishable** (until it is in Maven Central) with Maven and JDK 1.8 at least.

## License
This project is going to be released under version 2.0 of the [Apache License][l].

[l]: https://www.apache.org/licenses/LICENSE-2.0