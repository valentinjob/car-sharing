package carsharing.utils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MenuUtils {

    public static <T> List<String> createNumberedOptions(List<T> items, Function<T, String> itemToStringMapper) {
        if (items.isEmpty()) {
            return List.of();
        }

        return Stream.concat(
                IntStream.range(0, items.size())
                        .mapToObj(idx -> String.format("%d. %s", idx + 1, itemToStringMapper.apply(items.get(idx)))),
                Stream.of("0. Back")
        ).collect(Collectors.toList());
    }
}
