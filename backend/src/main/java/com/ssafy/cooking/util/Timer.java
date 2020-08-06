package com.ssafy.cooking.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Timer {
	public static String getTimer(String description) {
		List<int[]> timer = new LinkedList<>();

		description = description.replaceAll(" ", "");

		int length = description.length();
		boolean[] visit = new boolean[length];

		match("[0-9][0-9]분", "[0-9][0-9]분[0-9][0-9]초", description, timer, 60, 60, visit, false);
		match("[0-9]분", "[0-9]분[0-9][0-9]초", description, timer, 60, 60, visit, false);
		match("[0-9][0-9]분", "[0-9][0-9]분", description, timer, 60, 60, visit, false);
		match("[0-9]분", "[0-9][0-9]분", description, timer, 60, 60, visit, false);
		match("[0-9]분", "[0-9]분", description, timer, 60, 60, visit, false);
		match("[0-9][0-9]", "[0-9][0-9]분", description, timer, 60, 60, visit, false);
		match("[0-9]", "[0-9][0-9]분", description, timer, 60, 60, visit, false);
		match("[0-9]", "[0-9]분", description, timer, 60, 60, visit, false);
		match("[0-9][0-9]초", "[0-9][0-9]초", description, timer, 1, 1, visit, false);
		match("[0-9]초", "[0-9][0-9]초", description, timer, 1, 1, visit, false);
		match("[0-9]초", "[0-9]초", description, timer, 1, 1, visit, false);
		match("[0-9][0-9]", "[0-9][0-9]초", description, timer, 1, 1, visit, false);
		match("[0-9]", "[0-9][0-9]초", description, timer, 1, 1, visit, false);
		match("[0-9]", "[0-9]초", description, timer, 1, 1, visit, false);
		match("[0-9][0-9]초", "[0-9]분", description, timer, 1, 60, visit, false);
		match("[0-9][0-9]분", "[0-9]시간", description, timer, 60, 3600, visit, false);
		match("[0-9]시간", "[0-9]시간[0-9][0-9]분", description, timer, 3600, 3600, visit, false);
		match("[0-9][0-9]시간", "[0-9][0-9]시간", description, timer, 3600, 3600, visit, false);
		match("[0-9][0-9]", "[0-9][0-9]시간", description, timer, 3600, 3600, visit, false);
		match("[0-9]", "[0-9][0-9]시간", description, timer, 3600, 3600, visit, false);
		match("[0-9][0-9]", "[0-9]시간", description, timer, 60, 3600, visit, false);
		match("[0-9]", "[0-9]시간", description, timer, 3600, 3600, visit, false);

		match("[0-9]시간", "[0-9][0-9]분", description, timer, 3600, 60, visit, true);
		match("[0-9]분", "[0-9][0-9]초", description, timer, 60, 1, visit, true);
		match("[0-9][0-9][0-9]분", null, description, timer, 60, 0, visit, false);
		match("[0-9][0-9]분", null, description, timer, 60, 0, visit, false);
		match("[0-9]분", null, description, timer, 60, 0, visit, false);
		match("[0-9][0-9][0-9]여분", null, description, timer, 60, 0, visit, false);
		match("[0-9][0-9]여분", null, description, timer, 60, 0, visit, false);
		match("[0-9]여분", null, description, timer, 60, 0, visit, false);
		match("[0-9][0-9][0-9]초", null, description, timer, 1, 0, visit, false);
		match("[0-9][0-9]초", null, description, timer, 1, 0, visit, false);
		match("[0-9]초", null, description, timer, 1, 0, visit, false);
		match("[0-9][0-9][0-9]여초", null, description, timer, 1, 0, visit, false);
		match("[0-9][0-9]여초", null, description, timer, 1, 0, visit, false);
		match("[0-9]여초", null, description, timer, 1, 0, visit, false);
		match("[0-9][0-9]시간", null, description, timer, 3600, 0, visit, false);
		match("[0-9]시간", null, description, timer, 3600, 0, visit, false);

		Collections.sort(timer, new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				return Integer.compare(o1[0], o2[0]);
			}
		});

		return toJson(timer);
	}

	private static String match(String regex1, String regex2, String input, List<int[]> timer, int m1, int m2,
			boolean[] visit, boolean t) {
		Matcher matcher;
		if (regex2 != null) {
			matcher = Pattern.compile(regex1 + "[~|-]" + regex2).matcher(input);
			top: while (matcher.find()) {
				for (int i = matcher.start(); i < matcher.end(); i++) {
					if (visit[i]) {
						continue top;
					}
				}

				int time = (convert(regex1, input, matcher.start(), m1, false)
						+ convert(regex2, input, matcher.end(), m2, true)) / 2;
				timer.add(new int[] { matcher.start(), time });
				for (int i = matcher.start(); i < matcher.end(); i++) {
					visit[i] = true;
				}
			}

			matcher = Pattern.compile(regex1 + "에서" + regex2).matcher(input);
			top: while (matcher.find()) {
				for (int i = matcher.start(); i < matcher.end(); i++) {
					if (visit[i]) {
						continue top;
					}
				}

				int time = (convert(regex1, input, matcher.start(), m1, false)
						+ convert(regex2, input, matcher.end(), m2, true)) / 2;
				timer.add(new int[] { matcher.start(), time });
				for (int i = matcher.start(); i < matcher.end(); i++) {
					visit[i] = true;
				}
			}
			if (t) {
				matcher = Pattern.compile(regex1 + regex2).matcher(input);
				top: while (matcher.find()) {
					for (int i = matcher.start(); i < matcher.end(); i++) {
						if (visit[i]) {
							continue top;
						}
					}

					int time = (convert(regex1, input, matcher.start(), m1, false)
							+ convert(regex2, input, matcher.end(), m2, true)) / 2;
					timer.add(new int[] { matcher.start(), time });
					for (int i = matcher.start(); i < matcher.end(); i++) {
						visit[i] = true;
					}
				}
			}
		} else {
			matcher = Pattern.compile(regex1).matcher(input);
			top: while (matcher.find()) {
				for (int i = matcher.start(); i < matcher.end(); i++) {
					if (visit[i]) {
						continue top;
					}
				}

				int time = convert(regex1, input, matcher.start(), m1, false);
				timer.add(new int[] { matcher.start(), time });
				for (int i = matcher.start(); i < matcher.end(); i++) {
					visit[i] = true;
				}
			}
		}

		return input;
	}

	private static int convert(String regex, String input, int start, int mul, boolean end) {
		if (end) {
			if (regex.length() < 8) {
				start -= regex.length();
				start += 4;
			} else if (regex.length() < 13) {
				start -= regex.length();
				start += 8;
			} else {
				start -= regex.length();
				start += 12;
			}
		}
		if (regex.length() < 8) {
			return Integer.parseInt(input.substring(start, start + 1).replaceAll("[^0-9]", "")) * mul;
		} else if (regex.length() < 16) {
			return Integer.parseInt(input.substring(start, start + 2).replaceAll("[^0-9]", "")) * mul;
		} else {
			return Integer.parseInt(input.substring(start, start + 3).replaceAll("[^0-9]", "")) * mul;
		}
	}

	private static String toJson(List<int[]> list) {
		String result = "[ ";

		for (int i = 0; i < list.size(); i++) {
			if (i == list.size() - 1) {
				result += list.get(i)[1] + " ";
			} else {
				result += list.get(i)[1] + ", ";
			}
		}

		result += "]";

		return result;
	}
}
