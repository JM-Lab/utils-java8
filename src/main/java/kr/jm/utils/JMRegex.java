package kr.jm.utils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.rangeClosed;
import static kr.jm.utils.helper.JMLambda.getSelf;

public class JMRegex {
    private List<String> groupNameList;
    private Pattern pattern;

    public JMRegex(String regex) {
        this(regex, 0);
    }

    public JMRegex(String regex, int flag) {
        this.groupNameList = getMatchedPartList(
                Pattern.compile("\\(\\?\\<\\w+\\>").matcher(regex)).stream()
                .map(s -> s.substring(s.indexOf('<') + 1, s.indexOf('>')))
                .collect(toList());
        this.pattern = flag == 0 ? Pattern.compile(regex) : Pattern
                .compile(regex, flag);
    }

    public Pattern getPattern() {
        return pattern;
    }

    public List<String> getGroupNameList() {
        return groupNameList;
    }

    @Override
    public String toString() {
        return "JMRegex{" + "pattern=" + pattern + '}';
    }

    public boolean isMatchedWithPart(String
            targetString) {
        return pattern.matcher(targetString).lookingAt();
    }


    public boolean isMatchedWithEntire(String
            targetString) {
        return pattern.matcher(targetString).matches();
    }

    public List<String> getMatchedPartList(String targetString) {
        return getMatchedPartList(pattern.matcher(targetString));
    }

    private List<String> getMatchedPartList(Matcher matcher) {
        List<String> matchedList = new ArrayList<>();
        while (matcher.find())
            matchedList.add(matcher.group());
        return matchedList;
    }

    public List<String> getMatchedListByGroup(String targetString) {
        return getMatcherAsOpt(targetString)
                .map(matcher -> rangeClosed(1, matcher.groupCount())
                        .mapToObj(matcher::group).map(Object::toString)
                        .collect(toList())).orElseGet(Collections::emptyList);
    }

    public Map<String, String> getGroupNameValueMap(String targetString) {
        return getMatcherAsOpt(targetString)
                .map(matcher -> groupNameList.stream()
                        .collect(Collectors.toMap(getSelf(), matcher::group)))
                .orElseGet(Collections::emptyMap);
    }

    private Optional<Matcher> getMatcherAsOpt(String targetString) {
        return Optional.of(pattern.matcher(targetString))
                .filter(Matcher::matches);
    }
}
