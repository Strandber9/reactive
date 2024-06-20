package com.example.reactive;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ReactiveApplicationTests {

	@Test
	void contextLoads() {
	}

	package com.example.spel;

import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Regex {

    public static String convertMaskString(String target, String regex, String maskingGourp) {
        List<Integer> results = Pattern.compile("\\$\\d+").matcher(maskingGourp).results()
                .map(MatchResult::group)
                .map(s -> Integer.valueOf(s.substring(1)))
                .collect(Collectors.toList());
        
        Matcher matcher = Pattern.compile(regex).matcher(target);
        if (matcher.find()) {
            target = IntStream.rangeClosed(1, matcher.groupCount())
                .boxed()
                .map(groupIndex -> results.contains(groupIndex) 
                    ? "*".repeat(matcher.group(groupIndex).length()) 
                    : matcher.group(groupIndex))
                .collect(Collectors.joining());
        }
        return target;
    }

    public static void main(String[] args) {
        String input = "123456789012345678901";
        input = "1234567890123456";
        String regex = "(\\d{4})(\\d{4,7})(\\d{4})(\\d{4,6})";
        String maskingGourp = "$2$4";

        regex = "(.{1})(.*)(.{1})";
        maskingGourp = "$2";

        // regex = "(.*)";
        // maskingGourp = "$1";

        
        System.out.println(convertMaskString(input, regex, maskingGourp));
        
        String json = "{              \"cardNo\"          :                      \"1234567890123456\", \"test\" : \"최영영민\" }";
        Matcher matcher = Pattern.compile("(?<=\"(cardNo|test)\"\\s{0,32}:\\s{0,32}\")(.*?)(?=\")").matcher(json);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String key = matcher.group(1);
            String target = matcher.group(2);

            log.info("{}: \"{}\"", key, target);

            matcher.appendReplacement(sb, convertMaskString(target, regex, maskingGourp));
        }
        matcher.appendTail(sb);

        System.out.println(sb);
    }
}


}
