package com.dn.scanner.log.util;

import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dn.scanner.log.dto.GetRendering;
import com.dn.scanner.log.dto.Rendering;
import com.dn.scanner.log.dto.StartRendering;
import com.dn.scanner.log.dto.DocumentRequest;

public class MatcherUtils {
	
	public static final Pattern RENDERING = Pattern.compile("(\\S* \\S*) \\[(.*)\\] .* \\[.*arguments \\[(.*), (.*)\\] .*");
	public static final Pattern UID_START = Pattern.compile("(\\S* \\S*) \\[(.*)\\] .* Service startRendering returned (.*).*");
	public static final Pattern UID_GET = Pattern.compile(".* Executing request getRendering with arguments \\[(.*)\\].*");
	public static final Pattern GET_RENDERIG = Pattern.compile("(\\S* \\S*)" + UID_GET);
	
	public static Optional<StartRendering> getStartRendering(String line) {
		return createOnMatch(line, UID_START, matcher -> new StartRendering(matcher.group(1), matcher.group(2), matcher.group(3)) );
	}

	public static Optional<String> getUidGet(String line) {
		return createOnMatch(line, UID_GET, matcher -> matcher.group(1));
	}
	
	public static Optional<GetRendering> getGetRendering(String line) {
		return createOnMatch(line, GET_RENDERIG, matcher ->  new GetRendering(matcher.group(1), matcher.group(2)));
	}

	public static Optional<DocumentRequest> getDocumentRequest(String line) {
		return createOnMatch(line, RENDERING,
				matcher -> new DocumentRequest(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4)));
	}

	
	private static <T> Optional<T> createOnMatch(String line, Pattern pattern, Function<Matcher, T> constructor) {
		Matcher matcher = pattern.matcher(line);
		return matcher.matches() ? Optional.of(constructor.apply(matcher)) : Optional.empty();
	}


}
